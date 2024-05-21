package com.example.demo.Request;

import java.time.LocalDateTime;

public record ProductRequestToCreate(long productid, LocalDateTime starttime, LocalDateTime endtime, int minbet, int curprice, long customerid, long sellerid) {
}