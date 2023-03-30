package ui;

import model.User;

import java.util.Scanner;

// Print intro sequence, and instantiate a User and ShopUI to begin the gameplay loop.
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Card Wizardry! \nIn this game, you will buy and sell cards, edit your decks,"
                + " and battle ferocious enemies. ");
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = scanner.nextLine();
        System.out.println("Your journey begins in a dark forest, where you've been wandering for days on end,"
                + " lost beyond lost.");
        User user = User.getInstance();
        user.setName(name);
        ShopUI shopUI = new ShopUI();
    }
}
