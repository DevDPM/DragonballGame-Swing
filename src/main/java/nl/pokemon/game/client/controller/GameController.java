package nl.pokemon.game.client.controller;

import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.client.view.DBRadar;
import nl.pokemon.game.core.service.PlayerService;
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
    DBRadar DBRadar;

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        if (playerService.getPoints() >= 7) {
            return;
        }

        switch (keyEvent.getKeyCode()) {
//            case KeyEvent.VK_SPACE -> dragonBallRadar.useDragonBallRadar();
            case KeyEvent.VK_ALT -> DBRadar.useDragonBallRadar();
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
