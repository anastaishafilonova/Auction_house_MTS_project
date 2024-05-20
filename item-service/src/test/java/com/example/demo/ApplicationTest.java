package com.example.demo;

import com.example.demo.Customer.Controller.CustomerResponse;
import com.example.demo.Customer.Customer;
import com.example.demo.Customer.CustomerRepository;
import com.example.demo.Customer.CustomerService;
import com.example.demo.Product.Controller.ProductResponse;
import com.example.demo.Product.ProductCreateGateway;
import com.example.demo.Product.ProductRepository;
import com.example.demo.Product.ProductService;
import com.example.demo.Request.Request;
import com.example.demo.Seller.Controller.SellerResponse;
import com.example.demo.Seller.Seller;
import com.example.demo.Seller.SellerRepository;
import com.example.demo.Seller.SellerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import({CustomerService.class, ProductService.class, SellerService.class})
public class ApplicationTest extends DatabaseSuite {

  @MockBean
  private ProductCreateGateway productCreateGateway;
  @Autowired private TestRestTemplate rest;
  @Autowired private CustomerRepository customerRepository;
  @Autowired private ProductRepository productRepository;
  @Autowired private SellerRepository sellerRepository;
  @Autowired private CustomerService customerService;
  @Autowired private ProductService productService;
  @Autowired private SellerService sellerService;


  private Customer customer;
  private Seller seller;
  private ProductResponse product;

  @BeforeEach
  public void beforeEach() {
    customerRepository.deleteAll();
    productRepository.deleteAll();
    sellerRepository.deleteAll();
  }

  @Test
  void shouldCreateCustomer() {
    ResponseEntity<Customer> createCustomer;
    createCustomer = rest.postForEntity("/api/customer", new Request.RequestToCreateCustomer(1l,"Федор", "Солодов"), Customer.class);
    assertTrue(createCustomer.getStatusCode().is2xxSuccessful(), "Unexpected status code: " + createCustomer.getStatusCode());
    Customer createCustomerBody = createCustomer.getBody();
    Assertions.assertEquals(1l, createCustomerBody.getCustomerId());
    Assertions.assertEquals("Федор", createCustomerBody.getFirstName());
    Assertions.assertEquals("Солодов", createCustomerBody.getLastName());
  }

  @Test
  void shouldSellerCustomer() {
    ResponseEntity<Seller> createSeller;
    createSeller = rest.postForEntity("/api/seller", new Request.RequestToCreateSeller(2l,"Никита", "Васильев"), Seller.class);
    assertTrue(createSeller.getStatusCode().is2xxSuccessful(), "Unexpected status code: " + createSeller.getStatusCode());
    Seller createSellerBody = createSeller.getBody();
    Assertions.assertEquals(2l, createSellerBody.getSellerId());
    Assertions.assertEquals("Никита", createSellerBody.getFirstName());
    Assertions.assertEquals("Васильев", createSellerBody.getLastName());
  }

  @Test
  void shouldCreateProduct() {
    when(productCreateGateway.createProduct(anyLong(), any())).thenReturn(true);
    ResponseEntity<Seller> createSeller;
    createSeller = rest.postForEntity("/api/seller", new Request.RequestToCreateSeller(3L, "Данил", "Васильев"), Seller.class);
    Seller createSellerBody = createSeller.getBody();

    ResponseEntity<ProductResponse> createProduct;
    createProduct = rest.postForEntity("/api/product", new Request.RequestToCreateProduct("Talharpa", 100, createSellerBody.getSellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150, ""), ProductResponse.class);

    assertTrue(createProduct.getStatusCode().is2xxSuccessful(), "Unexpected status code: " + createProduct.getStatusCode());
    ProductResponse createProductResponseBody = createProduct.getBody();
    Assertions.assertEquals("Talharpa", createProductResponseBody.name());
    Assertions.assertEquals(100, createProductResponseBody.price());
    Assertions.assertEquals(createSellerBody.getSellerId(), createProductResponseBody.sellerId());
    Assertions.assertEquals(LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), createProductResponseBody.startTime());
    Assertions.assertEquals(LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), createProductResponseBody.finishTime());
    Assertions.assertEquals(150, createProductResponseBody.minBet());
  }
  @Test
  void shouldDeleteCustomer() {
    CustomerResponse customerSecond = customerService.createCustomer(4l,"Кирилл", "Петров");
    ResponseEntity<Void> deleteCustomer;
    deleteCustomer = rest.exchange("/api/customer/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class, Map.of("id", customerSecond.customerId()));
    assertTrue(deleteCustomer.getStatusCode().is2xxSuccessful(), "Unnexpected status code: " + deleteCustomer.getStatusCode());
  }

  @Test
  void shouldDeleteSeller() {
    SellerResponse sellerSecond = sellerService.createSeller(5l, "Кирилл", "Иванов");
    ResponseEntity<Void> deleteSeller;
    deleteSeller = rest.exchange("/api/seller/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class, Map.of("id", sellerSecond.sellerId()));
    assertTrue(deleteSeller.getStatusCode().is2xxSuccessful(), "Unnexpected status code: " + deleteSeller.getStatusCode());
  }

  @Test
  void shouldDeleteProduct() {
    SellerResponse sellerSecond = sellerService.createSeller(6l,"Кирилл", "Игнатьев");
    ProductResponse productSecond = productService.createProduct("Игрушка", 100, sellerSecond.sellerId(), LocalDateTime.of(2024, Month.APRIL, 8, 12, 30), LocalDateTime.of(2024, Month.APRIL, 10, 12, 30), 150, "");
    ResponseEntity<Void> deleteProduct;
    deleteProduct = rest.exchange("/api/product/{id}", HttpMethod.DELETE, HttpEntity.EMPTY, Void.class, Map.of("id", productSecond.productId()));
    assertTrue(deleteProduct.getStatusCode().is2xxSuccessful(), "Unnexpected status code: " + deleteProduct.getStatusCode());
  }
}
