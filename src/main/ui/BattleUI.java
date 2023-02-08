package ui;

import model.*;

import java.util.Scanner;

import static model.Card.CardType.*;
import static ui.Main.enterShop;
import static ui.Main.startBattle;
import static ui.ShopUI.*;

public class BattleUI extends UIMethods {

    protected static User user;
    protected static UserPlayer userPlayer;
    protected static EnemyPlayer enemyPlayer;
    private static int turnEnergy;

    public static final int VICTORY_COIN_AMOUNT = 4;

    public static void createBattle(User givenUser) {
        user = givenUser;

        beforeBattle();
    }

    public static void beforeBattle() {
        System.out.println("You decide it would only be smart to prepare for a battle, pulling out your favourite "
                + "deck of cards. \nSelect a deck to battle with:");
        Scanner s = new Scanner(System.in);
        printDecks();
        System.out.println("[" + (user.getDecks().size() + 1) + "] Sprint back to shop to edit your decks");
        int index = s.nextInt() - 1;
        if (index < user.getDecks().size()) {
            selectDeckForBattle(index);
        } else if (index == user.getDecks().size()) {
            editDecks();
        } else {
            beforeBattle();
        }
    }

    public static void selectDeckForBattle(int index) {
        user.setSelectedDeck(user.getDecks().get(index));
        System.out.println("You selected " + user.getSelectedDeck().getName() + ".");
        pause();
        preBattleText();
    }

    public static void preBattleText() {
        userPlayer = new UserPlayer(user.getSelectedDeck());
        enemyPlayer = new EnemyPlayer();

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
        battleStart();
    }

    public static void battleStart() {
        turnEnergy = 0;
        while (userPlayer.getHealth() > 0 && enemyPlayer.getHealth() > 0) {
            newTurn();
            playerTurn();
            enemyTurn();
        }
        if (userPlayer.getHealth() <= 0) {
            defeatSequence();
        } else {
            victorySequence();
        }
        enterShop(user);
    }

    public static void newTurn() {
        turnEnergy++;
        userPlayer.drawCard();
        enemyPlayer.drawCard();
        userPlayer.setEnergy(turnEnergy);
        enemyPlayer.setEnergy(turnEnergy);
        System.out.println("\nA new round begins. \nYou drew a card.");
    }

    public static void playerTurn() {
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
                    playerTurn();
            }
        }
    }

    public static void pickCard() {
        System.out.println("\nYou have " + userPlayer.getEnergy() + " energy.");
        Scanner s = new Scanner(System.in);
        int handSize = userPlayer.getHand().size();
        printHandCards(userPlayer);
        System.out.println("Pick a card to play, or " + (handSize + 1) + " to go back.");
        int selection = s.nextInt() - 1;
        playCard(selection);
    }

    public static void playCard(int selection) {
        if (selection < userPlayer.getHand().size()) {
            if (userPlayer.getEnergy() >= userPlayer.getHand().get(selection).getEnergyCost()) {
                if (userPlayer.getHand().get(selection).getType() == ATTACK) {
                    userPlayer.playCard(selection, enemyPlayer);
                } else {
                    userPlayer.playCard(selection, userPlayer);
                }
                printTurnStats();
            } else {
                System.out.println("You don't have enough energy to play that card!");
                playerTurn();
            }
        }
        playerTurn();
    }

    public static void printTurnStats() {
        System.out.println("The " + enemyPlayer.getName() + " has " + enemyPlayer.getHealth() + " health, and "
                + enemyPlayer.getShield() + " shield.");
        System.out.println("You have " + userPlayer.getHealth() + " health, and "
                + userPlayer.getShield() + " shield.");
    }

    public static void printHandCards(Player player) {
        int i = 1;
        for (Card c : player.getHand()) {
            System.out.println("[" + i + "] " + c.getName() + ": \n" + c.getType().toString() + " type, "
                    + c.getValue() + " strength, " + c.getEnergyCost() + " energy cost");
            i++;
        }
    }

    public static void enemyTurn() {
        // TODO create "ai" for enemies
    }

    public static void defeatSequence() {
        System.out.println("\"HAHAHAHAHAHHAHAHAHAHAHHAHAHAHAHHAHAHHAAHAHAHAHHAHAHHAHAHAHHAHAHAHHAHAHAHAHAHAHAH\"");
        System.out.println("The " + enemyPlayer.getName() + " seems very proud of its victory. You slumber away, "
                + "hoping that your next battle will go better.");
    }

    public static void victorySequence() {
        user.setCoins(user.getCoins() + VICTORY_COIN_AMOUNT);
        System.out.println("\"nonononono this cant be happening\" The " + enemyPlayer.getName() + " seems to be "
                + "in extreme distress over its loss. ");
        System.out.println("It wanders away, muttering about how 'The game is just terribly balanced', and "
                + "how it 'would've won if the devs weren't so terrible at game design'");
        System.out.println("Luckily, it dropped a few coins and didn't notice, so you'll gladly take them.");
        System.out.println("You now have " + user.getCoins() + " coins!");
    }

    public static void printDecks() {
        int i = 1;
        for (Deck d : user.getDecks()) {
            System.out.println("[" + i + "] " + d.getName() + ": " + d.getCardsInDeck().size() + "/20");
            i++;
        }
    }
}
