package nl.pokemon.game.view;

import nl.pokemon.game.controller.RpgController;
import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.players.Ash;
import nl.pokemon.game.model.clientViewMap.GridMap;
import nl.pokemon.game.model.clientViewMap.ViewMap;
import nl.pokemon.game.model.players.Goku;
import nl.pokemon.game.repository.UserRepository;
import nl.pokemon.game.service.DragonBallService;
import nl.pokemon.game.service.FullMapManager;
import nl.pokemon.game.service.PlayerService;
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


        PlayerService user = Kickstarter.getInstanceOf(PlayerService.class);
        FullMap.bootstrapFullMap();
        TilesetImageContainer.bootstrap();
        TilesetImageContainer.printSQMList();
        bootstrap();

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
                        if (elevation == user.getPlayerZ())
                            sqm.setVisible(true);
                        this.add(sqm);
                    }
                }
            }
        }
        clientViewMap.updateView(user.getPlayerZ());

        this.setVisible(true);
    }

    private void bootstrap() {
        User user = new User();
        user.setId(1);
        user.setX(45);
        user.setY(63);
        user.setZ(0);
        user.setBaseEntity(new Goku());
        user.setAreaType(AreaType.PLAYER_BOTTOM);

        User user1 = new User();
        user1.setId(2);
        user1.setX(5);
        user1.setY(5);
        user1.setZ(0);
        user1.setBaseEntity(new Ash());
        user1.setAreaType(AreaType.PLAYER_BOTTOM);
        Kickstarter.getInstanceOf(UserRepository.class).getUserDataBase().put(user.getId(), user);
        Kickstarter.getInstanceOf(UserRepository.class).getUserDataBase().put(user1.getId(), user1);
        FullMapManager fullMapManager = Kickstarter.getInstanceOf(FullMapManager.class);

        fullMapManager.moveUser(user, Direction.EAST);
        fullMapManager.moveUser(user1, Direction.EAST);
        Kickstarter.getInstanceOf(DragonBallService.class).generateLocationForDragonBall();
    }
}
