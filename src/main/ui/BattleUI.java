package ui;

import model.*;

import java.util.Scanner;

import static model.Card.CardType.*;

// UI class for battles. After being instantiated, it will loop the user turn, enemy turn, and new turn methods. If
// either the enemy or user's health goes below 0, the loop is broken out of, and it the class will play either the
// victory or defeat sequence.
public class BattleUI extends UIMethods {

    private UserPlayer userPlayer;
    private EnemyPlayer enemyPlayer;
    private int turnEnergy;

    public static final int VICTORY_COIN_AMOUNT = 4;

    // EFFECTS: Constructs battle UI.
    public BattleUI(User givenUser) {
        super.user = givenUser;

        System.out.println("\nA few hours pass, and nothing of interest comes before you. However, night is rapidly "
                + "approaching, and a sense of dread creeps down your spine...");
        System.out.println("You decide it would only be smart to prepare for a battle, pulling out your favourite "
                + "deck of cards.");
        initUI();
    }

    // EFFECTS: Initializes battle UI.
    public void initUI() {
        beforeBattle();
    }

    // EFFECTS: Choose a deck to select, construct deck editing UI, or loop if user's input does not correlate with an
    //          option.
    public void beforeBattle() {
        System.out.println("Select a deck to battle with:");
        Scanner s = new Scanner(System.in);
        printDecks(user.getDecks());
        System.out.println("[" + (user.getDecks().size() + 1) + "] Edit your decks");
        int index = s.nextInt() - 1;
        if (index < user.getDecks().size()) {
            selectDeckForBattle(index);
        } else if (index == user.getDecks().size()) {
            new EditDeckUI(user, this);
        } else {
            beforeBattle();
        }
    }

    // MODIFIES: user
    // EFFECTS: If deck has viable amount of cards, set deck as user's selected deck and start battle. Otherwise,
    //          return to deck selection UI.
    public void selectDeckForBattle(int index) {
        if (user.getSelectedDeck().checkViable()) {
            user.setSelectedDeck(user.getDecks().get(index));
            System.out.println("You selected " + user.getSelectedDeck().getName() + ".");
            pause();
            preBattleSetup();
        } else {
            System.out.println("This deck does not have the correct number of cards.");
            beforeBattle();
        }
    }
    
    //  EFFECTS: Constructs a user player and enemy player, and sets energy to 0. Executes battle loop.
    public void preBattleSetup() {
        userPlayer = new UserPlayer(user.getSelectedDeck());
        enemyPlayer = new EnemyPlayer();
        turnEnergy = 0;

        System.out.println("\nSuddenly, out of the darkness, a " + enemyPlayer.getName() + " leaps at you!");
        System.out.println("\"Prepare to be robbed and eaten!!!\" It screeches at you. In desperation, you pull out "
                + "your cards, praying they could defend you from the horrendous beast in front of you.");
        System.out.println("\"HAHA! You think those cards will save yo-\" It pauses.");
        System.out.println("\"Wait, is that the hit trading card battling game, Card Wizardry??? I love that game!\""
                + "\n\"If you wanted to play, you could've just asked...\", the creature mutters. You feel a bit "
                + "gaslit.");
        System.out.println("From its pocket(?), the " + enemyPlayer.getName() + " pulls out its own deck of Card "
                + "Wizardry cards, and without another word, the battle begins.");
        pause();
        battleLoop();
    }

    // EFFECTS: Execute new turn, user's turn, and enemy turn each loop. If the enemy's health is 0 after player's
    //          turn, break out of the loop. Stop the loop when either the user's health or the enemy's health drops
    //          below 0, and play the appropriate sequence. Then, create a shop UI.
    public void battleLoop() {
        while (userPlayer.getHealth() > 0) {
            newTurn();
            userTurn();
            if (enemyPlayer.getHealth() <= 0) {
                break;
            }
            enemyTurn();
        }
        if (userPlayer.getHealth() <= 0) {
            defeatSequence();
        } else {
            victorySequence();
        }
        pause();
        new ShopUI(user);
    }

    // MODIFIES: userPlayer, enemyPlayer, this
    // EFFECTS: Increase turn energy by 1, draw a card into both the user and enemy's hands, and set user and enemy's
    //          energy as the turn energy.
    public void newTurn() {
        turnEnergy++;
        userPlayer.drawCard();
        enemyPlayer.drawCard();
        userPlayer.setEnergy(turnEnergy);
        enemyPlayer.setEnergy(turnEnergy);
        System.out.println("\nA new round begins. \nYou drew a card.");
    }

    // MODIFIES: userPlayer
    // EFFECTS: If user's energy is 0, end its turn. Otherwise, user selects action. If user input out of selection
    //          range, execute this method again. Note: If the user draws a card, its turn is ended.
    public void userTurn() {
        if (userPlayer.getEnergy() == 0) {
            System.out.println("\nYou are out of energy.");
            pause();
        } else {
            System.out.println(enemyPlayer.produceEnemyIdleDescription(userPlayer));
            System.out.println("\nSelect an action:");
            Scanner s = new Scanner(System.in);
            printSelections("Play a Card", "Draw a Card (ends your turn)");
            int selection = s.nextInt();

            switch (selection) {
                case 1:
                    pickCard();
                    break;
                case 2:
                    userPlayer.drawCard();
                    System.out.println("You drew a card.");
                    break;
                default:
                    userTurn();
            }
        }
    }

    // EFFECTS: Print user's hand, and take user input.
    public void pickCard() {
        Scanner s = new Scanner(System.in);
        int handSize = userPlayer.getHand().size();
        printCards(userPlayer.getHand());
        System.out.println("\nYou have " + userPlayer.getEnergy() + " energy.");
        System.out.println("Pick a card to play, or " + (handSize + 1) + " to go back.");
        int selection = s.nextInt() - 1;
        playCard(selection);
    }

    // MODIFIES: userPlayer, enemyPlayer
    // EFFECTS: If user input is out of length of user's hand, return to player turn main menu. Otherwise, check if
    //          user has energy to play card. If user has enough energy, play the card, otherwise, return to pick card
    //          menu. If the enemy is dead after the user plays its card, user's turn ends.
    public void playCard(int selection) {
        if (selection < userPlayer.getHand().size()) {
            Card card = userPlayer.getHand().get(selection);

            if (userPlayer.getEnergy() >= card.getEnergyCost()) {
                if (card.getType() == ATTACK) {
                    userPlayer.playCard(card, enemyPlayer);
                } else if (card.getType() == HEAL || card.getType() == SHIELD) {
                    userPlayer.playCard(card, userPlayer);
                }
                System.out.println("You played your " + userPlayer.getLastCardPlayed().getName() + "!");
                printTurnStats();
            } else {
                System.out.println("You don't have enough energy to play that card!");
                pickCard();
            }
        }
        if (enemyPlayer.getHealth() > 0) {
            userTurn();
        }
    }

    // EFFECTS: Print player and enemy's health and shield.
    public void printTurnStats() {
        System.out.println("The " + enemyPlayer.getName() + " has " + enemyPlayer.getHealth() + " health, and "
                + enemyPlayer.getShield() + " shield.");
        System.out.println("You have " + userPlayer.getHealth() + " health, and "
                + userPlayer.getShield() + " shield.");
        pause();
    }

    // MODIFIES: enemyPlayer
    // EFFECTS: If enemy's energy is 0, end its turn. Otherwise, execute enemy decision-making, and print the
    //          corresponding message depending on the enemy's decision. If the enemy draws a card, end its turn. If
    //          the user is dead after the enemy plays its card, the enemy's turn ends.
    public void enemyTurn() {
        if (enemyPlayer.getEnergy() != 0) {
            enemyPlayer.enemyDecisionMaking(userPlayer);

            if (enemyPlayer.getIfDrewCard()) {
                System.out.println("The " + enemyPlayer.getName() + " drew a card.");
            } else {
                System.out.println("The " + enemyPlayer.getName() + " played "
                        + enemyPlayer.getLastCardPlayed().getName() + "!");
                printTurnStats();
                if (userPlayer.getHealth() > 0) {
                    enemyTurn();
                }
            }
        }
    }

    // EFFECTS: Print defeat sequence text.
    public void defeatSequence() {
        System.out.println("\"Haha! You're terrible at this!\"");
        System.out.println("The " + enemyPlayer.getName() + " seems very proud of its victory. You slumber away, "
                + "hoping that your next battle will go better.");
    }

    // EFFECTS: Print victory sequence text.
    public void victorySequence() {
        user.setCoins(user.getCoins() + VICTORY_COIN_AMOUNT);
        System.out.println("\"This game is stupid anyways.\" The " + enemyPlayer.getName() + " seems to be "
                + "in denial over its loss. ");
        System.out.println("It wanders away, muttering about how 'The game is just terribly balanced', and "
                + "how it 'would've won if the devs weren't so terrible at game design'");
        System.out.println("You noticed that it dropped a few coins while walking away. You gladly take them.");
        System.out.println("You now have " + user.getCoins() + " coins!");
    }
}
