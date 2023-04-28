package nl.pokemon.game.core.model.dragonballs;

import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.Tiles.BaseTile;
import nl.pokemon.game.core.model.Tiles.ItemTile;
import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.model.FullTileMap;
import nl.pokemon.game.core.service.FullMapManager;
import nl.pokemon.game.bootstrap.FullMap;
import nl.pokemon.game.bootstrap.TilesetImageContainer;
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
        ArrayList<Integer> dragonballIds = new ArrayList<>(Arrays.asList(10, 11, 12, 13, 14, 15, 16));
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
        int z = random.nextInt(FullTileMap.START_Z, FullTileMap.MAX_Z);

        BaseTile mapSQM = fullMapManager.getBaseSQMByPosition(new MapCoordination(x, y, z, AreaType.MAP));
        BaseTile terrainSQM = fullMapManager.getBaseSQMByPosition(new MapCoordination(x, y, z, AreaType.HIGHER_TERRAIN));

        if (!mapSQM.isNotWalkable() && mapSQM.getSqmId() != 0 &&
                !terrainSQM.isNotWalkable() && terrainSQM.getSqmId() == 0) {

            BaseTile baseTile = TilesetImageContainer.getSQMByIntAndArea(AreaType.LOWER_TERRAIN, sqmId);
            ItemTile dragonballSQM = new ItemTile();
            dragonballSQM.setImageIcon(baseTile.getImageIcon());
            dragonballSQM.setSqmId(baseTile.getSqmId());
            return new DragonBall(new MapCoordination(x, y, z, AreaType.LOWER_TERRAIN), dragonballSQM);
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
