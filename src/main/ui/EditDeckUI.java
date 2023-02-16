package ui;

import model.Card;
import model.Deck;
import model.User;

import java.util.Scanner;

import static model.Deck.VIABLE_DECK_CARD_COUNT;

// UI class for editing the user's decks. This class can be instantiated from any other UI class, and the previous UI
// class will be stored, so when the user is done editing the deck, they can return to the shop, or battle, or anything
// else.
public class EditDeckUI extends UIMethods {

    private UIMethods previousUI;

    // EFFECTS: Construct deck editing UI
    public EditDeckUI(User user, UIMethods previousUI) {
        super.user = user;
        this.previousUI = previousUI;

        initUI();
    }

    // EFFECTS: Initializes deck editing UI.
    public void initUI() {
        deckSelector();
    }

    // MODIFIES: user
    // EFFECTS: Print user's decks, and take user's input. Edit deck of user's input, create a deck, or return to the
    //          previous UI class depending on user's input.
    public void deckSelector() {
        System.out.println("These are your decks:");
        printDecks(user.getDecks());
        int index = takeIntInput("Select a deck, " + (user.getDecks().size() + 1)
                + " to create a new deck, or " + (user.getDecks().size() + 2) + " to go back.") - 1;

        if (index < user.getDecks().size()) {
            editDeck(user.getDecks().get(index));
        } else if (index == user.getDecks().size()) {
            Deck newDeck = new Deck("New Deck");
            user.addDeck(newDeck);
            deckSelector();
        } else if (index == user.getDecks().size() + 1) {
            previousUI.initUI();
        }
    }

    // EFFECTS: Print the cards in the given deck, and execute user input method.
    public void editDeck(Deck deck) {
        if (deck.getCardsInDeck().size() == 0) {
            System.out.println("\n You have no cards in your deck.");
        } else {
            System.out.println("\nHere are the cards in your deck:");
        }
        printCards(deck.getCardsInDeck());
        deckOptions(deck);
    }

    // EFFECTS: Take user's input, and execute corresponding method.
    public void deckOptions(Deck deck) {
        Scanner s = new Scanner(System.in);
        printSelections("Add a card", "Remove a card", "Randomly Fill Deck", "Rename Deck", "Delete Deck",
                "Back");
        int select = s.nextInt();

        switch (select) {
            case 1:
                deckAddCard(deck);
            case 2:
                deckRemoveCard(deck);
            case 3:
                deckFillRandom(deck);
            case 4:
                deckRename(deck);
            case 5:
                deckDelete(deck);
            default:
                deckSelector();
        }
    }

    // EFFECTS: Print a list of all the user's cards that are not already in the deck, and execute user input method
    //          to select card to add.
    public void deckAddCard(Deck deck) {
        Deck addableDeck = new Deck("Addable Cards");
        for (Card c : deck.getAvailableCards(user)) {
            addableDeck.addCard(c);
        }
        printCards(addableDeck.getCardsInDeck());
        selectCardToAdd(addableDeck, deck);
    }

    // MODIFIES: deck
    // EFFECTS: Take user input, and add card of given index to the given deck. Return to deck editor menu after.
    public void selectCardToAdd(Deck cardsToAdd, Deck deck) {
        int index = takeIntInput("Select a card to add, or " + (cardsToAdd.getCardsInDeck().size() + 1)
                + " to go back.") - 1;

        if (index < cardsToAdd.getCardsInDeck().size()) {
            Card c = cardsToAdd.getCardsInDeck().get(index);
            deck.addCard(c);
            System.out.println("\nYou added " + c.getName() + " to your deck.");
            pause();
        }
        editDeck(deck);
    }

    // MODIFIES: deck
    // EFFECTS: Add a card to the deck until it has the viable amount of cards. Return to deck editor menu after.
    public void deckFillRandom(Deck deck) {
        int beforeInt = deck.getCardsInDeck().size();
        deck.fillRandom(user);
        System.out.println("Deck filled with " + (VIABLE_DECK_CARD_COUNT - beforeInt) + " random cards.");
        editDeck(deck);
    }

    // MODIFIES: deck
    // EFFECTS: Take user's input, and remove card of given index from deck's cards. Return to deck editor menu after.
    public void deckRemoveCard(Deck deck) {
        int select = takeIntInput("\nWhich card would you like to remove?") - 1;

        if (select < deck.getCardsInDeck().size()) {
            Card c = deck.getCardsInDeck().get(select);
            deck.removeCard(c);
            System.out.println("\nYou removed " + c.getName() + " from your deck.");
            pause();
        }
        editDeck(deck);
    }

    // MODIFIES: deck
    // EFFECTS: Renames deck to user's input. Return to deck editor menu after.
    public void deckRename(Deck deck) {
        String newName = takeStringInput("\nWhat would you like to rename your deck as?");
        deck.setName(newName);
        System.out.println("\nRenamed deck to " + deck.getName());
        deckSelector();
    }

    // MODIFIES: user
    // EFFECTS: Print deletion confirmation, and delete deck from user's decks if user confirms. Return to deck editor
    //          menu after.
    public void deckDelete(Deck deck) {
        Scanner confirm = new Scanner(System.in);
        System.out.println("Are you sure you want to delete this deck?");
        printSelections("Yes", "No");
        int confirmInt = confirm.nextInt();

        if (confirmInt == 1) {
            user.getDecks().remove(deck);
            System.out.println("Deleted " + deck.getName());
        }
        deckSelector();
    }
}
