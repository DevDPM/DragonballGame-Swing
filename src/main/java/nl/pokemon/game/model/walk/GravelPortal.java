package nl.pokemon.game.model.walk;

import nl.pokemon.game.model.Walkable;
import nl.pokemon.game.util.Destination;

public class GravelPortal extends Gravel implements Walkable, Portable {

    private int destinationX;
    private int destinationY;
    private Destination currentLocation;
    private Destination newDestination;

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
    public Destination getNewDestinationFDM() {
        return newDestination;
    }

    @Override
    public Destination getCurrentDestinationFDM() {
        return currentLocation;
    }

    @Override
    public void setDestinationFDMIndexXY(int x, int y, Destination currentLocation, Destination destination) {
        this.destinationX = x;
        this.destinationY = y;
        this.currentLocation = currentLocation;
        this.newDestination = destination;

    }

}
