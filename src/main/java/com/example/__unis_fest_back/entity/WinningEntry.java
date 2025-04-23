package com.example.__unis_fest_back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WinningEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_order_number", referencedColumnName = "order_number")
    private User user;

    @Column(name = "prize_name", length = 100)
    private String prizeName;
}
