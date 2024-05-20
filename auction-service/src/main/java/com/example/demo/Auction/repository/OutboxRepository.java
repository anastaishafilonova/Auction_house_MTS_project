package com.example.demo.Auction.repository;

import com.example.demo.Auction.entity.OutboxRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<OutboxRecord, Long> {
}
