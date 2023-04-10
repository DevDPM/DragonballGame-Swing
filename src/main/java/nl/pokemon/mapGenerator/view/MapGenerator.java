package nl.pokemon.mapGenerator.view;

import nl.pokemon.game.model.BaseSQM;
import nl.pokemon.game.service.ViewService;
import nl.pokemon.mapGenerator.controller.MG_Controller;
import nl.pokemon.mapGenerator.model.MG_BaseSQM;
import nl.pokemon.mapGenerator.service.MG_ViewService;
import org.dpmFramework.Kickstarter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MapGenerator extends JFrame {

    public MapGenerator() {

        this.setTitle("Pokemon Map Generator!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1200, 1200);
        this.addKeyListener(Kickstarter.getInstanceOf(MG_Controller.class));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        JPanel panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(100,100));
        panelLeft.setLayout(new BorderLayout());

        JPanel panelLeftTop = new JPanel();
        panelLeftTop.setPreferredSize(new Dimension(100,100));
        panelLeftTop.setBackground(Color.BLACK);

        JPanel panelLeftBottom = new JPanel();
        panelLeftBottom.setAutoscrolls(true);

        panelLeft.add(panelLeftTop, BorderLayout.NORTH);
        panelLeft.add(panelLeftBottom, BorderLayout.CENTER);

        this.add(panelLeft, BorderLayout.WEST);



        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(null);
        this.add(panelCenter, BorderLayout.CENTER);

        MG_ViewService viewSQM = Kickstarter.getInstanceOf(MG_ViewService.class);

        MG_BaseSQM[][] fields = viewSQM.getViewMap();
        for (int y = 0; y < fields[1].length; y++) {
            for (int x = 0; x < fields.length; x++) {
                panelCenter.add(fields[y][x]);
            }
        }

        this.setVisible(true);

    }
}
