package com.peckingbird.avarice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_player")
public record Player(@Id @Column String playerId) { }
