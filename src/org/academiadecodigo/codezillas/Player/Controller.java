package org.academiadecodigo.codezillas.Player;

import org.academiadecodigo.codezillas.Server.ClientHandler;

import java.io.IOException;
import java.net.Socket;

public class Controller {
    private Client.InitSocket init;
    private Client inputThread;
    private Client outputThread;
    private Socket clientSocket;


    public Controller() throws IOException {
        clientSocket = new Socket("localhost", 8080);
        inputThread = new Client(clientSocket);
        ClientHandler wtf = new ClientHandler(clientSocket);
        inputThread.run();
        wtf.run();

        startCommunication();
        startListening();
    }

    Socket getSocket() throws IOException {
        return clientSocket;


    }

    void startCommunication() throws IOException {
        outputThread = new Client(clientSocket);
        outputThread.run();
    }

    void startListening() throws IOException {
        inputThread = new Client(clientSocket);
        inputThread.run();
    }

}


