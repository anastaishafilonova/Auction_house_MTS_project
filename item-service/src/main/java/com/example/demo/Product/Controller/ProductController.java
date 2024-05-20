package com.example.demo.Product.Controller;

import com.example.demo.Product.ProductRepository;
import com.example.demo.Request.ProductRequestToCreate;
import com.example.demo.Request.Request;
import com.example.demo.Product.Product;
import com.example.demo.Product.ProductService;
import com.example.demo.Seller.Seller;
import com.example.demo.Seller.SellerRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@RestController
@RequestMapping("/api/product")
public class ProductController {
  private ProductService productService;

  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }


  @PostMapping("")
  public ProductResponse createProduct(@NotNull @RequestBody @Valid Request.RequestToCreateProduct request) {
    return productService.createProduct(request);
  }

  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
  }

  @PutMapping("/{id}")
  public ProductResponse changeCurrentPrice(@PathVariable Long id, @RequestBody Request.RequestToChangeCurrentPrice request) {
    return productService.changeCurrentPrice(id, request.getNewPrice());
  }

  @GetMapping("/price/{id}")
  public int getProductPrice(@PathVariable Long id) {
    return productService.getProductPrice(id);
  }

  @GetMapping("/seller/{id}")
  public Long getSellerByProduct(@PathVariable Long id) {
    return productService.getSellerByProduct(id);
  }
}
