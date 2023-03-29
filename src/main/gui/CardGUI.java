package gui;

import model.Card;

import javax.swing.*;
import java.awt.*;

import static gui.Panel.FONT;
import static model.Card.CardType.*;

// Represents a graphic panel that displays the statistics, name, and type of the given card
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
        addIcon(card.getType());
    }

    private void decorateCard() {
        setColour();
        this.setBorder(BorderFactory.createLineBorder(Color.black));

        this.add(createText(card.getName(), "#ffffff", 280, 100,
                this.getWidth() / 2, 200, 15));

        this.add(createText("Strength:", "#ffffff", 100, 100, 40, 50,
                10));
        this.add(createText(Integer.toString(card.getValue()), "#ffffff", 100, 100, 40, 60,
                20));
        this.add(createText("Energy Cost:", "#ffffff", 100, 100,
                this.getWidth() - 40, 50, 10));
        this.add(createText(Integer.toString(card.getEnergyCost()), "#ffffff", 100, 100,
                this.getWidth() - 40, 60, 20));
    }

    private void setColour() {
        Card.CardType cardType = card.getType();
        if (cardType == ATTACK) {
            this.setBackground(Color.decode(RED));
        } else if (cardType == Card.CardType.HEAL) {
            this.setBackground(Color.decode(GREEN));
        } else {
            this.setBackground(Color.decode(BLUE));
        }
    }

    private void addIcon(Card.CardType cardType) {
        String fileName;
        if (cardType == ATTACK) {
            fileName = "SwordIcon";
        } else if (cardType == HEAL) {
            fileName = "HealIcon";
        } else {
            fileName = "ShieldIcon";
        }

        JLabel iconLabel = new JLabel();
        iconLabel.setSize(100, 100);
        iconLabel.setLocation(128 - iconLabel.getWidth() / 2, 80 - iconLabel.getHeight() / 2);

        iconLabel.setIcon(makeScaledImageIcon("./data/" + fileName + ".png", iconLabel.getWidth(),
                iconLabel.getHeight()));

        this.add(iconLabel);
    }

    private ImageIcon makeScaledImageIcon(String fileName, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(fileName);
        ImageIcon scaledImageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(width, height,
                Image.SCALE_DEFAULT));
        return scaledImageIcon;
    }

    private JPanel createText(String text, String colorHex, int width, int height, int x, int y, int textSize) {
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

    public Card getCard() {
        return card;
    }
}
