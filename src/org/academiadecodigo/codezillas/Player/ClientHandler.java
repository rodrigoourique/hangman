package org.academiadecodigo.codezillas.Player;

import org.academiadecodigo.bootcamp.Prompt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private String word;
    private Socket clientSocket;
    private Prompt prompt;
    private Scanner input;
    private PrintWriter output;
    private FileReader fileReader;
    private Controller controller = new Controller();

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        input = new Scanner(clientSocket.getInputStream());
        output = new PrintWriter(clientSocket.getOutputStream(), true);

    }

    @Override
    public void run() {
        boolean isWordGuessed;
        boolean playing = true;
        int tries;
        int score = 0;

        while (true) {
            word = controller.getWord().toLowerCase();
            char[] guessWord = word.toCharArray();
            int totalTries = guessWord.length;
            char[] playerGuess = new char[totalTries];

            for (int i = 0; i < playerGuess.length; i++) {
                playerGuess[i] = '_';
            }

            isWordGuessed = false;
            tries = 0;

            System.out.println("\nWelcome to Hangman game!");
            System.out.println("The word has %d letters.\n\n");
            System.out.println("You have " + totalTries + " tries left.\n\n");
            System.out.println("Let's begin!\n");

            while (!isWordGuessed && tries != totalTries) ;

            output.printf("\nCurrent state: ");
            printArray(playerGuess);
            output.printf("\nYou have %d tries left.\n" + (totalTries - tries));
            output.printf("Enter a letter or word. ('q' to quit)\n");
            String guessedWord = input.nextLine().toLowerCase();

            try {
                char letter = guessedWord.charAt(0);
                tries++;

                if (letter == 'q') {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    for (int i = 0; i < guessWord.length; i++) {
                        if (guessWord[i] == letter && guessedWord.length() == 1) {
                            playerGuess[i] = letter;
                        }
                    }
                }
            } catch (StringIndexOutOfBoundsException e) {
                output.println("\nYou must enter something.\n");
            }
            if (isWordGuessed(playerGuess) || word.equals(guessedWord)) {
                isWordGuessed = true;
                output.println("Congratulations! You won!");
                score++;
            }


            if (!isWordGuessed) {
                output.println("\nYou ran out of guesses.");
                output.print("Your final state was: ");
                printArray(playerGuess);
                output.println("Word was " + word + ".\n");
            }
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
            } catch (NoSuchElementException e) {
                System.out.println("Client with " + clientSocket.toString() + " ended the connection.\n");
            }
        }
    }

    public boolean isWordGuessed(char[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '_') {
                return false;
            }
        }
        return false;
    }

    public void printArray(char[] array) {
        for (int i = 0; i < array.length; i++) {
            output.print(array[i] + " ");
        }
        output.print("\n");
    }

    public class Controller {

        fileReader read = new fileReader();

        public String getWord() {
            String word = null;
            try {
                word = read.readFile();
            } catch (IOException x) {
            }
            return word;
        }
    }

    public class fileReader {

        public String readFile() throws IOException {
            BufferedReader br = null;
            FileReader fr = null;
            ArrayList<String> words = new ArrayList<String>();

            fr = new FileReader("words.txt");
            br = new BufferedReader(fr);
            String word;
            while ((word = br.readLine()) != null) {
                words.add(word);
            }
            return words.get((int) (Math.random() * words.size()));
        }
    }
}
