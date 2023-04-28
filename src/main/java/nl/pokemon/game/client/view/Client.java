package nl.pokemon.game.client.view;

import nl.pokemon.game.bootstrap.Bootstrap;
import nl.pokemon.game.client.controller.RpgController;
import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.core.model.Tiles.BaseTile;
import nl.pokemon.game.domain.Session;
import nl.pokemon.game.client.model.TileMap;
import nl.pokemon.game.client.model.FullTileMap;
import nl.pokemon.game.client.model.GameScreen;
import org.dpmFramework.Kickstarter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Client extends JFrame {

    public Client() {

        this.setTitle("Pokemon!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 800);
        this.addKeyListener(Kickstarter.getInstanceOf(RpgController.class));
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        Bootstrap.load();

        Session session = Kickstarter.getInstanceOf(Session.class);

        this.add(new TimeBox());
        this.add(new DBRadar());

        this.add(Kickstarter.getInstanceOf(DBCount.class));
        FullTileMap view = Kickstarter.getInstanceOf(FullTileMap.class);
        GameScreen gameScreen = Kickstarter.getInstanceOf(GameScreen.class);

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
                        sqm.setVisible(false);
                        if (elevation == session.getUser().getMapCoordination().getZ())
                            sqm.setVisible(true);
                        this.add(sqm);
                    }
                }
            }
        }
        gameScreen.updateView(session.getUser().getMapCoordination().getZ());

        this.setVisible(true);
    }
}
