package nl.pokemon.game.model.walk;

import nl.pokemon.game.util.Destination;

public interface Portable {

    int getDestinationFDMIndexY();
    int getDestinationFDMIndexX();
    Destination getNewDestinationFDM();
    Destination getCurrentDestinationFDM();
    void setDestinationFDMIndexXY(int x, int y, Destination currentLocation, Destination classFile);

}
