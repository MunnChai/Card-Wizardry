package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static gui.CardWizardryApp.WINDOW_HEIGHT;
import static gui.CardWizardryApp.WINDOW_WIDTH;

public class IntroSequenceGUI extends Panel {
    private JPanel interactionPanel;

    public IntroSequenceGUI(Component parent) {
        super(parent, "#63c74d");

        ActionListener toShop = switchPanelAction("ShopGUI", parent);
        this.add(createButton("CONTINUE", "#b86f50", 280, 80, WINDOW_WIDTH - 200,
                WINDOW_HEIGHT - 150, toShop, 40));

        interactionPanel = createInteractionPanel("#e4a672", "#733e39");
        JLabel text = new JLabel("Welcome to Card Wizardry! You wander for a while and enter a shop.");
        text.setFont(new Font(FONT, Font.BOLD, 30));
        interactionPanel.add(text);
        this.add(interactionPanel);
    }


}
