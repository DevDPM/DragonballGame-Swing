package nl.pokemon.game.controller;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.view.gamePanel.Radar;
import nl.pokemon.game.model.core.service.PlayerService;
import org.dpmFramework.annotation.Controller;
import org.dpmFramework.annotation.Inject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Controller
public class GameController implements KeyListener {

//    private final Logger log = LoggerFactory.getLogger(RpgController.class);

    @Inject
    PlayerService playerService;

    @Inject
    Radar Radar;

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        if (playerService.getPoints() >= 7) {
            return;
        }

        switch (keyEvent.getKeyCode()) {
//            case KeyEvent.VK_SPACE -> dragonBallRadar.useDragonBallRadar();
            case KeyEvent.VK_ALT -> Radar.useDragonBallRadar();
        }

        boolean pressWASD;
        pressWASD = switch (keyEvent.getKeyChar()) {
            case 'w' -> playerService.addOrUpdateDirection(Direction.NORTH);
            case 'a' -> playerService.addOrUpdateDirection(Direction.WEST);
            case 's' -> playerService.addOrUpdateDirection(Direction.SOUTH);
            case 'd' -> playerService.addOrUpdateDirection(Direction.EAST);
            default -> false;
        };

        if (!playerService.isCharacterMoving() && pressWASD)
            playerService.startMovingSequence();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
}
