package nl.pokemon.game.model.walk;

import nl.pokemon.game.model.Walkable;

import javax.swing.*;

public class GravelPortal extends Gravel implements Walkable, Portable {

    @Override
    public boolean isNotWalkable() {
        return false;
    }

    @Override
    public JFrame getFrameByPortalXY(int x, int y) {
        System.out.println(x+"-"+y);
        return null;
    }

}
