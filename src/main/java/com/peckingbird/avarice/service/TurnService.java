package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Turn;
import com.peckingbird.avarice.repository.TurnRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TurnService {
    private TurnRepository turnRepository;

    public Turn createTurn(String playerId) {
        return null;
    }

    public Turn updateRunningScore(String turnId) {
        return null;
    }
}
