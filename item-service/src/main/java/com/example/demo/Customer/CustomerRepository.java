package com.example.demo.Customer;

import com.example.demo.Product.Product;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository {
  //  Customer createCustomer(Customer customer);
//  void deleteCustomer(Long id);
//  void updateCustomer(Customer customer);
  int getBalance(Long id);

  void increaseBalance(Long id, int delta);

  void decreaseBalance(Long id, int delta);
}
