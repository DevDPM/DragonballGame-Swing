package nl.pokemon.game.enums;

import nl.pokemon.game.util.FullDataMap;
import nl.pokemon.game.util.House;

public enum Destination {

    FULL_MAP    (FullDataMap.getFDMap()),
    HOUSE       (House.getFDMap());

    private int[][] map;

    Destination(int[][] map) {
        this.map = map;
    }

    public int[][] getMap() {
        return this.map;
    }
}
