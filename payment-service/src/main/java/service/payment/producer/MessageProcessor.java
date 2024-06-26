package service.payment.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.payment.outbox.Outbox;
import service.payment.outbox.OutboxRepository;
import service.payment.payment.PaymentService;
import service.payment.purchase.Purchase;
import service.payment.purchase.PurchaseRepository;

import java.util.NoSuchElementException;

@Service
public class MessageProcessor {
  private static final Logger logger = LoggerFactory.getLogger(MessageProcessor.class);
  private final PurchaseRepository purchaseRepository;
  private final PaymentService paymentService;
  private final OutboxRepository outboxRepository;
  private final ObjectMapper objectMapper;

  @Autowired
  public MessageProcessor(
      PurchaseRepository purchaseRepository,
      PaymentService paymentService, OutboxRepository outboxRepository,
      ObjectMapper objectMapper) {
    this.purchaseRepository = purchaseRepository;
    this.paymentService = paymentService;
    this.outboxRepository = outboxRepository;
    this.objectMapper = objectMapper;
  }

  @Transactional
  public void process(Long customerId, Long sellerId, Long productId, int price) throws JsonProcessingException {
    String resultStatus = "";
    try {
      Purchase customerPurchase = purchaseRepository.findById(customerId).orElseThrow();
      if (customerPurchase.getMoney() >= price) {
        resultStatus = "paid";
        customerPurchase.setMoney(customerPurchase.getMoney() - price);
        paymentService.putMoney(sellerId, price);
        purchaseRepository.save(customerPurchase);
      } else {
        logger.info("Недостаточно денег на счёте");
        resultStatus = "cancelled";
      }
    } catch (NoSuchElementException e) {
      logger.info("Пользователь не найден");
      resultStatus = "cancelled";
    } finally {
      var message =
          objectMapper.writeValueAsString(new PaymentProduceMessage(productId, resultStatus));
      outboxRepository.save(new Outbox(message));
    }
  }
}
