package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static gui.CardWizardryApp.WINDOW_HEIGHT;
import static gui.CardWizardryApp.WINDOW_WIDTH;

public class BattleScreenGUI extends Panel {
    private JPanel interactionPanel;

    public BattleScreenGUI(Component parent) {
        super(parent, "#265c42");

        interactionPanel = createInteractionPanel("#733e39", "#3a4466");
        this.add(interactionPanel);

        ActionListener backToTitle = switchPanelAction("TitleScreenGUI", parent);
        this.add(createButton("BACK TO TITLE", "#193c3e", 280, 60, 170, 50,
                backToTitle, 30));
    }
}
