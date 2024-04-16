package com.example.demo.Customer;

import com.example.demo.Customer.Controller.CustomerResponse;
import com.example.demo.Product.Product;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomerService {
  private CustomerRepository customerRepository;
  private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  protected CustomerService() {}
  @Transactional
  public CustomerResponse createCustomer(String firstName, String lastName, int balance, int bet){
    return null;
  }

//  @Transactional
//  public void updateCustomer(Long id, String firstName, String lastName, int balance, int bet){}
  @Transactional
  public void deleteCustomer(Long id){
  }

  @Transactional
  public int getBalance(Long id){
    return 0;
  }

  @Transactional
  public void increaseBalance(Long id, int delta){
  }

  @Transactional
  public void decreaseBalance(Long id, int delta){
  }




}
