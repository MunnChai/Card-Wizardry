package ui;

import model.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardWizardryApp extends JFrame {
    public static final int WINDOW_WIDTH = 1600;
    public static final int WINDOW_HEIGHT = 900;

    public CardWizardryApp() {
        this.addMouseListener(new DesktopFocusAction());

        setTitle("Card Wizardry");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new CardLayout());

        addTitleScreenUI();

        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void addTitleScreenUI() {
        TitleScreenGUI titleScreenGUI = new TitleScreenGUI();
        this.add(titleScreenGUI, BorderLayout.CENTER);
    }

    /**
     * Represents action to be taken when user clicks desktop
     * to switch focus. (Needed for key handling.)
     */
    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            CardWizardryApp.this.requestFocusInWindow();
        }
    }

    public static void main(String[] args) {
        new CardWizardryApp();
    }
}
