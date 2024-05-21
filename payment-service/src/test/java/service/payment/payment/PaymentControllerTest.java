package service.payment.payment;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import service.payment.DatabaseSuite;
import service.payment.exceptions.UserNotFoundException;
import service.payment.purchase.PurchaseRepository;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({PaymentService.class})
@SpringBootTest(webEnvironment = RANDOM_PORT)
class PaymentControllerTest extends DatabaseSuite {
  @Autowired private TestRestTemplate rest;

  @Autowired private PurchaseRepository purchaseRepository;
  @Autowired private PaymentService paymentService;

  @Test
  void shouldSuccessfulPutMoney() {
    ResponseEntity<Void> putMoneyResponse;
    putMoneyResponse =
        rest.exchange(
            "/api/money/put/{userId}/{money}",
            HttpMethod.POST,
            new HttpEntity<>(new HttpHeaders()),
            void.class,
            Map.of("userId", 20, "money", 1000));
    assertTrue(
        putMoneyResponse.getStatusCode().is2xxSuccessful(),
        "Unexpected status code: " + putMoneyResponse.getStatusCode());
    assertEquals(1000, purchaseRepository.findById(20L).orElseThrow().getMoney());
  }

  @Test
  void shouldSuccessfulWithdrawMoney() {
    ResponseEntity<Void> putMoneyResponse;
    putMoneyResponse =
        rest.exchange(
            "/api/money/put/{userId}/{money}",
            HttpMethod.POST,
            new HttpEntity<>(new HttpHeaders()),
            void.class,
            Map.of("userId", 10, "money", 1000));
    assertTrue(
        putMoneyResponse.getStatusCode().is2xxSuccessful(),
        "Unexpected status code: " + putMoneyResponse.getStatusCode());
    assertEquals(1000, purchaseRepository.findById(10L).orElseThrow().getMoney());
    ResponseEntity<Void> withdrawMoneyResponse;
    withdrawMoneyResponse =
        rest.exchange(
            "/api/money/withdraw/{userId}/{money}",
            HttpMethod.POST,
            new HttpEntity<>(new HttpHeaders()),
            void.class,
            Map.of("userId", 10, "money", 900));
    assertTrue(
        withdrawMoneyResponse.getStatusCode().is2xxSuccessful(),
        "Unexpected status code: " + withdrawMoneyResponse.getStatusCode());
    assertEquals(100, purchaseRepository.findById(10L).orElseThrow().getMoney());
  }

  @Test
  void shouldWithdrawMoneyWithUserNoFoundException() {
    ResponseEntity<Void> withdrawMoneyResponse;
    withdrawMoneyResponse =
        rest.exchange(
            "/api/money/withdraw/{userId}/{money}",
            HttpMethod.POST,
            new HttpEntity<>(new HttpHeaders()),
            void.class,
            Map.of("userId", 30, "money", 900));
    assertTrue(
        withdrawMoneyResponse.getStatusCode().is2xxSuccessful(),
        "Unexpected status code: " + withdrawMoneyResponse.getStatusCode());
    assertThrows(UserNotFoundException.class, () -> paymentService.withdrawMoney(30L, 900));
  }

  @Test
  void shouldSuccessfulCheckBalance() {
    ResponseEntity<Void> putMoneyResponse;
    putMoneyResponse =
        rest.exchange(
            "/api/money/put/{userId}/{money}",
            HttpMethod.POST,
            new HttpEntity<>(new HttpHeaders()),
            void.class,
            Map.of("userId", 1, "money", 1000));
    assertTrue(
        putMoneyResponse.getStatusCode().is2xxSuccessful(),
        "Unexpected status code: " + putMoneyResponse.getStatusCode());
    assertEquals(1000, purchaseRepository.findById(1L).orElseThrow().getMoney());
    ResponseEntity<UserBalanceInfo> checkBalanceResponse;
    checkBalanceResponse =
        rest.exchange(
            "/api/money/check/balance/{userId}",
            HttpMethod.GET,
            new HttpEntity<>(new HttpHeaders()),
            UserBalanceInfo.class,
            Map.of("userId", 1));
    assertTrue(
        checkBalanceResponse.getStatusCode().is2xxSuccessful(),
        "Unexpected status code: " + checkBalanceResponse.getStatusCode());
    assertEquals(1000, checkBalanceResponse.getBody().balance());
  }

  @Test
  void shouldCheckBalanceWithUserNotFoundException() {
    boolean testOk = false;
    try {
      paymentService.checkBalance(100L);
    } catch (UserNotFoundException e) {
      testOk = true;
    }
    assertTrue(testOk);
  }
}
