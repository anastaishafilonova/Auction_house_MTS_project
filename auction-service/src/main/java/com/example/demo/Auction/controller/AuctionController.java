package com.example.demo.Auction.controller;

import com.example.demo.Auction.repository.AuctionRepository;
import com.example.demo.Auction.dto.ProductRequestToCreate;
import com.example.demo.Auction.dto.ProductRequestToUpdate;
import com.example.demo.Auction.entity.Auction;
import com.example.demo.Auction.service.AuctionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auction")
@Validated
public class AuctionController {
    private final AuctionRepository auctionRepository;

    public AuctionController(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @PostMapping("/create")
    public Boolean createProduct(@NotNull @RequestBody @Valid ProductRequestToCreate request) {
        AuctionService.createAuction(request);
        return true;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@NotNull @PathVariable Long id) {
        AuctionService.deleteProduct(id);
    }

    @PutMapping("/increase/bet/{id}")
    public Auction increaseBet(@NotNull @PathVariable Long id,
                               @NotNull @RequestBody @Valid ProductRequestToUpdate request) {
        return AuctionService.increaseBet(id, request);
    }
}
