package nl.pokemon.game.service;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.CurrentPlayer;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class PlayerService {

    @Inject
    CurrentPlayer player;

    public void setXYByDirection(Direction direction) {

        switch (direction) {
            case NORTH -> {
                player.setFDMIndexY(player.getFDMIndexY() - 1);
            }
            case EAST -> {
                player.setFDMIndexX(player.getFDMIndexX() + 1);
            }
            case SOUTH -> {
                player.setFDMIndexY(player.getFDMIndexY() + 1);
            }
            case WEST -> {
                player.setFDMIndexX(player.getFDMIndexX() - 1);
            }
        }
    }
}
