package nl.pokemon.game.util;

import javax.swing.*;

public class Portals {

    public static Object[] getDestinationXYByPortalXY(int x, int y, Destination currentLocation) {
        String xyCurrentLocation = x + "-" + y + "-" + currentLocation;

        // x, y, Destination of currentMap, Destination of newMap
        return switch (xyCurrentLocation) {
            case "13-7-FULL_MAP" -> new Object[]{0,0,Destination.FULL_MAP, Destination.HOUSE};
            case "15-5-HOUSE" -> new Object[]{8,8,Destination.HOUSE, Destination.FULL_MAP};
            default -> throw new RuntimeException("Portal at: x" + x + " / y" + y + " does not exist.");
        };
    }
}
