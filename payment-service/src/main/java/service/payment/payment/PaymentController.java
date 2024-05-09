package service.payment.payment;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.payment.exceptions.NotEnoughMoneyException;
import service.payment.exceptions.UserNotFoundException;

@RestController
@RequestMapping("/api/money")
public class PaymentController {
  private final PaymentService paymentService;
  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

  @Autowired
  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/put/{userId}/{money}")
  public void putMoney(@PathVariable @NotNull Long userId, @PathVariable int money) {
    if (money >= 0) {
      paymentService.putMoney(userId, money);
    } else {
      LOGGER.warn("Некорректное значение денежной суммы");
    }
  }

  @PostMapping("/withdraw/{userId}/{money}")
  public void withdrawMoney(@PathVariable @NotNull Long userId, @PathVariable int money) {
    if (money >= 0) {
      try {
        paymentService.withdrawMoney(userId, money);
      } catch (UserNotFoundException | NotEnoughMoneyException e) {
        LOGGER.warn(e.getMessage());
      }
    } else {
      LOGGER.warn("Некорректное значение денежной суммы");
    }
  }

  @GetMapping("/check/balance/{userId}")
  public UserBalanceInfo checkBalance(@PathVariable @NotNull Long userId)
      throws UserNotFoundException { // ВАЖНО: метод выкидывает ошибку, если пользователь не найден
    try {
      return paymentService.checkBalance(userId);
    } catch (UserNotFoundException e) {
      LOGGER.warn(e.getMessage());
      throw new UserNotFoundException(e.getMessage());
    }
  }
}
