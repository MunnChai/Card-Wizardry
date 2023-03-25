package gui;

import model.Card;
import model.Deck;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditDecksGUI extends Panel {
    private JPanel interactionPanel;
    private CardLayout interactionLayout;

    private JButton backToShopButton;

    private JPanel mainPanel;
    private JPanel mainScrollPanel;

    private User user;
    private Deck currentlyEditingDeck;



    public EditDecksGUI(Component parent) {
        super(parent, "#8b9bb4");

        this.user = User.getInstance();

        interactionPanel = createInteractionPanel("#5a6988", "#3a4466");
        this.add(interactionPanel);
        interactionLayout = new CardLayout();
        interactionPanel.setLayout(interactionLayout);

        ActionListener backToShop = switchPanelAction("ShopGUI", parent);
        backToShopButton = createButton("BACK TO SHOP", "#5a6988", 280, 60, 170, 50,
                backToShop, 30);
        this.add(backToShopButton);

        // TODO Make JPanel to display cards

        interactionPanel.add(createMainPanel());
        interactionLayout.show(interactionPanel, "MainPanel");
    }

    private JPanel createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.decode("#5a6988"));
        mainPanel.setLayout(new GridLayout());

        ActionListener createNewDeck = createNewDeckAction();
        mainPanel.add(createButton("CREATE NEW DECK", "#5a6988", 256, 300, 128, 150,
                createNewDeck, 30));

        JScrollPane scrollPane = makeDecksScrollPane();
        mainPanel.add(scrollPane);
        interactionLayout.addLayoutComponent("MainPanel", mainPanel);
        return mainPanel;
    }

    private JScrollPane makeDecksScrollPane() {
        mainScrollPanel = new JPanel();
        mainScrollPanel.setLayout(new GridLayout());
        mainScrollPanel.setBackground(Color.decode("#5a6988"));
        int scrollPanelWidth = 0;
        for (Deck deck : user.getDecks()) {
            JPanel deckPanel = makeDeckPanel(deck);
            JButton editButton = makeEditButton(deck);
            deckPanel.add(editButton);
            mainScrollPanel.add(deckPanel);
            interactionPanel.add(makeEditDeckPanel(deck, deckPanel));
            scrollPanelWidth += 256;
        }
        mainScrollPanel.setPreferredSize(new Dimension(scrollPanelWidth, 300));
        JScrollPane scrollPane = new JScrollPane(mainScrollPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1024,300));
        scrollPane.setLocation(768, 150);
        return scrollPane;
    }

    private ActionListener createNewDeckAction() {
        ActionListener action = e -> {
            Deck newDeck = new Deck("New Deck");
            user.addDeck(newDeck);
            JPanel newDeckPanel = makeDeckPanel(newDeck);
            mainScrollPanel.add(newDeckPanel);
            mainScrollPanel.setPreferredSize(new Dimension(mainScrollPanel.getComponentCount() * 256, 300));
            JPanel editDeckPanel = makeEditDeckPanel(newDeck, newDeckPanel);
            interactionLayout.addLayoutComponent("EditDeck" + newDeck.hashCode(), editDeckPanel);
            interactionPanel.add(editDeckPanel);
            interactionPanel.revalidate();
        };
        return action;
    }

    private JPanel makeDeckPanel(Deck deck) {
        JPanel panel = new JPanel();
        panel.setSize(256, 300);
        panel.setLayout(null);
        panel.setBackground(Color.decode("#5a6988"));

        panel.add(createText(deck.getName(), "#ffffff", 200, 100, panel.getWidth() / 2, 50, 30));
        panel.add(createText(deck.getCardsInDeck().size() + "/20 Cards", "#ffffff", 280, 100,
                panel.getWidth() / 2, 125, 20));

        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        JButton editButton = makeEditButton(deck);
        JButton deleteButton = makeDeleteButton(panel, deck);
        panel.add(editButton);
        panel.add(deleteButton);
        return panel;
    }

    private JButton makeEditButton(Deck deck) {
        ActionListener editDeckAction = e -> {
            interactionLayout.show(interactionPanel, "EditDeck" + deck.hashCode());
            currentlyEditingDeck = deck;
            backToShopButton.setVisible(false);
            System.out.println("Currently Editing Deck " + deck.hashCode());
        };
        JButton button = createButton("EDIT", "#8b9bb4", 200, 60, 128, 200,
                editDeckAction, 40);

        return button;
    }

    private JButton makeDeleteButton(JPanel panel, Deck deck) {
        ActionListener deleteAction = e -> {
            user.getDecks().remove(deck);
            mainScrollPanel.remove(panel);
            mainScrollPanel.setPreferredSize(new Dimension(mainScrollPanel.getComponentCount() * 256, 300));
            interactionPanel.revalidate();
        };

        JButton button = createButton("DELETE", "#e43b44", 160, 40, 128, 260,
                deleteAction, 20);

        return button;
    }

    // REQUIRES: currently editing deck is not null
    private JPanel makeEditDeckPanel(Deck deck, JPanel parentPanel) {
        JPanel editDeckPanel = new JPanel();
        editDeckPanel.setLayout(new GridLayout());

        ActionListener backAction = e -> {
            interactionLayout.show(interactionPanel, "MainPanel");
            backToShopButton.setVisible(true);
        };
        ActionListener addAction = e -> {
            // TODO
        };
        ActionListener removeAction = e -> {
            // TODO
        };
        ActionListener renameAction = e -> {
            interactionLayout.show(interactionPanel, "RenameDeck" + deck.hashCode());
        };

        editDeckPanel.add(createButton("BACK", "#5a6988", 0, 0, 0, 0, backAction, 30));
        editDeckPanel.add(createButton("ADD CARD", "#5a6988", 0, 0, 0, 0, addAction, 30));
        editDeckPanel.add(createButton("REMOVE CARD", "#5a6988", 0, 0, 0, 0, removeAction, 30));
        editDeckPanel.add(createButton("RENAME DECK", "#5a6988", 0, 0, 0, 0, renameAction, 30));
        interactionLayout.addLayoutComponent("EditDeck" + deck.hashCode(), editDeckPanel);
        JPanel renamePanel = makeRenamePanel(deck, parentPanel);
        interactionPanel.add(renamePanel);
        interactionLayout.addLayoutComponent("RenameDeck" + deck.hashCode(), renamePanel);
        return editDeckPanel;
    }

    private JPanel makeRenamePanel(Deck deck, JPanel grandparentPanel) {
        JPanel renamePanel = new JPanel();
        renamePanel.setLayout(new GridLayout());
        renamePanel.setBackground(Color.decode("#5a6988"));

        ActionListener backAction = e -> {
            interactionLayout.show(interactionPanel, "EditDeck" + deck.hashCode());
        };
        renamePanel.add(createButton("BACK", "#5a6988", 0, 0, 0, 0, backAction, 30));

        JPanel innerRenamePanel = new JPanel();
        innerRenamePanel.setLayout(null);
        innerRenamePanel.setBackground(Color.decode("#5a6988"));

        JLabel renameText = createText("RENAME \"" + deck.getName() + "\" TO:", "#ffffff", 640, 100,
                320, 50, 20);
        innerRenamePanel.add(renameText);

        JTextField renameTextField = new JTextField(deck.getName());
        renameTextField.setBounds(170, 100, 300, 100);
        renameTextField.setHorizontalAlignment(JTextField.CENTER);
        innerRenamePanel.add(renameTextField);

        ActionListener renameAction = makeRenameAction(renameTextField, deck, grandparentPanel, innerRenamePanel);
        JButton confirmButton = createButton("CONFIRM", "#8b9bb4", 160, 40, 320, 250, renameAction, 20);
        innerRenamePanel.add(confirmButton);

        renamePanel.add(innerRenamePanel);
        return renamePanel;
    }

    private ActionListener makeRenameAction(JTextField textField, Deck deck, JPanel grandparentPanel,
                                            JPanel parentPanel) {
        ActionListener renameAction = e -> {
            System.out.println(deck.getName());
            System.out.println(textField.getText());
            deck.setName(textField.getText());
            System.out.println("Deck Renamed to " + deck.getName());
            interactionLayout.show(interactionPanel, "EditDeck" + deck.hashCode());
            JLabel deckPanelLabel = (JLabel) grandparentPanel.getComponent(0);
            deckPanelLabel.setText(deck.getName());
            JLabel renamePanelLabel = (JLabel) parentPanel.getComponent(0);
            renamePanelLabel.setText("RENAME \"" + deck.getName() + "\" TO:");
        };
        return renameAction;
    }
}
