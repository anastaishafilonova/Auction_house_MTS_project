package com.example.demo.Product;

import com.example.demo.Request.ProductRequestToCreate;
import com.example.demo.Request.Request;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;


@Service
public class ProductCreateGateway {
    @Autowired
    private static RestTemplate restTemplate;

    @Autowired
    public ProductCreateGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RateLimiter(name = "createProduct", fallbackMethod = "fallbackRateLimiter")
    @CircuitBreaker(name = "createProduct", fallbackMethod = "fallbackCircuitBreaker")
    @Retry(name = "createProduct")
    public Boolean createProduct(long productId, Request.RequestToCreateProduct request) {
        try {
            ProductRequestToCreate productRequestToCreate = new ProductRequestToCreate(productId, request.getStartTime(), request.getFinishTime(), request.getMinBet(), request.getPrice(), -1, request.getSellerId());
            HttpHeaders headers = new HttpHeaders();

            restTemplate.exchange(
                    "/api/auction/create",
                    HttpMethod.POST,
                    new HttpEntity<>(productRequestToCreate, headers),
                    ProductCreateResponse.class
            );
        } catch (RestClientException e) {
            throw e;
        }
        return true;
    }

    public boolean fallbackRateLimiter(String requestId, RequestNotPermitted e) {
        throw new RestClientException(e.getMessage());
    }

    public boolean fallbackCircuitBreaker(String requestId, CallNotPermittedException e) {
        throw new RestClientException(e.getMessage());
    }
}
