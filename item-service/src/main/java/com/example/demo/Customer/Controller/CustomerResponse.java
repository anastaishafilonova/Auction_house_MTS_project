package com.example.demo.Customer.Controller;

import com.example.demo.Product.Product;

import java.util.ArrayList;

public record CustomerResponse(Long customerId, String firstName, String lastName, int balance, int bet) {}