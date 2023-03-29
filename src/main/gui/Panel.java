package gui;

import model.Shop;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static gui.CardWizardryApp.*;

// An abstract class that represents a screen of the app that you can switch on or off of
public abstract class Panel extends JPanel {
    protected Color backgroundColor;
    protected static final int CENTER_X = WINDOW_WIDTH / 2;
    protected static final int CENTER_Y = WINDOW_HEIGHT / 2;
    protected static final String FONT = "OpenSymbol";

    protected JPanel parent;
    protected CardLayout parentLayout;

    protected JButton saveButton;

    // Constructor for a Panel
    // Sets the colour, size, layout, and a save button on the Panel
    public Panel(JPanel parent, String backgroundHexColor, String thisPanelName) {
        this.parent = parent;
        backgroundColor = Color.decode(backgroundHexColor);
        this.setBackground(backgroundColor);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(null);

        parentLayout = (CardLayout) parent.getLayout();

        addSaveButton(thisPanelName);
    }

    // EFFECTS: returns a button with the given text, colour, size, location, action, and text size
    public JButton createButton(String text, String colorHex, int width, int height, int x, int y,
                                ActionListener action, int textSize) {
        JButton button = new JButton(text);
        button.setSize(width, height);

        button.setLocation(x - width / 2, y - height / 2);
        button.setFont(new Font(FONT, Font.BOLD, textSize));
        button.setForeground(Color.white);
        button.setBackground(Color.decode(colorHex));
        button.addActionListener(action);
        return button;
    }

    // EFFECTS: returns a JPanel that has a JLabel with given text, colour, size, location, and text size
    public JLabel createText(String text, String colorHex, int width, int height, int x, int y, int textSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setSize(width, height);
        label.setLocation(x - label.getWidth() / 2, y - label.getHeight() / 2);


        label.setFont(new Font(FONT, Font.BOLD, textSize));
        label.setForeground(Color.decode(colorHex));
        return label;
    }

    // EFFECTS: returns an ActionListener which switches to the panel with the given name
    public ActionListener switchPanelAction(String panelLayoutName) {
        ActionListener action = e -> {
            parentLayout.show(parent, panelLayoutName);
        };
        return action;
    }

    // EFFECTS: creates an interactionPanel with the given colours
    public JPanel createInteractionPanel(String hexColour, String borderHexColour) {
        JPanel panel = new JPanel();
        panel.setSize(WINDOW_WIDTH, 300);
        panel.setLocation(0, WINDOW_HEIGHT - panel.getHeight()  - 35);
        panel.setBorder(BorderFactory.createLineBorder(Color.decode(borderHexColour)));
        panel.setBackground(Color.decode(hexColour));
        return panel;
    }

    // EFFECTS: creates and adds a save button which allows a user to save their data
    public void addSaveButton(String previousPanelName) {
        ActionListener makeSavePanel = e -> {
            SavePanelGUI savePanel = (SavePanelGUI)parent.getComponent(5);
            savePanel.setPreviousPanel(previousPanelName);
            savePanel.resetSaveScreen();
            parentLayout.show(parent, "SavePanelGUI");
        };
        saveButton = new JButton(makeScaledImageIcon("./data/SaveIcon.png", 40, 40));
        saveButton.setBackground(Color.decode("#c0cbdc"));
        saveButton.setBounds(WINDOW_WIDTH - 80, 10, 60, 60);
        saveButton.addActionListener(makeSavePanel);
        this.add(saveButton);
    }

    // EFFECTS: returns an ImageIcon with given width and height
    public ImageIcon makeScaledImageIcon(String fileName, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(fileName);
        ImageIcon scaledImageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(width, height,
                Image.SCALE_DEFAULT));
        return scaledImageIcon;
    }
}
