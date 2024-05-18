package service.auth.user;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class UserController {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RestTemplate restTemplate;


  @Autowired
  public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.restTemplate = restTemplate;
  }

  @PostMapping("/register")
  @Transactional
  public void register(@RequestBody UserRegisterRequest request) {
    LOGGER.info("username: " + request.username() + " roles: " + request.roles());
    Set<Role> roles = new HashSet<>();
    for (String role : request.roles().split(", ")) {
      roles.add(Role.valueOf(role));
    }
    userRepository.save(
        // Создаем здесь нашу сущность User
        new User(
            request.username(),
            // не забываем, что пароли хранятся в кодированном виде
            passwordEncoder.encode(request.password()),
            roles
        )
    );
  }
}

