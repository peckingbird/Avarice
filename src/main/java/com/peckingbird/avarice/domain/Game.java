package com.peckingbird.avarice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="t_game")
public record Game(@Id String gameId,
                   @Column GameState gameState,
                   @Column Integer currentTurn) { }
