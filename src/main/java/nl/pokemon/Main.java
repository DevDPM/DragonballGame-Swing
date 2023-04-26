package nl.pokemon;

import nl.pokemon.game.client.view.Client;
import nl.pokemon.mapGenerator.view.MapGenerator;
import org.dpmFramework.Kickstarter;

public class Main {
    public static void main(String[] args) {
        Kickstarter.ignite(Main.class);
//        Kickstarter.printContext();
        new Client();
//        new MapGenerator();

    }
}