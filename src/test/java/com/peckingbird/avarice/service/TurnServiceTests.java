package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Turn;
import com.peckingbird.avarice.presets.PlayerPresets;
import com.peckingbird.avarice.repository.TurnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TurnServiceTests {
    private TurnService turnService;
    @Mock
    private TurnRepository turnRepository;

    @BeforeEach
    public void setup() {
        this.turnService = new TurnService(turnRepository);
    }

    @Test
    void givenPlayerIdAndStartingDice_whenCreateTurn_thenTurnCreated() {
        Turn expectedTurn = new Turn(null, PlayerPresets.PLAYER_ID, 0, 6);

        Mockito.when(turnRepository.save(expectedTurn))
                .thenReturn(expectedTurn);


    }

    @Test
    void givenExistingTurnId_whenUpdatingRunningScore_thenScoreUpdated() {

    }

    @Test
    void givenNonExistingTurnId_whenUpdatingRunningScore_thenNotFoundException() {

    }

    @Test
    void givenExistingTurnIdAndDifferentDiceNumber_whenUpdateAvailableDice_thenAvailableDiceUpdated() {

    }

    @Test
    void givenNonExistingTurnId_whenUpdateAvailableDice_thenNotFoundException() {

    }

    @Test
    void givenExistingTurnIdAndSameDiceNumber_whenUpdateAvailableDice_thenNoChange() {

    }

    @Test
    void givenExistingTurnId_whenGetTurn_thenTurnReturned() {

    }

    @Test
    void givenNonExistingTurnId_whenGetTurn_thenNotFoundException() {

    }
}
