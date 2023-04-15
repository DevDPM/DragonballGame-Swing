package nl.pokemon.game.view;

import nl.pokemon.game.controller.RpgController;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.CurrentPlayer;
import nl.pokemon.game.model.View.GridMap;
import nl.pokemon.game.model.View.ViewMap;
import nl.pokemon.game.service.ViewMapService;
import nl.pokemon.game.util.SQMObjects;
import org.dpmFramework.Kickstarter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Console extends JFrame {

    public Console() {

        this.setTitle("Pokemon!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 800);
        this.addKeyListener(Kickstarter.getInstanceOf(RpgController.class));
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(Kickstarter.getInstanceOf(CurrentPlayer.class));

        SQMObjects.bootstrap();
        SQMObjects.printSQMList();

        ViewMap view = Kickstarter.getInstanceOf(ViewMap.class);
        ViewMapService viewMapService = Kickstarter.getInstanceOf(ViewMapService.class);
        viewMapService.updateView();


        view.getViewMap().forEach(((integer, surfaceGridMapMap) -> {
            Map<Integer, Map<AreaType, GridMap>> fullMap = view.getViewMap();
            List<Integer> elevations = new ArrayList<>(fullMap.keySet());
            Collections.sort(elevations);

            for (int elevation : elevations) {
                Map<AreaType, GridMap> layerMap = view.getViewMap().get(elevation);
                List<AreaType> areaTypes = new ArrayList<>(layerMap.keySet());
                Collections.sort(areaTypes);

                for (AreaType areaType : areaTypes) {
                    GridMap areaMap = layerMap.get(areaType);
                    for (int y = 0; y < areaMap.getGridMap()[0].length; y++) {
                        for (int x = 0; x < areaMap.getGridMap().length; x++) {
                            BaseSQM sqm = areaMap.getGridMap()[y][x];
                            this.add(sqm);
                        }
                    }
                }
            }
        }));

        this.setVisible(true);
    }
}
