package service.auth.item;


import java.time.LocalDateTime;

public record ProductResponse(Long productId, String name, int price, Long sellerId, LocalDateTime startTime, LocalDateTime finishTime, int minBet) {}