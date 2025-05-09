package com.example.__unis_fest_back.service;

import com.example.__unis_fest_back.dto.DrawResponseDto;
import com.example.__unis_fest_back.dto.ResultDto;
import com.example.__unis_fest_back.dto.WinRequestDto;
import com.example.__unis_fest_back.entity.User;
import com.example.__unis_fest_back.entity.WinningEntry;
import com.example.__unis_fest_back.repository.UserRepository;
import com.example.__unis_fest_back.repository.WinningEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DrawService {
    private final UserRepository userRepository;
    private final WinningEntryRepository winningEntryRepository;

    public DrawResponseDto enter() {
        User user = new User(null, null, null, null, null, null);
        User saved = userRepository.save(user);

        DrawResponseDto response = new DrawResponseDto(saved.getOrderNumber(), null, null);
        if (winningEntryRepository.existsByUserId(saved.getOrderNumber())) {
            WinningEntry winningEntry = winningEntryRepository.findByUserId(saved.getOrderNumber());
            response.setIsWinner(true);
            response.setPrize(winningEntry.getPrizeName());
        }
        else response.setIsWinner(false);

        return response;
    }

    public void win(WinRequestDto request) {
        User user = userRepository.findById(request.getOrderNumber()).orElse(null);
        user.setCollegeName(request.getCollegeName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setReviewText(request.getReviewText());
        user.setEntryTime(LocalDateTime.now());
        user.setPrizeName(winningEntryRepository.findByUserId(user.getOrderNumber()).getPrizeName());
        userRepository.save(user);
    }

    public ArrayList<ResultDto> results() {
        ArrayList<User> users = userRepository.findTop10ByEntryTimeNotNullOrderByEntryTimeDesc();
        ArrayList<ResultDto> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(new ResultDto(
                    user.getCollegeName(),
                    user.getPrizeName(),
                    user.getEntryTime(),
                    user.getReviewText()
            ));
        }
        return responses;
    }
}
