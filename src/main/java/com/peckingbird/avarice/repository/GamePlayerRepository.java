package com.peckingbird.avarice.repository;

import com.peckingbird.avarice.domain.GamePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GamePlayerRepository extends JpaRepository<GamePlayer, Integer> {
    List<GamePlayer> findByGameId(String gameId);
    Optional<GamePlayer> findByGameIdAndPlayerId(String gameId, String playerId);
}
