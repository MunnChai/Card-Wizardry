package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EditDecksGUI extends Panel {
    private JPanel interactionPanel;

    public EditDecksGUI(Component parent) {
        super(parent, "#8b9bb4");

        interactionPanel = createInteractionPanel("#5a6988", "#3a4466");
        this.add(interactionPanel);

        ActionListener backToTitle = switchPanelAction("TitleScreenGUI", parent);
        this.add(createButton("BACK TO TITLE", "#5a6988", 280, 60, 170, 50,
                backToTitle, 30));

    }
}
