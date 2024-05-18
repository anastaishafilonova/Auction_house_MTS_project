package com.example.demo.Product;

import com.example.demo.Customer.Controller.CustomerResponse;
import com.example.demo.Customer.CustomerRepository;
import com.example.demo.Customer.CustomerService;
import com.example.demo.Product.Controller.ProductResponse;
import com.example.demo.Request.ProductRequestToCreate;
import com.example.demo.Request.Request;
import com.example.demo.Seller.Seller;
import com.example.demo.Seller.SellerRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
public class ProductService {
  private ProductRepository productRepository;
  private SellerRepository sellerRepository;

  @Autowired
  public ProductService(ProductRepository productRepository, SellerRepository sellerRepository) {
    this.productRepository = productRepository;
    this.sellerRepository = sellerRepository;
  }

  private static final Logger logger = LoggerFactory.getLogger(ProductService.class);



  protected ProductService() {
  }

  @Transactional
  public ProductResponse createProduct(Request.RequestToCreateProduct request) {
    Product product;
    if (productRepository.findByName(request.getName()) != null) {
      product = productRepository.findByName(request.getName());
    } else {
      Seller seller = sellerRepository.findById(request.getSellerId()).orElseThrow();
      seller.addProduct(request.getName(), request.getPrice(), request.getStartTime(), request.getFinishTime(), request.getMinBet());
      sellerRepository.save(seller);
      product = productRepository.findByName(request.getName());
    }
    ProductCreateGateway.createProduct(productRepository.findByName(request.getName()).getProductId(), request);
    return new ProductResponse(product.getProductId(), request.getName(), request.getPrice(), request.getSellerId(), request.getStartTime(), request.getFinishTime(), request.getMinBet());
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
    return new ProductResponse(product.getProductId(), product.getName(), product.getPrice(), product.getSeller().getSellerId(), product.getStartTime(), product.getFinishTime(), product.getMinBet());
  }

  @Transactional
  public Long getSellerByProduct(Long id) {
    Product product = productRepository.findById(id).orElseThrow();
    return product.getSeller().getSellerId();
  }
}
