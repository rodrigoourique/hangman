package org.academiadecodigo.codezillas.Player;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private String word;
    private Socket clientSocket;
    private Scanner input;
    private PrintWriter output;


    @Override
    public void run() {

    }

    public String getWord() {
        return word;
    }

    public boolean isWordCorrect() {

        return false;
    }
}
