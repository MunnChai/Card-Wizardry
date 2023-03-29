package gui;

import model.Shop;
import model.User;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import static gui.CardWizardryApp.WINDOW_WIDTH;

// Represents the save screen of the app
public class SavePanelGUI extends Panel {
    private JPanel interactionPanel;
    private String previousPanel;

    private JLabel questionLabel;
    private JLabel doneLabel;

    // Constructor for SavePanelGUI
    // Creates an interactionPanel that has buttons for the user to interact with, and text to display a question or
    // statement
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

    // MODIFIES: this
    // EFFECTS: resets this screen to its default state
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
        questionLabel.setVisible(true);
        doneLabel.setVisible(false);
    }

    // EFFECTS: saves a JSON representation of the current Shop
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

    // EFFECTS: saves a JSON representation of the current User
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

    // Setters
    public void setPreviousPanel(String previousPanel) {
        this.previousPanel = previousPanel;
    }
}
