package nl.pokemon.game.model;

import nl.pokemon.game.enums.AreaType;

public class MapCoordination {
    private int x;
    private int y;
    private int z;
    private AreaType areaType;

    public MapCoordination(int x, int y, int z, AreaType areaType) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.areaType = areaType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }
}
