package org.academiadecodigo.codezillas.Player;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class PromptMenu {

    private Prompt prompt;
    private Socket clientSocket;

    public PromptMenu(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public String askName() {

        try {
            this.prompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringInputScanner input = new StringInputScanner();
        input.setMessage("Please, enter your name: ");
        String name = prompt.getUserInput(input);
        return name;
    }
}
