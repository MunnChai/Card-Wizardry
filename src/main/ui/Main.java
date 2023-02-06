package ui;

import model.Card;
import model.Shop;
import model.User;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Card Battler!" + "\nWould you like a tutorial? (input integer)");
        Scanner selection = new Scanner(System.in);
        printSelections("Yeah, a tutorial would be great!", "Nah, I've got this");
        tutorialOption(selection);
    }

    public static void tutorialOption(Scanner s) {
        int index = s.nextInt();
        switch (index) {
            case 1:
                tutorialSequence();
                break;
            case 2:
                startGame();
                break;
            default:
                System.out.println("Sorry, that's not an option!");
                Scanner selection = new Scanner(System.in);
                tutorialOption(selection);
        }
    }

    public static void tutorialSequence() {
        System.out.println("Welcome to the Card Wizardry Tutorial!");
        System.out.println("In this tutorial, you'll be taught how to battle, build decks, and buy cards.");
        System.out.println("Here's a dummy for you to practice your spells on.");
        tutorialBattle();
        // TODO
    }

    public static void tutorialBattle() {
        System.out.println("*The dummy stares blankly at you*");
        System.out.println("This is your enemy. Try playing a spell to attack it!");
        Scanner selection = new Scanner(System.in);
        printSelections("Play a Card");
        // TODO
    }

    public static void tutorialSelectAction() {
        // TODO
    }

    // TODO
    public static void startGame() {
        User user = new User();
        enterShop(user);
    }

    public static void enterShop(User user) {
        Shop shop = new Shop(user);
        System.out.println("\nAfter wandering on and off some raggedy paths for a few hours, "
                + "you stumble upon a small merchant's hut.");
        System.out.println("\"Welcome!\"");
        shopOptions(shop);
    }

    public static void shopOptions(Shop shop) {
        System.out.println("\"What can I get for ya?\"");
        Scanner s = new Scanner(System.in);
        printSelections("\"I'd like to buy a card.\"", "\"I'd like to sell a card.\"",
                "\"I'd like to edit my deck\"", "\"I'd better get going.\"");
        int index = s.nextInt();
        switch (index) {
            case 1:
                buyCard(shop);
                break;
            case 2:
                sellCard(shop);
                break;
            case 3:
                editDeck(shop);
                break;
            case 4:
                System.out.println("\"Come again!!\"\nYou leave the hut and continue your journey once more.");
                startBattle();
                break;
            default:
                System.out.println("Sorry, that's not an option!");
                shopOptions(shop);
        }
    }

    public static void buyCard(Shop shop) {
        if (shop.getCardsForSale().size() == 0) {
            System.out.println("\"I've got nothing more.\"");
            shopOptions(shop);
        } else {
            System.out.println("\n\"Take a look at my collection.\"" + "\nThe merchant shows you some cards.\n");
            printShopSelection(shop);

            pickPurchasedCard(shop);
            shopOptions(shop);
        }
    }

    public static void pickPurchasedCard(Shop shop) {
        Scanner purchase = new Scanner(System.in);
        System.out.println("Pick a card, or " + (shop.getCardsForSale().size() + 1) + " to go back.");
        int purchaseIndex = purchase.nextInt() - 1;

        if (purchaseIndex >= shop.getCardsForSale().size()) {
            System.out.println("");
        } else if (shop.getUser().getCoins() >= shop.getCardsForSale().get(purchaseIndex).getCoinCost()) {
            shop.buyCard(purchaseIndex);
            System.out.println("\"Thank you for your purchase!\" The merchant giggles.");
        } else if (shop.getUser().getCoins() < shop.getCardsForSale().get(purchaseIndex).getCoinCost()) {
            Scanner pause = new Scanner(System.in);
            System.out.println("You cannot afford that.\n");
        }
    }

    public static void printShopSelection(Shop shop) {
        int i = 1;
        for (Card c : shop.getCardsForSale()) {
            System.out.println("[" + i + "] " + c.getName() + " - " + c.getCoinCost() + " coins");
            System.out.println("Type: " + c.getType().toString() + "\nStrength: " + c.getValue()
                    + "\nEnergy Cost: " + c.getEnergyCost() + "\n");
            i++;
        }
    }

    public static void sellCard(Shop shop) {
        System.out.println("\nWhat card would you like to sell? \n* You can only sell cards that are not in any"
                + " current decks.\n");
        printUserCards(shop.getUser());

        Scanner sell = new Scanner(System.in);
        System.out.println("Pick a card, or " + (shop.getUser().getCanSellCards().size() + 1) + " to go back.");
        int sellIndex = sell.nextInt() - 1;

        if (sellIndex >= shop.getUser().getCanSellCards().size()) {
            System.out.println("");
        } else {
            Card card = shop.getUser().getCanSellCards().get(sellIndex);
            confirmSell(card, shop);
        }
        shopOptions(shop);
    }

    public static void confirmSell(Card card, Shop shop) {
        Scanner confirm = new Scanner(System.in);
        System.out.println("Are you sure you want to sell your " + card.getName() + " for " + card.getCoinCost()
                + " coins? (Stats: " + card.getType().toString() + " type, " + card.getValue() + " strength, "
                + card.getEnergyCost() + " energy cost)");
        printSelections("Yes", "No");
        int confirmInt = confirm.nextInt();

        if (confirmInt == 1) {
            shop.sellCard(card);
            System.out.println("You now have: " + shop.getUser().getCoins() + " coins.");
        } else {
            System.out.println("");
        }
    }

    public static void printUserCards(User user) {
        int i = 1;
        for (Card c : user.getCanSellCards()) {
            System.out.println("[" + i + "] " + c.getName() + " - " + c.getCoinCost() + " coins");
            System.out.println("Stats: " + c.getType().toString() + " type, " + c.getValue()
                    + " strength, " + c.getEnergyCost() + " energy cost\n");
            i++;
        }
    }

    public static void editDeck(Shop shop) {

    }

    public static void startBattle() {

    }

    public static void printSelections(String... option) {
        int i = 1;
        for (String s : option) {
            System.out.println("[" + i + "] " + s);
            i++;
        }
    }
}
