package nl.pokemon.game.util;

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
