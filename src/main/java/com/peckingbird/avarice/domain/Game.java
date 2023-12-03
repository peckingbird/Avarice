package com.peckingbird.avarice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_game")
public record Game(@Id String gameId, @Column GameState gameState, @Column Integer currentTurn) {
  public Game(Game game, GameState gameState) {
    this(game.gameId, gameState, game.currentTurn);
  }

  public Game(Game game, Integer currentTurn) {
    this(game.gameId, game.gameState, currentTurn);
  }
}
