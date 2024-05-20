package com.example.demo.Auction.dto;

import java.time.LocalDateTime;

public record ProductRequestToCreate(long productid, LocalDateTime starttime, LocalDateTime endtime, int minbet, int curprice, long customerid, long sellerid) {
}
