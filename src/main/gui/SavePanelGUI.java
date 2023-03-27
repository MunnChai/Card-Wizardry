package gui;

import model.Shop;
import model.User;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import static gui.CardWizardryApp.WINDOW_WIDTH;

public class SavePanelGUI extends Panel {
    private JPanel interactionPanel;
    private String previousPanel;

    private JLabel questionLabel;
    private JLabel doneLabel;

    public SavePanelGUI(JPanel parent) {
        super(parent, "#5a6988", "SavePanelGUI");
        this.setLayout(null);

        questionLabel = createText("Would you like to save your game?", "#ffffff", 1000, 1000,
                WINDOW_WIDTH / 2, 200, 40);
        questionLabel.setHorizontalAlignment(JLabel.CENTER);
        questionLabel.setVerticalAlignment(JLabel.CENTER);

        doneLabel = createText("Your game has been saved!", "#ffffff", 1000, 1000,
                WINDOW_WIDTH / 2, 200, 40);

        interactionPanel = createInteractionPanel("#3a4466", "#262b44");
        interactionPanel.setLayout(new GridLayout());

        saveButton.setVisible(false);

        this.add(questionLabel);
        this.add(doneLabel);
        this.add(interactionPanel);
        resetSaveScreen();
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

    public void setPreviousPanel(String previousPanel) {
        this.previousPanel = previousPanel;
    }

    public void resetSaveScreen() {
        interactionPanel.removeAll();

        ActionListener returnAction = e -> {
            parentLayout.show(super.parent, previousPanel);
        };
        ActionListener saveAction = e -> {
            interactionPanel.removeAll();
            JButton continueButton = createButton("CONTINUE", "#3e8948", 0, 0, 0, 0,
                    returnAction, 40);
            interactionPanel.add(continueButton);
            interactionPanel.revalidate();
            saveUser();
            saveShop();
            questionLabel.setVisible(false);
            doneLabel.setVisible(true);
        };

        JButton saveButton = createButton("SAVE", "#3e8948", 0, 0,
                0, 0, saveAction, 40);
        JButton cancelButton = createButton("CANCEL", "#e43b44", 0, 0,
                0, 0, returnAction, 40);

        interactionPanel.add(saveButton);
        interactionPanel.add(cancelButton);
        doneLabel.setVisible(false);
    }

    private void saveShop() {
        JsonWriter shopWriter = new JsonWriter("./data/shop.json");
        try {
            shopWriter.open();
            shopWriter.write(Shop.getInstance());
            shopWriter.close();
            System.out.println("Saved shop to ./data/shop.json");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found. Could not save to file.");
        }
    }

    private void saveUser() {
        JsonWriter userWriter = new JsonWriter("./data/user.json");
        try {
            userWriter.open();
            userWriter.write(User.getInstance());
            userWriter.close();
            System.out.println("Saved " + User.getInstance().getName() + " to ./data/shop.json");
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found. Could not save to file.");
        }
    }
}
