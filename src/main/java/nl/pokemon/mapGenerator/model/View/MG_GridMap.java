package nl.pokemon.mapGenerator.model.View;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.SQMs.BaseSQM;
import nl.pokemon.game.model.SQMs.SQMFactory;
import nl.pokemon.game.util.TilesetImageContainer;
import nl.pokemon.mapGenerator.model.Editable_SQM;
import nl.pokemon.mapGenerator.model.SQMs.MG_BaseSQM;

public final class MG_GridMap {

    public static final int MAX_X = 130;
    public static final int MAX_Y = 130;
    private MG_BaseSQM[][] gridMap;

    public MG_GridMap(AreaType area) {
        this.gridMap = new MG_BaseSQM[MAX_Y][MAX_X];
        for (int y = 0; y < gridMap[0].length; y++) {
            for (int x = 0; x < gridMap.length; x++) {
                MG_BaseSQM sqm = new Editable_SQM();
                sqm.addActionListener(sqm);
                sqm.setIndexX(x);
                sqm.setIndexY(y);
                sqm.updateSQM();
                gridMap[y][x] = sqm;
            }
        }
    }

    public MG_BaseSQM[][] getGridMap() {
        return gridMap;
    }
}
