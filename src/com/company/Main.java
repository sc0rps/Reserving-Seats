package com.company;
import java.util.Locale;
import java.util.Scanner;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Random rand = new Random();
        boolean running = true;
        //int randomNumber = rand.nextInt(39);
        System.out.println("Would you like to reserve a seat? Type \"Y\" if you want to and \"N\" to randomly select one.");
        char option = in.next().charAt(0);
        while (running) {
            if (option  == 'Y') {
                checkSeat();
                running = false;
            }
        }

    }

    public static boolean checkSeat() {
        Scanner in = new Scanner(System.in);
        System.out.println("Which deck would you like to be at the lower deck or upper?\nType \"L\" or \"U\": ");
        char whichDeck = 0;
        String[][] deck = new String[40][6];
        boolean running = true;
        while (running) {
            whichDeck = in.next().charAt(0);
            if (whichDeck == 'L') {
                deck = retreiveLowerDeck();
                running = false;
            } else if (whichDeck == 'U') {
                deck = retreiveUpperDeck();
                running = false;
            } else {
                System.out.println("Try again");
            }
        }

        System.out.println("Which rows would you like to view?\nType \"0\" for 1-10 \nType \"1\" for 11-20 \nType \"2\" for 21-30 \nType \"3\" for 31-40\nType \"R\" to go straight into reserving your seat.");
        char whichRow;
        boolean running2 = true;
        while (running2) {
            whichRow = in.next().charAt(0);
            switch (whichRow) {
                case '0' :
                    check(deck, 0, 9);
                    break;
                case '1' :
                    check(deck, 10, 19);
                    break;
                case '2' :
                    check(deck, 20, 29);
                    break;
                case '3' :
                    check(deck, 30, 39);
                    break;
                case 'R' :
                    chooseSeat(deck, whichDeck);
                    return true;
                case 'O' :
                    checkSeat();
                    return true;
                default:
                    System.out.println("Try again");
            }
            System.out.println("\nEnter another letter to view another set of rows or type \"R\" to reserve your seat.\nIf you would like to view the other deck type \"O\".");
        }
        return true;
    }

    public static boolean chooseSeat(String[][] deck, char whichDeck) {
        Scanner in = new Scanner (System.in);
        boolean running = true;
        while (running) {
            System.out.println("Which column A-F would you like to book?");
            char columnLetter = 0;
            int columnNum = 0;
            boolean running2 = true;
            while (running2) {
                columnLetter = in.next().charAt(0);
                switch (columnLetter) {
                    case 'A':
                        columnNum = 0;
                        running2 = false;
                        break;
                    case 'B':
                        columnNum = 1;
                        running2 = false;
                        break;
                    case 'C':
                        columnNum = 2;
                        running2 = false;
                        break;
                    case 'D':
                        columnNum = 3;
                        running2 = false;
                        break;
                    case 'E':
                        columnNum = 4;
                        running2 = false;
                        break;
                    case 'F':
                        columnNum = 5;
                        running2 = false;
                        break;
                    default:
                        System.out.println("Try again");
                }
            }
            System.out.println("Which row on column " + columnLetter + " would you like to reserve?");
            int row = 0;
            boolean running3 = true;
            while (running3) {
                row = in.nextInt();
                if (row < 1 || row > 40) {
                    System.out.println("Try again");
                } else {
                    int rowNum = row - 1;
                    if (deck[rowNum][columnNum] == null) {
                        System.out.println("Seat is available!");
                        if (whichDeck == 'U' && row < 11) {
                            System.out.println("You require a first class ticket for this seat.");
                        }
                        if (row == 1 || row == 15) {
                            System.out.println("This seat has extra leg room. You are required to pay an extra £15 for this.");
                        }
                        System.out.println("Type \"Y\" to confirm\nType any other letter to cancel for whatever reason.");
                        char confirm = in.next().charAt(0);
                        if (confirm == 'Y') {
                            System.out.println("Please enter your name:");
                            String garbage = in.nextLine();
                            String name = in.nextLine();
                            deck[rowNum][columnNum] = name;
                            System.out.println("Updating on row " + rowNum + " and column " + columnNum);
                            System.out.println("Seat " + columnLetter + row + " has been successfully reserved!");
                            System.out.println("To reserve another seat, type \"A\". To exit the program, type anything else.");

                                running = false;
                                running3 = false;

                        }
                    }
                }
            }
        }
        if (whichDeck == 'L') {
            System.out.println(deck[0][0]);
            updateLowerDeck(deck);
        }
        if (whichDeck == 'U') {
            System.out.println("U");
            updateUpperDeck(deck);
        }
        return true;
    }
    public static void check(String[][] deck, int min, int max) {
        System.out.println("   A B C D E F");
        for (int i = min; i <= max; i++) {
            int row = i + 1;
            if (row < 10) {
                System.out.print("0" + row + " ");
            } else {
                System.out.print(row + " ");
            }
            for (int y = 0; y <= 5; y++) {
                if (deck[i][y] == null) {
                    System.out.print("o ");
                } else {
                    System.out.print("x ");
                }
            }
            System.out.print("\n");
        }
    }

    public static String[][] retreiveLowerDeck() {
        String[][] deck = null; // will deserialize to this
        try {
            FileInputStream fis = new FileInputStream("./files/lowerDeck.dat");
            ObjectInputStream iis = new ObjectInputStream(fis);
            deck = (String[][]) iis.readObject();

        } catch (Exception e) {

        }
        return deck;
    }
    public static String[][] retreiveUpperDeck() {
        String[][] deck = null; // will deserialize to this
        try {
            FileInputStream fis = new FileInputStream("./files/upperDeck.dat");
            ObjectInputStream iis = new ObjectInputStream(fis);
            deck = (String[][]) iis.readObject();

        } catch (Exception e) {

        }
        return deck;
    }


    public static void updateLowerDeck(String[][] lowerDeck) {
        try {
            FileOutputStream fos = new FileOutputStream("./files/lowerDeck.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(lowerDeck);
        } catch (Exception e) {

        }
    }
    public static void updateUpperDeck(String[][] upperDeck) {
        try {
            FileOutputStream fos = new FileOutputStream("./files/upperDeck.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(upperDeck);
        } catch (Exception e) {
        }
    }
}