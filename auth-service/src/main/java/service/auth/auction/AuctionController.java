package service.auth.auction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.auth.user.UserRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuctionController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuctionController.class);
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RestTemplate restTemplate;


  @Autowired
  public AuctionController(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.restTemplate = restTemplate;
  }

  @PreAuthorize("hasAuthority('CUSTOMER')")
  @PostMapping("/create/bet")
  public void createBet(@RequestBody RequestToCreateBet request) {
    LOGGER.info("Retrieved: " + "Bet: " + request.getBet() + " CustomerId: " + request.getCustomerId() + " ProductId: " + request.getProductId());
    restTemplate.put(
        "http://localhost:8082/api/auction/increase/bet/{id}",
        new ProductRequestToUpdate(request.getBet(), request.getCustomerId()),
        Map.of("id", request.getProductId()));
  }

  @GetMapping("/current/price/{productId}")
  public CurrentPriceInfo getCurPrice(@PathVariable Long productId) {
    return restTemplate.getForEntity("http://localhost:8082/api/auction/current/price/{productId}",
        CurrentPriceInfo.class,
        Map.of("productId", productId)).getBody();
  }
}

