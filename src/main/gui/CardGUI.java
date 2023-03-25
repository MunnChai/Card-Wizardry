package gui;

import model.Card;

import javax.swing.*;
import java.awt.*;

import static gui.Panel.FONT;

public class CardGUI extends JPanel {

    private static String RED = "#e43b44";
    private static String GREEN = "#3e8948";
    private static String BLUE = "#0099db";

    private Card card;
    private String action;

    public CardGUI(Card card) {
        this.card = card;
        this.setSize(256, 300);
        this.setLayout(null);

        decorateCard();
    }

    private void decorateCard() {
        setColour();
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.add(createText(card.getName(), "#ffffff", 280, 100,
                this.getWidth() / 2, 200, 15));

        this.add(createText(Integer.toString(card.getValue()), "#ffffff", 100, 100, 30, 50,
                20));
        this.add(createText(Integer.toString(card.getEnergyCost()), "#ffffff", 100, 100,
                this.getWidth() - 30, 50, 20));
    }

    private void setColour() {
        Card.CardType cardType = card.getType();
        if (cardType == Card.CardType.ATTACK) {
            this.setBackground(Color.decode(RED));
        } else if (cardType == Card.CardType.HEAL) {
            this.setBackground(Color.decode(GREEN));
        } else {
            this.setBackground(Color.decode(BLUE));
        }
    }

    public JPanel createText(String text, String colorHex, int width, int height, int x, int y, int textSize) {
        JPanel panel = new JPanel();
        panel.setSize(width, height);
        panel.setLocation(x - panel.getWidth() / 2, y - panel.getHeight() / 2);

        JLabel label = new JLabel(text);
        label.setFont(new Font(FONT, Font.BOLD, textSize));
        label.setForeground(Color.decode(colorHex));

        panel.add(label);
        panel.setOpaque(false);
        return panel;
    }

    public void addButton(JButton button) {
        button.setLocation(28, 190);
        this.add(button);
    }

    public Card getCard() {
        return card;
    }
}
