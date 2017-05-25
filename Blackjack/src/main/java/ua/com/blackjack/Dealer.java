package ua.com.blackjack;

public class Dealer {

    private double currentBet;
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private double balance = 100;
    private boolean isActionDone;
    private Server server;

    public void run() {
        server = new Server();
        server.run();
        startGame();
    }

    private void startGame() {
        deck = new Deck();
        do {
            playerHand = new Hand();
            dealerHand = new Hand();
            getPlayersBet();
            dealCards(playerHand, dealerHand);
            server.sendData("Dealer's card: [" + dealerHand.getCard(0) + ", **hidden**].");
            server.sendData("Your card: " + playerHand.toString());

            if (playerHand.isBlackjack()) {
                server.sendData("Blackjack!");
            } else {
                server.sendData("Chose the action STAND( \"s\"), HIT( \"h\"), or DOUBLE( \"d\")");
                isActionDone = false;
                while (!isActionDone) {
                    String action = getAction();
                    switch (action) {
                        case "h": {
                            hitAction();
                            break;
                        }
                        case "d": {
                            doubleAction();
                            break;
                        }
                        case "s": {
                            standAction();
                            break;
                        }
                        default: {
                            server.sendData("You need enter \"s\", \"h\" or \"d\". Please, try again.");
                        }
                    }
                }

                if (playerHand.isBusted()) {
                    server.sendData("You are busted!");
                } else {
                    dealerRound();
                }
            }

            makeResults();
            server.sendData(Console.SEPARATOR);
        } while (balance > 0);
        server.sendData("You don't have money to play.");
        server.sendData("gameOver");
        System.exit(0);
    }

    private void hitAction() {
        hitCard(playerHand);
        server.sendData("Your card: " + playerHand.toString());
        if (playerHand.isBusted() || playerHand.isBlackjack()) {
            isActionDone = true;
        } else {
            server.sendData("Chose the action STAND( \"s\") or HIT( \"h\")");
        }
    }

    private void doubleAction() {
        if (playerHand.getNumberOfCards() > 2) {
            server.sendData("You can double just once after the first 2 card was dealt. Choose another action.");
        } else if ((balance - currentBet) < 0) {
            server.sendData("You don't have enough money to double bet. Choose another action.");
        } else {
            balance = balance - currentBet;
            currentBet = 2 * currentBet;
            server.sendData("Current bet: " + currentBet);
            hitCard(playerHand);
            server.sendData("Your card: " + playerHand.toString());
            isActionDone = true;
        }
    }

    private void standAction() {
        isActionDone = true;
    }

    private void hitCard(Hand hand) {
        hand.addCard(deck.getCard());
    }

    private void makeResults() {
        int playerPoints = playerHand.countPoints();
        int dealerPoints = dealerHand.countPoints();
        if ((playerPoints < dealerPoints && !dealerHand.isBusted()) || playerHand.isBusted()) {
            server.sendData("Dealer win!");
        } else if (playerPoints > dealerPoints || dealerHand.isBusted() || playerHand.isBlackjack()) {
            double win = currentBet*2;
            balance = balance + win;
            server.sendData("You win: " + win);
        } else {
            server.sendData("Push!");
            balance = balance + currentBet;
        }
    }

    private void dealerRound() {
        server.sendData("Dealer's cards: " + dealerHand.toString());
        while (dealerHand.countPoints() <= 16 && dealerHand.countPoints() <= playerHand.countPoints()) {
            hitCard(dealerHand);
            server.sendData("Dealer hits card: " + dealerHand.toString());
            if (dealerHand.countPoints() > 21) {
                server.sendData("Dealer busted!");
            }
        }
    }

    private String getAction() {
        server.sendData("getData");
        return server.readData();
    }

    private void getPlayersBet() {
        server.sendData("Your balance: " + balance + "$. Please, enter your bet: ");
        while (true) {
            try {
                server.sendData("getData");
                currentBet = Double.parseDouble(server.readData());
                if ((currentBet <= 0) || (currentBet > balance)) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                server.sendData("You need enter a number between 0 and " + balance + "$. Try again.");
            }
        }

        balance = balance - currentBet;
        server.sendData("Bet: " + currentBet + "$. Current balance: " + balance + "$.");
    }

    private void dealCards(Hand playerHand, Hand dealerHand) {
        deck.shuffle();
        hitCard(playerHand);
        hitCard(playerHand);
        hitCard(dealerHand);
        hitCard(dealerHand);
    }
}

