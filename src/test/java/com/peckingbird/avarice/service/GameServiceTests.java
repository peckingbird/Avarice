package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Game;
import com.peckingbird.avarice.domain.GameState;
import com.peckingbird.avarice.exception.InvalidStateException;
import com.peckingbird.avarice.exception.NotFoundException;
import com.peckingbird.avarice.presets.GamePresets;
import com.peckingbird.avarice.repository.GameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class GameServiceTests {
    private GameService gameService;
    @Mock
    private GameRepository gameRepository;

    @BeforeEach
    void setup() {
        this.gameService = new GameService(gameRepository);
    }
    @Test
    void givenNoExistingGame_whenCreateGame_thenGameCreated() {
        Game expectedGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);

        Mockito.when(gameRepository.findById(expectedGame.gameId()))
                .thenReturn(Optional.empty());

        gameService.createGame(expectedGame.gameId());

        Mockito.verify(gameRepository,
                Mockito.times(1)
                        .description("Request made to save new game"))
                .save(expectedGame);
    }

    @Test
    void givenGameWithIdAlreadyExists_whenCreateGame_thenGameNotCreated() {
        Game expectedGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);

        Mockito.when(gameRepository.findById(expectedGame.gameId()))
                .thenReturn(Optional.of(expectedGame));

        gameService.createGame(expectedGame.gameId());

        Mockito.verify(gameRepository,
                        Mockito.times(0)
                                .description("No request made to save a game"))
                .save(expectedGame);
    }

    @Test
    void givenGameInFinalRoundState_whenEndGame_thenGameEnded() {
        Game finalRoundGame = new Game(GamePresets.GAME_ID, GameState.FINAL_ROUND, GamePresets.CURRENT_TURN);
        Game endedGame = new Game(finalRoundGame.gameId(), GameState.ENDED, finalRoundGame.currentTurn());

        Mockito.when(gameRepository.findById(finalRoundGame.gameId()))
                .thenReturn(Optional.of(finalRoundGame));

        gameService.endGame(finalRoundGame.gameId());

        Mockito.verify(gameRepository,
                Mockito.times(1)
                        .description("Request made to save game with updated state"))
                .save(endedGame);
    }

    @Test
    void givenGameInActiveState_whenEndGame_thenInvalidStateException() {
        Game activeGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);

        Mockito.when(gameRepository.findById(activeGame.gameId()))
                .thenReturn(Optional.of(activeGame));

        Assertions.assertThrows(InvalidStateException.class, () -> gameService.endGame(GamePresets.GAME_ID));
    }

    @Test
    void givenGameInEndedState_whenEndGame_thenNoChange() {
        Game endedGame = new Game(GamePresets.GAME_ID, GameState.ENDED, GamePresets.CURRENT_TURN);

        Mockito.when(gameRepository.findById(endedGame.gameId()))
                .thenReturn(Optional.of(endedGame));

        gameService.endGame(endedGame.gameId());

        Mockito.verify(gameRepository,
                        Mockito.times(0)
                                .description("No request made to update game state"))
                .save(Mockito.any(Game.class));
    }

    @Test
    void givenGameInActiveState_whenSetRoundToFinal_thenGameSetToFinalRoundState() {
        Game activeGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);
        Game finalRoundGame = new Game(activeGame.gameId(), GameState.FINAL_ROUND, activeGame.currentTurn());

        Mockito.when(gameRepository.findById(activeGame.gameId()))
                .thenReturn(Optional.of(activeGame));

        gameService.endGame(activeGame.gameId());

        Mockito.verify(gameRepository,
                        Mockito.times(1)
                                .description("Request made to save game with updated state"))
                .save(finalRoundGame);
    }

    @Test
    void givenGameInFinalRoundState_whenSetRoundToFinal_thenNoChange() {
        Game finalRoundGame = new Game(GamePresets.GAME_ID, GameState.FINAL_ROUND, GamePresets.CURRENT_TURN);

        Mockito.when(gameRepository.findById(finalRoundGame.gameId()))
                .thenReturn(Optional.of(finalRoundGame));

        gameService.endGame(finalRoundGame.gameId());

        Mockito.verify(gameRepository,
                        Mockito.times(0)
                                .description("No request made to update game state"))
                .save(Mockito.any(Game.class));
    }

    @Test
    void givenGameInEndedState_whenSetRoundToFinal_thenInvalidStateException() {
        Game endedGame = new Game(GamePresets.GAME_ID, GameState.ENDED, GamePresets.CURRENT_TURN);

        Mockito.when(gameRepository.findById(endedGame.gameId()))
                .thenReturn(Optional.of(endedGame));

        Assertions.assertThrows(InvalidStateException.class, () -> gameService.endGame(GamePresets.GAME_ID));
    }

    @Test
    void givenExistingGameId_whenGetCurrentTurn_thenTurnIdReturned() {
        Game existingGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);

        Mockito.when(gameRepository.findById(existingGame.gameId()))
                .thenReturn(Optional.of(existingGame));

        Integer actualTurnId = gameService.getCurrentTurn(existingGame.gameId());

        Assertions.assertEquals(existingGame.currentTurn(), actualTurnId,
                "Returned current turn id matches expected");
    }

    @Test
    void givenNonExistingGameId_whenGetCurrentTurn_thenNotFoundException() {
        Mockito.when(gameRepository.findById(GamePresets.GAME_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> gameService.getCurrentTurn(GamePresets.GAME_ID));
    }

    @Test
    void givenExistingGameId_whenSetCurrentTurn_thenCurrentTurnUpdated() {
        Game preUpdateGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, GamePresets.CURRENT_TURN);
        Game expectedGame = new Game(preUpdateGame.gameId(), preUpdateGame.gameState(), GamePresets.ALT_CURRENT_TURN);

        Mockito.when(gameRepository.findById(preUpdateGame.gameId()))
                .thenReturn(Optional.of(preUpdateGame));

        Game updatedGame = gameService.setCurrentTurn(preUpdateGame.gameId(), GamePresets.ALT_CURRENT_TURN);

        Assertions.assertAll(() -> {
            Assertions.assertNotEquals(preUpdateGame, updatedGame,
                    "Returned game's current turn id no long matches original game's current turn id");
            Assertions.assertEquals(expectedGame, updatedGame,
                    "Returned game's current turn id matches expected current turn id");
        });
    }

    @Test
    void givenNonExistingGameId_whenSetCurrentTurn_thenNotFoundException() {
        Mockito.when(gameRepository.findById(GamePresets.GAME_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> {
            gameService.setCurrentTurn(GamePresets.GAME_ID, GamePresets.ALT_CURRENT_TURN);
        });
    }
}
