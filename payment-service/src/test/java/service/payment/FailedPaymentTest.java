package service.payment;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import service.payment.consumer.PaymentConsumeMessage;
import service.payment.producer.PaymentProduceMessage;
import service.payment.purchase.Purchase;
import service.payment.purchase.PurchaseRepository;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(
    properties = {"topic-to-send-message=some-test-topic1",
        "topic-to-consume-message=some-test-topic",
        "spring.kafka.consumer.auto-offset-reset=earliest"
    }
)
@Import({KafkaAutoConfiguration.class, FailedPaymentTest.ObjectMapperTestConfig.class})
@Testcontainers
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext
class FailedPaymentTest extends DatabaseSuite {
  @TestConfiguration
  static class ObjectMapperTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
      return new ObjectMapper();
    }
  }

  @Container
  @ServiceConnection
  public static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @MockBean
  private PurchaseRepository purchaseRepository;

  @Test
  public void shouldPaidFailure() throws JsonProcessingException, InterruptedException {
    Purchase customerPurchase = new Purchase(1L, 50);
    when(purchaseRepository.findById(1L)).thenReturn(Optional.of(customerPurchase));
    when(purchaseRepository.save(any())).thenReturn(customerPurchase);
    Purchase sellerPurchase = new Purchase(2L, 0);
    when(purchaseRepository.findById(2L)).thenReturn(Optional.of(sellerPurchase));
    CompletableFuture<SendResult<String, String>> sendResult =
        kafkaTemplate.send(
            "some-test-topic", objectMapper.writeValueAsString(new PaymentConsumeMessage(1L, 2L, 10L, 100)));
    Thread.sleep(15000);
    KafkaTestConsumer consumer = new KafkaTestConsumer(KAFKA.getBootstrapServers(), "auction-service-group");
    consumer.subscribe(List.of("some-test-topic1"));
    ConsumerRecords<String, String> records = consumer.poll();
    assertEquals(1, records.count());
    records
        .iterator()
        .forEachRemaining(
            record -> {
              PaymentProduceMessage message = null;
              try {
                message = objectMapper.readValue(record.value(), PaymentProduceMessage.class);
              } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
              }
              assertEquals(new PaymentProduceMessage(10L, "cancelled"), message);
            });
    assertEquals(0, ((Purchase) purchaseRepository.findById(2L).orElseThrow()).getMoney());
  }
}