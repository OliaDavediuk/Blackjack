package ua.com.blackjack.card;

public enum CardValue {

    TWO(0, 2),
    THREE(1, 3),
    FOUR(2, 4),
    FIVE(3, 5),
    SIX(4, 6),
    SEVEN(5, 7),
    EIGHT(6, 8),
    NINE(7, 9),
    TEN(8, 10),
    JACK(9, 10),
    KING(10, 10),
    QUEEN(11, 10),
    ACE(12, 11);

    private int id;
    private int score;

    private CardValue(int id, int score) {
        this.id = id;
        this.score = score;
    }

    private int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public static CardValue getCardValue(int cardId) {
        for (CardValue cardValue : CardValue.values()) {
            if (cardValue.getId() == cardId) {
                return cardValue;
            }
        }
        return null;
    }
}
