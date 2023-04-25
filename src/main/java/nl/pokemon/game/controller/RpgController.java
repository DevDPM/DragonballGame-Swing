package nl.pokemon.game.controller;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.service.MoveViewMap;
import org.dpmFramework.annotation.Controller;
import org.dpmFramework.annotation.Inject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Controller
public class RpgController implements KeyListener {

//    private final Logger log = LoggerFactory.getLogger(RpgController.class);

    @Inject
    MoveViewMap moveViewMap;

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        boolean validKey;
        validKey = switch (keyEvent.getKeyChar()) {
            case 'w' -> moveViewMap.addOrUpdateDirection(Direction.NORTH);
            case 'a' -> moveViewMap.addOrUpdateDirection(Direction.WEST);
            case 's' -> moveViewMap.addOrUpdateDirection(Direction.SOUTH);
            case 'd' -> moveViewMap.addOrUpdateDirection(Direction.EAST);
            default -> false;
        };

        if (moveViewMap.isNotMoving() && validKey) {
            moveViewMap.setNotMoving(false);

            if (!moveViewMap.getMoveStack().isEmpty())
                moveViewMap.move(moveViewMap.getMoveStack().pop());
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
}
