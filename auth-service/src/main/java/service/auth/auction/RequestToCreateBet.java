package service.auth.auction;

public record RequestToCreateBet(Long productId, int bet, Long customerId) {}
