package nl.pokemon.game.controller;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.service.MoveViewMap;
import nl.pokemon.game.view.DragonBallRadar;
import org.dpmFramework.annotation.Controller;
import org.dpmFramework.annotation.Inject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Controller
public class RpgController implements KeyListener {

//    private final Logger log = LoggerFactory.getLogger(RpgController.class);

    @Inject
    MoveViewMap moveViewMap;

    @Inject
    DragonBallRadar dragonBallRadar;

    @Override
    public void keyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getKeyCode()) {
//            case KeyEvent.VK_SPACE -> dragonBallRadar.useDragonBallRadar();
            case KeyEvent.VK_ALT -> dragonBallRadar.useDragonBallRadar();
        }

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
