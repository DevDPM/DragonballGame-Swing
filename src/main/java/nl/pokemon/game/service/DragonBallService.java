package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.DragonBallContainer;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.ItemSQM;
import nl.pokemon.game.model.clientViewMap.ViewMap;
import nl.pokemon.game.util.FullMap;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import java.util.EmptyStackException;
import java.util.Random;

@Service
public class DragonBallService {

    @Inject
    private FullMapManager fullMapManager;

    private DragonBallContainer dragonBallContainer = new DragonBallContainer();

    public ItemSQM isDragonBallSQMOrNull(User user) {
        int x = user.getX();
        int y = user.getY();
        int z = user.getZ();

        BaseSQM baseSQM = fullMapManager.getBaseSQMByPosition(AreaType.TERRAIN, x, y, z);
        if (baseSQM instanceof ItemSQM item) {
            return item;
        } else {
            return null;
        }
    }

    public void generateLocationForDragonBall() {
        Random random = new Random();
        int randomZ = random.nextInt(ViewMap.START_Z, ViewMap.MAX_Z);
        int randomX = random.nextInt(0, FullMap.fullMapWidth());
        int randomY = random.nextInt(0, FullMap.fullMapHeight());
        BaseSQM mapSQM = fullMapManager.getBaseSQMByPosition(AreaType.MAP, randomX, randomY, randomZ);
        BaseSQM terrainSQM = fullMapManager.getBaseSQMByPosition(AreaType.TERRAIN, randomX, randomY, randomZ);

        if (!mapSQM.isNotWalkable() && mapSQM.getSqmId() != 0 &&
            !terrainSQM.isNotWalkable() && terrainSQM.getSqmId() == 0) {

            try {
                ItemSQM dragonBall = dragonBallContainer.getNextDragonBall();
                fullMapManager.setBaseSQMToPosition(AreaType.TERRAIN, randomX, randomY, randomZ, dragonBall);
                System.out.println("a new dragonball has been spotted! : " + randomX + ", " + randomY + ", " + randomZ);
            } catch (EmptyStackException e) {
                // finish game
                System.out.println("you won!");
            }
        } else {
            generateLocationForDragonBall();
        }
    }
}
