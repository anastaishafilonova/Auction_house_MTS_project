package com.example.demo.Product.Controller;

import com.example.demo.Request.Request;
import com.example.demo.Product.Product;
import com.example.demo.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductController {
  private ProductService productService;
  @Autowired
  public ProductController(ProductService productService) {
    this.productService = productService;
  }


  @PostMapping("")
  public ProductResponse createProduct(@RequestBody Request.RequestToCreateProduct request) {
    return productService.createProduct(
        request.getName(),
        request.getPrice(),
        request.getSellerId(),
        request.getStartTime(),
        request.getFinishTime(),
        request.getMinBet(),
        request.getUrlPicture());
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

  @GetMapping("/all")
  public ArrayList<ProductResponse> getAllProducts() {
    ArrayList<ProductResponse> result = new ArrayList<>();
    var products = productService.getAllProducts();
    for (Product product : products) {
      result.add(
          new ProductResponse(
              product.getProductId(),
              product.getName(),
              product.getPrice(),
              product.getSeller().getSellerId(),
              product.getStartTime(),
              product.getFinishTime(),
              product.getMinBet(),
              product.getUrlPicture()));
    }
    return result;
  }
}
