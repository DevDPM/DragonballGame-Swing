package nl.pokemon.game.controller;

import nl.pokemon.game.model.CurrentPlayer;
import nl.pokemon.game.service.Direction;
import org.dpmFramework.annotation.Controller;
import org.dpmFramework.annotation.Inject;
import nl.pokemon.game.service.ViewService;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

@Controller
public class RpgController implements KeyListener {

    @Inject
    CurrentPlayer player;

    @Inject
    ViewService viewService;

    boolean isNotMoving = true;
    Stack<Direction> moveStack = new Stack<>();


    @Override
    public void keyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getKeyChar()) {
            case 'w' -> addDirectionToStack(Direction.NORTH);
            case 'a' -> addDirectionToStack(Direction.WEST);
            case 's' -> addDirectionToStack(Direction.SOUTH);
            case 'd' -> addDirectionToStack(Direction.EAST);
        }

        if (isNotMoving) {
            isNotMoving = false;

            if (!moveStack.isEmpty())
                viewService.moveViewXYSmoothly(moveStack.pop());
        }

    }
    private void addDirectionToStack(Direction direction) {

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

    public Stack<Direction> getMoveStack() {
        return moveStack;
    }
}
