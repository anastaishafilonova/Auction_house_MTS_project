package com.example.demo.Auction.service;

import com.example.demo.Auction.dto.AuctionResponse;
import com.example.demo.Auction.dto.ProductRequestToCreate;
import com.example.demo.Auction.dto.ProductRequestToUpdate;
import com.example.demo.Auction.entity.Auction;
import com.example.demo.Auction.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionService {
  private static final Logger LOGGER = LoggerFactory.getLogger(AuctionService.class);
  private static AuctionRepository auctionRepository;

  @Autowired
  public AuctionService(AuctionRepository auctionRepository) {
    this.auctionRepository = auctionRepository;
  }

  @Transactional
  public static Auction createAuction(ProductRequestToCreate request) {
    Auction auction =
        new Auction(
            request.productid(),
            "Аукцион",
            request.starttime(),
            request.endtime(),
            request.minbet(),
            request.curprice(),
            request.customerid(),
            request.sellerid());
    return auctionRepository.save(auction);
  }

  @Transactional
  public static void deleteProduct(long id) {
    auctionRepository.deleteById(id);
  }

  @Transactional
  public static AuctionResponse increaseBet(long id, ProductRequestToUpdate request) {
    LOGGER.info("Retrieved: " + request.bet() + " " + request.customerid() + " " + id);
    Auction auction = auctionRepository.findById(id).orElseThrow();

    if (request.bet() - auction.getCurprice() >= auction.getMinbet()
        && CheckBalanceGateway.checkBalance(request.customerid(), request.bet())) {
      auctionRepository.save(
          new Auction(
              auction.getProductid(),
              auction.getStatus(),
              auction.getStarttime(),
              auction.getEndtime(),
              auction.getMinbet(),
              request.bet(),
              request.customerid(),
              auction.getSellerid()));
      return new AuctionResponse(
          auction.getProductid(),
          auction.getStatus(),
          auction.getStarttime(),
          auction.getEndtime(),
          auction.getMinbet(),
          request.bet(),
          request.customerid(),
          auction.getSellerid());
    } else
      return new AuctionResponse(
          auction.getProductid(),
          auction.getStatus(),
          auction.getStarttime(),
          auction.getEndtime(),
          auction.getMinbet(),
          auction.getCurprice(),
          auction.getCustomerid(),
          auction.getSellerid());
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
