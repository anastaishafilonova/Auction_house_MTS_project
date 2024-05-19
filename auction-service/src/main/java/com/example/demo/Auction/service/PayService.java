package com.example.demo.Auction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topic;

    @Autowired
    public PayService(KafkaTemplate<String, String> kafkaTemplate,
                      ObjectMapper objectMapper,
                      @Value("${topic-to-send-message}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.topic = topic;
    }

    public void payProduct(Long customerId, Long sellerId, Long productId, int price) throws JsonProcessingException {
        LOGGER.info(productId + " " + customerId + " " + productId + " " + price);
        String message = objectMapper.writeValueAsString(new PayMessage(productId, customerId, productId, price));
        CompletableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(topic, message);
    }
}

record PayMessage(Long customerId, Long sellerId, Long productId, int price) {
}
