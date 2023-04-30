package nl.pokemon.game.client.view;

import nl.pokemon.game.core.model.ScoreData;
import org.dpmFramework.Kickstarter;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

@Service
public class EndGamePanel extends JLabel {


    public EndGamePanel() {
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setVisible(false);
        this.setFocusable(false);
        this.setBounds(250,200,300,300);
        this.setBackground(Color.darkGray);
        this.setOpaque(true);
        this.add(new MainMenu());
    }

    public void writeDataOnScreen(ScoreData scoreData) {
        this.setVisible(true);
        this.setText(getEndGameText(scoreData));
        this.setForeground(Color.WHITE);
        this.setFont(new Font("Monaco", Font.BOLD, 20));
    }

    public String getEndGameText(ScoreData scoreData) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("Finished!<br/><br/>");
        sb.append("Your time:<br/>");
        sb.append(scoreData.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        sb.append("</html>");
        return sb.toString();
    }

    private class MainMenu extends JButton implements ActionListener {

        public MainMenu() {
            this.setVisible(true);
            this.setFocusable(false);
            this.setText("Go to menu");
            this.setBounds(0, 250, 300, 50);
            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Kickstarter.getInstanceOf(EndGamePanel.class).setVisible(false);

            JFrame f = (JFrame) SwingUtilities.getAncestorOfClass(Client.class, Kickstarter.getInstanceOf(GamePanel.class));
            f.getContentPane().removeAll();
            f.repaint();
            f.add(Kickstarter.getInstanceOf(MenuPanel.class));
            f.validate();
        }
    }
}
