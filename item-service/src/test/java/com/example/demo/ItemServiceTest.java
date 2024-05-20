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
    CustomerResponse customer = customerService.createCustomer("Степан", "Иванов");
    assertEquals(customer.firstName(), "Степан");
    assertEquals(customer.lastName(), "Иванов");
  }

  @Test
  public void deleteCustomerTest() {
    CustomerResponse customer = customerService.createCustomer("Степан", "Иванов");
    customerService.deleteCustomer(customer.customerId());
    assertEquals(customerRepository.findById(customer.customerId()), Optional.empty());
  }

  @Test
  public void increaseBalanceTest() {
    CustomerResponse customer = customerService.createCustomer("Степан", "Иванов");
    customerService.increaseBalance(customer.customerId(), 100);
    assertEquals(customerRepository.findByFirstNameAndLastName("Степан", "Иванов").getBalance(), 100);
  }

  @Test
  public void decreaseBalanceTest() {
    CustomerResponse customer = customerService.createCustomer("Joost", "Klein");
    customerService.increaseBalance(customer.customerId(), 200);
    customerService.decreaseBalance(customer.customerId(), 100);
    assertEquals(customerRepository.findByFirstNameAndLastName("Joost", "Klein").getBalance(), 100);
  }

  @Test
  public void getBalanceCustomerTest() {
    CustomerResponse customer = customerService.createCustomer("Marko", "Veisson");
    customerService.increaseBalance(customer.customerId(), 200);
    int balance = customerService.getBalanceCustomer(customer.customerId());
    assertEquals(balance, 200);
  }

  @Test
  public void createProductTest() {
    SellerResponse seller = sellerService.createSeller("Степан", "Иванов");
    ProductResponse product = productService.createProduct("Игрушка", 100, seller.sellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150, "");
    assertEquals(product.name(), "Игрушка");
    assertEquals(product.price(), 100);
    assertEquals(product.sellerId(), seller.sellerId());
    assertEquals(product.startTime(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30));
    assertEquals(product.finishTime(), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30));
    assertEquals(product.minBet(), 150);
  }

  @Test
  public void deleteProductTest() {
    SellerResponse seller = sellerService.createSeller("Kristjan", "Jakobson");
    ProductResponse product = productService.createProduct("Чипсы", 100, seller.sellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150, "");
    productService.deleteProduct(product.productId());
    assertEquals(productRepository.findById(product.productId()), Optional.empty());
  }

  @Test
  public void getProductPriceTest() {
    SellerResponse seller = sellerService.createSeller("Ramo", "Teder");
    ProductResponse product = productService.createProduct("Talharpa", 100, seller.sellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150, "");
    int price = productService.getProductPrice(product.productId());
    assertEquals(price, 100);
  }

  @Test
  public void changeCurrentPriceTest() {
    SellerResponse seller = sellerService.createSeller("Степан", "Иванов");
    ProductResponse product = productService.createProduct("Игрушка", 100, seller.sellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150, "");
    productService.changeCurrentPrice(product.productId(), 200);
    int price = productService.getProductPrice(product.productId());
    assertEquals(price, 200);
  }

  @Test
  public void getSellerByProductTest() {
    SellerResponse seller = sellerService.createSeller("Степан", "Иванов");
    ProductResponse product = productService.createProduct("Игрушка", 100, seller.sellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150, "");
    assertEquals(productService.getSellerByProduct(product.productId()), seller.sellerId());
  }

  @Test
  public void createSellerTest() {
    SellerResponse seller = sellerService.createSeller("Степан", "Иванов");
    assertEquals(seller.firstName(), "Степан");
    assertEquals(seller.lastName(), "Иванов");
  }

  @Test
  public void deleteSellerTest() {
    SellerResponse seller = sellerService.createSeller("Степан", "Иванов");
    sellerService.deleteSeller(seller.sellerId());
    assertEquals(sellerRepository.findById(seller.sellerId()), Optional.empty());
  }

}
