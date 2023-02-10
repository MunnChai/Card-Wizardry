package ui;

import model.Card;
import model.Deck;
import model.User;

import java.util.Scanner;

import static model.Deck.VIABLE_DECK_CARD_COUNT;

abstract class UIMethods {

    protected User user;

    public String takeStringInput(String question) {
        Scanner s = new Scanner(System.in);
        System.out.println(question);
        return s.nextLine();
    }

    public int takeIntInput(String question) {
        Scanner s = new Scanner(System.in);
        System.out.println(question);
        return s.nextInt();
    }

    public void printSelections(String... option) {
        int i = 1;
        for (String s : option) {
            System.out.println("[" + i + "] " + s);
            i++;
        }
    }

    public void pause() {
        Scanner pause = new Scanner(System.in);
        System.out.println("Enter anything to continue.");
        pause.nextLine();
    }

    public void printDecks() {
        int i = 1;
        for (Deck d : user.getDecks()) {
            System.out.println("[" + i + "] " + d.getName() + ": " + d.getCardsInDeck().size() + "/"
                    + VIABLE_DECK_CARD_COUNT);
            i++;
        }
    }

    public abstract void initUI();
}
