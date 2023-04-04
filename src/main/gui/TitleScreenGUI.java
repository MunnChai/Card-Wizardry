package gui;

import model.Event;
import model.EventLog;
import model.Shop;
import model.User;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.IOException;

// Represents the title screen for the app
public class TitleScreenGUI extends Panel {
    private ShopGUI shopGUI;

    // Constructor for TitleScreenGUI
    // Creates the title text, and 3 buttons to:
    // - start a new game
    // - load a save file
    // - quit game
    public TitleScreenGUI(JPanel parent, ShopGUI shopGUI) {
        super(parent,"#2ce8f5", "TitleScreenGUI");

        this.shopGUI = shopGUI;
        int buttonOffset = 120;

        this.add(createText("Card Wizardry", "#124e89", 1000, 200, CENTER_X, 200, 120));

        ActionListener newGame = switchPanelAction("IntroSequenceGUI");
        ActionListener loadGame = loadGameAction();
        ActionListener quitGame = quitGameAction();

        this.add(createButton("NEW GAME", "#0099db", 280, 80, CENTER_X, CENTER_Y, newGame,
                40));
        this.add(createButton("LOAD FILE", "#0099db", 280, 80, CENTER_X,
                CENTER_Y + buttonOffset, loadGame, 40));
        this.add(createButton("QUIT", "#0099db", 280, 80, CENTER_X,
                CENTER_Y + 2 * buttonOffset, quitGame, 40));

        saveButton.setVisible(false);
    }

    // EFFECTS: returns action to load a game from a save file
    private ActionListener loadGameAction() {
        ActionListener action = f -> {
            loadUser();
            loadShop();
            shopGUI.updateShop();
            shopGUI.makeCatSayText("Welcome to my shop.");
            parentLayout.show(parent, "ShopGUI");
        };
        return action;
    }

    // MODIFIES: User
    // EFFECTS: reads User from the JSON save data, and sets current instance to that user
    private void loadUser() {
        JsonReader jsonReader = new JsonReader("./data/user.json");
        User user;
        try {
            user = jsonReader.read();
            System.out.println("Loaded " + User.getInstance().getName() + " from ./data/user.json");
        } catch (IOException e) {
            user = new User("Failed To Load");
            System.out.println("Unable to read from file: ./data/user.json");
        }
        User.setInstance(user);
    }

    // MODIFIES: Shop
    // EFFECTS: reads Shop from the JSON save data, and sets current instance to that shop
    private void loadShop() {
        JsonReader shopReader = new JsonReader("./data/shop.json");
        Shop shop;
        try {
            shop = shopReader.readShop();
            System.out.println("Loaded shop from ./data/shop.json");
        } catch (IOException e) {
            shop = new Shop(User.getInstance());
            System.out.println("Unable to read from file: ./data/shop.json");
        }
        Shop.setInstance(shop);
        Shop.getInstance().setUser(User.getInstance());
    }

    // EFFECTS: returns an ActionListener to close the program
    private ActionListener quitGameAction() {
        ActionListener action = e -> {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event.toString());
            }
            System.exit(0);
        };
        return action;
    }
}
