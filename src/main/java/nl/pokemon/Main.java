package nl.pokemon;

import nl.pokemon.mapGenerator.view.MapGenerator;
import org.dpmFramework.Kickstarter;
import nl.pokemon.game.view.Console;

public class Main {
    public static void main(String[] args) {
        Kickstarter.ignite(Main.class);
//        new Console();
        new MapGenerator();
    }
}