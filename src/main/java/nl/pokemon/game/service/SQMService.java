package nl.pokemon.game.service;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.model.Elevatable;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.CurrentPlayer;
import nl.pokemon.game.util.SQMObjects;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class SQMService {

    @Inject
    CurrentPlayer player;

    @Inject
    FullMapService fullMapService;


    public BaseSQM getSQMByIntAndArea(AreaType area, int sqmId) {
        return SQMObjects.getSQMByIntAndArea(area, sqmId);
    }

    public boolean isNotWalkable(Direction direction) {
        BaseSQM sqm = getBaseSQMByDirection(direction);
        if (sqm == null)
            return true;
        return sqm.isNotWalkable();
    }

    public BaseSQM getBaseSQMByDirection(Direction direction) {
        int x = player.getFDMIndexX();
        int y = player.getFDMIndexY();
        int z = player.getFDMIndexZ();

        switch (direction) {
            case NORTH -> {
                y = player.getFDMIndexY() - 1;
            }
            case EAST -> {
                x = player.getFDMIndexX() + 1;
            }
            case SOUTH -> {
                y = player.getFDMIndexY() + 1;
            }
            case WEST -> {
                x = player.getFDMIndexX() - 1;
            }
        }
        BaseSQM sqm = fullMapService.getFullMapSQM(AreaType.TERRAIN, x, y, z);
        return sqm;
    }

    public boolean isElevatable(BaseSQM sqm) {
        return sqm instanceof Elevatable;
    }

    public BaseSQM getSQMByPlayerPosition() {
        return fullMapService.getFullMapSQM(AreaType.TERRAIN, player.getFDMIndexX(), player.getFDMIndexY(), player.getFDMIndexZ());
    }
}
