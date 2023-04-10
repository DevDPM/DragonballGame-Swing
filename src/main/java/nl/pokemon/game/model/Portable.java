package nl.pokemon.game.model;

import nl.pokemon.game.enums.Destination;

public interface Portable {

    int getDestinationFDMIndexY();
    int getDestinationFDMIndexX();
    Destination getNewDestinationFDM();
    Destination getCurrentDestinationFDM();
    void setDestinationFDMIndexXY(int x, int y, Destination currentLocation, Destination classFile);

}
