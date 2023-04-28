package nl.pokemon.game.core.model;

import nl.pokemon.game.client.enums.AreaType;
import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.domain.User;

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

    public void elevate(int elevation, Direction direction) {
        this.x += (direction.getX() * 2);
        this.y += (direction.getY() * 2);
        this.z += elevation;
        this.areaType = AreaType.PLAYER_BOTTOM;
    }

    public void incrementByDirection(Direction direction) {
        this.x += direction.getX();
        this.y += direction.getY();
    }

    public static MapCoordination getPlayerPositionWithDirection(User user, Direction direction) {
        MapCoordination mapCoordination = user.getMapCoordination();
        int x = mapCoordination.getX();
        int y = mapCoordination.getY();
        int z = mapCoordination.getZ();

        if (direction != null) {
            x += direction.getX();
            y += direction.getY();
        }
        return new MapCoordination(x, y, z, mapCoordination.getAreaType());
    }

    public static MapCoordination copyOf(MapCoordination nextPosition) {
        return new MapCoordination(nextPosition.getX(), nextPosition.getY(), nextPosition.getZ(), nextPosition.getAreaType());
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
