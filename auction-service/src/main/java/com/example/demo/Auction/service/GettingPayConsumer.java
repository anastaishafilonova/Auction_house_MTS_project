package com.example.demo.Auction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GettingPayConsumer {
    private final ObjectMapper objectMapper;
    private final AuctionService auctionService;
    private static final Logger LOGGER = LoggerFactory.getLogger(GettingPayConsumer.class);

    @Autowired
    public GettingPayConsumer(ObjectMapper objectMapper, AuctionService auctionService) {
        this.objectMapper = objectMapper;
        this.auctionService = auctionService;
    }

    @KafkaListener(topics = {"${topic-to-consume-message}"})
    public void onPayReceived(String message) throws JsonProcessingException {
        LOGGER.info(message);
        var result = objectMapper.readValue(message, PayResult.class);
        auctionService.process(result);
    }
}

record PayResult(Long productId, String status) {}