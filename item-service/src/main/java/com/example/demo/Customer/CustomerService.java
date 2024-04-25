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

  @Transactional
  public Customer createCustomer(String firstName, String lastName){
    if (customerRepository.findByFirstNameAndLastName(firstName, lastName) != null) {
      return customerRepository.findByFirstNameAndLastName(firstName, lastName);
    } else {
      Customer customer = new Customer(firstName, lastName);
      return customerRepository.save(customer);
    }
  }

  @Transactional
  public void deleteCustomer(Long id){
    Customer customer = customerRepository.findById(id).orElseThrow();
    customerRepository.delete(customer);
  }

  @Transactional
  public int getBalanceCustomer(Long id){
    Customer customer = customerRepository.findById(id).orElseThrow();
    return customer.getBalance();
  }

  @Transactional
  public CustomerResponse increaseBalance(Long id, int delta){
    Customer customer = customerRepository.findById(id).orElseThrow();
    customer.setBalance(customer.getBalance() + delta);
    return new CustomerResponse(customer.getCustomerId(), customer.getFirstName(), customer.getLastName());
  }

  @Transactional
  public CustomerResponse decreaseBalance(Long id, int delta){
    Customer customer = customerRepository.findById(id).orElseThrow();
    customer.setBalance(customer.getBalance() - delta);
    return new CustomerResponse(customer.getCustomerId(), customer.getFirstName(), customer.getLastName());
  }
}
