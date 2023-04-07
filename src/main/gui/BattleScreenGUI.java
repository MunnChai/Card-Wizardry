package gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static gui.CardWizardryApp.WINDOW_WIDTH;
import static model.Card.CardType.*;
import static ui.BattleUI.VICTORY_COIN_AMOUNT;

// Represents the screen for battle sequences
public class BattleScreenGUI extends Panel {
    private JPanel interactionPanel;
    private CardLayout interactionLayout;

    private JPanel dialoguePanel;
    private JTextArea dialogue;
    private JButton dialogueContinueButton;

    private JPanel userActionPanel;
    private JPanel playCardPanel;

    private JPanel playableCardPanel;

    private UserPlayer userPlayer;
    private EnemyPlayer enemyPlayer;

    private JTextArea energyCount;

    private JPanel userStatPanel;
    private JTextArea userStats;
    private JPanel enemyStatPanel;
    private JTextArea enemyStats;

    private int currentTurnEnergy;

    // Constructor for BattleScreenGUI
    public BattleScreenGUI(JPanel parent) {
        super(parent, "#265c42", "BattleScreenGUI");

        newBattle();
        initializeInteractionPanel();
        initializeDialoguePanel();
        initializeUserActionPanel();
        initializePlayCardPanel();
        initializeUserHealthPanel();
        initializeEnemyHealthPanel();

        updateUserStats();
        updateEnemyStats();

        saveButton.setVisible(false);
    }

    public void newBattle() {
        userPlayer = new UserPlayer(User.getInstance().getSelectedDeck());
        enemyPlayer = new EnemyPlayer();
        currentTurnEnergy = 1;
        userPlayer.setEnergy(currentTurnEnergy);
        enemyPlayer.setEnergy(currentTurnEnergy);
    }

    private void initializeInteractionPanel() {
        interactionPanel = createInteractionPanel("#733e39", "#3a4466");
        interactionLayout = new CardLayout();
        interactionPanel.setLayout(interactionLayout);
        this.add(interactionPanel);
    }

    private void initializeDialoguePanel() {
        dialoguePanel = new JPanel();
        dialoguePanel.setLayout(null);
        dialoguePanel.setBackground(Color.decode("#733e39"));

        dialogue = new JTextArea();
        dialogue.setEditable(false);
        dialogue.setBackground(Color.decode("#733e39"));
        dialogue.setForeground(Color.decode("#ffffff"));
        dialogue.setBounds(50, 50, 800, 300);
        dialogue.setFont(new Font(FONT, Font.BOLD, 30));
        dialogue.setLineWrap(true);
        dialoguePanel.add(dialogue);

        ActionListener continueAction = e -> {
            if (enemyPlayer.getHealth() <= 0) {
                // Show death thing
            } else if (userPlayer.getHealth() <= 0) {
                // Show death thing
            } else {
                interactionLayout.show(interactionPanel, "UserActionPanel");
            }
        };

        dialogueContinueButton = createButton("CONTINUE", "#b86f50", 280, 80, WINDOW_WIDTH - 200,
                100, continueAction, 40);
        dialoguePanel.add(dialogueContinueButton);

        interactionPanel.add(dialoguePanel);
        interactionLayout.addLayoutComponent("DialoguePanel", dialoguePanel);
    }

    private void initializeUserActionPanel() {
        userActionPanel = new JPanel();
        userActionPanel.setLayout(new GridLayout());
        userActionPanel.setBackground(Color.decode("#733e39"));

        ActionListener playCardAction = e -> {
            interactionLayout.show(interactionPanel, "PlayCardPanel");
        };
        userActionPanel.add(createButton("PLAY A CARD", "#b86f50", 0, 0, 0,
                0, playCardAction, 40));

        ActionListener drawCardAction = e -> {
            userPlayer.drawCard();
            setDialogue("You ended your turn."
                    + "\nYou drew a card!");
            interactionLayout.show(interactionPanel, "DialoguePanel");
            setDialogueContinueAction(playEnemyTurn());
            updatePlayCardPanel();
            updateUserStats();
        };
        userActionPanel.add(createButton("DRAW A CARD \n (ends turn)", "#b86f50", 0, 0, 0,
                0, drawCardAction, 40));

        interactionPanel.add(userActionPanel);
        interactionLayout.addLayoutComponent("UserActionPanel", userActionPanel);
    }

    private void initializePlayCardPanel() {
        playCardPanel = new JPanel();
        playCardPanel.setLayout(new GridLayout());

        ActionListener backAction = e -> {
            interactionLayout.show(interactionPanel, "UserActionPanel");
        };
        playCardPanel.add(createButton("BACK", "#b86f50", 0, 0, 0, 0, backAction, 40));

        playableCardPanel = new JPanel();
        playableCardPanel.setLayout(new GridLayout());
        playableCardPanel.setBackground(Color.decode("#733e39"));
        JScrollPane playableCardScrollPane = new JScrollPane(playableCardPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        updatePlayCardPanel();
        playCardPanel.add(playableCardScrollPane);

        interactionPanel.add(playCardPanel);
        interactionLayout.addLayoutComponent("PlayCardPanel", playCardPanel);
    }

    private void initializeUserHealthPanel() {
        userStatPanel = new JPanel();
        userStatPanel.setBounds(30, 200, 300, 150);
        userStatPanel.setBackground(Color.decode("#733e39"));
        userStats = new JTextArea();
        userStats.setOpaque(false);
        userStats.setFont(new Font(FONT, Font.BOLD, 30));
        userStats.setForeground(Color.decode("#ffffff"));
        userStatPanel.add(userStats);
        updateUserStats();
        this.add(userStatPanel);
    }

    private void initializeEnemyHealthPanel() {
        enemyStatPanel = new JPanel();
        enemyStatPanel.setBounds(WINDOW_WIDTH - 330, 30, 300, 150);
        enemyStatPanel.setBackground(Color.decode("#733e39"));
        enemyStats = new JTextArea();
        enemyStats.setOpaque(false);
        enemyStats.setFont(new Font(FONT, Font.BOLD, 30));
        enemyStats.setForeground(Color.decode("#ffffff"));
        enemyStatPanel.add(enemyStats);
        updateEnemyStats();
        this.add(enemyStatPanel);
    }

    private ActionListener playEnemyTurn() {
        if (userPlayer.getHealth() <= 0) {
            return e -> {
                endBattle();
            };
        } else {
            return e -> {
                enemyPlayer.enemyDecisionMaking(userPlayer);
                if (enemyPlayer.getIfDrewCard()) {
                    enemyEndTurn();
                } else {
                    enemyTurnDialogue();
                    updateUserStats();
                    updateEnemyStats();
                    setDialogueContinueAction(playEnemyTurn());
                }
            };
        }
    }

    private void enemyTurnDialogue() {
        setDialogue("The " + enemyPlayer.getName() + " plays " + enemyPlayer.getLastCardPlayed().getName()
                + "!");
        Card.CardType cardType = enemyPlayer.getLastCardPlayed().getType();
        if (cardType == ATTACK) {
            setDialogue(dialogue.getText() + "\nYou have " + userPlayer.getHealth() + " health.");
        } else if (cardType == SHIELD) {
            setDialogue(dialogue.getText() + "\nIt has " + enemyPlayer.getShield() + " shield.");
        } else {
            setDialogue(dialogue.getText() + "\nIt has " + enemyPlayer.getHealth() + " health.");
        }
    }

    private void endBattle() {
        if (userPlayer.getHealth() <= 0) {
            setDialogue("You were defeated."
                    + "\nYou did not get any coins.");
        } else {
            User.getInstance().setCoins(User.getInstance().getCoins() + VICTORY_COIN_AMOUNT);
            setDialogue("The " + enemyPlayer.getName() + " dies."
                    + "\nYou got " + VICTORY_COIN_AMOUNT + " coins!");

        }
        setDialogueContinueAction(f -> {
            Shop.setInstance(new Shop(User.getInstance()));
            parentLayout.show(parent, "ShopGUI");
            ShopGUI shopGUI = (ShopGUI) parent.getComponent(1);
            shopGUI.updateShop();
        });
    }

    private void enemyEndTurn() {
        setDialogue("The " + enemyPlayer.getName() + " ends its turn."
                + "\n It draws a card.");
        setDialogueContinueAction(f -> {
            currentTurnEnergy++;
            userPlayer.setEnergy(currentTurnEnergy);
            enemyPlayer.setEnergy(currentTurnEnergy);
            updateUserStats();
            updateEnemyStats();
            updatePlayCardPanel();
            interactionLayout.show(interactionPanel, "UserActionPanel");
            setDialogueContinueAction(g -> {
                interactionLayout.show(interactionPanel, "UserActionPanel");
            });
        });
    }

    private ActionListener makePlayAction(Card card) {
        Card.CardType cardType = card.getType();

        ActionListener action;

        if (userPlayer.getEnergy() < card.getEnergyCost()) {
            action = e -> {
                // TODO alert player no energy
            };
        } else if (cardType == ATTACK) {
            action = makeAttackAction(card);
        } else if (cardType == SHIELD) {
            action = makeShieldAction(card);
        } else {
            action = makeHealAction(card);
        }

        return action;
    }

    private ActionListener makeAttackAction(Card card) {
        return e -> {
            userPlayer.playCard(card, enemyPlayer);
            updatePlayCardPanel();
            updateUserStats();
            updateEnemyStats();
            setDialogue("You played " + card.getName() + "!"
                    + "\nThe " + enemyPlayer.getName() + " took " + card.getValue() + " damage."
                    + "\nYou have " + userPlayer.getEnergy() + " energy.");
            interactionLayout.show(interactionPanel, "DialoguePanel");
            setDialogueContinueAction(f -> {
                if (enemyPlayer.getHealth() <= 0) {
                    endBattle();
                } else {
                    interactionLayout.show(interactionPanel, "UserActionPanel");
                }
            });
        };
    }

    private ActionListener makeShieldAction(Card card) {
        return e -> {
            userPlayer.playCard(card, userPlayer);
            updatePlayCardPanel();
            updateUserStats();
            updateEnemyStats();
            setDialogue("You played " + card.getName() + "!"
                    + "\nYou gained " + card.getValue() + " shield."
                    + "\nYou have " + userPlayer.getEnergy() + " energy.");
            interactionLayout.show(interactionPanel, "DialoguePanel");
        };
    }

    private ActionListener makeHealAction(Card card) {
        return e -> {
            userPlayer.playCard(card, userPlayer);
            updatePlayCardPanel();
            updateUserStats();
            updateEnemyStats();
            setDialogue("You played " + card.getName() + "!"
                    + "\nYou gained " + card.getValue() + " health."
                    + "\nYou have " + userPlayer.getEnergy() + " energy.");
            interactionLayout.show(interactionPanel, "DialoguePanel");
        };
    }

    private void setDialogueContinueAction(ActionListener action) {
        if (dialogueContinueButton.getActionListeners().length == 1) {
            dialogueContinueButton.removeActionListener(dialogueContinueButton.getActionListeners()[0]);
            dialogueContinueButton.addActionListener(action);
        } else {
            System.out.println("Something is going wrong, BattleScreenGUI.setDialogueContinueAction()");
        }
    }

    public void setDialogue(String text) {
        dialogue.setText(text);
    }

    public void updatePlayCardPanel() {
        playableCardPanel.removeAll();

        for (Card card : userPlayer.getHand()) {
            CardGUI cardGUI = new CardGUI(card);
            ActionListener playAction = makePlayAction(card);
            cardGUI.add(createButton("PLAY", "#feae34", 200, 60, 128, 220, playAction, 40));
            playableCardPanel.add(cardGUI);
        }

        playableCardPanel.setPreferredSize(new Dimension(playableCardPanel.getComponentCount() * 256, 300));
        playableCardPanel.revalidate();
        playableCardPanel.repaint();
    }

    public void updateUserStats() {
        userStats.setText("Health: " + userPlayer.getHealth()
                + "\nShield: " + userPlayer.getShield()
                + "\nEnergy: " + userPlayer.getEnergy());
        userStatPanel.revalidate();
        userStatPanel.repaint();
    }

    public void updateEnemyStats() {
        enemyStats.setText("Health: " + enemyPlayer.getHealth()
                + "\nShield: " + enemyPlayer.getShield()
                + "\nEnergy: " + enemyPlayer.getEnergy());
        enemyStatPanel.revalidate();
        enemyStatPanel.repaint();
    }
}
