package service.auth;

import com.github.dockerjava.zerodep.shaded.org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PaymentControllerTest extends DatabaseSuite {

  @Autowired
  private TestRestTemplate rest;

  private ClientAndServer mockServer;

  @BeforeEach
  public void startServer() {
    mockServer = startClientAndServer(8081);
  }

  @AfterEach
  public void stopServer() {
    mockServer.stop();
  }

  @Test
  void shouldPutMoneyWithAuthSuccess() {
    var headers = new HttpHeaders();
    String base64AuthCredentials =
        new String(Base64.encodeBase64("TEST:TEST".getBytes()));
    headers.add("Authorization", "Basic " + base64AuthCredentials);

    new MockServerClient("localhost", 8081)
        .when(
            request()
                .withMethod("POST")
                .withPath("/api/money/put/1/1000")
                .withHeaders(new Header("Accept", "application/json, application/*+json"),
                    new Header("User-Agent", "Java/17.0.9"),
                    new Header("Host", "localhost:8081"),
                    new Header("Connection", "keep-alive"),
                    new Header("Content-type", "application/x-www-form-urlencoded"),
                    new Header("Content-length", "0")))
        .respond(
            response()
                .withStatusCode(200)
                );
    new MockServerClient("localhost", 8081)
        .when(
            request()
                .withMethod("POST")
                .withPath("/api/money/put/1/1000")
                .withHeaders(new Header("Accept", "application/json, application/*+json"),
                    new Header("User-Agent", "Java/17.0.11"),
                    new Header("Host", "localhost:8081"),
                    new Header("Connection", "keep-alive"),
                    new Header("Content-type", "application/x-www-form-urlencoded"),
                    new Header("Content-length", "0")))
        .respond(
            response()
                .withStatusCode(200)
        );

    ResponseEntity<Void> putMoneyWithAuthResponse;
    putMoneyWithAuthResponse = rest.exchange("/api/auth/put/{userId}/{money}", HttpMethod.POST, new HttpEntity<>(headers), void.class, Map.of("userId", "1", "money", "1000"));
    assertTrue(putMoneyWithAuthResponse.getStatusCode().is2xxSuccessful(), "Unexpected status code: " + putMoneyWithAuthResponse.getStatusCode());
  }
  @Test
  void shouldWithdrawMoneyWithAuthFailure() {
    var headers = new HttpHeaders();
    String base64AuthCredentials =
        new String(Base64.encodeBase64("TEST:TEST1".getBytes()));
    headers.add("Authorization", "Basic " + base64AuthCredentials);

    ResponseEntity<Void> withdrawMoneyWithAuthResponse;
    withdrawMoneyWithAuthResponse = rest.exchange("/api/auth/withdraw/{userId}/{money}", HttpMethod.POST, new HttpEntity<>(headers), void.class, Map.of("userId", "1", "money", "1000"));
    assertTrue(withdrawMoneyWithAuthResponse.getStatusCode().is4xxClientError(), "Unexpected status code: " + withdrawMoneyWithAuthResponse.getStatusCode());
  }
}

