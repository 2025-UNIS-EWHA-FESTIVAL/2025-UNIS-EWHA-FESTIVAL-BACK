package com.example.__unis_fest_back.service;

import com.example.__unis_fest_back.dto.DrawResponseDto;
import com.example.__unis_fest_back.dto.ResultDto;
import com.example.__unis_fest_back.dto.WinRequestDto;
import com.example.__unis_fest_back.entity.User;
import com.example.__unis_fest_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DrawService {
    public static final Map<Integer, String> WINNING_PRIZES = Map.ofEntries(
            Map.entry(1, "스타벅스 3만원 기프티콘"),
            Map.entry(3, "스타벅스 3만원 기프티콘"),
            Map.entry(5, "스타벅스 3만원 기프티콘"),
            Map.entry(7, "스타벅스 3만원 기프티콘"),
            Map.entry(9, "스타벅스 3만원 기프티콘"),
            Map.entry(11, "스타벅스 3만원 기프티콘"),
            Map.entry(13, "스타벅스 3만원 기프티콘"),
            Map.entry(15, "스타벅스 3만원 기프티콘"),
            Map.entry(17, "스타벅스 3만원 기프티콘"),
            Map.entry(19, "스타벅스 3만원 기프티콘"),
            Map.entry(21, "스타벅스 3만원 기프티콘")
    );
    private final UserRepository userRepository;

    public DrawResponseDto enter() {
        User user = new User(null, null, null, null, null);
        User saved = userRepository.save(user);

        DrawResponseDto response = new DrawResponseDto(saved.getOrderNumber(), null, null);
        if (WINNING_PRIZES.containsKey(saved.getOrderNumber())) {
            response.setIsWinner(true);
            response.setPrize(WINNING_PRIZES.get(saved.getOrderNumber()));
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
        userRepository.save(user);
    }

    public ArrayList<ResultDto> results() {
        ArrayList<User> users = userRepository.findTop10ByEntryTimeNotNullOrderByEntryTimeDesc();
        ArrayList<ResultDto> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(new ResultDto(
                    user.getCollegeName(),
                    WINNING_PRIZES.get(user.getOrderNumber()),
                    user.getEntryTime(),
                    user.getReviewText()
            ));
        }
        return responses;
    }
}
