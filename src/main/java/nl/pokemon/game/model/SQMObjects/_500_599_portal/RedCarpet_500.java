package nl.pokemon.game.model.SQMObjects._500_599_portal;

import nl.pokemon.game.enums.Classify;
import nl.pokemon.game.model.BasePortalSQM;
import nl.pokemon.game.enums.Destination;

import javax.swing.*;

public class RedCarpet_500 extends BasePortalSQM {

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

    @Override
    public ImageIcon getImageIcon() {
        return null;
    }

    @Override
    public Classify getClassify() {
        return Classify.PORTAL;
    }

    @Override
    public int getObjectNumber() {
        return 500;
    }

}
