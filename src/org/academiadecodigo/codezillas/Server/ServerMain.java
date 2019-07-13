package org.academiadecodigo.codezillas.Server;

import org.academiadecodigo.codezillas.Player.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    private static ServerSocket serverSocket;
    private static final int PORT = 1337;

    //Is initiated on the given port and loops listening to clients
    //to then send them to the ClientHandler
    public static void main(String[] args) throws IOException {

        System.out.println("Starting server!");
        serverSocket = new ServerSocket(PORT);
        System.out.println("\nServer Running!\n");

        while (true) {

            Socket client = serverSocket.accept();
            System.out.println("Client with " + client.toString() + " accepted. \n");
            ClientHandler handler = new ClientHandler(client);
            handler.run();
        }

    }

}
