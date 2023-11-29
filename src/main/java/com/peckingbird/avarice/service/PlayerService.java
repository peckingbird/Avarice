package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Game;
import com.peckingbird.avarice.domain.GamePlayer;
import com.peckingbird.avarice.domain.Player;
import com.peckingbird.avarice.exception.BadRequestException;
import com.peckingbird.avarice.exception.InvalidStateException;
import com.peckingbird.avarice.repository.GamePlayerRepository;
import com.peckingbird.avarice.repository.GameRepository;
import com.peckingbird.avarice.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayerService {
    private PlayerRepository playerRepository;
    private GamePlayerRepository gamePlayerRepository;
    private GameRepository gameRepository;

    public List<Player> getPlayersForGame(String gameId) {
        if (Objects.isNull(gameId)) {
            throw new BadRequestException("Cannot get players for a null game id");
        }

        List<GamePlayer> gamePlayers = gamePlayerRepository.findByGameId(gameId);
        return gamePlayers.stream()
                .map(gamePlayer -> playerRepository.findById(gamePlayer.playerId())
                                .orElseThrow(() ->
                                        new InvalidStateException(String.format("Player not found with id: %s",
                                                gamePlayer.playerId()))))
                .toList();
    }

    public void addPlayerToGame(String playerId, String gameId) {
        if (Objects.isNull(playerId)) {
            throw new BadRequestException("Cannot add null as a player");
        }
        if (Objects.isNull(gameId)) {
            throw new BadRequestException("Cannot add player to game id of null");
        }

        Player player = playerRepository.findById(playerId)
                .orElseGet(() -> playerRepository.save(new Player(playerId)));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new InvalidStateException(String.format("Game not found with id: %s", gameId)));

        Optional<GamePlayer> gamePlayer = gamePlayerRepository
                .findByGameIdAndPlayerId(game.getGameId(), player.playerId());

        if (gamePlayer.isEmpty()) {
            gamePlayerRepository
                    .save(new GamePlayer(null, game.getGameId(), player.playerId()));
        }
    }
}