package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static gui.CardWizardryApp.*;

public abstract class Panel extends JPanel {
    protected Color backgroundColor;
    protected static final int CENTER_X = WINDOW_WIDTH / 2;
    protected static final int CENTER_Y = WINDOW_HEIGHT / 2;
    protected static final String FONT = "OpenSymbol";
    protected Component parent;

    public Panel(Component parent, String backgroundHexColor) {
        this.parent = parent;
        backgroundColor = Color.decode(backgroundHexColor);
        this.setBackground(backgroundColor);
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(null);
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

    public ActionListener switchPanelAction(String panelLayoutName, Component parent) {
        JPanel parentPanel = (JPanel)parent;
        CardLayout parentLayout = (CardLayout)parentPanel.getLayout();
        ActionListener action = e -> {
            parentLayout.show(parentPanel, panelLayoutName);
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

//    @Override
//    public void paint(Graphics g) {
//        super.paint(g);
//        g.setColor(backgroundColor);
//        g.fillRect(0, 0, getWidth(), getHeight());
//    }
}
