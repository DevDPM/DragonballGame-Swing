package nl.pokemon.game.model.players;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;

import javax.swing.*;

public class Ash extends BaseEntity{

    public Ash() {
        ImageIcon icon = new ImageIcon("src/main/resources/images/entity/ash/3_stand-south.png");
        this.setImageIcon(icon);
        this.setVisible(true);
    }

    @Override
    public void setWalkingImage(Direction direction) {
        switch (direction) {
            case NORTH -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/ash/6_walk-north.gif"));
            case EAST -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/ash/5_walk-east.gif"));
            case SOUTH -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/ash/7_walk-south.gif"));
            case WEST -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/ash/8_walk-west.gif"));
        };
    }

    @Override
    public void setStandingImage(Direction direction) {
        switch (direction) {
            case NORTH -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/ash/2_stand-north.png"));
            case EAST -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/ash/1_stand-east.png"));
            case SOUTH -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/ash/3_stand-south.png"));
            case WEST -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/ash/4_stand-west.png"));
        };
    }

    @Override
    public int getObjectNumber() {
        return 0;
    }

    @Override
    public boolean isNotWalkable() {
        return true;
    }

    @Override
    public AreaType getAreaType() {
        return null;
    }
}
