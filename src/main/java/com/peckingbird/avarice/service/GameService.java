package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Game;
import com.peckingbird.avarice.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {
    private GameRepository gameRepository;

    public Game createGame(String gameId) {
        return null;
    }

    public Game endGame(String gameId) {
        return null;
    }

    public Game setTurnToFinal(String gameId) {
        return null;
    }

    public Game updateTurn(String gameId, String turnId) {
        return null;
    }

    public Integer getCurrentTurn(String gameId) {
        return null;
    }
}
