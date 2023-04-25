package nl.pokemon.game.model;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.ItemSQM;
import nl.pokemon.game.util.TilesetImageContainer;

import java.util.*;

public class DragonBallContainer {

    private Stack<ItemSQM> dragonBallsContainer = new Stack<>();

    public DragonBallContainer() {
        TilesetImageContainer.bootstrap();
        ArrayList<Integer> dragonballIds = new ArrayList<>(Arrays.asList(41, 42, 43, 44, 45, 46, 47));
        Random randomizedDB = new Random();
        while (dragonballIds.size() > 0) {
            int dragonBallId = randomizedDB.nextInt(0, dragonballIds.size());
            dragonBallsContainer.push((ItemSQM) TilesetImageContainer.getSQMByIntAndArea(AreaType.TERRAIN, dragonballIds.get(dragonBallId)));
            dragonballIds.remove(dragonBallId);
        }
    }

    public ItemSQM getNextDragonBall() throws EmptyStackException {
        return dragonBallsContainer.pop();
    }
}
