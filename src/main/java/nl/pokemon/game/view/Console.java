package nl.pokemon.game.view;

import nl.pokemon.game.controller.RpgController;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.Session;
import nl.pokemon.game.model.clientViewMap.GridMap;
import nl.pokemon.game.model.clientViewMap.ViewMap;
import nl.pokemon.game.service.UserService;
import nl.pokemon.game.service.ClientViewMap;
import nl.pokemon.game.util.FullMap;
import nl.pokemon.game.util.TilesetImageContainer;
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


        UserService user = Kickstarter.getInstanceOf(UserService.class);
        FullMap.bootstrapFullMap();
        TilesetImageContainer.bootstrap();
        TilesetImageContainer.printSQMList();
        Session session = Kickstarter.getInstanceOf(Session.class);

        this.add(new TimeBox(user.getUserInstance()));
        this.add(Kickstarter.getInstanceOf(DragonBallCounter.class));
        this.add(Kickstarter.getInstanceOf(DragonBallRadar.class));

        ViewMap view = Kickstarter.getInstanceOf(ViewMap.class);
        ClientViewMap clientViewMap = Kickstarter.getInstanceOf(ClientViewMap.class);

        Map<Integer, Map<AreaType, GridMap>> fullMap = view.getViewMap();
        List<Integer> elevations = new ArrayList<>(fullMap.keySet());
        Collections.reverse(elevations);

        for (int elevation : elevations) {
            Map<AreaType, GridMap> layerMap = view.getViewMap().get(elevation);
            List<AreaType> areaTypes = new ArrayList<>(layerMap.keySet());
            Collections.sort(areaTypes);

            for (AreaType areaType : areaTypes) {
                GridMap areaMap = layerMap.get(areaType);
                for (int y = areaMap.getGridMap()[0].length-1; y >= 0; y--) {
                    for (int x = areaMap.getGridMap().length-1; x >= 0; x--) {
                        BaseSQM sqm = areaMap.getGridMap()[y][x];
                        sqm.setVisible(false);
                        if (elevation == session.getUser().getMapCoordination().getZ())
                            sqm.setVisible(true);
                        this.add(sqm);
                    }
                }
            }
        }
        clientViewMap.updateView(session.getUser().getMapCoordination().getZ());

        this.setVisible(true);
    }
}
