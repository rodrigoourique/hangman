package org.academiadecodigo.codezillas.Player;

import java.io.IOException;
import java.net.Socket;

public class Controller {
    private Client.InitSocket init;
    private Client inputThread;
    private Client outputThread;
    private Socket clientSocket;


    public Controller() throws IOException {
        clientSocket = getSocket();
        startComunication();
        startListening();
    }

    Socket getSocket() throws IOException {
        init = new Client.InitSocket();
        return init.getSocket();
    }

    void startComunication() throws IOException {
        outputThread = new Client(clientSocket);
        outputThread.run();
    }

    void startListening() throws IOException {
        inputThread = new Client(clientSocket);
        inputThread.run();
    }

}

//    @Override
//    public void run() {
//
//    }
//}
