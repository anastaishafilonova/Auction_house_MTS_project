package com.example.demo.Product.Controller;

import com.example.demo.Seller.Seller;

import java.time.LocalDateTime;

public record ProductResponse(Long productId, String name, int price, Long sellerId, LocalDateTime startTime, LocalDateTime finishTime, int minBet, String urlPicture) {}