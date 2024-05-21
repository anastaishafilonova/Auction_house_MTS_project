package com.example.demo.exception;

public class CheckBalanceException extends Exception {
    public CheckBalanceException(Exception e) {
        super(e);
    }
}
