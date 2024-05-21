package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "auction")
public class Auction {
    @Id
    @NotNull(message = "Product_id have to be filled")
    private long productid;

    @NotNull(message = "Status have to be filled")
    private String status;

    @NotNull(message = "StartTime have to be filled")
    private LocalDateTime starttime;

    @NotNull(message = "EndTime have to be filled")
    private LocalDateTime endtime;

    @NotNull(message = "MinBet have to be filled")
    private int minbet;

    @NotNull(message = "CurPrice have to be filled")
    private int curprice;

    @NotNull(message = "Customer_id have to be filled")
    private long customerid;

    @NotNull(message = "Seller_id have to be filled")
    private long sellerid;

    protected Auction(){
    }

    public Auction(long productid, String status, LocalDateTime starttime, LocalDateTime endtime, int minbet, int curprice, long customerid, long sellerid) {
        this.productid = productid;
        this.status = status;
        this.starttime = starttime;
        this.endtime = endtime;
        this.minbet = minbet;
        this.curprice = curprice;
        this.customerid = customerid;
        this.sellerid = sellerid;
    }

    public long getProductid() {
        return productid;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getStarttime() {
        return starttime;
    }

    public LocalDateTime getEndtime() {
        return endtime;
    }

    public int getMinbet() {
        return minbet;
    }

    public int getCurprice() {
        return curprice;
    }

    public long getCustomerid() {
        return customerid;
    }

    public long getSellerid() {
        return sellerid;
    }

    public void setProductid(long productid) {
        this.productid = productid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStarttime(LocalDateTime starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(LocalDateTime endtime) {
        this.endtime = endtime;
    }

    public void setMinbet(int minbet) {
        this.minbet = minbet;
    }

    public void setCurprice(int curprice) {
        this.curprice = curprice;
    }

    public void setCustomerid(long customerid) {
        this.customerid = customerid;
    }

    public void setSellerid(long sellerid) {
        this.sellerid = sellerid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return productid == auction.productid && minbet == auction.minbet && curprice == auction.curprice && customerid == auction.customerid && sellerid == auction.sellerid && Objects.equals(status, auction.status) && Objects.equals(starttime, auction.starttime) && Objects.equals(endtime, auction.endtime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productid, status, starttime, endtime, minbet, curprice, customerid, sellerid);
    }
}
