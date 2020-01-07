/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smashladder;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author tybik
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Scanner k = new Scanner(System.in);
//        System.out.print("Enter a file to read from: ");
//        String file = k.next();
        System.out.print("Enter the name of the tournament: ");
        String name = k.next();
        System.out.println();
        Tournament test = new Tournament("players.txt",name);
        int choice = 0;
        while(true){
            printMenu(name);
            choice = getChoice(1,10,"Enter a menu option: ");
            System.out.println();
            switch (choice) {
                case 1:
                    test.reportMatches();
                    break;
                case 2:
                    test.findMatch();
                    break;
                case 3:
                    test.printPastGames();
                    break;
                case 4:
                    test.printStandings();
                    break;
                case 5:
                    test.printPlayers();
                    break;
                case 6:
                    test.printActiveGames();
                    break;
                case 7:
                    test.editMatch();
                    break;
                case 8:
                    test.deleteMatch();
                    break;
                case 9:
                    test.addMatch();
                    break;
                case 10:
                    test.finalFour();
                    break;
                default:
                    break;
            }
            System.out.println();
        }
    }
    public static void printMenu(String name){
        System.out.println(name);
        System.out.println("1. Report matches ");
        System.out.println("2. Find match ");
        System.out.println("3. View past matches ");
        System.out.println("4. View standings ");
        System.out.println("5. View seeds");
        System.out.println("6. View active matches");
        System.out.println("7. Edit match");
        System.out.println("8. Delete match");
        System.out.println("9. Add match");
        System.out.println("10. Calculate final four");
    }
    public static int getChoice(int min, int max, String message){
        Scanner k = new Scanner(System.in); 
        int choice = 0;
        do {
            try {
                System.out.print(message);
                choice = k.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Invalid choice. ");
            }
            k.nextLine();
        } while (choice < min || choice > max);
        return choice;
    }
}
