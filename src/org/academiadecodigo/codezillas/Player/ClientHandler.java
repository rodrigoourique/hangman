package org.academiadecodigo.codezillas.Player;

import org.academiadecodigo.codezillas.Server.Game;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private Scanner inputScanner;
    private PrintWriter output;
    private boolean isWordGuessed = false;
    private PromptMenu promptMenu;
    private int score = 0;


    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        inputScanner = new Scanner(clientSocket.getInputStream());
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        this.promptMenu = new PromptMenu(clientSocket);
    }

    @Override
    public void run() {

        String word;
        boolean playing = true;
        Game game = new Game();

        while (playing) {
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

            int tries = 0;

            output.println("\nWelcome to the Hangman game!\n");
            promptMenu.askName();
            output.printf("Your total score is: %d\n\n", score);
            output.printf("The Word has %d letters.\n", totalTries);
            output.printf("Let's begin!\n");

            while (!isWordGuessed || tries != totalTries) {

                output.print("\nWord: ");
                printArray(playerGuess);
                output.printf("\nYou have %d tries left.\n", totalTries);
                System.out.println(word);
                output.printf("Enter a letter or word. ('0' to quit)\n");

                String guessedLetter = inputScanner.nextLine().toLowerCase();

                try {
                    char letter = guessedLetter.charAt(0);

                    if (guessedLetter == null) {
                        output.println("\nYou must enter something.\n");
                        return;

                    }

                    if (letter == '0') {

                        try {
                            playing = false;
                            isWordGuessed = true;
                            clientSocket.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        boolean isLetterGuessed = false;

                        for (int i = 0; i < guessWord.length; i++) {

                            if (guessWord[i] == letter) {
                                playerGuess[i] = letter;
                                isLetterGuessed = true;
                            }
                        }
                        if (!isLetterGuessed) {
                            totalTries--;
                        }
                        if (isWordGuessed) {
                            win();
                        }
                    }


                } catch (java.lang.StringIndexOutOfBoundsException e) {
                    output.println("\nYou must enter something.\n");
                }

                if (isWordGuessed(playerGuess) || word.equals(guessedLetter)) {
                    win();
                    output.println("Do you want to play another game? (yes/no)");

                        try {
                            String temp = inputScanner.nextLine();
                            if (temp.equals("no") || temp.equals("0")) {
                                playing = false;
                                isWordGuessed = true;
                                System.out.println("Client with " + clientSocket.toString() + " ended the connection.");

                                try {
                                    clientSocket.close();

                                } catch (IOException e) {
                                    System.out.println("Unable to disconnect!");
                                }
                            } else {
                                run();
                            }

                        } catch (NoSuchElementException e) {
                            System.out.println("Client with " + clientSocket.toString() + " ended the connection.\n");
                        }

                }

                if (totalTries == 0) {
                    output.println("\nYou ran out of guesses.");
                    output.println("Your final state was: ");
                    printArray(playerGuess);
                    output.println("Word was: " + word + "\n");
                    output.println("Do you want to play another game? (yes/no)");

                    try {
                        String temp = inputScanner.nextLine();
                        if (temp.equals("no") || temp.equals("0")) {
                            playing = false;
                            isWordGuessed = true;
                            System.out.println("Client with " + clientSocket.toString() + " ended the connection.");

                            try {
                                clientSocket.close();

                            } catch (IOException e) {
                                System.out.println("Unable to disconnect!");
                            }
                        } else {
                            run();
                        }

                    } catch (NoSuchElementException e) {
                        System.out.println("Client with " + clientSocket.toString() + " ended the connection.\n");
                    }
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

    public void win() {
        isWordGuessed = true;
        output.println("Congratulations! You won!");
        score++;
    }
}

