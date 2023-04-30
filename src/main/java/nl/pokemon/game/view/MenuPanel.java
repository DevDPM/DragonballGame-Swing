package nl.pokemon.game.view;

import nl.pokemon.game.controller.GameController;
import nl.pokemon.game.view.gamePanel.GamePanel;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Service
public class MenuPanel extends JPanel {

    public MenuPanel() {
        this.setVisible(true);
        this.setFocusable(false);
        this.setLayout(null);
        this.setBounds(0,0,800,800);
        this.setBackground(Color.darkGray);
        this.setOpaque(true);

        this.add(new StartGame());
        this.add(new ShowTopScore());
    }

    private class StartGame extends JButton implements ActionListener {

        public StartGame() {
            this.setVisible(true);
            this.setFocusable(false);
            this.setText("Start Game");
            this.setBounds(250, 200, 300, 50);
            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame f = (JFrame) SwingUtilities.getAncestorOfClass(Client.class, Kickstarter.getInstanceOf(MenuPanel.class));
            f.getContentPane().removeAll();
            f.repaint();
            f.add(Kickstarter.getInstanceOf(GamePanel.class));
            f.addKeyListener(Kickstarter.getInstanceOf(GameController.class));
            f.validate();
            Kickstarter.getInstanceOf(GamePanel.class).startGame();
        }
    }

    private class ShowTopScore extends JButton implements ActionListener {

        public ShowTopScore() {
            this.setVisible(true);
            this.setFocusable(false);
            this.setText("Top score");
            this.setBounds(250, 275, 300, 50);
            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame f = (JFrame) SwingUtilities.getAncestorOfClass(Client.class, Kickstarter.getInstanceOf(MenuPanel.class));
            f.getContentPane().removeAll();
            f.repaint();
            f.add(Kickstarter.getInstanceOf(TopScorePanel.class));
            f.validate();
            Kickstarter.getInstanceOf(TopScorePanel.class).showTopScore();
        }
    }
}

