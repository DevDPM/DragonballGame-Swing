package nl.pokemon.game.service;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.CurrentPlayer;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;

@Service
public class PlayerService {

    @Inject
    CurrentPlayer player;

    public void setCoordinationByDirection(Direction direction) {

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

    public void moveDirection(Direction direction) {
        ImageIcon icon;

        icon = switch (direction) {
            case NORTH -> new ImageIcon("src/main/resources/images/userWalk/walk-north.gif");
            case EAST -> new ImageIcon("src/main/resources/images/userWalk/walk-east.gif");
            case SOUTH -> new ImageIcon("src/main/resources/images/userWalk/walk-south.gif");
            case WEST -> new ImageIcon("src/main/resources/images/userWalk/walk-west.gif");
        };
        player.setIcon(icon);
    }

    public void standStill(Direction direction) {
        ImageIcon icon;

        icon = switch (direction) {
            case NORTH -> new ImageIcon("src/main/resources/images/userWalk/stand-north.png");
            case EAST -> new ImageIcon("src/main/resources/images/userWalk/stand-east.png");
            case SOUTH -> new ImageIcon("src/main/resources/images/userWalk/stand-south.png");
            case WEST -> new ImageIcon("src/main/resources/images/userWalk/stand-west.png");
        };
        player.setIcon(icon);
    }

    public void addElevation(int elevation) {
        player.setFDMIndexZ(player.getFDMIndexZ()+elevation);
        System.out.println(player.getFDMIndexZ());
    }
}
