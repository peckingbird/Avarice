package com.peckingbird.avarice.domain;

import java.util.List;

/**
 * Response object from which a game client can update.
 * @param gameId - The unique identifier for a game.
 * @param playerList - A list of players participating in a game.
 * @param turn - Details regarding the current turn.
 * @param gameState - Whether the game is active, ending, or ended.
 */
public record GameStatus(String gameId, List<Player> playerList, Turn turn, GameState gameState) { }
