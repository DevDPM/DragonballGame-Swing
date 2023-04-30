package nl.pokemon;

import nl.pokemon.game.bootstrap.Bootstrap;
import nl.pokemon.game.client.view.Client;
import org.dpmFramework.Kickstarter;

public class Main {
    public static void main(String[] args) {
        Kickstarter.ignite(Main.class);
        Bootstrap.load();
        new Client();
//        new MapGenerator();

    }
}