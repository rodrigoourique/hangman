package org.academiadecodigo.codezillas.Server;

import org.academiadecodigo.codezillas.Server.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private String word;
    private Socket clientSocket;
    private Scanner input;
    private PrintWriter output;
    private Game game;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        input = new Scanner(clientSocket.getInputStream());
        output = new PrintWriter(clientSocket.getOutputStream(), true);

    }

    @Override
    public void run() {
        boolean isWordGuessed = false;
        boolean playing = true;
        int tries;
        int score = 0;
        game = new Game();
        try {
            game.gameStart(game.readFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
            word = game.getWord().toLowerCase();
            char[] guessWord = word.toCharArray();
            int totalTries = guessWord.length;
            char[] playerGuess = new char[totalTries];

            for (int i = 0; i < playerGuess.length; i++) {
                playerGuess[i] = '_';
            }
        while (true) {




            isWordGuessed = false;
            tries = 0;
            output.println("\nWelcome (again) to the Hangman game!\n");
            output.printf("Your total score is: %d\n\n", score);
            output.printf("The Word has %d letters.\n", totalTries);
            output.printf("Let's Begin!\n");


            // MENU ***********************************

            while (isWordGuessed && tries != totalTries) ;

            output.print("\nWord: ");
            printArray(playerGuess);
            output.printf("\nYou have %d tries left.\n", totalTries);
            output.printf("Enter a letter or word. ('-' to quit)\n");
            String guessedLetter = input.nextLine().toLowerCase();

            totalTries--;
            try {
                char letter = guessedLetter.charAt(0);

                if (letter == '-') {

                    try {
                        playing = false;
                        isWordGuessed = true;
                        tries = totalTries;
                        clientSocket.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    for (int i = 0; i < guessWord.length; i++) {
                        if (guessWord[i] == letter) {
                            playerGuess[i] = letter;
                        }
                    }
                }

           } catch (StringIndexOutOfBoundsException e) {
                output.println("\nYou must enter something.\n");
            }
            if (isWordGuessed(playerGuess) || word.equals(guessedLetter)) {
                isWordGuessed = true;
                output.println("Congratulations! You won!");
                score++;
            }

             if (totalTries == 0) {
                 output.println("\nYou ran out of guesses.");
                 output.println("Your final state was: ");
                 printArray(playerGuess);
                 output.println("Word was " + word + ".\n");
                 output.println("Do you want to play another game? (yes/no)");


                 try {
                     String temp = input.nextLine();
                     if (temp.equals("no") || temp.equals("-")) {
                         playing = false;
                         isWordGuessed = true;
                         System.out.println("Client with " + clientSocket.toString() + " ended the connection.");

                         try {
                             clientSocket.close();

                         } catch (IOException e) {
                             System.out.println("Unable to disconnect!");
                         }
                     }
                     else {
                         run();
                     }

                 } catch (NoSuchElementException e) {
                     System.out.println("Client with " + clientSocket.toString() + " ended the connection.\n");
                 }
             }
        }
    }

    private boolean isWordGuessed(char[] playerGuess) {
        for (int i = 0; i < playerGuess.length; i++) {
            if (playerGuess[i] == '_') {
                return false;
            }
        }

        return true;
    }


    public void printArray(char[] array) {
        for (int i = 0; i < array.length; i++) {
            output.print(array[i] + " ");
        }
        output.print("\n");
    }
}
