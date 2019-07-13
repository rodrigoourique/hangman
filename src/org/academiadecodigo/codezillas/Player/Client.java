package org.academiadecodigo.codezillas.Player;

import java.io.IOException;
import java.io.PrintWriter;
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
}
