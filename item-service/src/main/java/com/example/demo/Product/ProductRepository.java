package com.example.demo.Product;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository {
//  Product createProduct(Product product);
//  void deleteProduct(Long id);
//  void updateProduct(Product product);
  int getProductPrice(Long id);
  void changeCurrentPrice(Long id, int delta);
}
