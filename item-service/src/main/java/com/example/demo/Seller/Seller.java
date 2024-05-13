package com.example.demo.Seller;

import com.example.demo.Product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seller")
public class Seller {
  private static final Logger logger = LoggerFactory.getLogger(Seller.class);
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long sellerId;

  @Column(name = "first_name")
  @NotNull(message = "first_name can not be null")
  private String firstName;
  @Column(name = "last_name")
  @NotNull(message = "last_name can not be null")
  private String lastName;
  @Column(name = "balance")
  private int balance = 0;

  @OneToMany(mappedBy = "seller", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Product> products = new ArrayList<>();

  public Seller(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  protected Seller() {}

  public Long getSellerId() {
    return sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }

  public List<Product> getProducts() {
    return products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Seller)) return false;
    Seller seller = (Seller) o;
    return sellerId != null && sellerId.equals(seller.sellerId);
  }

  @Override
  public int hashCode() {
    return Seller.class.hashCode();
  }

  public void addProduct(String name, int price, LocalDateTime startTime, LocalDateTime finishTime, int minBet) {
    this.products.add(new Product(name, price, this, startTime, finishTime, minBet));
  }
}
