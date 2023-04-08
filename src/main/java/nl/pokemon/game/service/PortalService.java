package nl.pokemon.game.service;

import nl.pokemon.game.util.Portals;
import org.dpmFramework.annotation.Service;

import javax.swing.*;

@Service
public class PortalService {

    public JFrame getFrameByPortalXY(int x, int y) {
        return Portals.getFrameByPortalXY(x,y);
    }
}
