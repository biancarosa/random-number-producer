package br.com.biancarosa.producer;

import java.util.Random;

public class Producer extends Thread {

    private String name;

    public Producer(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        super.run();

        int n = new Random().nextInt();

        System.out.println("Number " + n + " generated - sending to buffer");
    }
}
