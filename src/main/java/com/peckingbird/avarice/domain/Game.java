package com.peckingbird.avarice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name="t_game")
@AllArgsConstructor
public class Game {
    @Id
    private String gameId;
    @Column
    private GameState gameState;
    @Column
    private Integer turn;
}
