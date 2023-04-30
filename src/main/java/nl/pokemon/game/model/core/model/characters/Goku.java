package nl.pokemon.game.model.core.model.characters;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;

import javax.swing.*;

public class Goku extends BaseEntity{

    public Goku() {
        ImageIcon icon = new ImageIcon("src/main/resources/images/entity/goku/3_goku-stand-south.png");
        this.setImageIcon(icon);
        this.setVisible(true);
    }

    @Override
    public void setWalkingImage(Direction direction) {
        switch (direction) {
            case NORTH -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/goku/6_goku-walk-north.gif"));
            case EAST -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/goku/5_goku-walk-east.gif"));
            case SOUTH -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/goku/7_goku-walk-south.gif"));
            case WEST -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/goku/8_goku-walk-west.gif"));
        };
    }

    @Override
    public void setStandingImage(Direction direction) {
        switch (direction) {
            case NORTH -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/goku/2_goku-stand-north.png"));
            case EAST -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/goku/1_goku-stand-east.png"));
            case SOUTH -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/goku/3_goku-stand-south.png"));
            case WEST -> this.setImageIcon(new ImageIcon("src/main/resources/images/entity/goku/4_goku-stand-west.png"));
        };
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
