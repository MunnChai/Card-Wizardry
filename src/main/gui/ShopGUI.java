package gui;

import model.Card;
import model.Shop;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

// Represents the shop screen of the app
public class ShopGUI extends Panel {
    private JPanel interactionPanel;
    private CardLayout interactionLayout;

    private JButton backToTitleButton;
    private JLabel coinCount;

    private JLabel catLabel;
    private ImageIcon catBrowRaiseIcon;
    private ImageIcon catHappyIcon;
    private JLabel speechBubble;
    private JLabel speechText;
    private Timer catTimer;
    private Timer speechTimer;

    private JPanel selectionPanel;

    private JPanel buyCardPanel;
    private JPanel buyCardScrollPanel;

    private JPanel sellCardPanel;
    private JPanel sellCardScrollPanel;

    // Constructor for ShopGUI
    // Creates an interactionPanel, back button, text for the user's coin count
    public ShopGUI(JPanel parent) {
        super(parent,"#ead4aa", "ShopGUI");

        coinCount = createText("You have " + User.getInstance().getCoins() + " coins.", "#733e39", 400,
                100, 180, 220, 30);

        this.add(createText("Shop", "#733e39", 200, 100, 170, 140, 60));
        this.add(coinCount);

        ActionListener backToTitle = switchPanelAction("TitleScreenGUI");

        backToTitleButton = createButton("BACK TO TITLE", "#b86f50", 280, 60, 170, 50,
                backToTitle, 30);

        this.add(backToTitleButton);
        this.add(makeCatPanel());

        interactionPanel = createInteractionPanel("#e4a672", "#733e39");
        this.add(interactionPanel);
        interactionLayout = new CardLayout();
        interactionPanel.setLayout(interactionLayout);
        interactionPanel.add(makeSelectionPanel());
        interactionPanel.add(makeBuyCardPanel());
        interactionPanel.add(makeSellCardPanel());
    }

    // MODIFIES: this
    // EFFECTS: updates the changing parts of the shop
    public void updateShop() {
        updateSellCardScrollPanel();
        updateBuyCardScrollPanel();
        coinCount.setText("You have " + User.getInstance().getCoins() + " coins.");
    }

    // EFFECTS: returns the "shop owner" as a visible JPanel
    private JPanel makeCatPanel() {
        JPanel wholePanel = new JPanel();
        wholePanel.setBounds(350, 35, 800, 350);
        wholePanel.setOpaque(false);
        wholePanel.setLayout(null);

        catTimer = new Timer(5000, null);
        speechTimer = new Timer(5000, null);

        catLabel = new JLabel();
        catLabel.setBounds(0, 150,200, 200);
        catHappyIcon = makeScaledImageIcon("./data/CatNormal.jpg", 200, 200);
        catBrowRaiseIcon = makeScaledImageIcon("./data/CatBrowRaise2.jpg", 200, 200);
        catLabel.setIcon(catBrowRaiseIcon);

        speechBubble = new JLabel();
        speechBubble.setBounds(200, 0, 500, 200);
        ImageIcon speechIcon = makeScaledImageIcon("./data/SpeechBubble.png", 500, 200);
        speechBubble.setIcon(speechIcon);

        speechText = createText("Welcome to my shop!", "#000000", 480, 200, 270, 90, 30);

        speechBubble.add(speechText);

        wholePanel.add(speechBubble);
        wholePanel.add(catLabel);
        return wholePanel;
    }

    // MODIFIES: catPanel
    // EFFECTS: changes cat icon to smiling cat for 5 seconds, then change back to brow raise cat
    public void makeCatHappy() {
        ActionListener setIcon = e -> {
            catLabel.setIcon(catBrowRaiseIcon);
        };
        catTimer.addActionListener(setIcon);

        catLabel.setIcon(catHappyIcon);

        catTimer.setRepeats(false);
        catTimer.restart();
    }

    // MODIFIES: catPanel
    // EFFECTS: makes cat "say" given text for 5 seconds
    public void makeCatSayText(String text) {
        ActionListener hideText = e -> {
            speechBubble.setVisible(false);
        };
        speechTimer.addActionListener(hideText);

        speechText.setText(text);
        speechBubble.setVisible(true);

        speechTimer.setRepeats(false);
        speechTimer.restart();
    }

    // EFFECTS: returns the main interactionPanel where the user can select to buy or sell a card, or edit their decks
    private JPanel makeSelectionPanel() {
        selectionPanel = new JPanel();
        selectionPanel.setBackground(Color.decode("#e4a672"));
        selectionPanel.setLayout(new GridLayout());

        ActionListener buyCardAction = e -> {
            makeCatSayText("What would you like to buy?");
            backToTitleButton.setVisible(false);
            interactionLayout.show(interactionPanel, "BuyCardPanel");
        };
        ActionListener sellCardAction = e -> {
            makeCatSayText("What would you like to sell?");
            backToTitleButton.setVisible(false);
            interactionLayout.show(interactionPanel, "SellCardPanel");
        };
        ActionListener editDecksAction = e -> {
            EditDecksGUI editDecksGUI = (EditDecksGUI) parent.getComponent(4);
            editDecksGUI.updateDecksScrollPane();
            parentLayout.show(parent, "EditDecksGUI");
        };
//        ActionListener battleAction = switchPanelAction("BattleScreenGUI");

        selectionPanel.add(createButton("BUY A CARD", "#b86f50", 280, 200, 0, 0, buyCardAction, 30));
        selectionPanel.add(createButton("SELL A CARD", "#b86f50", 280, 200, 0, 0, sellCardAction, 30));
        selectionPanel.add(createButton("EDIT DECKS", "#b86f50", 280, 200, 0, 0, editDecksAction, 30));
//        selectionPanel.add(createButton("OFF TO BATTLE!", "#b86f50", 280, 200, 0, 0, battleAction, 30));
        interactionLayout.addLayoutComponent("SelectionPanel", selectionPanel);
        return selectionPanel;
    }

    // EFFECTS: returns the interactionPanel where the user can buy a card
    private JPanel makeBuyCardPanel() {
        buyCardPanel = new JPanel();
        buyCardPanel.setBackground(Color.decode("#e4a672"));
        buyCardPanel.setLayout(new GridLayout());

        ActionListener backToSelection = e -> {
            interactionLayout.show(interactionPanel, "SelectionPanel");
            backToTitleButton.setVisible(true);
        };

        buyCardPanel.add(createButton("BACK", "#b86f50", 280, 200, 0, 0,
                backToSelection, 40));

        buyCardScrollPanel = new JPanel();
        buyCardScrollPanel.setLayout(new GridLayout());
        buyCardScrollPanel.setBackground(Color.decode("#e4a672"));
        JScrollPane scrollPane = new JScrollPane(buyCardScrollPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1024,300));
        scrollPane.setLocation(768, 150);
        buyCardPanel.add(scrollPane);
        updateBuyCardScrollPanel();
        interactionLayout.addLayoutComponent("BuyCardPanel", buyCardPanel);
        return buyCardPanel;
    }

    // MODIFIES: buyCardScrollPanel
    // EFFECTS: updates the cards for sale in the shop
    private void updateBuyCardScrollPanel() {
        buyCardScrollPanel.removeAll();

        int scrollPanelWidth = 0;
        List<Card> cardsForSale = Shop.getInstance().getCardsForSale();

        if (cardsForSale.size() != 0) {
            for (Card card : cardsForSale) {
                CardGUI cardGUI = new CardGUI(card);
                JButton buyButton = makeBuyButton(cardGUI);
                cardGUI.add(buyButton);
                buyCardScrollPanel.add(cardGUI);
                scrollPanelWidth += 256;
            }
        }
        buyCardScrollPanel.setPreferredSize(new Dimension(scrollPanelWidth,300));
        buyCardScrollPanel.revalidate();
        buyCardScrollPanel.repaint();
    }

    // EFFECTS: returns a button that allows a user to buy a card
    private JButton makeBuyButton(CardGUI cardGUI) {
        Card card = cardGUI.getCard();
        ActionListener action = e -> {
            if (User.getInstance().getCoins() >= card.getCoinCost()) {
                int index = Shop.getInstance().getCardsForSale().indexOf(card);
                Shop.getInstance().buyCard(index);

                coinCount.setText("You have " + User.getInstance().getCoins() + " coins.");

                int guiIndex = buyCardScrollPanel.getComponentZOrder(cardGUI);
                buyCardScrollPanel.remove(guiIndex);
                makeCatSayText("Thank you for buying!");
                makeCatHappy();
                updateShop();
            } else {
                makeCatSayText("You're broke.");
            }
        };
        JButton button = createButton("BUY: $" + card.getCoinCost(), "#feae34",200, 60,
                128, 220, action, 20);
        return button;
    }

    // EFFECTS: returns the interactionPanel where the user can sell a card
    private JPanel makeSellCardPanel() {
        sellCardPanel = new JPanel();
        sellCardPanel.setBackground(Color.decode("#e4a672"));
        sellCardPanel.setLayout(new GridLayout());

        ActionListener backToSelection = e -> {
            interactionLayout.show(interactionPanel, "SelectionPanel");
            backToTitleButton.setVisible(true);
        };
        sellCardPanel.add(createButton("BACK", "#b86f50", 256, 300, 128, 150,
                backToSelection, 40));

        sellCardScrollPanel = new JPanel();
        sellCardScrollPanel.setLayout(new GridLayout());
        sellCardScrollPanel.setBackground(Color.decode("#733e39"));
        JScrollPane scrollPane = new JScrollPane(sellCardScrollPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1024,300));
        scrollPane.setLocation(768, 150);
        sellCardPanel.add(scrollPane);

        interactionLayout.addLayoutComponent("SellCardPanel", sellCardPanel);
        return sellCardPanel;
    }

    // MODIFIES: sellCardScrollPanel
    // EFFECTS: updates the sellable cards in the shop
    private void updateSellCardScrollPanel() {
        sellCardScrollPanel.removeAll();
        int scrollPanelWidth = 0;

        List<Card> canSellCards = User.getInstance().getCanSellCards();

        if (canSellCards.size() != 0) {
            for (Card card : canSellCards) {
                CardGUI cardGUI = new CardGUI(card);
                JButton sellButton = makeSellButton(cardGUI);
                cardGUI.add(sellButton);
                sellCardScrollPanel.add(cardGUI);
                scrollPanelWidth += 256;
            }
        }

        sellCardScrollPanel.setPreferredSize(new Dimension(scrollPanelWidth,300));
        sellCardScrollPanel.revalidate();
        sellCardScrollPanel.repaint();
    }

    // EFFECTS: returns a button that sells a card
    private JButton makeSellButton(CardGUI cardGUI) {
        Card card = cardGUI.getCard();
        ActionListener action = e -> {
            Shop.getInstance().sellCard(card);
            coinCount.setText("You have " + User.getInstance().getCoins() + " coins.");
            int guiIndex = sellCardScrollPanel.getComponentZOrder(cardGUI);
            sellCardScrollPanel.remove(guiIndex);
            makeCatSayText("Thank you for your sale!");
            makeCatHappy();
            updateSellCardScrollPanel();
        };
        JButton button = createButton("SELL: $" + card.getCoinCost(), "#feae34",200, 60,
                128, 220, action, 20);
        return button;
    }

    // Setters
    public void setShop(Shop shop) {
        Shop.setInstance(shop);
    }
}
