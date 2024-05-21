package service.auth.auction;

import jakarta.validation.constraints.NotNull;

public class RequestToCreateBet{
  private Long productId;
  private int bet;
  private Long customerId;

  public RequestToCreateBet(Long productId, int bet, Long customerId) {
    this.customerId = customerId;
    this.productId = productId;
    this.bet = bet;
  }

  public Long getProductId() {
    return productId;
  }

  public int getBet() {
    return bet;
  }

  public Long getCustomerId() {
    return customerId;
  }
}
