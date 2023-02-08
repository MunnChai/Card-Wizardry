package ui;

import model.Card;
import model.Deck;
import model.Shop;
import model.User;

import java.util.Scanner;

import static model.Deck.VIABLE_DECK_CARD_COUNT;
import static ui.Main.startBattle;

public class ShopUI extends UIMethods {

    protected static Shop shop;
    protected static User user;

    public static void openShop(User givenUser) {
        user = givenUser;
        shop = new Shop(user);

        shopOptions();
    }

    public static void shopOptions() {
        System.out.println("\"What can I get for ya?\" \nYou have " + user.getCoins() + " coins.");
        Scanner s = new Scanner(System.in);
        printSelections("\"I'd like to buy a card.\"", "\"I'd like to sell a card.\"",
                "\"I'd like to edit my deck\"", "\"I'd better get going.\"");
        int index = s.nextInt();
        switch (index) {
            case 1:
                buyCard();
                break;
            case 2:
                sellCard();
                break;
            case 3:
                editDecks();
                break;
            case 4:
                System.out.println("\"Come again!!\"\nYou leave the hut and continue your journey once more.");
                startBattle(user);
                break;
            default:
                System.out.println("Sorry, that's not an option!");
                shopOptions();
        }
    }

    public static void buyCard() {
        if (shop.getCardsForSale().size() == 0) {
            System.out.println("\"I've got nothing more.\"");
            shopOptions();
        } else {
            System.out.println("\n\"Take a look at my collection.\"" + "\nThe merchant shows you some cards.\n");
            printShopCards();

            pickPurchasedCard();
            shopOptions();
        }
    }

    public static void pickPurchasedCard() {
        int purchaseIndex = takeIntInput("Pick a card, or " + (shop.getCardsForSale().size() + 1)
                + " to go back.") - 1;

        if (purchaseIndex >= shop.getCardsForSale().size()) {
            System.out.println("");
        } else {
            checkMoney(purchaseIndex);
        }
    }

    public static void checkMoney(int purchaseIndex) {
        if (shop.getUser().getCoins() >= shop.getCardsForSale().get(purchaseIndex).getCoinCost()) {
            shop.buyCard(purchaseIndex);
            System.out.println("\"Thank you for your purchase!\" The merchant giggles.");
        } else if (shop.getUser().getCoins() < shop.getCardsForSale().get(purchaseIndex).getCoinCost()) {
            System.out.println("You cannot afford that.\n");
        }
    }

    public static void printShopCards() {
        int i = 1;
        for (Card c : shop.getCardsForSale()) {
            System.out.println("[" + i + "] " + c.getName() + " - " + c.getCoinCost() + " coins");
            System.out.println("Type: " + c.getType().toString() + "\nStrength: " + c.getValue()
                    + "\nEnergy Cost: " + c.getEnergyCost() + "\n");
            i++;
        }
    }

    public static void sellCard() {
        System.out.println("\nWhat card would you like to sell? \n* You can only sell cards that are not in any"
                + " current decks.\n");
        printUserCards();

        Scanner sell = new Scanner(System.in);
        System.out.println("Pick a card, or " + (shop.getUser().getCanSellCards().size() + 1) + " to go back.");
        int sellIndex = sell.nextInt() - 1;

        if (sellIndex >= shop.getUser().getCanSellCards().size()) {
            System.out.println("");
        } else {
            Card card = shop.getUser().getCanSellCards().get(sellIndex);
            confirmSell(card);
        }
        shopOptions();
    }

    public static void confirmSell(Card card) {
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

    public static void printUserCards() {
        int i = 1;
        for (Card c : user.getCanSellCards()) {
            System.out.println("[" + i + "] " + c.getName() + " - " + c.getCoinCost() + " coins");
            System.out.println("Stats: " + c.getType().toString() + " type, " + c.getValue()
                    + " strength, " + c.getEnergyCost() + " energy cost\n");
            i++;
        }
    }
}
