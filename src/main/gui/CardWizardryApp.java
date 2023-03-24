package gui;

import javax.swing.*;
import java.awt.*;

public class CardWizardryApp {
    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 900;

    private CardLayout layout;
    private JFrame frame;
    private JPanel cardPanel;

    public CardWizardryApp() {
        frame = new JFrame();

        frame.setTitle("Card Wizardry");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        layout = new CardLayout();
        cardPanel = new JPanel(layout);

        addGuiPanel(new TitleScreenGUI(cardPanel), "TitleScreenGUI");
        addGuiPanel(new ShopGUI(cardPanel), "ShopGUI");
        addGuiPanel(new SaveScreenGUI(cardPanel), "SaveScreenGUI");
        addGuiPanel(new IntroSequenceGUI(cardPanel), "IntroSequenceGUI");
        addGuiPanel(new BattleScreenGUI(cardPanel), "BattleScreenGUI");
        addGuiPanel(new EditDecksGUI(cardPanel), "EditDecksGUI");

        frame.add(cardPanel);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void addGuiPanel(Panel panel, String layoutComponentName) {
        cardPanel.add(panel, BorderLayout.CENTER);
        layout.addLayoutComponent(layoutComponentName, panel);
    }

    public static void main(String[] args) {
        new CardWizardryApp();
    }
}
