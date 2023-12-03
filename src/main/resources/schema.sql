CREATE TABLE t_game(
    game_id VARCHAR(36) PRIMARY KEY,
    game_state VARCHAR(10),
    current_turn INT,
    UNIQUE(current_turn)
);

CREATE TABLE t_player (
    player_id VARCHAR(15) NOT NULL PRIMARY KEY
);

CREATE TABLE t_turn(
    turn_id SERIAL PRIMARY KEY,
    player_id VARCHAR(15) NOT NULL,
    running_score INT NOT NULL DEFAULT 0,
    available_dice INT NOT NULL DEFAULT 6,
    FOREIGN KEY(player_id) REFERENCES t_player(player_id)
);

CREATE TABLE t_game_player (
    game_player_id SERIAL PRIMARY KEY,
    player_id VARCHAR(15) NOT NULL,
    game_id VARCHAR(36) NOT NULL,
    FOREIGN KEY(game_id) REFERENCES t_game(game_id),
    FOREIGN KEY(player_id) REFERENCES t_player(player_id),
    UNIQUE(player_id, game_id)
);