package nl.pokemon.game.client.controller;

import nl.pokemon.game.client.enums.Direction;
import nl.pokemon.game.client.model.Movement;
import nl.pokemon.game.client.view.DBRadar;
import org.dpmFramework.annotation.Controller;
import org.dpmFramework.annotation.Inject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Controller
public class RpgController implements KeyListener {

//    private final Logger log = LoggerFactory.getLogger(RpgController.class);

    @Inject
    Movement movement;

    @Inject
    DBRadar DBRadar;

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getKeyCode()) {
//            case KeyEvent.VK_SPACE -> dragonBallRadar.useDragonBallRadar();
            case KeyEvent.VK_ALT -> DBRadar.useDragonBallRadar();
        }

        boolean validKey;
        validKey = switch (keyEvent.getKeyChar()) {
            case 'w' -> movement.addOrUpdateDirection(Direction.NORTH);
            case 'a' -> movement.addOrUpdateDirection(Direction.WEST);
            case 's' -> movement.addOrUpdateDirection(Direction.SOUTH);
            case 'd' -> movement.addOrUpdateDirection(Direction.EAST);
            default -> false;
        };

        if (movement.isNotMoving() && validKey) {
            movement.setNotMoving(false);

            if (!movement.getMoveStack().isEmpty())
                movement.moveScreenByDirection(movement.getMoveStack().pop());
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
}
