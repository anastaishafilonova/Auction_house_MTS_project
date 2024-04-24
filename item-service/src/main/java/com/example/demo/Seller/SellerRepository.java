package com.example.demo.Seller;

import com.example.demo.Customer.Customer;
import com.example.demo.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
  Seller findByFirstNameAndLastName(String firstName, String lastName);
}
