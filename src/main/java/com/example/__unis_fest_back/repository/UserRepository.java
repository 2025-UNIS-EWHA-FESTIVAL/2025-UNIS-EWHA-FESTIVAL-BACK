package com.example.__unis_fest_back.repository;

import com.example.__unis_fest_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User, Integer> {
    ArrayList<User> findTop10ByEntryTimeNotNullOrderByEntryTimeDesc();

    ArrayList<User> findByPhoneNumberNotNullOrderByOrderNumberAsc();
}
