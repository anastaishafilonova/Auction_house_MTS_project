package com.example.demo.controller;


import com.example.demo.repository.AuctionRepository;
import com.example.demo.service.AuctionService;
import com.example.demo.dto.*;
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
    public ProductCreateResponse createProduct(@NotNull @RequestBody @Valid ProductRequestToCreate request) {
        AuctionService.createAuction(request);
        return new ProductCreateResponse(true);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@NotNull @PathVariable Long id) {
        AuctionService.deleteProduct(id);
    }

    @PutMapping("/increase/bet/{id}")
    public AuctionResponse increaseBet(@NotNull @PathVariable Long id,
                                       @NotNull @RequestBody @Valid ProductRequestToUpdate request) {
        return AuctionService.increaseBet(id, request);
    }

    @GetMapping("/current/price/{productId}")
    public CurrentPriceInfo getCurPrice(@NotNull @PathVariable Long productId) {
        return new CurrentPriceInfo(productId, AuctionService.getCurPrice(productId));
    }
}
