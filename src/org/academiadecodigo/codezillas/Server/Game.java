package org.academiadecodigo.codezillas.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Game {

    private String word;

    public void gameStart(String word) {
        this.word = word;
    }

    public String readFile() throws IOException {
        BufferedReader br;
        FileReader fr;
        ArrayList<String> words = new ArrayList<>();

        fr = new FileReader("resources/wordslist");
        br = new BufferedReader(fr);
        String word;

        while ((word = br.readLine()) != null) {
            words.add(word);
            //System.out.println(word);
        }
        return words.get((int) (Math.random() * words.size()));
    }

    public String getWord() {
        return word;
    }
}
