package nl.pokemon.game.model.walk;

import nl.pokemon.game.model.Walkable;

import javax.swing.*;

public class GravelPortal extends Gravel implements Walkable, Portable {

    private int destinationX;
    private int destinationY;

    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public int getDestinationFDMIndexY() {
        return this.destinationY;
    }

    @Override
    public int getDestinationFDMIndexX() {
        return this.destinationX;
    }

    @Override
    public void setDestinationFDMIndexXY(int x, int y) {
        this.destinationX = x;
        this.destinationY = y;
    }

}
