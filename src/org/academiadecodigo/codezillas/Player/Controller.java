package org.academiadecodigo.codezillas.Player;

import java.io.IOException;
import java.net.Socket;

public class Controller {
    private Client inputThread;
    private Socket clientSocket;


    public Controller() throws IOException {
        clientSocket = new Socket("localhost", 8080);
        inputThread = new Client(clientSocket);
        ClientHandler handler = new ClientHandler(clientSocket);
        inputThread.run();
        handler.run();

        startCommunication();
        startListening();
    }

    Socket getSocket() {
        return clientSocket;
    }

    void startCommunication() throws IOException {
        Client outputThread;
        outputThread = new Client(clientSocket);
        outputThread.run();
    }

    void startListening() throws IOException {
        inputThread = new Client(clientSocket);
        inputThread.run();
    }
}
