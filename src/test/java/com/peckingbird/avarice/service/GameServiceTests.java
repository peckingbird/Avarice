package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Game;
import com.peckingbird.avarice.domain.GameState;
import com.peckingbird.avarice.exception.InvalidStateException;
import com.peckingbird.avarice.exception.NotFoundException;
import com.peckingbird.avarice.presets.GamePresets;
import com.peckingbird.avarice.repository.GameRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameServiceTests {
  private GameService gameService;
  @Mock private GameRepository gameRepository;

  @BeforeEach
  void setup() {
    this.gameService = new GameService(gameRepository);
  }

  @Test
  void givenNoExistingGame_whenCreateGame_thenGameCreated() {
    Game expectedGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);

    Mockito.when(gameRepository.findById(expectedGame.gameId())).thenReturn(Optional.empty());

    gameService.createGame(expectedGame.gameId(), GamePresets.CURRENT_TURN);

    Mockito.verify(gameRepository, Mockito.times(1).description("Request made to save new game"))
        .save(expectedGame);
  }

  @Test
  void givenGameWithIdAlreadyExists_whenCreateGame_thenGameNotCreated() {
    Game expectedGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);

    Mockito.when(gameRepository.findById(expectedGame.gameId()))
        .thenReturn(Optional.of(expectedGame));

    gameService.createGame(expectedGame.gameId(), GamePresets.CURRENT_TURN);

    Mockito.verify(gameRepository, Mockito.times(0).description("No request made to save a game"))
        .save(expectedGame);
  }

  @Test
  void givenGameInFinalRoundAndNewStateIsEnded_whenSetState_thenGameEnded() {
    Game finalRoundGame =
        new Game(GamePresets.GAME_ID, GameState.FINAL_ROUND, GamePresets.CURRENT_TURN);
    Game endedGame =
        new Game(finalRoundGame.gameId(), GameState.ENDED, finalRoundGame.currentTurn());

    Mockito.when(gameRepository.findById(finalRoundGame.gameId()))
        .thenReturn(Optional.of(finalRoundGame));

    gameService.setState(finalRoundGame.gameId(), GameState.ENDED);

    Mockito.verify(
            gameRepository,
            Mockito.times(1).description("Request made to save game with updated state"))
        .save(endedGame);
  }

  @Test
  void givenGameIsActiveAndNewStateIsEnded_whenSetState_thenInvalidStateException() {
    Game activeGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);

    Mockito.when(gameRepository.findById(activeGame.gameId())).thenReturn(Optional.of(activeGame));

    Assertions.assertThrows(
        InvalidStateException.class,
        () -> gameService.setState(GamePresets.GAME_ID, GameState.ENDED));
  }

  @Test
  void givenGameIsEndedAndNewStateIsEnded_whenSetState_thenNoChange() {
    Game endedGame = new Game(GamePresets.GAME_ID, GameState.ENDED, GamePresets.CURRENT_TURN);

    Mockito.when(gameRepository.findById(endedGame.gameId())).thenReturn(Optional.of(endedGame));

    gameService.setState(endedGame.gameId(), GameState.ENDED);

    Mockito.verify(
            gameRepository, Mockito.times(0).description("No request made to update game state"))
        .save(Mockito.any(Game.class));
  }

  @Test
  void givenGameIsActiveAndNewStateIsFinalRound_whenSetState_thenGameSetToFinalRoundState() {
    Game activeGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);
    Game finalRoundGame =
        new Game(activeGame.gameId(), GameState.FINAL_ROUND, activeGame.currentTurn());

    Mockito.when(gameRepository.findById(activeGame.gameId())).thenReturn(Optional.of(activeGame));

    gameService.setState(activeGame.gameId(), GameState.FINAL_ROUND);

    Mockito.verify(
            gameRepository,
            Mockito.times(1).description("Request made to save game with updated state"))
        .save(finalRoundGame);
  }

  @Test
  void givenGameInFinalRoundAndNewStateIsFinalRound_whenSetState_thenNoChange() {
    Game finalRoundGame =
        new Game(GamePresets.GAME_ID, GameState.FINAL_ROUND, GamePresets.CURRENT_TURN);

    Mockito.when(gameRepository.findById(finalRoundGame.gameId()))
        .thenReturn(Optional.of(finalRoundGame));

    gameService.setState(finalRoundGame.gameId(), GameState.FINAL_ROUND);

    Mockito.verify(
            gameRepository, Mockito.times(0).description("No request made to update game state"))
        .save(Mockito.any(Game.class));
  }

  @Test
  void givenGameIsEndedAndNewStateIsFinalRound_whenSetState_thenInvalidStateException() {
    Game endedGame = new Game(GamePresets.GAME_ID, GameState.ENDED, GamePresets.CURRENT_TURN);

    Mockito.when(gameRepository.findById(endedGame.gameId())).thenReturn(Optional.of(endedGame));

    Assertions.assertThrows(
        InvalidStateException.class,
        () -> gameService.setState(GamePresets.GAME_ID, GameState.FINAL_ROUND));
  }

  @Test
  void givenExistingGameId_whenGetCurrentTurn_thenTurnIdReturned() {
    Game existingGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);

    Mockito.when(gameRepository.findById(existingGame.gameId()))
        .thenReturn(Optional.of(existingGame));

    Integer actualTurnId = gameService.getCurrentTurn(existingGame.gameId());

    Assertions.assertEquals(
        existingGame.currentTurn(), actualTurnId, "Returned current turn id matches expected");
  }

  @Test
  void givenNonExistingGameId_whenGetCurrentTurn_thenNotFoundException() {
    Mockito.when(gameRepository.findById(GamePresets.GAME_ID)).thenReturn(Optional.empty());

    Assertions.assertThrows(
        NotFoundException.class, () -> gameService.getCurrentTurn(GamePresets.GAME_ID));
  }

  @Test
  void givenExistingGameId_whenSetCurrentTurn_thenCurrentTurnUpdated() {
    Game preUpdateGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);
    Game expectedGame =
        new Game(preUpdateGame.gameId(), preUpdateGame.gameState(), GamePresets.ALT_CURRENT_TURN);

    Mockito.when(gameRepository.findById(preUpdateGame.gameId()))
        .thenReturn(Optional.of(preUpdateGame));

    gameService.setCurrentTurn(preUpdateGame.gameId(), GamePresets.ALT_CURRENT_TURN);

    Mockito.verify(
            gameRepository, Mockito.times(1).description("Request made to save updated game"))
        .save(expectedGame);
  }

  @Test
  void givenNonExistingGameId_whenSetCurrentTurn_thenNotFoundException() {
    Mockito.when(gameRepository.findById(GamePresets.GAME_ID)).thenReturn(Optional.empty());

    Assertions.assertThrows(
        NotFoundException.class,
        () -> gameService.setCurrentTurn(GamePresets.GAME_ID, GamePresets.ALT_CURRENT_TURN));
  }
}
