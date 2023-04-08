package nl.pokemon;

import org.dpmFramework.Kickstarter;
import nl.pokemon.game.view.Console;

public class Main {
    public static void main(String[] args) {
        Kickstarter.ignite(Main.class);
        new Console();
    }
}