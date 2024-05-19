package com.example.demo.Auction.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GettingPayConsumer {
    private final ObjectMapper objectMapper;
    private final AuctionService auctionService;

    @Autowired
    public GettingPayConsumer(ObjectMapper objectMapper, AuctionService auctionService) {
        this.objectMapper = objectMapper;
        this.auctionService = auctionService;
    }

    @KafkaListener(topics = {"${topic-to-consume-message}"})
    @Transactional
    public void onPayReceived(String message) throws JsonProcessingException {
        var result = objectMapper.readValue(message, PayResult.class);
        auctionService.process(result);
    }
}

record PayResult(Long productId, String resultStatus) {}