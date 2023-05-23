package nl.pokemon.mapGenerator.view;

import nl.pokemon.mapGenerator.controller.MG_Controller;
import org.dpmFramework.Kickstarter;

import javax.swing.*;
import java.awt.*;

public class MapGenerator extends JFrame {

    public MapGenerator() {

        this.setTitle("Dragonball Z Map Editor!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1300, 1000);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.addKeyListener(Kickstarter.getInstanceOf(MG_Controller.class));

        JPanel panelLeft = new JPanel();
            panelLeft.setPreferredSize(new Dimension(300,100));
            panelLeft.setLayout(new BorderLayout());
            panelLeft.addKeyListener(Kickstarter.getInstanceOf(MG_Controller.class));

            panelLeft.add(Kickstarter.getInstanceOf(OptionPanel.class), BorderLayout.CENTER);
        this.add(panelLeft, BorderLayout.WEST);


        this.add(Kickstarter.getInstanceOf(ViewPanel.class), BorderLayout.CENTER);
        this.setVisible(true);

    }
}
