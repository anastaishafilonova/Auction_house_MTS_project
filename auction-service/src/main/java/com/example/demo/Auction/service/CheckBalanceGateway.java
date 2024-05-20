package com.example.demo.Auction.service;

import com.example.demo.Auction.dto.CheckBalanceResponse;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class CheckBalanceGateway {
    private static RestTemplate restTemplate;

    @Autowired
    public CheckBalanceGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RateLimiter(name = "checkBalance", fallbackMethod = "fallbackRateLimiter")
    @CircuitBreaker(name = "checkBalance", fallbackMethod = "fallbackCircuitBreaker")
    @Retry(name = "checkBalance")
    public static boolean checkBalance(Long customerId, int price) {
        try {
            HttpHeaders headers = new HttpHeaders();
            ResponseEntity<CheckBalanceResponse> response = restTemplate.exchange(
                    "/api/money/check/balance/{customerId}",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    CheckBalanceResponse.class,
                    Map.of("customerId", customerId)
            );
            return response.getBody().balance() >= price;

        } catch (RestClientException e) {
            throw e;
        }
    }

    public boolean fallbackRateLimiter(String requestId, RequestNotPermitted e) {
        throw new RestClientException(e.getMessage());
    }

    public boolean fallbackCircuitBreaker(String requestId, CallNotPermittedException e) {
        throw new RestClientException(e.getMessage());
    }
}
