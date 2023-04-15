package nl.pokemon;

import nl.pokemon.game.view.Console;
import org.dpmFramework.Kickstarter;

public class Main {
    public static void main(String[] args) {
        Kickstarter.ignite(Main.class);
        new Console();
//        new MapGenerator();

    }
}