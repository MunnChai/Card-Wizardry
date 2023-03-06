package ui;

import model.Card;
import model.Deck;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static model.Deck.VIABLE_DECK_CARD_COUNT;

// Abstract UI class for any other UI class. Contains commonly used methods.
public abstract class UIMethods {
    private static final String JSON_STORE = "./data/user.json";

    protected User user;

    // EFFECTS: Abstract method for initializing a UI class
    public abstract void initUI();

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

    public void save() {
        JsonWriter jsonWriter = new JsonWriter("./data/user.json");
        try {
            jsonWriter.open();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found. Could not save to file.");
        }
        jsonWriter.write(user);
        jsonWriter.close();
    }

    public void load() {
        JsonReader jsonReader = new JsonReader("./data/user.json");
        try {
            user = jsonReader.read();
            System.out.println("Loaded " + user.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
