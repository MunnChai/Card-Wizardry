package ui;

import model.Card;
import model.Deck;
import model.Shop;
import model.User;

import java.util.Scanner;



public class ShopUI extends UIMethods {

    protected Shop shop;

    public ShopUI(User givenUser) {
        user = givenUser;
        shop = new Shop(user);

        initUI();
    }

    public void initUI() {
        enterShop();
    }

    public void enterShop() {
        System.out.println("\nAfter wandering on and off some raggedy paths for a few hours, "
                + "you stumble upon a small merchant's hut.");
        System.out.println("\"Welcome!\"");
        shopOptions();
    }

    public void shopOptions() {
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
                new EditDeckUI(user, this);
                shopOptions();
                break;
            case 4:
                System.out.println("\"Come again!!\"\nYou leave the hut and continue your journey once more.");
                new BattleUI(user);
                break;
            default:
                shopOptions();
        }
    }

    public void buyCard() {
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

    public void pickPurchasedCard() {
        int purchaseIndex = takeIntInput("Pick a card, or " + (shop.getCardsForSale().size() + 1)
                + " to go back.") - 1;

        if (purchaseIndex < shop.getCardsForSale().size()) {
            checkMoney(purchaseIndex);
        }
    }

    public void checkMoney(int purchaseIndex) {
        if (shop.getUser().getCoins() >= shop.getCardsForSale().get(purchaseIndex).getCoinCost()) {
            shop.buyCard(purchaseIndex);
            System.out.println("\"Thank you for your purchase!\" The merchant giggles.");
        } else if (shop.getUser().getCoins() < shop.getCardsForSale().get(purchaseIndex).getCoinCost()) {
            System.out.println("You cannot afford that.\n");
        }
    }

    public void printShopCards() {
        int i = 1;
        for (Card c : shop.getCardsForSale()) {
            System.out.println("[" + i + "] " + c.getName() + " - " + c.getCoinCost() + " coins");
            System.out.println("Type: " + c.getType().toString() + "\nStrength: " + c.getValue()
                    + "\nEnergy Cost: " + c.getEnergyCost() + "\n");
            i++;
        }
    }

    public void sellCard() {
        System.out.println("\nWhat card would you like to sell? \n* You can only sell cards that are not in any"
                + " current decks.\n");
        printUserCards();

        Scanner sell = new Scanner(System.in);
        System.out.println("Pick a card, or " + (shop.getUser().getCanSellCards().size() + 1) + " to go back.");
        int sellIndex = sell.nextInt() - 1;

        if (sellIndex < shop.getUser().getCanSellCards().size()) {
            Card card = shop.getUser().getCanSellCards().get(sellIndex);
            confirmSell(card);
        }
        shopOptions();
    }

    public void confirmSell(Card card) {
        Scanner confirm = new Scanner(System.in);
        System.out.println("Are you sure you want to sell your " + card.getName() + " for " + card.getCoinCost()
                + " coins? (Stats: " + card.getType().toString() + " type, " + card.getValue() + " strength, "
                + card.getEnergyCost() + " energy cost)");
        printSelections("Yes", "No");
        int confirmInt = confirm.nextInt();

        if (confirmInt == 1) {
            shop.sellCard(card);
            System.out.println("You now have: " + shop.getUser().getCoins() + " coins.");
        }
    }

    public void printUserCards() {
        int i = 1;
        for (Card c : user.getCanSellCards()) {
            System.out.println("[" + i + "] " + c.getName() + " - " + c.getCoinCost() + " coins");
            System.out.println("Stats: " + c.getType().toString() + " type, " + c.getValue()
                    + " strength, " + c.getEnergyCost() + " energy cost\n");
            i++;
        }
    }
}
