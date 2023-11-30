package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Game;
import com.peckingbird.avarice.domain.GamePlayer;
import com.peckingbird.avarice.domain.GameState;
import com.peckingbird.avarice.domain.Player;
import com.peckingbird.avarice.exception.InvalidStateException;
import com.peckingbird.avarice.presets.GamePlayerPresets;
import com.peckingbird.avarice.presets.GamePresets;
import com.peckingbird.avarice.presets.PlayerPresets;
import com.peckingbird.avarice.repository.GamePlayerRepository;
import com.peckingbird.avarice.repository.GameRepository;
import com.peckingbird.avarice.repository.PlayerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTests {
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GamePlayerRepository gamePlayerRepository;
    @Mock
    private GameRepository gameRepository;

    @BeforeEach
    public void setup() {
        playerService = new PlayerService(playerRepository, gamePlayerRepository, gameRepository);
    }

    @Test
    void givenPlayersInGame_whenGetPlayersForGame_thenPlayerListReturned() {
        List<GamePlayer> gamePlayers = List.of(
                new GamePlayer(GamePlayerPresets.GAME_PLAYER_ID, GamePresets.GAME_ID, PlayerPresets.PLAYER_ID),
                new GamePlayer(GamePlayerPresets.ALT_GAME_PLAYER_ID, GamePresets.GAME_ID, PlayerPresets.ALT_PLAYER_ID));

        List<Player> expectedPlayerList = gamePlayers.stream()
                .map(gamePlayer -> new Player(gamePlayer.playerId()))
                .toList();

        Mockito.when(gamePlayerRepository.findByGameId(GamePresets.GAME_ID))
                .thenReturn(gamePlayers);
        expectedPlayerList.forEach(player -> {
            Mockito.when(playerRepository.findById(player.playerId()))
                    .thenReturn(Optional.of(player));
        });

        List<Player> actualPlayerList = playerService.getPlayersForGame(GamePresets.GAME_ID);

        Assertions.assertEquals(expectedPlayerList, actualPlayerList,
                "Player list returned matches players in the game");
    }

    @Test
    void givenNoPlayersInGame_whenGetPlayersForGame_thenEmptyList() {
        Mockito.when(gamePlayerRepository.findByGameId(GamePresets.GAME_ID))
                .thenReturn(List.of());

        List<Player> actualPlayerList = playerService.getPlayersForGame(GamePresets.GAME_ID);

        Assertions.assertEquals(List.of(), actualPlayerList,
                "Player List returned is empty");
    }

    @Test
    void givenPlayerInGameDoesNotExist_whenGetPlayersForGame_thenInvalidStateException() {
        List<GamePlayer> gamePlayers = List.of(
                new GamePlayer(GamePlayerPresets.GAME_PLAYER_ID, GamePresets.GAME_ID, PlayerPresets.PLAYER_ID),
                new GamePlayer(GamePlayerPresets.ALT_GAME_PLAYER_ID, GamePresets.GAME_ID, PlayerPresets.ALT_PLAYER_ID));

        Mockito.when(gamePlayerRepository.findByGameId(GamePresets.GAME_ID))
                .thenReturn(gamePlayers);

        Assertions.assertThrows(InvalidStateException.class, () ->
                playerService.getPlayersForGame(GamePresets.GAME_ID));
    }

    @Test
    void givenPlayerId_whenAddPlayerToGame_thenPlayerAdded() {
        Player expectedPlayer = new Player(PlayerPresets.PLAYER_ID);
        Game expectedGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, 1);
        GamePlayer expectedGamePlayer = new GamePlayer(null,
                expectedGame.gameId(), expectedPlayer.playerId());

        Mockito.when(playerRepository.findById(expectedPlayer.playerId()))
                .thenReturn(Optional.of(expectedPlayer));
        Mockito.when(gamePlayerRepository.save(expectedGamePlayer))
                        .thenReturn(expectedGamePlayer);
        Mockito.when(gamePlayerRepository
                        .findByGameIdAndPlayerId(expectedGamePlayer.gameId(), expectedPlayer.playerId()))
                .thenReturn(Optional.empty());
        Mockito.when(gameRepository.findById(expectedGamePlayer.gameId()))
                .thenReturn(Optional.of(expectedGame));

        playerService.addPlayerToGame(expectedPlayer.playerId(), expectedGamePlayer.gameId());
        Mockito.verify(gamePlayerRepository, Mockito.times(1)
                                .description("Request made to add player to game"))
                .save(expectedGamePlayer);
    }

    @Test
    void givenPlayerAlreadyInGame_whenAddPlayerToGame_thenPlayerNotAdded() {
        Player expectedPlayer = new Player(PlayerPresets.PLAYER_ID);
        Game expectedGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, 1);
        GamePlayer expectedGamePlayer = new GamePlayer(null,
                expectedGame.gameId(), expectedPlayer.playerId());

        Mockito.when(playerRepository.findById(expectedPlayer.playerId()))
                .thenReturn(Optional.of(expectedPlayer));
        Mockito.when(gamePlayerRepository
                        .findByGameIdAndPlayerId(expectedGamePlayer.gameId(), expectedPlayer.playerId()))
                .thenReturn(Optional.of(expectedGamePlayer));
        Mockito.when(gameRepository.findById(expectedGamePlayer.gameId()))
                .thenReturn(Optional.of(expectedGame));

        playerService.addPlayerToGame(expectedPlayer.playerId(),expectedGamePlayer.gameId());
        Mockito.verify(gamePlayerRepository, Mockito.times(0)
                        .description("No request made to add player to game"))
                .save(expectedGamePlayer);

    }

    @Test
    void givenPlayerNotExist_whenAddPlayerToGame_thenPlayerCreated() {
        Player expectedPlayer = new Player(PlayerPresets.PLAYER_ID);
        Game expectedGame = new Game(GamePresets.GAME_ID, GameState.ACTIVE, 1);
        GamePlayer expectedGamePlayer = new GamePlayer(null,
                expectedGame.gameId(), expectedPlayer.playerId());

        Mockito.when(playerRepository.findById(expectedPlayer.playerId()))
                .thenReturn(Optional.empty());
        Mockito.when(playerRepository.save(expectedPlayer))
                .thenReturn(expectedPlayer);
        Mockito.when(gamePlayerRepository.save(expectedGamePlayer))
                .thenReturn(expectedGamePlayer);
        Mockito.when(gamePlayerRepository
                        .findByGameIdAndPlayerId(expectedGamePlayer.gameId(), expectedPlayer.playerId()))
                .thenReturn(Optional.empty());
        Mockito.when(gameRepository.findById(expectedGamePlayer.gameId()))
                .thenReturn(Optional.of(expectedGame));

        playerService.addPlayerToGame(expectedPlayer.playerId(), expectedGamePlayer.gameId());
        Mockito.verify(playerRepository, Mockito.times(1)
                        .description("Request made to create player"))
                .save(expectedPlayer);
    }

    @Test
    void givenGameDoesNotExist_whenAddPlayerToGame_thenInvalidStateException() {
        Player expectedPlayer = new Player(PlayerPresets.PLAYER_ID);

        Mockito.when(playerRepository.findById(expectedPlayer.playerId()))
                .thenReturn(Optional.of(expectedPlayer));

        Assertions.assertThrows(InvalidStateException.class, () ->
                playerService.addPlayerToGame(PlayerPresets.PLAYER_ID, GamePresets.GAME_ID));
    }
}
