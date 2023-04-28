package nl.pokemon.game.client.view;

import nl.pokemon.game.core.model.ScoreData;
import org.dpmFramework.annotation.Service;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class EndGame extends JLabel {


    public EndGame() {
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.setVisible(false);
        this.setFocusable(false);
        this.setBounds(250,200,300,300);
        this.setBackground(Color.darkGray);
        this.setOpaque(true);
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
}
