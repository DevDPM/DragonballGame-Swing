package nl.pokemon.game.model.clientViewMap;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.SQMFactory;

public final class GridMap {

    public static final int MAX_X = 21;
    public static final int MAX_Y = 21;
    private BaseSQM[][] gridMap;

    public GridMap(AreaType area) {
        this.gridMap = new BaseSQM[MAX_Y][MAX_X];
        for (int y = 0; y < gridMap[0].length; y++) {
            for (int x = 0; x < gridMap.length; x++) {
                BaseSQM thisSQM = SQMFactory.getSQMBySurface(area);
                thisSQM.setIndexX(x);
                thisSQM.setIndexY(y);
                thisSQM.updateSQM();
                gridMap[y][x] = thisSQM;
            }
        }
    }

    public BaseSQM[][] getGridMap() {
        return gridMap;
    }
}
