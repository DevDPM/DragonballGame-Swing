package nl.pokemon.game.util;

import javax.swing.*;

public class Portals {

    public static JFrame getFrameByPortalXY(int x, int y) {
        String xy = x + "-" + y;

        return switch (xy) {
            case "10-10" -> new JFrame();
            default -> null;
        };
    }
}
