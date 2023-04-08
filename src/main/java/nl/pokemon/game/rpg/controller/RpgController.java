package nl.pokemon.game.rpg.controller;

import org.dpmFramework.annotation.Controller;
import org.dpmFramework.annotation.Inject;
import nl.pokemon.game.rpg.model.CurrentPlayer;
import nl.pokemon.game.rpg.service.ViewService;
import nl.pokemon.game.rpg.service.MapService;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

@Controller
public class RpgController implements KeyListener {

    @Inject
    CurrentPlayer player;

    @Inject
    MapService mapService;

    @Inject
    ViewService viewService;

    boolean isNotMoving = true;
    Stack<ViewService.Direction> moveStack = new Stack<>();


    @Override
    public void keyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getKeyChar()) {
            case 'w' -> addDirectionToStack(ViewService.Direction.NORTH);
            case 'a' -> addDirectionToStack(ViewService.Direction.WEST);
            case 's' -> addDirectionToStack(ViewService.Direction.SOUTH);
            case 'd' -> addDirectionToStack(ViewService.Direction.EAST);
        }

        if (isNotMoving) {
            isNotMoving = false;

            viewService.moveViewXYSmoothly(moveStack.pop());
        }

    }
    private void addDirectionToStack(ViewService.Direction direction) {

        if ((moveStack.isEmpty())) {
            moveStack.add(direction);
        } else {
            moveStack.pop();
            moveStack.add(direction);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    public void setNotMoving(boolean notMoving) {
        isNotMoving = notMoving;
    }

    public Stack<ViewService.Direction> getMoveStack() {
        return moveStack;
    }
}
