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
  public SellerResponse createSeller(Long sellerId, String firstName, String lastName){
    if (sellerRepository.findByFirstNameAndLastName(firstName, lastName) != null) {
      Seller seller = sellerRepository.findByFirstNameAndLastName(firstName, lastName);
      return new SellerResponse(seller.getSellerId(), seller.getFirstName(), seller.getLastName());
    } else {
      Seller seller = new Seller(sellerId, firstName, lastName);
      sellerRepository.save(seller);
      return new SellerResponse(seller.getSellerId(), seller.getFirstName(), seller.getLastName());
    }
  }

  @Transactional
  public void deleteSeller(Long id){
    Seller seller = sellerRepository.findById(id).orElseThrow();
    sellerRepository.delete(seller);
  }

  @Transactional
  public SellerResponse getSeller(Long id) {
    Seller seller = sellerRepository.findById(id).orElseThrow();
    return new SellerResponse(seller.getSellerId(), seller.getFirstName(), seller.getLastName());
  }
}
