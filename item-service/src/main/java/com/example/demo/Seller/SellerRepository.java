package com.example.demo.Seller;

import com.example.demo.Product.Product;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository {
  //  Seller createSeller(Seller seller);
  //void deleteSeller(Long id);

  //  void updateSeller(Seller seller);
  Long getSellerByProduct(Product product);
}
