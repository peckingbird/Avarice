package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Game;
import com.peckingbird.avarice.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {
    private GameRepository gameRepository;

    public void createGame(String gameId) {
    }

    public void endGame(String gameId) {
    }

    public Game setRoundToFinal(String gameId) {
        return null;
    }

    public Integer getCurrentTurn(String gameId) { return null; }

    public Game setCurrentTurn(String gameId, Integer turnId) {
        return null;
    }
}
