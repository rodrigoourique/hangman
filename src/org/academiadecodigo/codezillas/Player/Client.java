package org.academiadecodigo.codezillas.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

    private final PrintWriter outPut;
    private final Scanner userEntry;

    public Client(Socket socket) throws IOException {
        this.userEntry = new Scanner(System.in);
        outPut = new PrintWriter(socket.getOutputStream(), true);
    }


    @Override
    public void run() {
        String message;

        while (true) {
            message = userEntry.nextLine();
            outPut.println(message);

            if (!(message.equals("-") || message.equals("no"))) {
                System.out.println("\nClosing connection...");
                System.exit(0);
            }
        }
    }
    public class InitSocket {
        private Scanner write = new Scanner(System.in);
        private Socket socket;
        private final int PORT = 8080;
        private String HOST;

        public InitSocket() throws IOException{
            System.out.println("Hey! Before you start, please enter your IP Address! (If you are a localhost, press Enter.)");
            Scanner host = new Scanner(System.in);
            HOST = host.nextLine();

            if(HOST == null){
                HOST = InetAddress.getLocalHost().toString();
            }
            socket = new Socket(HOST, PORT);
        }

        public Socket getSocket() {
            return this.socket;
        }
    }
}
