package service.payment.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import service.payment.producer.MessageProcessor;

@Service
public class PaymentConsumer {
  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentConsumer.class);

  private final ObjectMapper objectMapper;
  private final MessageProcessor messageProcessor;

  @Autowired
  public PaymentConsumer(ObjectMapper objectMapper, MessageProcessor messageProcessor) {
    this.objectMapper = objectMapper;
    this.messageProcessor = messageProcessor;
  }

  @KafkaListener(topics = {"${topic-to-consume-message}"})
  public void payConsume(String message, Acknowledgment acknowledgment) throws JsonProcessingException {
    PaymentConsumeMessage parsedMessage = objectMapper.readValue(message, PaymentConsumeMessage.class);
    LOGGER.info("Retrieved message {}", message);
    messageProcessor.process(
        parsedMessage.customerId(),
        parsedMessage.sellerId(),
        parsedMessage.productId(),
        parsedMessage.price());
    acknowledgment.acknowledge();
  }
}
