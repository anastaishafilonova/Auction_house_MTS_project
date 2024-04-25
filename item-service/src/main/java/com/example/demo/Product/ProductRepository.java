package com.example.demo.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  int getProductPrice(Long id);
  void changeCurrentPrice(Long id, int newPrice);

  public Long getSellerByProduct(Long idProduct);

  Product findByName(String name);
}
