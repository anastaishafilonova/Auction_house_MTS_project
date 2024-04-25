package com.example.demo.Customer;

import com.example.demo.Customer.Controller.CustomerResponse;
import com.example.demo.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Customer findByFirstNameAndLastName(String firstName, String lastName);
}
