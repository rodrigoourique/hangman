package org.academiadecodigo.codezillas.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client implements Runnable {

    private final PrintWriter outPut;
    private final Scanner userEntry;
    private final Scanner input;

    public Client(Socket socket) throws IOException {
        this.userEntry = new Scanner(System.in);
        outPut = new PrintWriter(socket.getOutputStream(), true);
        input = new Scanner(socket.getInputStream());
    }


    @Override
    public void run() {
        String message;

        while (true) {
            message = userEntry.nextLine();
            outPut.println(message);

            if ((message.equals("-") || message.equals("no"))) {
                System.out.println("\nClosing connection...");
                System.exit(0);
            }
            try {
                System.out.println(input.nextLine());
            } catch (NoSuchElementException ex) {
                System.out.println("Connection closed");
                System.exit(0);
            }
        }
    }

    public static class InitSocket {
        private Scanner write = new Scanner(System.in);
        private Socket socket;
        private String HOST;
        private final int PORT = 8080;

        public void initSocket() throws IOException{
            System.out.println("Hey! Before you start, please enter your IP Address! (If you are a localhost, press Enter.)");
           // Scanner host = new Scanner(System.in);
           /* HOST = host.nextLine();

            if(HOST == null){
                HOST = InetAddress.getLocalHost().toString();
            }  */
            socket = new Socket(HOST, PORT); // TODO: ver verificaçao do HOST
            //return socket;
        }

        public Socket getSocket() {
            return this.socket;
        }
    }
}
