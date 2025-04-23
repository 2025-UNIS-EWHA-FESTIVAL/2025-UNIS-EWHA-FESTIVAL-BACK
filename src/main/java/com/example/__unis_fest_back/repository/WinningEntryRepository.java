package com.example.__unis_fest_back.repository;

import com.example.__unis_fest_back.entity.WinningEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WinningEntryRepository extends JpaRepository<WinningEntry, Integer> {
    boolean existsByUser_OrderNumber(Integer orderNumber);

    WinningEntry findByUser_OrderNumber(Integer orderNumber);
}
