package nl.pokemon.game.domain;

import nl.pokemon.game.core.model.MapCoordination;
import nl.pokemon.game.core.model.characters.BaseEntity;

import java.util.concurrent.atomic.AtomicInteger;

public class User {

    private static AtomicInteger GENERATE_ID = new AtomicInteger(1);
    private int id;
    private String name;
    private int points = 0;

    private BaseEntity character;
    private MapCoordination mapCoordination;

    public User(String name, BaseEntity character, MapCoordination mapCoordination) {
        this.id = GENERATE_ID.getAndIncrement();
        this.name = name;
        this.character = character;
        this.mapCoordination = mapCoordination;
    }

    public String getName() {
        return name;
    }

    public MapCoordination getMapCoordination() {
        return mapCoordination;
    }

    public int getId() {
        return id;
    }

    public BaseEntity getCharacter() {
        return character;
    }

    public int getPoints() {
        return points;
    }

    public void addToPoints(int points) {
        this.points += points;
    }
}
