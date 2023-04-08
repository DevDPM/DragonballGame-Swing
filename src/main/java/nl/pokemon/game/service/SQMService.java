package nl.pokemon.game.service;

import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.model.CurrentPlayer;
import nl.pokemon.game.model.walk.Portable;
import nl.pokemon.game.util.FDMapToSQM;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class SQMService {

    @Inject
    CurrentPlayer player;

    public boolean isNotWalkable(Direction direction) {
        BaseSQM sqm = getBaseSQMByDirection(direction);
        if (sqm == null)
            return true;
        return sqm.isNotWalkable();
    }

    public BaseSQM getBaseSQMfromDirection(Direction direction) {
        BaseSQM sqm = getBaseSQMByDirection(direction);
        if (!(sqm instanceof Portable)) {
            return null;
        }
        return sqm;
    }

    public BaseSQM getBaseSQMfromCurrentXY() {
        BaseSQM sqm = getBaseSQMByCurrentXY();
        if (!(sqm instanceof Portable)) {
            return null;
        }
        return sqm;
    }

    private BaseSQM getBaseSQMByCurrentXY() {
        int FDMIndexX = player.getFDMIndexX();
        int FDMIndexY = player.getFDMIndexY();
        BaseSQM sqm = FDMapToSQM.convertFDM_XY_ToSQM(FDMIndexX, FDMIndexY);
        return sqm;
    }

    private BaseSQM getBaseSQMByDirection(Direction direction) {
        int FDMIndexX = player.getFDMIndexX();
        int FDMIndexY = player.getFDMIndexY();

        switch (direction) {
            case NORTH -> {
                FDMIndexY = player.getFDMIndexY() - 1;
            }
            case EAST -> {
                FDMIndexX = player.getFDMIndexX() + 1;
            }
            case SOUTH -> {
                FDMIndexY = player.getFDMIndexY() + 1;
            }
            case WEST -> {
                FDMIndexX = player.getFDMIndexX() - 1;
            }
        }
        BaseSQM sqm = FDMapToSQM.convertFDM_XY_ToSQM(FDMIndexX, FDMIndexY);
        return sqm;
    }
}
