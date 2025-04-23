package com.example.__unis_fest_back.service;

import com.example.__unis_fest_back.dto.DrawInfoDto;
import com.example.__unis_fest_back.entity.User;
import com.example.__unis_fest_back.entity.WinningEntry;
import com.example.__unis_fest_back.repository.UserRepository;
import com.example.__unis_fest_back.repository.WinningEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final WinningEntryRepository winningEntryRepository;

    public ArrayList<DrawInfoDto> getDrawInfo() {
        ArrayList<User> users = userRepository.findByEntryTimeNotNullOrderByEntryTimeDesc();
        ArrayList<DrawInfoDto> responses = new ArrayList<>();
        for (User user : users) {
            WinningEntry winningEntry = winningEntryRepository.findByUser_OrderNumber(user.getOrderNumber());
            responses.add(new DrawInfoDto(
                    user.getOrderNumber(),
                    user.getEntryTime(),
                    user.getPhoneNumber(),
                    winningEntry.getPrizeName()
            ));
        }
        return responses;
    }
}
