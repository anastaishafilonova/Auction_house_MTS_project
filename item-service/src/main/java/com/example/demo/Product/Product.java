package com.example.demo.Product;

import com.example.demo.Customer.Customer;
import com.example.demo.Seller.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
@Entity
@Table(name = "product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @Column(name = "name")
  @NotNull(message = "name can not be null")
  private String name;

  @Column(name = "price")
  private int price;

  @Column(name = "name")
  private Long customerId;

  @Column(name = "seller_id")
  private Long sellerId;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  @Column(name = "finish_time")
  private LocalDateTime finishTime;

  @Column(name = "status")
  private boolean status;

  @Column(name = "minBet")
  @NotNull(message = "minBet can not be null")
  private int minBet;

  public Product(Long productId, String name, int price, Long sellerId, LocalDateTime startTime, LocalDateTime finishTime, int minBet) {
    this.productId = productId;
    this.name = name;
    this.price = price;
    this.sellerId = sellerId;
    this.startTime = startTime;
    this.finishTime = finishTime;
    this.minBet = minBet;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }


  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Long getSellerId() {
    return sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  public LocalDateTime getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(LocalDateTime finishTime) {
    this.finishTime = finishTime;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public int getMinBet() {
    return minBet;
  }

  public void setMinBet(int minBet) {
    this.minBet = minBet;
  }
}
