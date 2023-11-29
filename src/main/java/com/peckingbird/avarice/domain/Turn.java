package com.peckingbird.avarice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "t_turn")
public class Turn {
    @Id
    private Integer turnId;
    @Column
    private String playerId;
    @Column
    private Integer runningScore;
    @Column
    private Integer availableDice;
}
