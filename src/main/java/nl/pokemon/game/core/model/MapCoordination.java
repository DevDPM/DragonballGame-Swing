package nl.pokemon.game.core.model;

import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.enums.Direction;

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

    public void elevate(int elevation, int x, int y, AreaType areaType) {
        this.x += (x * 2);
        this.y += (y * 2);
        this.z += elevation;
        this.areaType = areaType;
    }

    public void incrementByDirection(Direction direction) {
        this.x += direction.getX();
        this.y += direction.getY();
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
