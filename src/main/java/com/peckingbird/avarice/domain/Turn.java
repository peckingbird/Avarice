package com.peckingbird.avarice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_turn")
public record Turn(@Id Integer turnId,
                   @Column String playerId,
                   @Column Integer runningScore,
                   @Column Integer availableDice) {
}
