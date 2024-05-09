package service.payment.consumer;

public record PaymentConsumeMessage(Long userId, Long productId, int price) {}
