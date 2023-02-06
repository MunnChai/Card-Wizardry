package model;

import java.util.List;

public interface Player {
    public void playCard(int index, Player player);

    public void drawCard();

    public void takeDamage(int amount);

    public void heal(int amount);

    public void shield(int amount);

    public int getHealth();

    public int getEnergy();

    public List<Card> getHand();

    public Deck getCurrentDeck();

    public int getShield();

    public List<Card> getOwnedCards();
}
