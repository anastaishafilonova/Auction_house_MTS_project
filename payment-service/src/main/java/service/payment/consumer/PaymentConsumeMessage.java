package service.payment.consumer;

public record PaymentConsumeMessage(Long customerId, Long sellerId, Long productId, int price) {}
