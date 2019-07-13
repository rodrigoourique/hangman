package org.academiadecodigo.codezillas.Player;

import java.io.IOException;
import java.net.Socket;

public class Controller {
    private InitSocket init;
    private ListenerThread inputThread;
    private TalkThread outputThread;
    private Socket clientSocket;

    public Controller() {
        clientSocket = getSocket();
        startComunication();
        startListening();
    }

    Socket getSocket() throws IOException {
        init = new InitSocket();
        return init.getSocket();
    }

    void startComunication() throws IOException {
        outputThread = new TalkThread(clientSocket);
        outputThread.start();
    }

    void startListening() throws IOException {
        inputThread = new ListenerThread(clientSocket);
        inputThread.start();
    }

    @Override
    public void run() {

    }
}
