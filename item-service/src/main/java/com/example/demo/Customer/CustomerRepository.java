package com.example.demo.Customer;

import com.example.demo.Customer.Controller.CustomerResponse;
import com.example.demo.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  int getBalance(Long id);

  CustomerResponse increaseBalance(Long id, int delta);

  CustomerResponse decreaseBalance(Long id, int delta);

  Customer findByFirstNameAndLastName(String firstName, String lastName);
}
