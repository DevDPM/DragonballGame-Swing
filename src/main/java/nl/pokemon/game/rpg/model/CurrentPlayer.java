package nl.pokemon.game.rpg.model;

import nl.pokemon.game.rpg.service.ViewService;

import javax.swing.*;
import java.awt.*;

public class CurrentPlayer extends JLabel implements Movable {

    int x = 380;
    int y = 330;
    int FDMIndexX = 8;
    int FDMIndexY = 8;

    int speed = 10;

    public CurrentPlayer() {
        this.setBounds(x, y,50,50);
        this.setBackground(Color.CYAN);
        ImageIcon icon = new ImageIcon("src/main/resources/images/userWalk/stand-south.png");
        this.setIcon(icon);
        this.setVisible(true);
    }

    public void moveDirection(ViewService.Direction direction) {
        ImageIcon icon;

        icon = switch (direction) {
            case NORTH -> new ImageIcon("src/main/resources/images/userWalk/walk-north.gif");
            case EAST -> new ImageIcon("src/main/resources/images/userWalk/walk-east.gif");
            case SOUTH -> new ImageIcon("src/main/resources/images/userWalk/walk-south.gif");
            case WEST -> new ImageIcon("src/main/resources/images/userWalk/walk-west.gif");
        };
        this.setIcon(icon);
    }

    public void standStill(ViewService.Direction direction) {
        ImageIcon icon;

        icon = switch (direction) {
            case NORTH -> new ImageIcon("src/main/resources/images/userWalk/stand-north.png");
            case EAST -> new ImageIcon("src/main/resources/images/userWalk/stand-east.png");
            case SOUTH -> new ImageIcon("src/main/resources/images/userWalk/stand-south.png");
            case WEST -> new ImageIcon("src/main/resources/images/userWalk/stand-west.png");
        };
        this.setIcon(icon);
    }



    public int getFDMIndexX() {
        return FDMIndexX;
    }

    public void setFDMIndexX(int FDMIndexX) {
        this.FDMIndexX = FDMIndexX;
    }

    public int getFDMIndexY() {
        return FDMIndexY;
    }

    public void setFDMIndexY(int FDMIndexY) {
        this.FDMIndexY = FDMIndexY;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }


}
