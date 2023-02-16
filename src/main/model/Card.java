package model;

// Represents a playing card with a name, type, value (potency), energy cost, and coin cost
public class Card {

    // Types of cards.
    public enum CardType {
        ATTACK, HEAL, SHIELD;

        public boolean isCardType(CardType cardType) {
            return this == cardType;
        }
    }

    // Different adjectives used to generate card names. Sorted by adjective types that correspond with card types.
    enum Adjectives {

        DEADLY(AdjType.ATTACK), EVIL(AdjType.ATTACK), MONSTROUS(AdjType.ATTACK), LETHAL(AdjType.ATTACK),
        MALICIOUS(AdjType.ATTACK), VICIOUS(AdjType.ATTACK), WICKED(AdjType.ATTACK), VILLAINOUS(AdjType.ATTACK),

        HOLY(AdjType.HEAL), DIVINE(AdjType.HEAL), TRANSCENDENT(AdjType.HEAL), ANGELIC(AdjType.HEAL),
        HEAVENLY(AdjType.HEAL), MYSTICAL(AdjType.HEAL), SACRED(AdjType.HEAL), ETERNAL(AdjType.HEAL),

        VIGILANT(AdjType.SHIELD), SHIELDING(AdjType.SHIELD), SHELTERING(AdjType.SHIELD), WATCHFUL(AdjType.SHIELD),
        FORTIFYING(AdjType.SHIELD), REINFORCING(AdjType.SHIELD), STRENGTHENING(AdjType.SHIELD), BRACING(AdjType.SHIELD);

        private final AdjType adjType;

        Adjectives(AdjType adjType) {
            this.adjType = adjType;
        }

        enum AdjType {
            ATTACK, HEAL, SHIELD
        }
    }

    // Different nouns used to generate card names. Sorted by noun types that correspond with card types.
    enum Nouns {

        DOOM(NounType.ATTACK), CATACLYSM(NounType.ATTACK), DISASTER(NounType.ATTACK), MISHAP(NounType.ATTACK),
        DISTRESS(NounType.ATTACK), JUDGEMENT(NounType.ATTACK), DEATH(NounType.ATTACK), SABOTAGE(NounType.ATTACK),

        HAPPINESS(NounType.HEAL), WELLBEING(NounType.HEAL), PROSPERITY(NounType.HEAL), FORTUNE(NounType.HEAL),
        PLENTEOUSNESS(NounType.HEAL), CONTENTMENT(NounType.HEAL), DELIGHT(NounType.HEAL), GLEE(NounType.HEAL),

        SECURITY(NounType.SHIELD), SAFETY(NounType.SHIELD), PRESERVATION(NounType.SHIELD), ASSURANCE(NounType.SHIELD),
        SAFEGUARDING(NounType.SHIELD), HEALTHINESS(NounType.SHIELD), INVULNERABILITY(NounType.SHIELD),
        IMMUNITY(NounType.SHIELD);

        private final NounType nounType;

        Nouns(NounType nounType) {
            this.nounType = nounType;
        }

        enum NounType {
            ATTACK, HEAL, SHIELD
        }
    }

    private String name;
    private CardType type;
    private int value;
    private int energyCost;
    private int coinCost;

    // EFFECTS: Constructs a card, with given name, type, value, and cost
    public Card(CardType type, int value, int energyCost, int coinCost) {
        this.type = type;
        this.name = pickRandomAdj(type) + " Spell Of " + pickRandomNoun(type);
        this.value = value;
        this.energyCost = energyCost;
        this.coinCost = coinCost;
    }

    // EFFECTS: Returns random adjective, depending on which type is given
    public String pickRandomAdj(CardType type) {
        int randomInt;
        switch (type) {
            case ATTACK:
                randomInt = (int)(Math.random() * 8);
                break;

            case HEAL:
                randomInt = (int)(Math.random() * 8) + 8;
                break;

            default:
                randomInt = (int)(Math.random() * 8) + 16;
        }
        return Adjectives.values()[randomInt].name();
    }

    // EFFECTS: Returns random noun, depending on which type is given
    public String pickRandomNoun(CardType type) {
        int randomInt;
        switch (type) {
            case ATTACK:
                randomInt = (int)(Math.random() * 8);
                break;

            case HEAL:
                randomInt = (int)(Math.random() * 8) + 8;
                break;

            default:
                randomInt = (int)(Math.random() * 8) + 16;
        }
        return Nouns.values()[randomInt].name();
    }

    // MODIFIES: player
    // EFFECTS: increases player shield/player health by this.value, or decrease enemy health by this.value,
    //          player energy - energy cost
    public void cardEffect(Player player) {
        switch (this.type) {
            case ATTACK:
                player.takeDamage(this.getValue());
                break;
            case HEAL:
                player.heal(this.getValue());
                break;
            default:
                player.shield(this.getValue());
        }
    }


    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Getters
    public CardType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public int getEnergyCost() {
        return energyCost;
    }

    public int getCoinCost() {
        return coinCost;
    }
}
