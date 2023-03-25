package gui;

import java.awt.*;
import java.awt.event.ActionListener;

public class SaveScreenGUI extends Panel {

    public SaveScreenGUI(Component parent) {
        super(parent,"#8b9bb4");
        ActionListener backToTitle = switchPanelAction("TitleScreenGUI", parent);
        this.add(createButton("BACK TO TITLE", "#5a6988", 280, 60, 170, 50, backToTitle,
                30));
        // TODO
    }
}
