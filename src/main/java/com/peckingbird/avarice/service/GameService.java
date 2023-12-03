package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Game;
import com.peckingbird.avarice.domain.GameState;
import com.peckingbird.avarice.exception.InvalidStateException;
import com.peckingbird.avarice.exception.NotFoundException;
import com.peckingbird.avarice.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {
  private GameRepository gameRepository;

  public void createGame(String gameId, Integer firstTurn) {
    if (gameRepository.findById(gameId).isEmpty()) {
      gameRepository.save(new Game(gameId, GameState.ACTIVE, firstTurn));
    }
  }

  public void setState(String gameId, GameState newGameState) {
    Game game = findGame(gameId);
    boolean isOneStateForward = newGameState.ordinal() == game.gameState().ordinal() + 1;
    if (isOneStateForward) {
      gameRepository.save(new Game(game, newGameState));
    } else if (game.gameState() != newGameState) {
      throw new InvalidStateException(
          String.format(
              "Cannot move game id %s, from %s to %s", gameId, game.gameState(), newGameState));
    }
  }

  public Integer getCurrentTurn(String gameId) {
    Game game = findGame(gameId);
    return game.currentTurn();
  }

  public void setCurrentTurn(String gameId, Integer turnId) {
    Game game = findGame(gameId);
    gameRepository.save(new Game(game, turnId));
  }

  private Game findGame(String gameId) {
    return gameRepository
        .findById(gameId)
        .orElseThrow(
            () -> new NotFoundException(String.format("No game found with id: %s", gameId)));
  }
}
