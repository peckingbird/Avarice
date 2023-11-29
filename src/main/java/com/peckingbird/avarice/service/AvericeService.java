package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.GameStatus;
import com.peckingbird.avarice.repository.GamePlayerRepository;
import com.peckingbird.avarice.repository.GameRepository;
import com.peckingbird.avarice.repository.PlayerRepository;
import com.peckingbird.avarice.repository.TurnRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AvericeService {
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private GamePlayerRepository gamePlayer;
    private TurnRepository turnRepository;

    public GameStatus addPlayer() {
        return null;
    }
    public GameStatus saveScore() {
        return null;
    }
    public GameStatus roll() {
        return null;
    }
    private void validateScore() { }
}
