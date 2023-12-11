package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Turn;
import com.peckingbird.avarice.repository.TurnRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TurnService {
    private TurnRepository turnRepository;

    public void createTurn(String playerId, String startingDice) { }

    public void updateRunningScore(String turnId) { }

    public void updateAvailableDice(String turnId, Integer availableDice) { }

    public Turn getTurn(String turnId) {
        return null;
    }
}
