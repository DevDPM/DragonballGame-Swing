package nl.pokemon.game.model.dragonballs;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.MapCoordination;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.ItemSQM;
import nl.pokemon.game.model.clientViewMap.ViewMap;
import nl.pokemon.game.service.FullMapManager;
import nl.pokemon.game.util.FullMap;
import nl.pokemon.game.util.TilesetImageContainer;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.util.*;

@Service
public class DragonBallContainer {

    @Inject
    FullMapManager fullMapManager;

    private Stack<DragonBall> dragonBallsContainer = new Stack<>();
    private DragonBall currentDragonball;

    private void init() {
        TilesetImageContainer.bootstrap();
        FullMap.bootstrapFullMap();
        addNewDragonballs();
    }
    private void addNewDragonballs() {
        ArrayList<Integer> dragonballIds = new ArrayList<>(Arrays.asList(41, 42, 43, 44, 45, 46, 47));
        Random randomizedDB = new Random();
        while (dragonballIds.size() > 0) {
            int dragonBallId = randomizedDB.nextInt(0, dragonballIds.size());
            DragonBall dragonBall = createDragonBallBySqmId(dragonballIds.get(dragonBallId));
            dragonBallsContainer.push(dragonBall);
            dragonballIds.remove(dragonBallId);
        }
    }

    private DragonBall createDragonBallBySqmId(int sqmId) {
        Random random = new Random();
        int x = random.nextInt(0, FullMap.fullMapWidth());
        int y = random.nextInt(0, FullMap.fullMapHeight());
        int z = random.nextInt(ViewMap.START_Z, ViewMap.MAX_Z);

        BaseSQM mapSQM = fullMapManager.getBaseSQMByPosition(new MapCoordination(x, y, z, AreaType.MAP));
        BaseSQM terrainSQM = fullMapManager.getBaseSQMByPosition(new MapCoordination(x, y, z, AreaType.TERRAIN));

        if (!mapSQM.isNotWalkable() && mapSQM.getSqmId() != 0 &&
                !terrainSQM.isNotWalkable() && terrainSQM.getSqmId() == 0) {

            ItemSQM dragonballSQM = (ItemSQM) TilesetImageContainer.getSQMByIntAndArea(AreaType.TERRAIN, sqmId);
            return new DragonBall(new MapCoordination(x, y, z, dragonballSQM.getAreaType()), dragonballSQM);
        } else {
            return createDragonBallBySqmId(sqmId);
        }
    }

    public DragonBall getNextDragonBall() throws EmptyStackException {
        DragonBall dragonBall;
        try {
            dragonBall = dragonBallsContainer.pop();
            fullMapManager.setBaseSQMToPosition(dragonBall.getMapCoordination(), dragonBall.getItem());
            this.currentDragonball = dragonBall;
            System.out.println("current db: " + dragonBall.getMapCoordination().getX() + " " + dragonBall.getMapCoordination().getY() + " " + dragonBall.getMapCoordination().getZ() + "");
            return dragonBall;
        } catch (EmptyStackException e) {
            // finish game
            System.out.println("you won!");
        }
        throw new RuntimeException("Something terrible happened during getNextDragonBall");
    }

    public DragonBall getCurrentDragonball() {
        return currentDragonball;
    }
}
