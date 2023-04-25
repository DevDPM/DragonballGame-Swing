package nl.pokemon.game.service;

import nl.pokemon.game.domain.User;
import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.ItemSQM;
import nl.pokemon.game.model.SQMs.LowTerrainSQM;
import nl.pokemon.game.util.FullMap;
import nl.pokemon.game.util.TilesetImageContainer;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class SQMService {

    @Inject
    PlayerService playerService;

    @Inject
    FullMapManager fullMapManager;

    @Inject
    DragonBallService dragonBallService;

    public ItemSQM stepOnDragonBall() {
        ItemSQM item = dragonBallService.isDragonBallSQMOrNull(playerService.getPlayerById(1));
        if (item != null) {
            return item;
        }
        return null;
    }

    public BaseSQM getSQMByIntAndArea(AreaType area, int sqmId) {
        return TilesetImageContainer.getSQMByIntAndArea(area, sqmId);
    }

    public BaseSQM isWalkableTerrain(User user, Direction direction) {
        return isWalkableTerrain(user.getX() + direction.getX(), user.getY() + direction.getY(), user.getZ());
    }

    public BaseSQM isWalkableTerrain(User user) {
        return isWalkableTerrain(user.getX(), user.getY(), user.getZ());
    }

    private BaseSQM isWalkableTerrain(int x, int y, int z) {
        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return null;

        BaseSQM baseSQM = fullMapManager.getBaseSQMByPosition(AreaType.TERRAIN, x, y, z);
        if (baseSQM instanceof LowTerrainSQM && !baseSQM.isNotWalkable()) {
            return baseSQM;
        }

        return null;
    }

    public Elevatable isElevatingSQM(User user, Direction direction) {
        return isElevatingSQM(user.getX() + direction.getX(), user.getY() + direction.getY(), user.getZ());
    }

    private Elevatable isElevatingSQM(int x, int y, int z) {
        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return null;

        for (AreaType areaType : AreaType.values()) {
            BaseSQM baseSQM = fullMapManager.getBaseSQMByPosition(areaType, x, y, z);
            if (baseSQM instanceof Elevatable elevation)
                return elevation;
        }
        return null;
    }

    public boolean isWalkableSQM(User user, Direction direction) {
        return isWalkableSQM(user.getX() + direction.getX(), user.getY() + direction.getY(), user.getZ());
    }

    private boolean isWalkableSQM(int x, int y, int z) {
        if (x < 0 || y < 0 || x >= FullMap.fullMapWidth() || y >= FullMap.fullMapHeight())
            return false;

        for (AreaType areaType : AreaType.values()) {
            BaseSQM baseSQM = fullMapManager.getBaseSQMByPosition(areaType, x, y, z);

            if (baseSQM.isNotWalkable())
                return false;
        }
        return true;
    }

    public boolean isNotWalkable(Direction direction) {
        BaseSQM sqm = getBaseSQMByDirection(direction);
        if (sqm == null)
            return true;
        return sqm.isNotWalkable();
    }

    public BaseSQM getBaseSQMByDirection(Direction direction) {
        int x = playerService.getPlayerX();
        int y = playerService.getPlayerY();
        int z = playerService.getPlayerZ();

        switch (direction) {
            case NORTH -> {
                y = playerService.getPlayerY() - 1;
            }
            case EAST -> {
                x = playerService.getPlayerX() + 1;
            }
            case SOUTH -> {
                y = playerService.getPlayerY() + 1;
            }
            case WEST -> {
                x = playerService.getPlayerX() - 1;
            }
        }
        BaseSQM sqm = fullMapManager.getBaseSQMByPosition(AreaType.TERRAIN, x, y, z);
        return sqm;
    }

    public boolean isElevatable(BaseSQM sqm) {
        return sqm instanceof Elevatable;
    }

    public BaseSQM getSQMByPlayerPosition() {
        return fullMapManager.getBaseSQMByPosition(AreaType.TERRAIN, playerService.getPlayerX(), playerService.getPlayerY(), playerService.getPlayerZ());
    }
}
