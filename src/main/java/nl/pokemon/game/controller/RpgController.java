package nl.pokemon.game.controller;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.service.MoveService;
import org.dpmFramework.annotation.Controller;
import org.dpmFramework.annotation.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Controller
public class RpgController implements KeyListener {

    private final Logger log = LoggerFactory.getLogger(RpgController.class);

    @Inject
    MoveService moveService;

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        boolean validKey;
        validKey = switch (keyEvent.getKeyChar()) {
            case 'w' -> moveService.addOrUpdateDirection(Direction.NORTH);
            case 'a' -> moveService.addOrUpdateDirection(Direction.WEST);
            case 's' -> moveService.addOrUpdateDirection(Direction.SOUTH);
            case 'd' -> moveService.addOrUpdateDirection(Direction.EAST);
            default -> false;
        };

        if (moveService.isNotMoving() && validKey) {
            moveService.setNotMoving(false);

            if (!moveService.getMoveStack().isEmpty())
                moveService.move(moveService.getMoveStack().pop());
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
}
