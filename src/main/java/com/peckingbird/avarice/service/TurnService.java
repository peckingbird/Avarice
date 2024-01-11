package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Turn;
import com.peckingbird.avarice.exception.NotFoundException;
import com.peckingbird.avarice.repository.TurnRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TurnService {
    private TurnRepository turnRepository;
    private static final String TURN_NOT_FOUND = "Turn not found";

    public void createTurn(String playerId, Integer startingDice) {
        Turn turn = new Turn(null, playerId, 0, startingDice);
        turnRepository.save(turn);
    }

    public void updateRunningScore(Integer turnId, Integer newScore) {
        Turn turn = turnRepository.findById(turnId)
                .orElseThrow(() -> new NotFoundException(TURN_NOT_FOUND));

        if (!newScore.equals(turn.runningScore())) {
            Turn updatedTurn = new Turn(turn.turnId(), turn.playerId(), newScore, turn.availableDice());
            turnRepository.save(updatedTurn);
        }
    }

    public void updateAvailableDice(Integer turnId, Integer availableDice) {
        Turn turn = turnRepository.findById(turnId)
                .orElseThrow(() -> new NotFoundException(TURN_NOT_FOUND));

        if(!availableDice.equals(turn.availableDice())) {
            Turn updatedTurn = new Turn(turn.turnId(), turn.playerId(),
                    turn.runningScore(), availableDice);
            turnRepository.save(updatedTurn);
        }
    }

    public Turn getTurn(Integer turnId) {
        return turnRepository.findById(turnId)
                .orElseThrow(() -> new NotFoundException(TURN_NOT_FOUND));
    }
}
