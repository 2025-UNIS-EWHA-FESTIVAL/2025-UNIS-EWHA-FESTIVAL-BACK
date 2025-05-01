package com.example.__unis_fest_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "phone_number", length = 11)
    private String phoneNumber;

    @Column(name = "review_text", length = 50)
    private String reviewText;

    @Column(name = "college_name", length = 20)
    private String collegeName;
}
