package service.auth.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.auth.user.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class ItemController {
  private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RestTemplate restTemplate;


  @Autowired
  public ItemController(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.restTemplate = restTemplate;
  }

  @PreAuthorize("hasAuthority('SELLER')")
  @PostMapping("/product")
  public void createProduct(@RequestBody Request.RequestToCreateProduct request) {
    restTemplate.postForEntity(
        "http://localhost:5438/api/product",
        request,
        ProductResponse.class
    );
  }
}

