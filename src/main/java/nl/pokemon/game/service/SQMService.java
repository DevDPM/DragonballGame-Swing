package nl.pokemon.game.service;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.util.TilesetImageContainer;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class SQMService {

    @Inject
    PlayerService playerService;

    @Inject
    FullMapService fullMapService;


    public BaseSQM getSQMByIntAndArea(AreaType area, int sqmId) {
        return TilesetImageContainer.getSQMByIntAndArea(area, sqmId);
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
        BaseSQM sqm = fullMapService.getBaseSQMByPosition(AreaType.TERRAIN, x, y, z);
        return sqm;
    }

    public boolean isElevatable(BaseSQM sqm) {
        return sqm instanceof Elevatable;
    }

    public BaseSQM getSQMByPlayerPosition() {
        return fullMapService.getBaseSQMByPosition(AreaType.TERRAIN, playerService.getPlayerX(), playerService.getPlayerY(), playerService.getPlayerZ());
    }
}
