package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// Represents the screen for battle sequences
public class BattleScreenGUI extends Panel {
    private JPanel interactionPanel;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Constructor for BattleScreenGUI
    public BattleScreenGUI(JPanel parent) {
        super(parent, "#265c42", "BattleScreenGUI");

        interactionPanel = createInteractionPanel("#733e39", "#3a4466");
        this.add(interactionPanel);

        ActionListener backToTitle = switchPanelAction("TitleScreenGUI");
        this.add(createButton("BACK TO TITLE", "#193c3e", 280, 60, 170, 50,
                backToTitle, 30));

        saveButton.setVisible(false);
    }
}
