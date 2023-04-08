package nl.pokemon.game.util;

import javax.swing.*;

public class Portals {

    public static int[] getDestinationXYByPortalXY(int x, int y) {
        String xy = x + "-" + y;

        return switch (xy) {
            case "13-7" -> new int[]{0,0};
            default -> throw new RuntimeException("Portal at: x" + x + " / y" + y + " does not exist.");
        };
    }
}
