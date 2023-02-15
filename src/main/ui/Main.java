package ui;

import model.User;

// Print intro sequence, and instantiate a User and ShopUI to begin the gameplay loop.
public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Card Wizardry! \nIn this game, you will buy and sell cards, edit your decks,"
                + " and battle ferocious enemies. ");
        System.out.println("Your journey begins in a dark forest, where you've been wandering for days on end,"
                + " lost beyond lost.");
        User user = new User();
        ShopUI shopUI = new ShopUI(user);
    }
}
