package com.example.demo.Auction.service;

import com.example.demo.Auction.entity.Auction;
import com.example.demo.Auction.entity.OutboxRecord;
import com.example.demo.Auction.repository.AuctionRepository;
import com.example.demo.Auction.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PayService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final String topic;
    private final OutboxRepository outboxRepository;
    private final AuctionRepository auctionRepository;

    @Autowired
    public PayService(KafkaTemplate<String, String> kafkaTemplate,
                      ObjectMapper objectMapper,
                      @Value("${topic-to-send-message}") String topic, OutboxRepository outboxRepository, AuctionRepository auctionRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
        this.topic = topic;
        this.outboxRepository = outboxRepository;
        this.auctionRepository = auctionRepository;
    }

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void processOutbox() {
        List<OutboxRecord> result = outboxRepository.findAll();
        for (OutboxRecord outboxRecord : result) {
            CompletableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(topic, outboxRecord.getData());
            // block on sendResult until finished
        }
        outboxRepository.deleteAll(result);
    }

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void payProduct() throws JsonProcessingException {
        List<Auction> result = auctionRepository.findAll();
        for (Auction auction : result) {
            if (auction.getCustomerid() != -1 && auction.getEndtime().isBefore(LocalDateTime.now())) {
                LOGGER.info("" + auction.getProductid());
                outboxRepository.save(new OutboxRecord(
                        objectMapper.writeValueAsString(
                                new PayMessage(auction.getCustomerid(), auction.getSellerid(), auction.getProductid(), auction.getCurprice())
                        )
                ));
            }
        }
    }
}

record PayMessage(Long customerId, Long sellerId, Long productId, int price) {
}
