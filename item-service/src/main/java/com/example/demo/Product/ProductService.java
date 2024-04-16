package com.example.demo.Product;

import com.example.demo.Customer.Controller.CustomerResponse;
import com.example.demo.Customer.CustomerRepository;
import com.example.demo.Customer.CustomerService;
import com.example.demo.Product.Controller.ProductResponse;
import com.example.demo.Seller.Seller;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProductService {
  private ProductRepository productRepository;
  private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }


  protected ProductService() {}
  @Transactional
  public ProductResponse createProduct(String name, int price, Seller seller, LocalDateTime startTime, LocalDateTime finishTime, int minBet){
    return null;
  }

  //  @Transactional
  // public void updateProduct(Long id, String name, int price, Seller seller, LocalDateTime startTime, LocalDateTime finishTime, int minBet){}
  @Transactional
  public void deleteProduct(Long id){
  }

  @Transactional
  public int getProductPrice(Long id){
    return 0;
  }

  @Transactional
  public void changeCurrentPrice(Long id, int delta){
  }


}
