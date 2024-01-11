package com.peckingbird.avarice.service;

import com.peckingbird.avarice.domain.Turn;
import com.peckingbird.avarice.exception.NotFoundException;
import com.peckingbird.avarice.presets.PlayerPresets;
import com.peckingbird.avarice.presets.TurnPresets;
import com.peckingbird.avarice.repository.TurnRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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
        Turn expectedTurn = new Turn(null, PlayerPresets.PLAYER_ID,
                TurnPresets.STARTING_SCORE, TurnPresets.STARTING_DICE);

        Mockito.when(turnRepository.save(expectedTurn))
                .thenReturn(expectedTurn);

        turnService.createTurn(expectedTurn.playerId(), expectedTurn.availableDice());

        Mockito.verify(turnRepository, Mockito.times(1)
                        .description("call made to save new turn")).save(expectedTurn);
    }

    @Test
    void givenExistingTurnId_whenUpdatingRunningScore_thenScoreUpdated() {
        Turn expectedTurn = new Turn(TurnPresets.TURN_ID, PlayerPresets.PLAYER_ID,
                TurnPresets.STARTING_SCORE, TurnPresets.STARTING_DICE);

        Turn updatedTurn = new Turn(expectedTurn.turnId(), expectedTurn.playerId(),
                TurnPresets.RUNNING_SCORE, expectedTurn.availableDice());

        Mockito.when(turnRepository.findById(expectedTurn.turnId()))
                .thenReturn(Optional.of(expectedTurn));
        Mockito.when(turnRepository.save(updatedTurn))
                .thenReturn(updatedTurn);

        turnService.updateRunningScore(expectedTurn.turnId(), updatedTurn.runningScore());

        Mockito.verify(turnRepository, Mockito.times(1)
                        .description("Call made to update turn")).save(updatedTurn);
    }

    @Test
    void givenSameScore_whenUpdatingRunningScore_thenNoChange() {
        Turn expectedTurn = new Turn(TurnPresets.TURN_ID, PlayerPresets.PLAYER_ID,
                TurnPresets.STARTING_SCORE, TurnPresets.STARTING_DICE);

        Mockito.when(turnRepository.findById(expectedTurn.turnId()))
                .thenReturn(Optional.of(expectedTurn));

        turnService.updateRunningScore(expectedTurn.turnId(), TurnPresets.STARTING_SCORE);

        Mockito.verify(turnRepository, Mockito.times(0)
                .description("Call not made to update turn")).save(Mockito.any());
    }

    @Test
    void givenNonExistingTurnId_whenUpdatingRunningScore_thenNotFoundException() {
        Mockito.when(turnRepository.findById(TurnPresets.TURN_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            turnService.updateRunningScore(TurnPresets.TURN_ID, TurnPresets.RUNNING_SCORE);
        }).as("Not Found Exception Thrown").isInstanceOf(NotFoundException.class);
    }

    @Test
    void givenExistingTurnIdAndDifferentDiceNumber_whenUpdateAvailableDice_thenAvailableDiceUpdated() {
        Turn expectedTurn = new Turn(TurnPresets.TURN_ID, PlayerPresets.PLAYER_ID,
                TurnPresets.STARTING_SCORE, TurnPresets.STARTING_DICE);

        Turn updatedTurn = new Turn(expectedTurn.turnId(), expectedTurn.playerId(),
                expectedTurn.runningScore(), TurnPresets.AVAILABLE_DICE);

        Mockito.when(turnRepository.findById(expectedTurn.turnId()))
                .thenReturn(Optional.of(expectedTurn));
        Mockito.when(turnRepository.save((updatedTurn)))
                .thenReturn(updatedTurn);

        turnService.updateAvailableDice(expectedTurn.turnId(), updatedTurn.availableDice());

        Mockito.verify(turnRepository, Mockito.times(1)
                .description("Call made to update turn")).save(updatedTurn);
    }

    @Test
    void givenNonExistingTurnId_whenUpdateAvailableDice_thenNotFoundException() {
        Mockito.when(turnRepository.findById(TurnPresets.TURN_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            turnService.updateAvailableDice(TurnPresets.TURN_ID, TurnPresets.AVAILABLE_DICE);
        }).as("Not found exception is thrown").isInstanceOf(NotFoundException.class);
    }

    @Test
    void givenExistingTurnIdAndSameDiceNumber_whenUpdateAvailableDice_thenNoChange() {
        Turn expectedTurn = new Turn(TurnPresets.TURN_ID, PlayerPresets.PLAYER_ID,
                TurnPresets.STARTING_SCORE, TurnPresets.STARTING_DICE);

        Mockito.when(turnRepository.findById(expectedTurn.turnId()))
                .thenReturn(Optional.of(expectedTurn));

        turnService.updateAvailableDice(expectedTurn.turnId(), expectedTurn.availableDice());

        Mockito.verify(turnRepository, Mockito.times(0)
                .description("Call not made to update turn")).save(Mockito.any());
    }

    @Test
    void givenExistingTurnId_whenGetTurn_thenTurnReturned() {
        Turn expectedTurn = new Turn(TurnPresets.TURN_ID, PlayerPresets.PLAYER_ID,
                TurnPresets.STARTING_SCORE, TurnPresets.STARTING_DICE);

        Mockito.when(turnRepository.findById(expectedTurn.turnId()))
                .thenReturn(Optional.of(expectedTurn));

        Turn returnedTurn = turnService.getTurn(expectedTurn.turnId());

        Assertions.assertThat(returnedTurn)
                .as("returned turn matches expected turn")
                .isEqualTo(expectedTurn);
    }

    @Test
    void givenNonExistingTurnId_whenGetTurn_thenNotFoundException() {
        Mockito.when(turnRepository.findById(TurnPresets.TURN_ID))
                .thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> {
            turnService.getTurn(TurnPresets.TURN_ID);
        }).isInstanceOf(NotFoundException.class);
    }
}
