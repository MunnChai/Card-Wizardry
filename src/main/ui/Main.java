package ui;

import model.Card;
import model.Deck;
import model.Shop;
import model.User;

import java.util.Scanner;

import static ui.ShopUI.shopOptions;

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
        ShopUI shopUI = new ShopUI(shop, user);
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
