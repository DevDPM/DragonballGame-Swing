package nl.pokemon.game.bootstrap;

public class Bootstrap {

    public static void load() {
        FullMap.bootstrapFullMap();
        TilesetImageContainer.bootstrap();
    }
}
