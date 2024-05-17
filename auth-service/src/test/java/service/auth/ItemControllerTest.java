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
import service.auth.item.Request;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.StringBody.exact;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ItemControllerTest extends DatabaseSuite {

  @Autowired
  private TestRestTemplate rest;

  private ClientAndServer mockServer;

  @BeforeEach
  public void startServer() {
    mockServer = startClientAndServer(5438);
  }

  @AfterEach
  public void stopServer() {
    mockServer.stop();
  }

  @Test
  void shouldCreateProductWithAuthSuccess() {
    var headers = new HttpHeaders();
    String base64AuthCredentials =
        new String(Base64.encodeBase64("TEST:TEST".getBytes()));
    headers.add("Authorization", "Basic " + base64AuthCredentials);

    new MockServerClient("localhost", 5438)
        .when(
            request()
                .withMethod("POST")
                .withPath("/api/product")
                .withHeaders(new Header("Accept", "application/json, application/*+json"),
                    new Header("Content-Type", "application/json"),
                    new Header("User-Agent", "Java/17.0.9"),
                    new Header("Host", "localhost:5438"),
                    new Header("Connection", "keep-alive"),
                    new Header("content-length", "115"))
                .withBody("{\"name\":\"second\",\"price\":100,\"sellerId\":3,\"startTime\":[2023,7,15,12,30],\"finishTime\":[2023,7,15,12,30],\"minBet\":50}"))
        .respond(
            response()
                .withStatusCode(200)
                .withHeaders(
                    new Header("Content-Type", "application/json"))
                .withBody("""
                    {
                    "productId": 1,
                    "name": "second",
                    "price": "100",
                    "sellerId": "3",
                    "startTime": "2023-07-15T12:30:00",
                    "finishTime": "2023-07-15T12:30:00",
                    "minBet": "50"
                    }"""));

    ResponseEntity<Void> createProductWithAuthResponse;
    createProductWithAuthResponse = rest.exchange("/api/auth/product", HttpMethod.POST, new HttpEntity<>(new Request.RequestToCreateProduct("second", 100, 3L, LocalDateTime.of(2023, Month.JULY, 15, 12, 30), LocalDateTime.of(2023, Month.JULY, 15, 12, 30), 50), headers), void.class);
    assertTrue(createProductWithAuthResponse.getStatusCode().is2xxSuccessful(), "Unexpected status code: " + createProductWithAuthResponse.getStatusCode());
  }
  @Test
  void shouldCreateProductWithAuthFailure() {
    var headers = new HttpHeaders();
    String base64AuthCredentials =
        new String(Base64.encodeBase64("TEST:TEST1".getBytes()));
    headers.add("Authorization", "Basic " + base64AuthCredentials);

    new MockServerClient("localhost", 5438)
        .when(
            request()
                .withMethod("POST")
                .withPath("/api/product")
                .withHeaders(new Header("Accept", "application/json, application/*+json"),
                    new Header("Content-Type", "application/json"),
                    new Header("User-Agent", "Java/17.0.9"),
                    new Header("Host", "localhost:5438"),
                    new Header("Connection", "keep-alive"),
                    new Header("content-length", "115"))
                .withBody("{\"name\":\"second\",\"price\":100,\"sellerId\":3,\"startTime\":[2023,7,15,12,30],\"finishTime\":[2023,7,15,12,30],\"minBet\":50}"))
        .respond(
            response()
                .withStatusCode(200)
                .withHeaders(
                    new Header("Content-Type", "application/json"))
                .withBody("""
                    {
                    "productId": 1,
                    "name": "second",
                    "price": "100",
                    "sellerId": "3",
                    "startTime": "2023-07-15T12:30:00",
                    "finishTime": "2023-07-15T12:30:00",
                    "minBet": "50"
                    }"""));

    ResponseEntity<Void> createProductWithAuthResponse;
    createProductWithAuthResponse = rest.exchange("/api/auth/product", HttpMethod.POST, new HttpEntity<>(new Request.RequestToCreateProduct("second", 100, 3L, LocalDateTime.of(2023, Month.JULY, 15, 12, 30), LocalDateTime.of(2023, Month.JULY, 15, 12, 30), 50), headers), void.class);
    assertTrue(createProductWithAuthResponse.getStatusCode().is4xxClientError(), "Unexpected status code: " + createProductWithAuthResponse.getStatusCode());
  }
}

