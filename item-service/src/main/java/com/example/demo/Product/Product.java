package com.example.demo.Product;

import com.example.demo.Customer.Customer;
import com.example.demo.Seller.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
@Entity
@Table(name = "product")
public class Product {
  private static final Logger logger = LoggerFactory.getLogger(Product.class);
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productId;

  @Column(name = "name")
  @NotNull(message = "name can not be null")
  private String name;

  @Column(name = "price")
  private int price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id")
  private Seller seller;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column(name = "start_time")
  private LocalDateTime startTime;

  @Column(name = "finish_time")
  private LocalDateTime finishTime;

  @Column(name = "status")
  private boolean status;

  @Column(name = "min_bet")
  @NotNull(message = "minBet can not be null")
  private int minBet;

  @Column(name = "url_picture")
  @NotNull(message = "urlPicture can not be null")
  private String urlPicture;

  public Product(String name, int price, Seller seller, LocalDateTime startTime, LocalDateTime finishTime, int minBet, String urlPicture) {
    this.name = name;
    this.price = price;
    this.startTime = startTime;
    this.finishTime = finishTime;
    this.minBet = minBet;
    this.seller = seller;
    this.urlPicture = urlPicture;
  }

  protected Product() {}

  public String getUrlPicture() {
    return urlPicture;
  }

  public void setUrlPicture(String urlPicture) {
    this.urlPicture = urlPicture;
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

  public Seller getSeller() {
    return seller;
  }

  public void setSeller(Seller seller) {
    this.seller = seller;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product)) return false;
    Product product = (Product) o;
    return productId != null && productId.equals(product.productId);
  }

  @Override
  public int hashCode() {
    return Product.class.hashCode();
  }

}
