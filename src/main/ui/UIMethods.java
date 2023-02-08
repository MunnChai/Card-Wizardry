package ui;

import model.Card;
import model.Deck;

import java.util.Scanner;

import static model.Deck.VIABLE_DECK_CARD_COUNT;
import static ui.ShopUI.*;

abstract class UIMethods {
    public static String takeStringInput(String question) {
        Scanner s = new Scanner(System.in);
        System.out.println(question);
        return s.nextLine();
    }

    public static int takeIntInput(String question) {
        Scanner s = new Scanner(System.in);
        System.out.println(question);
        return s.nextInt();
    }

    public static void printSelections(String... option) {
        int i = 1;
        for (String s : option) {
            System.out.println("[" + i + "] " + s);
            i++;
        }
    }

    public static void editDecks() {
        System.out.println("These are your decks:");
        printDecks();
        deckSelector();
    }

    public static void printDecks() {
        int i = 1;
        for (Deck d : user.getDecks()) {
            System.out.println("[" + i + "] " + d.getName() + ": " + d.getCardsInDeck().size() + "/20");
            i++;
        }
    }

    public static void deckSelector() {
        int index = takeIntInput("Select a deck, or " + (shop.getUser().getDecks().size() + 1)
                + " to create a new deck, or " + (shop.getUser().getDecks().size() + 2) + " to go back.") - 1;

        if (index < shop.getUser().getDecks().size()) {
            editDeck(user.getDecks().get(index));
        } else if (index == shop.getUser().getDecks().size()) {
            Deck newDeck = new Deck("New Deck");
            shop.getUser().addDeck(newDeck);
            printDecks();
            deckSelector();
        } else {
            shopOptions();
        }
    }

    public static void editDeck(Deck deck) {
        if (deck.getCardsInDeck().size() == 0) {
            System.out.println("\n You have no cards in your deck.");
        } else {
            System.out.println("\nHere are the cards in your deck:");
        }
        printCardsInDeck(deck);
        deckOptions(deck);
    }

    public static void printCardsInDeck(Deck deck) {
        int i = 1;
        for (Card c : deck.getCardsInDeck()) {
            System.out.println("[" + i + "] " + c.getName() + " - " + c.getCoinCost() + " coins");
            System.out.println("Type: " + c.getType().toString() + "\nStrength: " + c.getValue()
                    + "\nEnergy Cost: " + c.getEnergyCost() + "\n");
            i++;
        }
    }

    public static void deckOptions(Deck deck) {
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
                editDecks();
        }
    }

    public static void deckAddCard(Deck deck) {
        Deck addableDeck = new Deck("Addable Cards");
        for (Card c : deck.getAvailableCards(user)) {
            addableDeck.addCard(c);
        }
        printCardsInDeck(addableDeck);
        selectCardToAdd(addableDeck, deck);
    }

    public static void selectCardToAdd(Deck cardsToAdd, Deck deck) {
        int index = takeIntInput("Select a card to add, or " + (cardsToAdd.getCardsInDeck().size() + 1)
                + " to go back.") - 1;

        if (index < cardsToAdd.getCardsInDeck().size()) {
            deck.addCard(cardsToAdd.getCardsInDeck().get(index));
            editDeck(deck);
        } else {
            shopOptions();
        }
    }

    public static void deckFillRandom(Deck deck) {
        int beforeInt = deck.getCardsInDeck().size();
        deck.fillRandom(user);
        System.out.println("Deck filled with " + (VIABLE_DECK_CARD_COUNT - beforeInt) + " random cards.");
        editDeck(deck);
    }

    public static void deckRemoveCard(Deck deck) {
        int select = takeIntInput("\nWhich card would you like to remove?") - 1;

        if (select < deck.getCardsInDeck().size()) {
            Card c = deck.getCardsInDeck().get(select);
            deck.removeCard(c);
            System.out.println("\nYou removed " + c.getName() + " from your deck.");
        }
        editDeck(deck);
    }

    public static void deckRename(Deck deck) {
        String newName = takeStringInput("\nWhat would you like to rename your deck as?");
        deck.setName(newName);
        System.out.println("\nRenamed deck to " + deck.getName());
        editDecks();
    }

    public static void deckDelete(Deck deck) {
        Scanner confirm = new Scanner(System.in);
        System.out.println("Are you sure you want to delete this deck?");
        printSelections("Yes", "No");
        int confirmInt = confirm.nextInt();

        if (confirmInt == 1) {
            user.getDecks().remove(deck);
            System.out.println("Deleted " + deck.getName());
        } else {
            System.out.println("");
        }
        editDecks();
    }

    public static void pause() {
        Scanner pause = new Scanner(System.in);
        System.out.println("Enter anything to continue.");
        pause.nextLine();
    }
}
