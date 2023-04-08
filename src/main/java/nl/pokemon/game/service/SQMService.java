package nl.pokemon.game.service;

import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.model.CurrentPlayer;
import nl.pokemon.game.model.ViewSQM;
import nl.pokemon.game.model.obstacle.GreenPlant;
import nl.pokemon.game.model.obstacle.bigTree.BigTreeLL;
import nl.pokemon.game.model.obstacle.bigTree.BigTreeLR;
import nl.pokemon.game.model.obstacle.bigTree.BigTreeTL;
import nl.pokemon.game.model.obstacle.bigTree.BigTreeTR;
import nl.pokemon.game.model.walk.Grass;
import nl.pokemon.game.model.walk.Gravel;
import nl.pokemon.game.util.FullDataMap;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

@Service
public class SQMService {

    @Inject
    CurrentPlayer player;

    public boolean isNotWalkable(Direction direction) {

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
        BaseSQM sqm = convertFDM_XY_ToSQM(FDMIndexX, FDMIndexY);
        if (sqm == null)
            return true;

        return sqm.isNotWalkable();
    }

    public BaseSQM convertFDM_XY_ToSQM(int x, int y) {
        int[][] dataMap = FullDataMap.getFDMap();

        if (x < 0 || y < 0 || x > FullDataMap.X_MAX_VALUE()-1 || y > FullDataMap.Y_MAX_VALUE()-1) {
            return null;
        }

        return switch(dataMap[y][x]) {
            case 0 -> new Gravel();
            case 1 -> new Grass();
            case 3 -> new GreenPlant();
            case 91 -> new BigTreeLL();
            case 92 -> new BigTreeLR();
            case 93 -> new BigTreeTL();
            case 94 -> new BigTreeTR();
            default -> new ViewSQM();
        };
    }
}
