package com.example.demo.Auction.dto;

import java.time.LocalDateTime;

public record AuctionResponse(Long productId, String status, LocalDateTime startTime, LocalDateTime finishTime, int minBet, int curPrice, Long customerId, Long sellerId) {

}
