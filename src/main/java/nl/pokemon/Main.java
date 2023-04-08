package nl.pokemon;

import org.dpmFramework.Kickstarter;
import nl.pokemon.game.rpg.view.Rpg;

public class Main {
    public static void main(String[] args) {
        Kickstarter.ignite(Main.class);
        new Rpg();
    }
}