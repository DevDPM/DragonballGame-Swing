package nl.pokemon.game.view;

import org.dpmFramework.Kickstarter;

import javax.swing.*;

public class Client extends JFrame {

    public Client() {

        this.setTitle("Dragon ball Z!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.add(Kickstarter.getInstanceOf(MenuPanel.class));


        this.setVisible(true);
    }
}
