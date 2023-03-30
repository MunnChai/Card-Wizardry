# Card Wizardry

### A card battling game where you draw and play different cards to defeat your opponents.

### What is this?

**Card Wizardry** is a card game, where you battle opponents in a turn based style, playing
different cards in your deck to do certain actions, such as *attack* or *heal*. This game will have
many features, such as:
- **Energy System:** During each turn of a battle, the player is given a certain amount of energy 
which they can spend to play cards.
- **Randomized Enemies:** Different enemies will have different stats and decks, making every battle
unique. 
- **Shop:** In between battles, you may visit a shop to purchase new cards, or upgrade your character's
stats.
- **Customizable Decks:** You can also edit your decks in between battles, adding or removing certain 
cards to prepare for your next battle. 
- ***MANY* Customizable Decks:** You will be able to save multiple decks, which you can choose between.
- **Saving System:** Your game will be saved between battles, so you can quit without worry of losing
your *beautiful, perfectly optimized decks*.

### Who is this for?

This game is for people who are looking for a casual game to pass some time.

### Why did you make this?

I decided to make this project because game development has always been of interest to me. I feel that
this project is a good foundation for me to create more in depth and complex games.

## User Stories

### Out of Battle

- As a user, I want to be able to create a new deck and add it to a list of decks. 
- As a user, I want to be able to remove a deck from a list of decks.
- As a user, I want to be able to select a deck, and view the cards in the deck.
- As a user, I want to be able to select a deck, and add a card to the deck. 
- As a user, I want to be able to select a deck, and remove a card from the deck.
- As a user, I want to be able to purchase a card, and add it to my owned cards. 
- As a user, I want to be able to sell a card from my owned cards.
- As a user, I want to be able to save my game. 
- As a user, I want to be able to load a saved game. 

### In Battle

- As a user, I want to be able to draw a card from my deck, and add it to my hand. 
- As a user, I want to be able to play a card, removing it from my hand and having an effect occur
in the battle.


## Instructions for Grader

- To generate the action related to adding a card to a deck:
  1. Run the main method in the CardWizardryApp class.
  2. Click the button that says "NEW GAME"
  3. Click the button that says "CONTINUE"
  4. Click the button that says "EDIT DECKS"
  5. Click the button that says "CREATE NEW DECK"
  6. You will see a new panel that has the name "New Deck", click the button that says "EDIT"
  7. Click the button that says "ADD CARD"
  8. Press any button that says "ADD"
  9. You will see that the card has been added to your deck, which you can see in the upper half of the screen
- To generate the action related to removing a card from your deck:
  1. Follow steps 1-4 from above. 
  2. You will see a panel which is named "Starter Deck". Click the button on this panel that says "EDIT"
  3. In the upper half of the screen, you will see many panels which have visual representations of cards on them. 
  4. On any one of these panels, press the button that says "REMOVE" to remove that card from your deck
  5. There are other actions you can do from the deck editing screen, such as filling it randomly with cards, or removing all the cards from the deck.
- To find the visual component:
  1. Run the main method in the CardWizardryApp class
  2. Click the button that says "NEW GAME"
  3. Click the button that says "CONTINUE"
  4. You will see an image of a cat in the centre of the screen, who will say certain phrases depending on what you do.
  5. For example, if you click "BUY CARD" or "SELL CARD", you will see the cat say something different.
  6. If you buy or sell a card, the cat will smile slightly.
  7. There are also ImageIcons rendered on each card's panel
- To save the state of the application, there is a small button in the top right of most screens:
  1. Click this button, then click the button that says "SAVE"
  2. Click the button that says "CONTINUE"
  3. Your game state has now been saved.
- You can load a save file from the title screen of the application.
  1. To load the save file, press the button that says "LOAD FILE"
  2. Your file has now been loaded, and you can play the game.