package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleScreenGUI extends Panel {

    public TitleScreenGUI(Component parent) {
        super(parent,"#2ce8f5");

        int buttonOffset = 120;

        addText("Card Wizardry", "#124e89", 1000, 200, CENTER_X, 200, 120);

        ActionListener newGame = switchPanelAction("IntroSequenceGUI", parent);
        ActionListener loadGame = switchPanelAction("SaveScreenGUI", parent);
        ActionListener quitGame = quitGameAction();

        this.add(createButton("NEW GAME", "#0099db", 280, 80, CENTER_X, CENTER_Y, newGame,
                40));
        this.add(createButton("LOAD FILE", "#0099db", 280, 80, CENTER_X,
                CENTER_Y + buttonOffset, loadGame, 40));
        this.add(createButton("QUIT", "#0099db", 280, 80, CENTER_X,
                CENTER_Y + 2 * buttonOffset, quitGame, 40));
    }

    private ActionListener quitGameAction() {
        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        return action;
    }
}
