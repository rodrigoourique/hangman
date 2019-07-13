package org.academiadecodigo.codezillas.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private String word;
    private Socket clientSocket;
    private Scanner input;
    private PrintWriter output;

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

        
    }

    public String getWord() {
        return word;
    }

    public boolean isWordCorrect(char[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == '_') {
                return false;
            }
        }
        return false;
    }
}
