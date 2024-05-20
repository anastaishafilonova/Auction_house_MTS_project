package com.example.demo.Customer.Controller;


import com.example.demo.Customer.Customer;
import com.example.demo.Customer.CustomerService;
import com.example.demo.Request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
  private CustomerService customerService;
  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }
  protected CustomerController(){}

  @PostMapping("")
  public CustomerResponse createCustomer(@RequestBody Request.RequestToCreateCustomer request) {
    return customerService.createCustomer(request.getFirstName(), request.getLastName());
  }

  @DeleteMapping("/{id}")
  public void deleteCustomer(@PathVariable Long id) {
    customerService.deleteCustomer(id);
  }

  @PutMapping("/increase/{id}")
  public CustomerResponse increaseBalance(@PathVariable Long id, @RequestBody Request.RequestToIncreaseBalance request) {
    return customerService.increaseBalance(id, request.getDelta());
  }

  @PutMapping("/decrease/{id}")
  public CustomerResponse decreaseBalance(@PathVariable Long id, @RequestBody Request.RequestToDecreaseBalance request) {
    return customerService.decreaseBalance(id, request.getDelta());
  }

  @GetMapping("/{id}")
  public int getBalanceCustomer(@PathVariable Long id) {
    return customerService.getBalanceCustomer(id);
  }

}
