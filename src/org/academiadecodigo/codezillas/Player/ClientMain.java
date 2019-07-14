package org.academiadecodigo.codezillas.Player;

import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        controller.getSocket();
        controller.startCommunication();

        
    }
}
