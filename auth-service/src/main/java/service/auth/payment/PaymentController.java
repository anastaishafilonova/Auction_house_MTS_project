package service.auth.payment;

import java.util.Map;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.auth.user.User;
import service.auth.user.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class PaymentController {
  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RestTemplate restTemplate;


  @Autowired
  public PaymentController(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.restTemplate = restTemplate;
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/put/{userId}/{money}")
  public void putMoney(@PathVariable @NotNull Long userId, @PathVariable int money) {
    restTemplate.postForEntity(
        "http://localhost:8081/api/money/put/{userId}/{money}",
        new HttpEntity<>(null),
        void.class,
        Map.of("userId", userId, "money", money)
    );
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping("/withdraw/{userId}/{money}")
  public void withdrawMoney(@PathVariable @NotNull Long userId, @PathVariable int money) {
    restTemplate.postForEntity(
        "http://localhost:8081/api/money/withdraw/{userId}/{money}",
        new HttpEntity<>(null),
        void.class,
        Map.of("userId", userId, "money", money)
    );
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/check/balance/{userId}")
  public UserBalanceInfo checkBalance(@PathVariable @NotNull Long userId) {
    return restTemplate.getForEntity(
        "http://localhost:8081/api/money/check/balance/{userId}",
        UserBalanceInfo.class,
        Map.of("userId", userId)
    ).getBody();
  }
}

