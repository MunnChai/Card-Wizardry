package gui;

import model.Card;
import model.Shop;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ShopGUI extends Panel {
    private JPanel interactionPanel;
    private CardLayout interactionLayout;

    private JButton backToTitleButton;

    private JPanel selectionPanel;
    private JPanel buyCardPanel;
    private JPanel sellCardPanel;
    private JLabel coinCount;

    private JPanel sellCardScrollPanel;

    private Shop shop;

    public ShopGUI(Component parent) {
        super(parent,"#ead4aa");

        user = User.getInstance();
        shop = new Shop(user);

        coinCount = createText("You have " + user.getCoins() + " coins.", "#733e39", 400, 100,
                180, 220, 30);

        this.add(createText("Shop", "#733e39", 200, 100, 170, 140, 60));
        this.add(coinCount);

        ActionListener backToTitle = switchPanelAction("TitleScreenGUI", parent);

        backToTitleButton = createButton("BACK TO TITLE", "#b86f50", 280, 60, 170, 50,
                backToTitle, 30);

        this.add(backToTitleButton);

        interactionPanel = createInteractionPanel("#e4a672", "#733e39");
        this.add(interactionPanel);
        interactionLayout = new CardLayout();
        interactionPanel.setLayout(interactionLayout);
        interactionPanel.add(makeSelectionPanel());
        interactionPanel.add(makeBuyCardPanel());
        interactionPanel.add(makeSellCardPanel());
    }

    private JPanel makeSelectionPanel() {
        selectionPanel = new JPanel();
        selectionPanel.setBackground(Color.decode("#e4a672"));
        selectionPanel.setLayout(new GridLayout());

        ActionListener buyCardAction = e -> {
            interactionLayout.show(interactionPanel, "BuyCardPanel");
            backToTitleButton.setVisible(false);
        };
        ActionListener sellCardAction = e -> {
            interactionLayout.show(interactionPanel, "SellCardPanel");
            backToTitleButton.setVisible(false);
            updateSellableCards();
        };
        ActionListener editDecksAction = switchPanelAction("EditDecksGUI", parent);
        ActionListener battleAction = switchPanelAction("BattleScreenGUI", parent);

        selectionPanel.add(createButton("BUY A CARD", "#b86f50", 280, 200, 0, 0, buyCardAction, 30));
        selectionPanel.add(createButton("SELL A CARD", "#b86f50", 280, 200, 0, 0, sellCardAction, 30));
        selectionPanel.add(createButton("EDIT DECKS", "#b86f50", 280, 200, 0, 0, editDecksAction, 30));
        selectionPanel.add(createButton("OFF TO BATTLE!", "#b86f50", 280, 200, 0, 0, battleAction, 30));
        interactionLayout.addLayoutComponent("SelectionPanel", selectionPanel);
        return selectionPanel;
    }

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
        interactionLayout.addLayoutComponent("BuyCardPanel", buyCardPanel);

        for (Card card : shop.getCardsForSale()) {
            CardGUI cardGUI = new CardGUI(card);
            JButton buyButton = makeBuyButton(cardGUI);
            cardGUI.addButton(buyButton);
            buyCardPanel.add(cardGUI);
        }

        return buyCardPanel;
    }

    private JButton makeBuyButton(CardGUI cardGUI) {
        Card card = cardGUI.getCard();
        ActionListener action = e -> {
            if (user.getCoins() >= card.getCoinCost()) {
                int index = shop.getCardsForSale().indexOf(card);
                shop.buyCard(index);

                coinCount.setText("You have " + user.getCoins() + " coins.");

                int guiIndex = buyCardPanel.getComponentZOrder(cardGUI);
                buyCardPanel.remove(guiIndex);
                buyCardPanel.add(createBlankSpot("#733e39"), guiIndex);
            }
        };
        JButton button = createButton("BUY: $" + card.getCoinCost(), "#feae34",200, 60,
                28, 200, action, 20);
        return button;
    }

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
        JScrollPane scrollPane = new JScrollPane(sellCardScrollPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(1024,300));
        scrollPane.setLocation(768, 150);
        sellCardPanel.add(scrollPane);

        interactionLayout.addLayoutComponent("SellCardPanel", sellCardPanel);
        return sellCardPanel;
    }

    private void updateSellableCards() {
        sellCardScrollPanel.removeAll();
        int scrollPanelWidth = 0;
        for (Card card : user.getCanSellCards()) {
            CardGUI cardGUI = new CardGUI(card);
            JButton sellButton = makeSellButton(cardGUI);
            cardGUI.add(sellButton);
            sellCardScrollPanel.add(cardGUI);
            scrollPanelWidth += 256;
        }
        sellCardScrollPanel.setPreferredSize(new Dimension(scrollPanelWidth,300));
    }

    private JButton makeSellButton(CardGUI cardGUI) {
        Card card = cardGUI.getCard();
        ActionListener action = e -> {
            shop.sellCard(card);
            coinCount.setText("You have " + user.getCoins() + " coins.");
            int guiIndex = sellCardScrollPanel.getComponentZOrder(cardGUI);
            sellCardScrollPanel.remove(guiIndex);
            sellCardScrollPanel.add(createBlankSpot("#733e39"), guiIndex);
            updateSellableCards();
        };
        JButton button = createButton("SELL: $" + card.getCoinCost(), "#feae34",200, 60,
                128, 220, action, 20);
        return button;
    }

    public static JPanel createBlankSpot(String hexColour) {
        JPanel panel = new JPanel();
        panel.setSize(256, 300);
        panel.setBackground(Color.decode(hexColour));
        return panel;
    }
}
