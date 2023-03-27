package gui;

import model.Shop;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static gui.CardWizardryApp.*;

public abstract class Panel extends JPanel {
    protected Color backgroundColor;
    protected static final int CENTER_X = WINDOW_WIDTH / 2;
    protected static final int CENTER_Y = WINDOW_HEIGHT / 2;
    protected static final String FONT = "OpenSymbol";

    protected JPanel parent;
    protected CardLayout parentLayout;

    protected JButton saveButton;

    protected User user;
    protected Shop shop;

    public Panel(JPanel parent, String backgroundHexColor, String thisPanelName) {
        user = User.getInstance();
        Shop.setInstance(new Shop(user));
        shop = Shop.getInstance();
        this.parent = parent;
        backgroundColor = Color.decode(backgroundHexColor);
        this.setBackground(backgroundColor);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(null);

        parentLayout = (CardLayout) parent.getLayout();

        addSaveButton(thisPanelName);
    }

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

    public JLabel createText(String text, String colorHex, int width, int height, int x, int y, int textSize) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setSize(width, height);
        label.setLocation(x - label.getWidth() / 2, y - label.getHeight() / 2);


        label.setFont(new Font(FONT, Font.BOLD, textSize));
        label.setForeground(Color.decode(colorHex));
        return label;
    }

    public ActionListener switchPanelAction(String panelLayoutName) {
        user = User.getInstance();
        ActionListener action = e -> {
            parentLayout.show(parent, panelLayoutName);
        };
        return action;
    }

    public JPanel createInteractionPanel(String hexColour, String borderHexColour) {
        JPanel panel = new JPanel();
        panel.setSize(WINDOW_WIDTH, 300);
        panel.setLocation(0, WINDOW_HEIGHT - panel.getHeight()  - 35);
        panel.setBorder(BorderFactory.createLineBorder(Color.decode(borderHexColour)));
        panel.setBackground(Color.decode(hexColour));
        return panel;
    }

    public void addSaveButton(String previousPanelName) {
        ActionListener makeSavePanel = e -> {
            SavePanelGUI savePanel = (SavePanelGUI)parent.getComponent(5);
            savePanel.setPreviousPanel(previousPanelName);
            savePanel.resetSaveScreen();
            parentLayout.show(parent, "SavePanelGUI");
        };
        saveButton = createButton("SAVE", "#c0cbdc", 40, 40,WINDOW_WIDTH - 60, 40, makeSavePanel, 10);
        this.add(saveButton);
    }

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        g.setColor(backgroundColor);
//        g.fillRect(0, 0, getWidth(), getHeight());
//    }
}
