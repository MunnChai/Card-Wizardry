package ui;

import model.Card;
import model.Deck;
import model.User;

import java.util.List;
import java.util.Scanner;

import static model.Deck.VIABLE_DECK_CARD_COUNT;

abstract class UIMethods {

    protected User user;

    // EFFECTS: Prints the given question, and takes the user's input as a string.
    public String takeStringInput(String question) {
        Scanner s = new Scanner(System.in);
        System.out.println(question);
        return s.nextLine();
    }

    // EFFECTS: Prints the given question, and takes the user's input as an integer.
    public int takeIntInput(String question) {
        Scanner s = new Scanner(System.in);
        System.out.println(question);
        return s.nextInt();
    }

    // EFFECTS: Takes an arbitrary number of strings, and print them.
    public void printSelections(String... option) {
        int i = 1;
        for (String s : option) {
            System.out.println("[" + i + "] " + s);
            i++;
        }
    }

    // EFFECTS: Waits for user input before continuing the program.
    public void pause() {
        Scanner pause = new Scanner(System.in);
        System.out.println("Enter anything to continue.");
        pause.nextLine();
    }

    // EFFECTS: Prints description of each deck in given list of decks
    public void printDecks(List<Deck> decks) {
        int i = 1;
        for (Deck d : decks) {
            System.out.println("[" + i + "] " + d.getName() + ": " + d.getCardsInDeck().size() + "/"
                    + VIABLE_DECK_CARD_COUNT);
            i++;
        }
    }

    // EFFECTS: Print a description of all the cards in list of cards.
    public void printCards(List<Card> cards) {
        int i = 1;
        for (Card c : cards) {
            System.out.println("\n[" + i + "] " + c.getName() + ": \n" + c.getType().toString() + " type, "
                    + c.getValue() + " strength, " + c.getEnergyCost() + " energy cost");
            i++;
        }
    }
    
    public abstract void initUI();
}
