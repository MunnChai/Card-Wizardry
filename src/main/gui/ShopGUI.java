package gui;

import model.Shop;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ShopGUI extends Panel {
    private JPanel interactionPanel;
    private CardLayout interactionLayout;

    private JPanel selectionPanel;
    private JPanel buyCardPanel;
    private JPanel sellCardPanel;
    private Shop shop;

    public ShopGUI(Component parent) {
        super(parent,"#ead4aa");

        addText("Shop", "#733e39", 200, 100, 170, 140, 60);

        ActionListener backToTitle = switchPanelAction("TitleScreenGUI", parent);
        this.add(createButton("BACK TO TITLE", "#b86f50", 280, 60, 170, 50,
                backToTitle, 30));

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

        ActionListener buyCardAction = switchPanelAction("BuyCardPanel", interactionPanel);
        ActionListener sellCardAction = switchPanelAction("SellCardPanel", interactionPanel);
        ActionListener editDecksAction = switchPanelAction("EditDecksGUI", parent);
        ActionListener battleAction = switchPanelAction("BattleScreenGUI", parent);

        selectionPanel.add(createButton("BUY A CARD", "#b86f50", 280, 200, 0, 0,
                buyCardAction, 40));
        selectionPanel.add(createButton("SELL A CARD", "#b86f50", 280, 200, 0, 0,
                sellCardAction, 40));
        selectionPanel.add(createButton("EDIT DECKS", "#b86f50", 280, 200, 0, 0,
                editDecksAction, 40));
        selectionPanel.add(createButton("OFF TO BATTLE!", "#b86f50", 280, 200, 0, 0,
                battleAction, 40));
        interactionLayout.addLayoutComponent("SelectionPanel", selectionPanel);
        return selectionPanel;
    }

    private JPanel makeBuyCardPanel() {
        buyCardPanel = new JPanel();
        buyCardPanel.setBackground(Color.decode("#e4a672"));
        buyCardPanel.setLayout(new GridLayout());

        ActionListener backToSelection = switchPanelAction("SelectionPanel", interactionPanel);

        buyCardPanel.add(createButton("BACK", "#b86f50", 280, 200, 0, 0,
                backToSelection, 40));
        interactionLayout.addLayoutComponent("BuyCardPanel", buyCardPanel);
        return buyCardPanel;
    }

    private JPanel makeSellCardPanel() {
        sellCardPanel = new JPanel();
        sellCardPanel.setBackground(Color.decode("#e4a672"));
        sellCardPanel.setLayout(new GridLayout());

        ActionListener backToSelection = switchPanelAction("SelectionPanel", interactionPanel);

        sellCardPanel.add(createButton("BACK", "#b86f50", 280, 200, 0, 0,
                backToSelection, 40));
        interactionLayout.addLayoutComponent("SellCardPanel", sellCardPanel);
        return sellCardPanel;
    }


}
