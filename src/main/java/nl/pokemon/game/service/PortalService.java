package nl.pokemon.game.service;

import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.model.CurrentPlayer;
import nl.pokemon.game.model.walk.Portable;
import nl.pokemon.game.util.Portals;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;

@Service
public class PortalService {

    @Inject
    SQMService sqmService;

    @Inject
    CurrentPlayer player;

    public boolean isPortalAndTeleport() {
        BaseSQM portalSQM = sqmService.getBaseSQMfromCurrentXY();
        if (portalSQM != null) {
            Portable portal = (Portable) portalSQM;
            if (player.getViewMap().equals(portal.getCurrentDestinationFDM())) {
                player.setViewMap(portal.getNewDestinationFDM());
                player.setFDMIndexY(portal.getDestinationFDMIndexY());
                player.setFDMIndexX(portal.getDestinationFDMIndexX());
                return true;
            } else {
                System.out.println("An unknown portal was stepped on, but not wired.");
            }
        }
        return false;
    }
}
