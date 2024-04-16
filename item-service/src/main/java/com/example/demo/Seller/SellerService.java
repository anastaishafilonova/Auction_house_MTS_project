package com.example.demo.Seller;

import com.example.demo.Product.Controller.ProductResponse;
import com.example.demo.Product.Product;
import com.example.demo.Product.ProductRepository;
import com.example.demo.Product.ProductService;
import com.example.demo.Seller.Controller.SellerResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SellerService {
  private SellerRepository sellerRepository;
  private static final Logger logger = LoggerFactory.getLogger(SellerRepository.class);

  @Autowired
  public SellerService(SellerRepository sellerRepository) {
    this.sellerRepository = sellerRepository;
  }


  protected SellerService() {}
  @Transactional
  public SellerResponse createSeller(String firstName, String lastName){
    return null;
  }

  //  @Transactional
  // public void updateSeller(Long id, String firstName, String lastName){}
  @Transactional
  public void deleteSeller(Long id){
  }

  @Transactional
  public Long getSellerByProduct(Product product){
    return 0l;
  }
}
