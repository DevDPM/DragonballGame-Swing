package nl.pokemon.mapGenerator.model.View;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.mapGenerator.model.SQMs.Editable_Tile;
import nl.pokemon.mapGenerator.model.SQMs.DevTool_BaseTile;

public final class MG_GridMap {

    public static final int MAX_X = 90;
    public static final int MAX_Y = 90;
    private DevTool_BaseTile[][] gridMap;

    public MG_GridMap(AreaType area) {
        this.gridMap = new DevTool_BaseTile[MAX_Y][MAX_X];
        for (int y = 0; y < gridMap[0].length; y++) {
            for (int x = 0; x < gridMap.length; x++) {
                DevTool_BaseTile sqm = new Editable_Tile();
                sqm.addActionListener(sqm);
                sqm.setCoordinateX(x);
                sqm.setCoordinateY(y);
                sqm.setIndexX(x);
                sqm.setIndexY(y);
                sqm.updateSQM();
                gridMap[y][x] = sqm;
            }
        }
    }

    public DevTool_BaseTile[][] getGridMap() {
        return gridMap;
    }
}
