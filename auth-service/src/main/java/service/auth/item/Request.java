package service.auth.item;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class Request {
  public static class RequestToCreateCustomer {
    @NotNull(message = "Customer`s id can`t be null")
    private Long customerId;
    @NotNull(message = "Customer`s firstName can`t be null")
    private String firstName;

    @NotNull(message = "Customer`s lastName can`t be null")
    private String lastName;

    public RequestToCreateCustomer(Long customerId, String firstName, String lastName) {
      this.customerId = customerId;
      this.firstName = firstName;
      this.lastName = lastName;
    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
    }

    public Long getCustomerId() {
      return customerId;
    }
  }

  public static class RequestToGetBalanceCustomer{
    @NotNull(message = "Customer`s id can't be null")
    private Long id;

    public RequestToGetBalanceCustomer(Long id) {
      this.id = id;
    }

    public Long getId() {
      return id;
    }
  }

  public static class RequestToIncreaseBalance {
    @NotNull(message = "Customer`s id can't be null")
    private Long id;

    private Integer delta;

    public RequestToIncreaseBalance(Long id, Integer delta) {
      this.id = id;
      this.delta = delta;
    }

    public Long getId() {
      return id;
    }

    public Integer getDelta() {
      return delta;
    }
  }

  public static class RequestToDecreaseBalance {
    @NotNull(message = "Customer`s id can't be null")
    private Long id;

    private int delta;

    public RequestToDecreaseBalance(Long id, int delta) {
      this.id = id;
      this.delta = delta;
    }

    public Long getId() {
      return id;
    }

    public int getDelta() {
      return delta;
    }
  }

  public static class RequestToCreateProduct{
    @NotNull(message = "Product`s name can`t be null")
    private String name;

    private int price;
    private Long sellerId;
    private LocalDateTime startTime;
    private LocalDateTime finishTime;

    @NotNull(message = "Product`s price can`t be null")
    private int minBet;

    private String urlPicture;

    public RequestToCreateProduct(String name, int price, Long sellerId, LocalDateTime startTime, LocalDateTime finishTime, int minBet, String urlPicture) {
      this.name = name;
      this.price = price;
      this.sellerId = sellerId;
      this.startTime = startTime;
      this.finishTime = finishTime;
      this.minBet = minBet;
      this.urlPicture = urlPicture;
    }

    public String getName() {
      return name;
    }

    public int getPrice() {
      return price;
    }

    public Long getSellerId() {
      return sellerId;
    }

    public LocalDateTime getStartTime() {
      return startTime;
    }

    public LocalDateTime getFinishTime() {
      return finishTime;
    }

    public int getMinBet() {
      return minBet;
    }

    public String getUrlPicture() {
      return urlPicture;
    }
  }

  public static class RequestToGetProductPrice {

    @NotNull(message = "Product`s id can't be null")
    private Long id;

    public RequestToGetProductPrice(Long id) {
      this.id = id;
    }

    public Long getId() {
      return id;
    }
  }

  public static class RequestToChangeCurrentPrice{
    @NotNull(message = "Product`s id can't be null")
    private Long id;

    private int newPrice;

    public RequestToChangeCurrentPrice(Long id, int newPrice) {
      this.id = id;
      this.newPrice = newPrice;
    }

    public Long getId() {
      return id;
    }

    public int getNewPrice() {
      return newPrice;
    }
  }

  public static class RequestToCreateSeller {
    @NotNull(message = "Seller`s id can`t be null")
    private Long sellerId;
    @NotNull(message = "Seller`s firstName can`t be null")
    private String firstName;

    @NotNull(message = "Seller`s lastName can`t be null")
    private String lastName;

    public RequestToCreateSeller(Long sellerId, String firstName, String lastName) {
      this.sellerId = sellerId;
      this.firstName = firstName;
      this.lastName = lastName;
    }

    public Long getSellerId() {
      return sellerId;
    }

    public String getFirstName() {
      return firstName;
    }

    public String getLastName() {
      return lastName;
    }
  }

  public static class RequestToGetSellerByProduct{
    @NotNull(message = "Seller`s id can`t be null")
    private Long id;

    public RequestToGetSellerByProduct(Long id) {
      this.id = id;
    }

    public Long getId() {
      return id;
    }
  }
}
