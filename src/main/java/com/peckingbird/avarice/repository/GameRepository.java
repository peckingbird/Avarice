package com.peckingbird.avarice.repository;

import com.peckingbird.avarice.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, String> { }
