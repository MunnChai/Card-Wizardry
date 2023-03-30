package ui;

import model.Card;
import model.Shop;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


// UI class for shops. From this class, a user can buy and sell cards, and access the deck editing UI class.
public class ShopUI extends UIMethods {

    protected Shop shop;

    // EFFECTS: Constructs shop UI.
    public ShopUI() {
        user = User.getInstance();
        User.setInstance(user);
        shop = Shop.getInstance();
        shop.setUser(user);
        Shop.setInstance(shop);

        initUI();
    }

    // EFFECTS: Initializes shop UI.
    public void initUI() {
        enterShop();
    }

    // EFFECTS: Print enter shop message, and execute shop options method.
    public void enterShop() {
        System.out.println("\nAfter wandering on and off some raggedy paths for a few hours, "
                + "you stumble upon a small merchant's hut.");
        System.out.println("\"Welcome!\"");
        shopOptions();
    }

    // EFFECTS: Take user input, and execute correlating method.
    public void shopOptions() {
        System.out.println("\"What can I get for ya?\" \nYou have " + user.getCoins() + " coins.");
        Scanner s = new Scanner(System.in);
        printSelections("\"I'd like to buy a card.\"", "\"I'd like to sell a card.\"",
                "\"I'd like to edit my decks.\"", "\"I'd better get going.\"", "Save Game",
                "Load Game", "Quit Game");
        int index = s.nextInt();
        userSelection(index);
    }

    // EFFECTS: execute methods depending on given index
    private void userSelection(int index) {
        if (index == 1) {
            buyCard();
        } else if (index == 2) {
            sellCard();
        } else if (index == 3) {
            new EditDeckUI(user, this);
        } else if (index == 4) {
            System.out.println("\"Come again!!\"\nYou leave the hut and continue your journey once more.");
            new BattleUI(user);
        } else if (index == 5) {
            save();
            saveShop();
            shopOptions();
        } else if (index == 6) {
            load();
            loadShop();
            shop.setUser(user);
            shopOptions();
        } else if (index == 7) {
            System.out.println("\"Ciao\"");
        } else {
            shopOptions();
        }
    }

    // EFFECTS: If shop has 0 cards, return to shop options menu. Otherwise, print shop's cards, and execute user
    //          picking method.
    public void buyCard() {
        if (shop.getCardsForSale().size() == 0) {
            System.out.println("\"I've got nothing more.\"");
            shopOptions();
        } else {
            System.out.println("\n\"Take a look at my collection.\"" + "\nThe merchant shows you some cards.\n");
            printShopUICards(shop.getCardsForSale());

            pickPurchasedCard();
            shopOptions();
        }
    }

    // EFFECTS: Takes the user's input, and checks if the user has enough coins to buy the card with index of the
    //          user's input.
    public void pickPurchasedCard() {
        int purchaseIndex = takeIntInput("Pick a card, or " + (shop.getCardsForSale().size() + 1)
                + " to go back.") - 1;

        if (purchaseIndex < shop.getCardsForSale().size()) {
            checkMoney(purchaseIndex);
        }
    }

    // MODIFIES: user, shop
    // EFFECTS: If user has enough coins, purchase card. Otherwise, return to shop menu UI.
    public void checkMoney(int purchaseIndex) {
        if (user.getCoins() >= shop.getCardsForSale().get(purchaseIndex).getCoinCost()) {
            shop.buyCard(purchaseIndex);
            System.out.println("\"Thank you for your purchase!\" The merchant giggles.");
        } else if (user.getCoins() < shop.getCardsForSale().get(purchaseIndex).getCoinCost()) {
            System.out.println("You cannot afford that.\n");
        }
    }

    // MODIFIES: user
    // EFFECTS: Prints user's sellable cards, and takes user input. Sell corresponding card from user's sellable cards.
    //          Return to shop options menu after.
    public void sellCard() {
        System.out.println("\nWhat card would you like to sell? \n* You can only sell cards that are not in any"
                + " current decks.\n");
        printShopUICards(user.getCanSellCards());

        int sellIndex = takeIntInput("Pick a card, or " + (user.getCanSellCards().size() + 1) + " to go back.")
                - 1;

        if (sellIndex < user.getCanSellCards().size()) {
            Card card = user.getCanSellCards().get(sellIndex);
            confirmSell(card);
        }
        shopOptions();
    }

    // MODIFIES: user
    // EFFECTS: Take user confirmation to sell card. If user input is 1, sell the card. Otherwise, return to shop
    //          option menu.
    public void confirmSell(Card card) {
        Scanner confirm = new Scanner(System.in);
        System.out.println("Are you sure you want to sell your " + card.getName() + " for " + card.getCoinCost()
                + " coins? (Stats: " + card.getType().toString() + " type, " + card.getValue() + " strength, "
                + card.getEnergyCost() + " energy cost)");
        printSelections("Yes", "No");
        int confirmInt = confirm.nextInt();

        if (confirmInt == 1) {
            shop.sellCard(card);
            System.out.println("You now have: " + user.getCoins() + " coins.");
        }
    }

    // EFFECTS: Prints description of each card in given cards in shop format.
    public void printShopUICards(List<Card> cards) {
        int i = 1;
        for (Card c : cards) {
            System.out.println("[" + i + "] " + c.getName() + " - " + c.getCoinCost() + " coins");
            System.out.println("Type: " + c.getType().toString() + "\nStrength: " + c.getValue()
                    + "\nEnergy Cost: " + c.getEnergyCost() + "\n");
            i++;
        }
    }

    // EFFECTS: Saves JSON representation of shop to file
    public void saveShop() {
        JsonWriter shopWriter = new JsonWriter("./data/shop.json");
        try {
            shopWriter.open();
            shopWriter.write(shop);
            shopWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found. Could not save to file.");
        }
    }

    // MODIFIES: user
    // EFFECTS: Loads user from file
    public void loadShop() {
        JsonReader jsonReader = new JsonReader("./data/shop.json");
        try {
            shop = jsonReader.readShop();
            System.out.println("Loaded shop from ./data/shop.json");
        } catch (IOException e) {
            System.out.println("Unable to read from file: ./data/shop.json");
        }
    }
}
