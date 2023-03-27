package gui;

import model.Card;
import model.Shop;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static gui.CardWizardryApp.WINDOW_HEIGHT;
import static gui.CardWizardryApp.WINDOW_WIDTH;

public class IntroSequenceGUI extends Panel {
    private JPanel interactionPanel;

    private ShopGUI shopGUI;

    public IntroSequenceGUI(JPanel parent, ShopGUI shopGUI) {
        super(parent, "#63c74d", "IntroSequenceGUI");

        this.shopGUI = shopGUI;

        ActionListener toShop = e -> {
            createUser();
            createShop();
            parentLayout.show(parent, "ShopGUI");
        };
        this.add(createButton("CONTINUE", "#b86f50", 280, 80, WINDOW_WIDTH - 200,
                WINDOW_HEIGHT - 150, toShop, 40));

        interactionPanel = createInteractionPanel("#e4a672", "#733e39");
        JLabel text = new JLabel("Welcome to Card Wizardry! You wander for a while and enter a shop.");
        text.setFont(new Font(FONT, Font.BOLD, 30));
        interactionPanel.add(text);
        this.add(interactionPanel);

        saveButton.setVisible(false);
    }

    public void createShop() {
        Shop.setInstance(new Shop(User.getInstance()));
        shopGUI.updateShop();
    }

    public void createUser() {
        User.setInstance(new User("New User"));
    }


}
