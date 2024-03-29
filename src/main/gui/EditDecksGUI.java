package gui;

import model.Card;
import model.Deck;
import model.EventLog;
import model.User;
import model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

// Represents the screen for editing user's decks
public class EditDecksGUI extends Panel {
    private JPanel interactionPanel;
    private CardLayout interactionLayout;

    private JButton backToShopButton;
    private JPanel cardPanel;
    private JScrollPane cardScrollPane;
    private JLabel deckName;

    private JPanel addableCardPanel;

    private JPanel mainPanel;
    private JPanel mainScrollPanel;

    // Constructor for EditDecksGUI
    // Creates an interaction panel for the user, and a back to shop button to return to the ShopGUI
    public EditDecksGUI(JPanel parent) {
        super(parent, "#8b9bb4", "EditDecksGUI");

        interactionPanel = createInteractionPanel("#5a6988", "#3a4466");
        this.add(interactionPanel);
        interactionLayout = new CardLayout();
        interactionPanel.setLayout(interactionLayout);

        ActionListener backToShop = e -> {
            ShopGUI shopGUI = (ShopGUI) parent.getComponent(1);
            shopGUI.updateShop();
            parentLayout.show(parent, "ShopGUI");
        };
        backToShopButton = createButton("BACK TO SHOP", "#5a6988", 280, 60, 170, 50,
                backToShop, 30);
        this.add(backToShopButton);

        initializeCardPanel();

        interactionPanel.add(createMainPanel());
        interactionLayout.show(interactionPanel, "MainPanel");
    }

    // MODIFIES: interactionPanel
    // EFFECTS: Adds the main JPanel to the interaction panel, where users can either create a new deck or select
    //          an existing deck
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

    // MODIFIES: this
    // EFFECTS: Initializes the panel which shows a user the cards in a deck
    private void initializeCardPanel() {
        cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout());
        cardPanel.setBackground(Color.decode("#5a6988"));
        cardPanel.setLocation(70, 70);
        cardScrollPane = new JScrollPane(cardPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        cardScrollPane.setSize(1140,300);
        cardScrollPane.setLocation(70, 70);
        this.add(cardScrollPane);
        deckName = new JLabel("Temp Text");
        deckName.setBounds(CENTER_X - 200, 10,400,60);
        deckName.setFont(new Font(FONT, Font.BOLD, 50));
        deckName.setHorizontalAlignment(JLabel.CENTER);
        deckName.setVerticalAlignment(JLabel.CENTER);
        this.add(deckName);
        setCardPanelVisibility(false);
    }

    // EFFECTS: returns a JScrollPane that holds panels for each of the user's decks
    private JScrollPane makeDecksScrollPane() {
        mainScrollPanel = new JPanel();
        mainScrollPanel.setLayout(new GridLayout());
        mainScrollPanel.setBackground(Color.decode("#5a6988"));

        updateDecksScrollPane();

        JScrollPane scrollPane = new JScrollPane(mainScrollPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(1024,300));
        scrollPane.setLocation(768, 150);
        return scrollPane;
    }

    // MODIFIES: mainScrollPanel
    // EFFECTS: repaints the JScrollPane that shows the user's decks
    public void updateDecksScrollPane() {
        mainScrollPanel.removeAll();

        int scrollPanelWidth = 0;
        List<Deck> decks = User.getInstance().getDecks();

        if (decks.size() != 0) {
            for (Deck deck : decks) {
                JPanel deckPanel = makeDeckPanel(deck);
                mainScrollPanel.add(deckPanel);
                interactionPanel.add(makeEditDeckPanel(deck, deckPanel));
                scrollPanelWidth += 256;
            }
        }
        mainScrollPanel.setPreferredSize(new Dimension(scrollPanelWidth, 300));
    }

    // MODIFIES: mainScrollPanel, interactionPanel
    // EFFECTS: returns an ActionListener that creates a new deck, updates the parts of the shop that have been affected
    private ActionListener createNewDeckAction() {
        ActionListener action = e -> {
            Deck newDeck = new Deck("New Deck");
            User.getInstance().addDeck(newDeck);
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

    // EFFECTS: returns a deck panel, which is decorated with the deck's name, number of cards, and a button to edit the
    //          deck
    private JPanel makeDeckPanel(Deck deck) {
        JPanel panel = new JPanel();
        panel.setSize(256, 300);
        panel.setLayout(null);
        panel.setBackground(Color.decode("#5a6988"));

        panel.add(createText(deck.getName(), "#ffffff", 200, 100, panel.getWidth() / 2, 50, 30));
        panel.add(createText(deck.getCardsInDeck().size() + "/20 Cards", "#ffffff", 280, 100,
                panel.getWidth() / 2, 125, 20));

        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        JButton editButton = makeEditButton(panel, deck);
        JButton deleteButton = makeDeleteButton(panel, deck);
        panel.add(editButton);
        panel.add(deleteButton);
        return panel;
    }

    // EFFECTS: returns the button to edit a given deck
    private JButton makeEditButton(JPanel deckPanel, Deck deck) {
        ActionListener editDeckAction = e -> {
            interactionLayout.show(interactionPanel, "EditDeck" + deck.hashCode());
            backToShopButton.setVisible(false);
            decorateCardPanel(deckPanel, deck);
            decorateAddCardPanel(deckPanel, deck);
            setCardPanelVisibility(true);
        };
        JButton button = createButton("EDIT", "#8b9bb4", 200, 60, 128, 200,
                editDeckAction, 40);

        return button;
    }

    // MODIFIES: cardScrollPane
    // EFFECTS: sets the visibility of the card in the user's deck
    private void setCardPanelVisibility(Boolean visible) {
        cardScrollPane.setVisible(visible);
        deckName.setVisible(visible);
    }

    // MODIFIES: cardPanel
    // EFFECTS: updates the JPanel that holds the cards in a deck
    private void decorateCardPanel(JPanel deckPanel, Deck deck) {
        deckName.setText(deck.getName());

        cardPanel.removeAll();
        List<Card> cards = deck.getCardsInDeck();

        if (cards.size() != 0) {
            for (Card card : cards) {
                CardGUI cardGUI = new CardGUI(card);
                ActionListener removeAction = createRemoveCardAction(deckPanel, deck, card, cardGUI);
                JButton removeButton = createButton("REMOVE", "#a22633", 200, 60, 128, 230,
                        removeAction, 20);
                cardGUI.add(removeButton);
                cardPanel.add(cardGUI);
            }
        }
        cardPanel.setPreferredSize(new Dimension(cardPanel.getComponentCount() * 256, 300));
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    // EFFECTS: returns the ActionListener to remove a card from a deck
    private ActionListener createRemoveCardAction(JPanel deckPanel, Deck deck, Card card, CardGUI cardGUI) {
        ActionListener removeCardAction = e -> {
            deck.removeCard(card);
            cardPanel.remove(cardGUI);
            decorateCardPanel(deckPanel, deck);
            decorateAddCardPanel(deckPanel, deck);
            JLabel cardCount = (JLabel)deckPanel.getComponent(1);
            cardCount.setText(deck.getCardsInDeck().size() + "/20 Cards");
        };
        return removeCardAction;
    }

    // EFFECTS: returns a button which removes a deck from a user's list of decks
    private JButton makeDeleteButton(JPanel panel, Deck deck) {
        ActionListener deleteAction = e -> {
            User.getInstance().removeDeck(deck);
            mainScrollPanel.remove(panel);
            mainScrollPanel.setPreferredSize(new Dimension(mainScrollPanel.getComponentCount() * 256, 300));
            mainScrollPanel.revalidate();
            mainScrollPanel.repaint();
        };

        JButton button = createButton("DELETE", "#e43b44", 160, 40, 128, 260,
                deleteAction, 20);

        return button;
    }

    // EFFECTS: creates and returns a JPanel for the interactionPanel which has buttons that allow actions for a user
    //          to edit their deck
    private JPanel makeEditDeckPanel(Deck deck, JPanel parentPanel) {
        JPanel editDeckPanel = new JPanel();
        editDeckPanel.setLayout(new GridLayout());
        interactionLayout.addLayoutComponent("EditDeck" + deck.hashCode(), editDeckPanel);

        addRequiredPanels(parentPanel, deck);

        ActionListener backButtonAction = makeBackButtonAction();
        ActionListener addButtonAction = makeAddButtonAction(deck, parentPanel);
        ActionListener fillRandomButtonAction = makeFillRandomAction(deck, parentPanel);
        ActionListener removeCardsInDeckAction = makeRemoveAllCardsInDeckAction(deck, parentPanel);
        ActionListener renameButtonAction = makeRenameButtonAction(deck);

        editDeckPanel.add(createButton("BACK", "#5a6988", 0, 0, 0, 0, backButtonAction, 25));
        editDeckPanel.add(createButton("ADD CARD", "#5a6988", 0, 0, 0, 0, addButtonAction, 25));
        editDeckPanel.add(createButton("FILL RANDOMLY", "#5a6988", 0, 0, 0, 0, fillRandomButtonAction, 25));
        editDeckPanel.add(createButton("CLEAR DECK", "#5a6988", 0, 0, 0, 0, removeCardsInDeckAction, 25));
        editDeckPanel.add(createButton("RENAME DECK", "#5a6988", 0, 0, 0, 0, renameButtonAction, 25));

        return editDeckPanel;
    }

    // EFFECTS: returns actionListener to remove all the cards from a deck
    private ActionListener makeRemoveAllCardsInDeckAction(Deck deck, JPanel parentPanel) {
        ActionListener removeCardsInDeckAction = e -> {
            deck.clearDeck();
            cardPanel.removeAll();
            decorateCardPanel(parentPanel, deck);
            JLabel cardCount = (JLabel) parentPanel.getComponent(1);
            cardCount.setText(deck.getCardsInDeck().size() + "/20 Cards");
        };
        return removeCardsInDeckAction;
    }

    // EFFECTS: returns actionListener to show interactionPanel that allows user to add cards to a deck
    private ActionListener makeAddButtonAction(Deck deck, JPanel parentPanel) {
        ActionListener addButtonAction = e -> {
            interactionLayout.show(interactionPanel, "AddCard" + deck.hashCode());
            decorateAddCardPanel(parentPanel, deck);
        };
        return addButtonAction;
    }

    // EFFECTS: returns actionListener to return to the interactionPanel which shows all the user's decks
    private ActionListener makeBackButtonAction() {
        ActionListener backButtonAction = e -> {
            interactionLayout.show(interactionPanel, "MainPanel");
            backToShopButton.setVisible(true);
            cardPanel.removeAll();
            setCardPanelVisibility(false);
        };
        return backButtonAction;
    }

    // EFFECTS: returns actionListener to show the interactionPanel where a user can rename their deck
    private ActionListener makeRenameButtonAction(Deck deck) {
        ActionListener renameButtonAction = e -> {
            interactionLayout.show(interactionPanel, "RenameDeck" + deck.hashCode());
        };
        return renameButtonAction;
    }

    // EFFECTS: creates and adds the interactionPanel where a user can rename their deck
    private void addRequiredPanels(JPanel parentPanel, Deck deck) {
        JPanel addCardPanel = makeAddCardPanel(deck, parentPanel);
        interactionPanel.add(addCardPanel);
        interactionLayout.addLayoutComponent("AddCard" + deck.hashCode(), addCardPanel);
        JPanel renamePanel = makeRenamePanel(deck, parentPanel);
        interactionPanel.add(renamePanel);
        interactionLayout.addLayoutComponent("RenameDeck" + deck.hashCode(), renamePanel);
    }

    // EFFECTS: returns the actionListener to randomly fill a user's deck
    private ActionListener makeFillRandomAction(Deck deck, JPanel parentPanel) {
        ActionListener fillRandomAction = e -> {
            deck.fillRandom(User.getInstance());
            cardPanel.removeAll();
            decorateCardPanel(parentPanel, deck);
            JLabel cardCount = (JLabel) parentPanel.getComponent(1);
            cardCount.setText(deck.getCardsInDeck().size() + "/20 Cards");
        };
        return fillRandomAction;
    }

    // EFFECTS: returns an interactionPanel for user's to add a card from their owned cards to their deck
    private JPanel makeAddCardPanel(Deck deck, JPanel grandparentPanel) {
        JPanel addCardPanel = new JPanel();
        addCardPanel.setLayout(new GridLayout());
        addCardPanel.setBackground(Color.decode("#5a6988"));

        ActionListener backAction = e -> {
            interactionLayout.show(interactionPanel, "EditDeck" + deck.hashCode());
            decorateCardPanel(grandparentPanel, deck);
        };
        addCardPanel.add(createButton("BACK", "#5a6988", 0, 0, 0, 0, backAction, 30));

        addableCardPanel = new JPanel();
        addableCardPanel.setLayout(new GridLayout());
        addableCardPanel.setBackground(Color.decode("#5a6988"));
        JScrollPane addableCardScrollPane = new JScrollPane(addableCardPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        decorateAddCardPanel(grandparentPanel, deck);
        addCardPanel.add(addableCardScrollPane);
        return addCardPanel;
    }

    // MODIFIES: addableCardPanel
    // EFFECTS: updates a user's owned cards
    private void decorateAddCardPanel(JPanel deckPanel, Deck deck) {
        addableCardPanel.removeAll();

        List<Card> canAddCards = deck.getCanAddCards(User.getInstance());

        if (canAddCards.size() != 0) {
            for (Card card : canAddCards) {
                CardGUI cardGUI = new CardGUI(card);
                ActionListener addAction = makeAddCardAction(addableCardPanel, deckPanel, deck, card, cardGUI);
                JButton addButton = createButton("ADD", "#63c74d", 200, 60, 128, 230, addAction, 20);
                cardGUI.add(addButton);
                addableCardPanel.add(cardGUI);
            }
        }
        addableCardPanel.setPreferredSize(new Dimension(addableCardPanel.getComponentCount() * 256, 300));
        addableCardPanel.revalidate();
        addableCardPanel.repaint();
    }

    // EFFECTS: returns actionListener for adding a card to a deck
    private ActionListener makeAddCardAction(JPanel addCardPanel, JPanel deckPanel, Deck deck, Card card,
                                             CardGUI cardGUI) {
        ActionListener addCardAction = e -> {
            deck.addCard(card);
            addCardPanel.remove(cardGUI);
            cardGUI.remove(cardGUI.getComponentCount() - 1);
            cardPanel.add(cardGUI);
            JLabel cardCount = (JLabel)deckPanel.getComponent(1);
            cardCount.setText(deck.getCardsInDeck().size() + "/20 Cards");
            decorateAddCardPanel(deckPanel, deck);
            decorateCardPanel(deckPanel, deck);
        };
        return addCardAction;
    }

    // EFFECTS: returns an interactionPanel where a user can rename their deck
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
        renameTextField.setFont(new Font(FONT, Font.BOLD, 40));
        innerRenamePanel.add(renameTextField);

        ActionListener renameAction = makeRenameAction(renameTextField, deck, grandparentPanel, innerRenamePanel);
        JButton confirmButton = createButton("CONFIRM", "#8b9bb4", 160, 40, 320, 250, renameAction, 20);
        innerRenamePanel.add(confirmButton);

        renamePanel.add(innerRenamePanel);
        return renamePanel;
    }

    // EFFECTS: returns an actionListener for a user to rename their deck
    private ActionListener makeRenameAction(JTextField textField, Deck deck, JPanel grandparentPanel,
                                            JPanel parentPanel) {
        ActionListener renameAction = e -> {
            String oldName = deck.getName();
            deck.setName(textField.getText());
            interactionLayout.show(interactionPanel, "EditDeck" + deck.hashCode());
            JLabel deckPanelLabel = (JLabel) grandparentPanel.getComponent(0);
            deckPanelLabel.setText(deck.getName());
            JLabel renamePanelLabel = (JLabel) parentPanel.getComponent(0);
            renamePanelLabel.setText("RENAME \"" + deck.getName() + "\" TO:");
            deckName.setText(deck.getName());
        };
        return renameAction;
    }
}
