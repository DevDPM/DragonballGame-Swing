package nl.pokemon.game.view;

import nl.pokemon.game.controller.GameController;
import nl.pokemon.game.model.core.model.ScoreData;
import nl.pokemon.game.repository.ScoreRepository;
import nl.pokemon.game.view.gamePanel.EndGamePanel;
import nl.pokemon.game.view.gamePanel.GamePanel;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Inject;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopScorePanel extends JPanel {

    @Inject
    ScoreRepository scoreRepository;

    public TopScorePanel() {
        this.setVisible(false);
        this.setFocusable(false);
        this.setLayout(null);
        this.setBounds(0,0,800,800);
        this.setBackground(Color.darkGray);
        this.setOpaque(true);
    }

    public void showTopScore() {
        this.setVisible(true);
        this.add(new TopScoreList());
        this.add(new MainMenu());
    }

    private class TopScoreList extends JLabel {

        public TopScoreList() {
            this.setVisible(true);
            this.setFocusable(false);
            this.setBounds(250, 50, 500, 200);
            this.setText(getTopScore());
            this.setFont(new Font("Monaco", Font.BOLD, 20));
            this.setForeground(Color.WHITE);
        }

        private String getTopScore() {
            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("Top score list<br/><br/>");
            sb.append("Date:&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;Time:<br/><br/>");
            List<ScoreData> scoreDataList = new ArrayList<>(scoreRepository.getTopScoreList());

            if (scoreDataList.isEmpty())
                return sb.append("No data available").toString();

            Collections.sort(scoreDataList, new Comparator<ScoreData>() {
                @Override
                public int compare(ScoreData o1, ScoreData o2) {
                    return o1.getTime().compareTo(o2.getTime());
                }
            });
            scoreDataList.forEach(scoreData -> {
                sb.append(scoreData.getDate().format(DateTimeFormatter.ISO_DATE) + "&emsp;&emsp;&emsp;" + scoreData.getTime().format(DateTimeFormatter.ISO_LOCAL_TIME) + "<br/>");
            });
            return sb.toString();
        }
    }

    private class MainMenu extends JButton implements ActionListener {

        public MainMenu() {
            this.setVisible(true);
            this.setFocusable(false);
            this.setText("Go to menu");
            this.setBounds(250, 400, 300, 50);
            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Kickstarter.getInstanceOf(TopScorePanel.class).setVisible(false);
            Kickstarter.getInstanceOf(TopScorePanel.class).removeAll();
            Kickstarter.getInstanceOf(TopScorePanel.class).repaint();

            JFrame f = (JFrame) SwingUtilities.getAncestorOfClass(Client.class, Kickstarter.getInstanceOf(TopScorePanel.class));
            f.getContentPane().removeAll();
            f.repaint();
            f.add(Kickstarter.getInstanceOf(MenuPanel.class));
            f.validate();
        }
    }
}
