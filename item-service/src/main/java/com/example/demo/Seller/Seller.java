package com.example.demo.Seller;

import com.example.demo.Product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

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
  private ArrayList<Product> products;

  public Seller(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

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

  public ArrayList<Product> getProducts() {
    return products;
  }

  public void setProducts(ArrayList<Product> products) {
    this.products = products;
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
}
