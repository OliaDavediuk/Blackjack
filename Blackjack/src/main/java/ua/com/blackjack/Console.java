package ua.com.blackjack;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console{

    public static final String SEPARATOR = "========================================================================";

    public void write(String message) {
        System.out.println(message);
    }

    public String read() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextLine();
        }catch (NoSuchElementException e){
            return null;
        }
    }
}