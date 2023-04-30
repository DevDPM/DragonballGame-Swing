package nl.pokemon.game.view.gamePanel;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.client.FullTileMap;
import nl.pokemon.game.model.client.TileMap;
import nl.pokemon.game.model.core.model.tiles.BaseTile;
import nl.pokemon.game.domain.Session;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class GamePanel extends JPanel {

    @Inject
    private TimeBox timeBox;

    @Inject
    private Counter counter;

    @Inject
    private AlertSign alertSign;

    @Inject
    private EndGamePanel endGamePanel;

    @Inject
    private FullTileMap fullTileMap;

    @Inject
    private Session session;

    public GamePanel() {
        this.setLayout(null);
        this.setVisible(true);
        this.setFocusable(true);
        this.setBounds(0,0,800,800);
    }

    private void init() {
        this.add(timeBox);
        this.add(counter);
        this.add(alertSign);
        this.add(endGamePanel);

        Map<Integer, Map<AreaType, TileMap>> fullMap = fullTileMap.getFullTileMap();
        List<Integer> elevations = new ArrayList<>(fullMap.keySet());
        Collections.reverse(elevations);

        for (int elevation : elevations) {
            Map<AreaType, TileMap> layerMap = fullTileMap.getFullTileMap().get(elevation);
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
        session.start();
    }
}
