package com.peckingbird.avarice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_game_player")
public record GamePlayer(@Id Integer gamePlayerId,
                         @Column String gameId,
                         @Column String playerId) { }
