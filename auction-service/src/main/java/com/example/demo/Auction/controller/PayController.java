package com.example.demo.Auction.controller;

import com.example.demo.Auction.entity.Auction;
import com.example.demo.Auction.repository.AuctionRepository;
import com.example.demo.Auction.service.PayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auction")
@Validated
public class PayController {
    private final PayService payService;
    private final AuctionRepository auctionRepository;

    @Autowired
    public PayController(PayService payService, AuctionRepository auctionRepository) {
        this.payService = payService;
        this.auctionRepository = auctionRepository;
    }

    @PostMapping("/pay/{productId}:payProduct")
    public void countRating(@PathVariable @NotNull Long productId) throws JsonProcessingException {
        Auction auction = auctionRepository.findById(productId).orElseThrow();
        payService.payProduct(auction.getCustomerid(), auction.getSellerid(), productId, auction.getCurprice());
    }
}
