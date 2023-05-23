package nl.pokemon;

import nl.pokemon.game.bootstrap.Bootstrap;
import nl.pokemon.game.view.Client;
import nl.pokemon.mapGenerator.view.MapGenerator;
import org.dpmFramework.Kickstarter;

public class Main {
    public static void main(String[] args) {
        Bootstrap.load();
        Kickstarter.ignite(Main.class);

//        new Client();
        new MapGenerator();

    }
}