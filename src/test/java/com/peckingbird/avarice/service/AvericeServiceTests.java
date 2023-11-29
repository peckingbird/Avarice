package com.peckingbird.avarice.service;

import com.peckingbird.avarice.repository.GamePlayerRepository;
import com.peckingbird.avarice.repository.GameRepository;
import com.peckingbird.avarice.repository.PlayerRepository;
import com.peckingbird.avarice.repository.TurnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AvericeServiceTests {
    private AvericeService avericeService;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private GamePlayerRepository gamePlayerRepository;
    @Mock
    private TurnRepository turnRepository;

    @BeforeEach
    public void setup() {
        this.avericeService = new AvericeService(gameRepository, playerRepository, gamePlayerRepository, turnRepository);
    }

    @Test
    void givenValidInputs_whenAddPlayer_thenGameStatusReturned() {

    }

    @Test
    void givenGameNotExist_whenAddPlayer_thenGameCreated() {

    }

    @Test void givenPlayerAlreadyAdded_whenAddPlayer_thenPlayerNotAdded() {

    }

    @Test
    void givenEndedGame_whenAddPlayer_thenPlayerNotAdded() {

    }

    @Test
    void givenNullGameId_whenAddPlayer_thenBadRequestException() {

    }

    @Test
    void givenNullPlayerId_whenAddPlayer_thenBadRequestException() {

    }
}
