package service.payment.producer;

public record PaymentProduceMessage(Long productId, String status) {}
