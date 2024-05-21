package com.example.demo.Product;

import com.example.demo.Customer.Controller.CustomerResponse;
import com.example.demo.Customer.CustomerRepository;
import com.example.demo.Customer.CustomerService;
import com.example.demo.Product.Controller.ProductResponse;
import com.example.demo.Request.Request;
import com.example.demo.Seller.Seller;
import com.example.demo.Seller.SellerRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductService {
  private ProductRepository productRepository;
  private SellerRepository sellerRepository;
  @Autowired ProductCreateGateway productCreateGateway;

  @Autowired
  public ProductService(ProductRepository productRepository, SellerRepository sellerRepository) {
    this.productRepository = productRepository;
    this.sellerRepository = sellerRepository;
  }

  private static final Logger logger = LoggerFactory.getLogger(ProductService.class);



  protected ProductService() {
  }

  @Transactional
  public ProductResponse createProduct(String name, int price, Long sellerId, LocalDateTime startTime, LocalDateTime finishTime, int minBet, String urlPicture) {
    if (productRepository.findByName(name) != null) {
      Product product = productRepository.findByName(name);
      return new ProductResponse(product.getProductId(), name, price, sellerId, startTime, finishTime, minBet, urlPicture);
    } else {
      Seller seller = sellerRepository.findById(sellerId).orElseThrow();
      seller.addProduct(name, price, startTime, finishTime, minBet, urlPicture);
      sellerRepository.save(seller);
      Product product = productRepository.findByName(name);
      productCreateGateway.createProduct(
          product.getProductId(), new Request.RequestToCreateProduct(name, price, sellerId, startTime, finishTime, minBet, urlPicture));
      return new ProductResponse(product.getProductId(), name, price, sellerId, startTime, finishTime, minBet, urlPicture);
    }
  }

  @Transactional
  public void deleteProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow();
    productRepository.delete(product);
  }

  @Transactional
  public int getProductPrice(Long id) {
    Product product = productRepository.findById(id).orElseThrow();
    return product.getPrice();
  }

  @Transactional
  public ProductResponse changeCurrentPrice(Long id, int newPrice) {
    Product product = productRepository.findById(id).orElseThrow();
    product.setPrice(newPrice);
    productRepository.save(product);
    return new ProductResponse(product.getProductId(), product.getName(), product.getPrice(), product.getSeller().getSellerId(), product.getStartTime(), product.getFinishTime(), product.getMinBet(), product.getUrlPicture());
  }

  @Transactional
  public Long getSellerByProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow();
    return product.getSeller().getSellerId();
  }

  @Transactional
  public ArrayList<Product> getAllProducts() {
    return new ArrayList<>(productRepository.findAll());
  }
}
