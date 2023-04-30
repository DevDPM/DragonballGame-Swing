package nl.pokemon.game.client.view;

import nl.pokemon.game.client.controller.GameController;
import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.model.FullTileMap;
import nl.pokemon.game.client.model.TileMap;
import nl.pokemon.game.core.model.tiles.BaseTile;
import nl.pokemon.game.domain.Session;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GamePanel extends JPanel {

    public GamePanel() {
        this.setLayout(null);
        this.setVisible(true);
        this.setFocusable(true);
        this.setBounds(0,0,800,800);
    }

    private void init() {
        this.add(Kickstarter.getInstanceOf(TimeBox.class));
        this.add(Kickstarter.getInstanceOf(DBCount.class));
        this.add(Kickstarter.getInstanceOf(FoundDragonball.class));
        this.add(Kickstarter.getInstanceOf(EndGamePanel.class));
        this.add(Kickstarter.getInstanceOf(DBCount.class));

        FullTileMap view = Kickstarter.getInstanceOf(FullTileMap.class);

        Map<Integer, Map<AreaType, TileMap>> fullMap = view.getFullTileMap();
        List<Integer> elevations = new ArrayList<>(fullMap.keySet());
        Collections.reverse(elevations);

        for (int elevation : elevations) {
            Map<AreaType, TileMap> layerMap = view.getFullTileMap().get(elevation);
            List<AreaType> areaTypes = new ArrayList<>(layerMap.keySet());
            Collections.sort(areaTypes);

            for (AreaType areaType : areaTypes) {
                TileMap areaMap = layerMap.get(areaType);
                for (int y = areaMap.getTileMap()[0].length-1; y >= 0; y--) {
                    for (int x = areaMap.getTileMap().length-1; x >= 0; x--) {
                        BaseTile sqm = areaMap.getTileMap()[y][x];
                        this.setVisible(true);
                        this.add(sqm);
                    }
                }
            }
        }
    }

    public void startGame() {
        Session session = Kickstarter.getInstanceOf(Session.class);
        session.start();
    }
}
