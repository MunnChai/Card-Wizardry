package gui;

import model.Shop;
import model.User;
import model.Event;
import model.EventLog;
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

    private JLabel textLabel;
    private JTextArea eventLog;

    // Constructor for SavePanelGUI
    // Creates an interactionPanel that has buttons for the user to interact with, and text to display a question or
    // statement
    public SavePanelGUI(JPanel parent) {
        super(parent, "#5a6988", "SavePanelGUI");
        this.setLayout(null);

        textLabel = createText("Would you like to save your game?", "#ffffff", 1000, 1000,
                450, 200, 40);
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setVerticalAlignment(JLabel.CENTER);

        interactionPanel = createInteractionPanel("#3a4466", "#262b44");
        interactionPanel.setLayout(new GridLayout());

        saveButton.setVisible(false);

        this.add(textLabel);
        addEventLog();
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
            textLabel.setText("Your game has been saved!");
        };

        JButton saveButton = createButton("SAVE", "#3e8948", 0, 0,
                0, 0, saveAction, 40);
        JButton cancelButton = createButton("CANCEL", "#e43b44", 0, 0,
                0, 0, returnAction, 40);

        interactionPanel.add(saveButton);
        interactionPanel.add(cancelButton);
        textLabel.setText("Would you like to save your game?");
        updateEventLog();
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

    // MODIFIES: this
    // EFFECTS: adds the event to the interaction panel with all the events so far
    private void addEventLog() {
        eventLog = new JTextArea();
        eventLog.setEditable(false);
        eventLog.setBackground(Color.decode("#8b9bb4"));
        eventLog.setText("\n");
        JScrollPane scrollPane = new JScrollPane(eventLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(WINDOW_WIDTH - 395, 0, 380, 385);
        this.add(scrollPane);
        updateEventLog();
    }

    // MODIFIES: eventLog
    // EFFECTS: updates text with all the events that have been added so far
    public void updateEventLog() {
        eventLog.removeAll();
        eventLog.setText("\n");

        JLabel eventLogLabel = createText("Event Log", "#262b44", 200, 100, 80, 25, 30);
        eventLog.add(eventLogLabel);

        for (Event event : EventLog.getInstance()) {
            eventLog.setText(eventLog.getText() + "\n\n" + event.toString());
        }
        eventLog.repaint();
    }

    // Setters
    public void setPreviousPanel(String previousPanel) {
        this.previousPanel = previousPanel;
    }
}
