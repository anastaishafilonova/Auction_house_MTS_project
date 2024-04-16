package com.example.demo.Seller;

import com.example.demo.Product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

@Entity
@Table(name = "seller")
public class Seller {
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
  private int balance;

  @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private ArrayList<Product> products;

  public Seller(Long sellerId, String firstName, String lastName) {
    this.sellerId = sellerId;
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
}
