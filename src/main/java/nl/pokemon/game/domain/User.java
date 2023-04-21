package nl.pokemon.game.domain;

import nl.pokemon.game.enums.AreaType;
import nl.pokemon.game.model.players.BaseEntity;

public class User {

    private int id;
    private String name;

    private BaseEntity baseEntity;

    private int x;
    private int y;
    private int z;
    private AreaType areaType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public BaseEntity getBaseEntity() {
        return baseEntity;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    public void setBaseEntity(BaseEntity baseEntity) {
        this.baseEntity = baseEntity;
    }
}
