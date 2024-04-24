package com.example.demo.Customer;

import com.example.demo.Product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@Entity
@Table(name = "customer")
public class Customer {
  private static final Logger logger = LoggerFactory.getLogger(Customer.class);
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long customerId;

  @Column(name = "first_name")
  @NotNull(message = "firstname can not be null")
  private String firstName;
  @Column(name = "last_name")
  @NotNull(message = "Lastname can not be null")
  private String lastName;

  @Column(name = "balance")
  private int balance = 0;

  @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private ArrayList<Product> products;

  @Column(name = "bet")
  private int bet = 0;

  public Customer(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
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

  public int getBet() {
    return bet;
  }

  public void setBet(int bet) {
    this.bet = bet;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Customer)) return false;
    Customer customer = (Customer) o;
    return customerId != null && customerId.equals(customer.customerId);
  }

  @Override
  public int hashCode() {
    return Customer.class.hashCode();
  }
}
