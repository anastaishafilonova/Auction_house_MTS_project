package com.example.demo.Auction.service;

import com.example.demo.Auction.dto.ProductRequestToCreate;
import com.example.demo.Auction.dto.ProductRequestToUpdate;
import com.example.demo.Auction.entity.Auction;
import com.example.demo.Auction.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuctionService {
    private static AuctionRepository auctionRepository;

    @Autowired
    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Transactional
    public static Auction createAuction(ProductRequestToCreate request) {
        Auction auction = new Auction(request.productid(), "Аукцион", request.starttime(), request.endtime(), request.minbet(), request.curprice(), request.customerid(), request.sellerid());
        return auctionRepository.save(auction);
    }

    @Transactional
    public static void deleteProduct(long id) {
        auctionRepository.deleteById(id);
    }

    @Transactional
    public static Auction increaseBet(long id, ProductRequestToUpdate request) {
        Auction auction = auctionRepository.findById(id).orElseThrow();
        if (request.bet() - auction.getCurprice() >= auction.getMinbet() && CheckBalanceGateway.checkBalance(request.customerid(), request.bet())) return auctionRepository.save(new Auction(auction.getProductid(), auction.getStatus(), auction.getStarttime(), auction.getEndtime(), auction.getMinbet(), request.bet(), request.customerid(), auction.getSellerid()));
        else return auction;
    }

    @Transactional
    public static int getCurPrice(long id) {
        Auction auction = auctionRepository.findById(id).orElseThrow();
        return auction.getCurprice();
    }

    @Transactional
    public static void process(PayResult result) {
        Auction auction = auctionRepository.findById(result.productId()).orElseThrow();
        if (!result.resultStatus().equals("cancelled")) auction.setStatus("Аукцион закрыт");
    }
}