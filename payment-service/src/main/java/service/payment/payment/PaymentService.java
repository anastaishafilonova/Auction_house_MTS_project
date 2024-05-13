package service.payment.payment;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.payment.exceptions.NotEnoughMoneyException;
import service.payment.exceptions.UserNotFoundException;
import service.payment.purchase.Purchase;
import service.payment.purchase.PurchaseRepository;

import java.util.NoSuchElementException;

@Service
public class PaymentService {

  private final PurchaseRepository purchaseRepository;

  @Autowired
  public PaymentService(PurchaseRepository purchaseRepository) {
    this.purchaseRepository = purchaseRepository;
  }

  @Transactional
  public void putMoney(Long userId, int money) {
    try {
      Purchase purchase = purchaseRepository.findById(userId).orElseThrow();
      purchase.setMoney(purchase.getMoney() + money);
      purchaseRepository.save(purchase);
    } catch (NoSuchElementException e) {
      Purchase purchase = new Purchase(userId, money);
      purchaseRepository.save(purchase);
    }
  }

  @Transactional
  public void withdrawMoney(Long userId, int money) throws UserNotFoundException, NotEnoughMoneyException{
    try {
      Purchase purchase = purchaseRepository.findById(userId).orElseThrow();
      if (purchase.getMoney() >= money) {
        purchase.setMoney(purchase.getMoney() - money);
        purchaseRepository.save(purchase);
      } else {
        throw new NotEnoughMoneyException("Недостаточно денег на счёте");
      }
    } catch (NoSuchElementException e) {
      throw new UserNotFoundException("Пользователя с таким id не существует");
    } catch (NotEnoughMoneyException e) {
      throw new NotEnoughMoneyException("Недостаточно денег на счёте");
    }
  }

  @Transactional
  public UserBalanceInfo checkBalance(Long userId) {
    try {
      Purchase purchase = purchaseRepository.findById(userId).orElseThrow();
      return new UserBalanceInfo(userId, purchase.getMoney());
    } catch (NoSuchElementException e) {
      throw new UserNotFoundException("Пользователя с таким id не существует");
    }
  }
}
