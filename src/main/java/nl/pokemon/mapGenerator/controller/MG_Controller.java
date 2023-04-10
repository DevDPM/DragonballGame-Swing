package nl.pokemon.mapGenerator.controller;

import nl.pokemon.game.enums.Direction;
import nl.pokemon.game.service.ViewService;
import nl.pokemon.mapGenerator.service.MG_ViewService;
import org.dpmFramework.annotation.Controller;
import org.dpmFramework.annotation.Inject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

@Controller
public class MG_Controller implements KeyListener {

    @Inject
    MG_ViewService mgViewService;


    @Override
    public void keyPressed(KeyEvent keyEvent) {

        switch (keyEvent.getKeyChar()) {
            case 'w' -> mgViewService.moveView(Direction.NORTH);
            case 'a' -> mgViewService.moveView(Direction.WEST);
            case 's' -> mgViewService.moveView(Direction.SOUTH);
            case 'd' -> mgViewService.moveView(Direction.EAST);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }
}
