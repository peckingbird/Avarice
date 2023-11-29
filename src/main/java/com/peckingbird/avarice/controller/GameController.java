package com.peckingbird.avarice.controller;

import com.peckingbird.avarice.domain.GameStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    @MessageMapping("/player/{playerId}/join/{gameId}")
    @SendTo("/topic/status/{gameId}")
    public GameStatus joinGame(@DestinationVariable String playerId,
                               @DestinationVariable String gameId) {
        return new GameStatus(null, null, null, null);
    }


}
