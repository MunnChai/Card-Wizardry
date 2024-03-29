package gui;

import javax.swing.*;
import java.awt.*;

// Represents the frame of the app, holds the different screens in a CardLayout
public class CardWizardryApp {
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    private CardLayout layout;
    private JFrame frame;
    private JPanel cardPanel;

    private ShopGUI shopGUI;

    // Constructor for CardWizardryApp
    // Creates a JFrame that holds a JPanel. This JPanel has the CardLayout which can be used to switch between
    // different screens
    public CardWizardryApp() {
        frame = new JFrame();

        frame.setTitle("Card Wizardry");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        layout = new CardLayout();
        cardPanel = new JPanel(layout);

        shopGUI = new ShopGUI(cardPanel);
        addGuiPanel(new TitleScreenGUI(cardPanel, shopGUI), "TitleScreenGUI");
        addGuiPanel(shopGUI, "ShopGUI");
        addGuiPanel(new IntroSequenceGUI(cardPanel, shopGUI), "IntroSequenceGUI");
        addGuiPanel(new BattleScreenGUI(cardPanel), "BattleScreenGUI");
        addGuiPanel(new EditDecksGUI(cardPanel), "EditDecksGUI");
        addGuiPanel(new SavePanelGUI(cardPanel), "SavePanelGUI");

        frame.add(cardPanel);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // MODIFIES: cardPanel
    // EFFECTS: Add a Panel to the CardPanel and CardLayout, which can be set as the visible screen using show()
    public void addGuiPanel(Panel panel, String layoutComponentName) {
        cardPanel.add(panel, BorderLayout.CENTER);
        layout.addLayoutComponent(layoutComponentName, panel);
    }

    public static void main(String[] args) {
        new CardWizardryApp();
    }
}
