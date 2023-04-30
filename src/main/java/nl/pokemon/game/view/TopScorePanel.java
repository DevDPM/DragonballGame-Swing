package nl.pokemon.game.view;

import nl.pokemon.game.controller.GameController;
import nl.pokemon.game.model.core.model.ScoreData;
import nl.pokemon.game.repository.ScoreRepository;
import nl.pokemon.game.view.gamePanel.GamePanel;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class TopScorePanel extends JPanel {

    @Inject
    ScoreRepository scoreRepository;

    public TopScorePanel() {
        this.setVisible(true);
        this.setFocusable(false);
        this.setLayout(null);
        this.setBounds(0,0,800,800);
        this.setBackground(Color.darkGray);
        this.setOpaque(true);

        this.add(new TopScoreList());
        this.add(new BackButton());
    }

    private class TopScoreList extends JLabel {

        public TopScoreList() {
            this.setVisible(true);
            this.setFocusable(false);
            this.setText("Start Game");
            this.setBounds(250, 200, 300, 50);
        }

        private String getTopScore() {
            StringBuilder sb = new StringBuilder();
            scoreRepository.getTopScoreList().forEach(scoreData -> {

            });
            return sb.toString();
        }
    }

    private class BackButton extends JButton implements ActionListener {

        public BackButton() {
            this.setVisible(true);
            this.setFocusable(false);
            this.setText("Back to menu");
            this.setBounds(250, 275, 300, 50);
            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("show top score");
        }
    }
}
