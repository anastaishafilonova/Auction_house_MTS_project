package com.example.demo.Seller.Controller;


import com.example.demo.Request.Request;
import com.example.demo.Seller.Seller;
import com.example.demo.Seller.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class SellerController {
  private SellerService sellerService;

  @Autowired
  public SellerController(SellerService sellerService) {
    this.sellerService = sellerService;
  }

  protected SellerController(){}

  @PostMapping("")
  public SellerResponse createSeller(@RequestBody Request.RequestToCreateSeller request) {
    return sellerService.createSeller(request.getFirstName(), request.getLastName());
  }

  @DeleteMapping("/{id}")
  public void deleteSeller(@PathVariable Long id) {
    sellerService.deleteSeller(id);
  }
}
