package nl.pokemon.game.util;

import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.model.ViewSQM;
import nl.pokemon.game.model.obstacle.GreenPlant;
import nl.pokemon.game.model.obstacle.bigTree.BigTreeLL;
import nl.pokemon.game.model.obstacle.bigTree.BigTreeLR;
import nl.pokemon.game.model.obstacle.bigTree.BigTreeTL;
import nl.pokemon.game.model.obstacle.bigTree.BigTreeTR;
import nl.pokemon.game.model.walk.GravelPortal;
import nl.pokemon.game.model.walk.Grass;
import nl.pokemon.game.model.walk.Gravel;

public class FDMapToSQM {

    public static BaseSQM convertFDM_XY_ToSQM(int x, int y) {
        int[][] dataMap = FullDataMap.getFDMap();

        if (x < 0 || y < 0 || x > FullDataMap.X_MAX_VALUE()-1 || y > FullDataMap.Y_MAX_VALUE()-1) {
            return null;
        }

        return switch(dataMap[y][x]) {
            case 0 -> new Gravel();
            case 1 -> new Grass();
            case 3 -> new GreenPlant();
            case 4 -> {
                GravelPortal sqm = new GravelPortal();
                int[] xyDestination = Portals.getDestinationXYByPortalXY(x, y);
                sqm.setDestinationFDMIndexXY(xyDestination[0], xyDestination[1]);
                yield sqm;
            }
            case 91 -> new BigTreeLL();
            case 92 -> new BigTreeLR();
            case 93 -> new BigTreeTL();
            case 94 -> new BigTreeTR();
            default -> new ViewSQM();
        };
    }
}
