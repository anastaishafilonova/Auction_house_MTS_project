package service.auth.user;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.auth.item.Request;

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
  public UserResponse register(@RequestBody UserRegisterRequest request) {
    LOGGER.info("username: " + request.username() + " roles: " + request.roles());
    Set<Role> roles = new HashSet<>();
    for (String role : request.roles().split(", ")) {
      roles.add(Role.valueOf(role));
    }
    User user = userRepository.save(
        // Создаем здесь нашу сущность User
        new User(
            request.username(),
            // не забываем, что пароли хранятся в кодированном виде
            passwordEncoder.encode(request.password()),
            roles
        )
    );
    for (Role role: roles) {
      if (role == Role.CUSTOMER) {
        CustomerResponse customer =
            restTemplate
                .postForEntity(
                    "http://localhost:5438/api/customer",
                    new Request.RequestToCreateCustomer(
                        user.getId(), request.firstName(), request.lastName()),
                    CustomerResponse.class)
                .getBody();
        return new UserResponse(customer.customerId(), "CUSTOMER");
      } else if (role == Role.SELLER) {
        SellerResponse seller =
            restTemplate
                .postForEntity(
                    "http://localhost:5438/api/seller",
                    new Request.RequestToCreateSeller(
                        user.getId(), request.firstName(), request.lastName()),
                    SellerResponse.class)
                .getBody();
        return new UserResponse(seller.sellerId(), "SELLER");
      }
    }
    return new UserResponse(0L, "1");
  }

  @PostMapping("/enter")
  public EnterUserResponse enterUser(@RequestBody RequestToEnterUser request) {
    String username = request.username();
    String password = request.password();
    try {
      User user = userRepository.findByUsername(username).orElseThrow();
      if (passwordEncoder.matches(password, user.getPassword())) {
        String role = user.getRoles().toArray()[0].toString();
        if (Objects.equals(role, "SELLER")) {
          SellerResponse sellerResponse = restTemplate.getForEntity("http://localhost:5438/api/seller/user/{userId}",
              SellerResponse.class,
              Map.of("userId", user.getId())).getBody();
          return new EnterUserResponse(
              role, user.getId(), sellerResponse.firstName(), sellerResponse.lastName());
        } else if (Objects.equals(role, "CUSTOMER")) {
          CustomerResponse customerResponse = restTemplate.getForEntity("http://localhost:5438/api/customer/user/{userId}",
              CustomerResponse.class,
              Map.of("userId", user.getId())).getBody();
          return new EnterUserResponse(role, user.getId(), customerResponse.firstName(), customerResponse.lastName());
        }

      }
      return new EnterUserResponse("Пароль некорректен", 1L, "", "");
    } catch (NoSuchElementException e) {
      return new EnterUserResponse("Имя пользователя введено неверно", 1L, "", "");
    }
  }
}

