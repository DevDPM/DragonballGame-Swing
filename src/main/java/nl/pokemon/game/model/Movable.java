package nl.pokemon.game.model;

import nl.pokemon.game.service.Direction;

public interface Movable {

    void moveDirection(Direction direction);
    void standStill(Direction direction);
}
