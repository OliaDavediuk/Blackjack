package ua.com.blackjack.card;

public enum CardSuit {
    HEARTS(0),
    DIAMONDS(1),
    CLUBS(2),
    SPADES(3);

    private int id;

    private CardSuit(int id) {
        this.id = id;
    }

    private int getId() {
        return id;
    }

    public static CardSuit getCardSuit(int suitId) {
        for (CardSuit cardSuit : CardSuit.values()) {
            if (cardSuit.getId() == suitId) {
                return cardSuit;
            }
        }
        return null;
    }
}
