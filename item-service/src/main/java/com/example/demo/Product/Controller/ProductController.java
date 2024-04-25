package com.example.demo.Product.Controller;

import com.example.demo.Request.Request;
import com.example.demo.Product.Product;
import com.example.demo.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/product")
public class ProductController {
  private ProductService productService;
  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }


  @PostMapping("")
  public Product createProduct(@RequestBody Request.RequestToCreateProduct request) {
    return productService.createProduct(request.getName(), request.getPrice(), request.getSellerId(), request.getStartTime(), request.getFinishTime(), request.getMinBet());
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
