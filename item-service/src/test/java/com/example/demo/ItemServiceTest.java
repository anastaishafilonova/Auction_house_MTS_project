package com.example.demo;

import com.example.demo.Customer.Controller.CustomerResponse;
import com.example.demo.Customer.Customer;
import com.example.demo.Customer.CustomerRepository;
import com.example.demo.Customer.CustomerService;
import com.example.demo.Product.Controller.ProductResponse;
import com.example.demo.Product.ProductRepository;
import com.example.demo.Product.ProductService;
import com.example.demo.Seller.Controller.SellerResponse;
import com.example.demo.Seller.Seller;
import com.example.demo.Seller.SellerRepository;
import com.example.demo.Seller.SellerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({CustomerService.class, ProductService.class, SellerService.class})
public class ItemServiceTest extends DatabaseSuite {
  @Autowired
  private CustomerService customerService;
  @Autowired
  private ProductService productService;
  @Autowired
  private SellerService sellerService;
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private SellerRepository sellerRepository;
  @Test
  public void createCustomerTest() {
    Customer customer = customerService.createCustomer("Степан", "Иванов");
    assertEquals(customer.getFirstName(), "Степан");
    assertEquals(customer.getLastName(), "Иванов");
  }

  @Test
  public void deleteCustomerTest() {
    Customer customer = customerService.createCustomer("Степан", "Иванов");
    customerService.deleteCustomer(customer.getCustomerId());
    assertEquals(customerRepository.findById(customer.getCustomerId()), Optional.empty());
  }

  @Test
  public void increaseBalanceTest() {
    Customer customer = customerService.createCustomer("Степан", "Иванов");
    customerService.increaseBalance(customer.getCustomerId(), 100);
    assertEquals(customerRepository.findByFirstNameAndLastName("Степан", "Иванов").getBalance(), 100);
  }

  @Test
  public void decreaseBalanceTest() {
    Customer customer = customerService.createCustomer("Степан", "Иванов");
    customerService.increaseBalance(customer.getCustomerId(), 200);
    customerService.decreaseBalance(customer.getCustomerId(), 100);
    assertEquals(customerRepository.findByFirstNameAndLastName("Степан", "Иванов").getBalance(), 100);
  }

  @Test
  public void getBalanceCustomerTest() {
    Customer customer = customerService.createCustomer("Степан", "Иванов");
    customerService.increaseBalance(customer.getCustomerId(), 200);
    int balance = customerService.getBalanceCustomer(customer.getCustomerId());
    assertEquals(balance, 200);
  }

  @Test
  public void createProductTest() {
    Seller seller = sellerService.createSeller("Степан", "Иванов");
    ProductResponse product = productService.createProduct("Игрушка", 100, seller.getSellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150);
    assertEquals(product.name(), "Игрушка");
    assertEquals(product.price(), 100);
    assertEquals(product.sellerId(), seller.getSellerId());
    assertEquals(product.startTime(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30));
    assertEquals(product.finishTime(), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30));
    assertEquals(product.minBet(), 150);
  }

  @Test
  public void deleteProductTest() {
    Seller seller = sellerService.createSeller("Степан", "Иванов");
    ProductResponse product = productService.createProduct("Игрушка", 100, seller.getSellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150);
    productService.deleteProduct(product.productId());
    assertEquals(customerRepository.findById(product.productId()), Optional.empty());
  }

  @Test
  public void getProductPriceTest() {
    Seller seller = sellerService.createSeller("Степан", "Иванов");
    ProductResponse product = productService.createProduct("Игрушка", 100, seller.getSellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150);
    int price = productService.getProductPrice(product.productId());
    assertEquals(price, 100);
  }

  @Test
  public void changeCurrentPriceTest() {
    Seller seller = sellerService.createSeller("Степан", "Иванов");
    ProductResponse product = productService.createProduct("Игрушка", 100, seller.getSellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150);
    productService.changeCurrentPrice(product.productId(), 200);
    int price = productService.getProductPrice(product.productId());
    assertEquals(price, 200);
  }

  @Test
  public void getSellerByProductTest() {
    Seller seller = sellerService.createSeller("Степан", "Иванов");
    ProductResponse product = productService.createProduct("Игрушка", 100, seller.getSellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150);
    assertEquals(productService.getSellerByProduct(product.productId()), seller.getSellerId());
  }

  @Test
  public void createSellerTest() {
    Seller seller = sellerService.createSeller("Степан", "Иванов");
    assertEquals(seller.getFirstName(), "Степан");
    assertEquals(seller.getLastName(), "Иванов");
  }

  @Test
  public void deleteSellerTest() {
    Seller seller = sellerService.createSeller("Степан", "Иванов");
    sellerService.deleteSeller(seller.getSellerId());
    assertEquals(sellerRepository.findById(seller.getSellerId()), Optional.empty());
  }

}
