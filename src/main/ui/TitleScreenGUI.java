package ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static ui.CardWizardryApp.*;

public class TitleScreenGUI extends JPanel {
    private Color backgroundColor;
    private static final int CENTER_X = WINDOW_WIDTH / 2;
    private static final int CENTER_Y = WINDOW_HEIGHT / 2;

    public TitleScreenGUI() {
        backgroundColor = Color.decode("#33D1FF");
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setLayout(null);

        int buttonOffset = 120;

        addTitle();

        addButton("NEW GAME", "#2978D6", 280, 80, CENTER_X, CENTER_Y);
        addButton("LOAD FILE", "#2978D6", 280, 80, CENTER_X, CENTER_Y + buttonOffset);
        addButton("QUIT", "#2978D6", 280, 80, CENTER_X, CENTER_Y + 2 * buttonOffset);
    }

    public void addTitle() {
        JLabel label = new JLabel("Card Wizardry");
        label.setSize(200, 400);
        label.setLocation(CENTER_X - label.getWidth(), 400 - label.getHeight());
        label.setFont(new Font("OpenSymbol", Font.BOLD, 40));
        label.setForeground(Color.decode("#2F20E3"));
        this.add(label, BorderLayout.CENTER);
    }

    public void addButton(String text, String colorHex, int width, int height, int x, int y) {
        JButton button = new JButton(text);
        button.setSize(width, height);

        button.setLocation(x - width / 2, y - height / 2);
        button.setFont(new Font("OpenSymbol", Font.BOLD, 40));
        button.setForeground(Color.white);
        button.setBackground(Color.decode(colorHex));
        this.add(button, BorderLayout.CENTER);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
