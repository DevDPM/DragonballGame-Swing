package nl.pokemon.game.rpg.model;

import nl.pokemon.game.rpg.service.ViewService;

public interface Movable {

    void moveDirection(ViewService.Direction direction);
    void standStill(ViewService.Direction direction);
}
