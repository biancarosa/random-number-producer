package br.com.biancarosa.producer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
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
        try {
            Socket echoSocket = new Socket("127.0.0.1", 8000);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);

            out.println(n);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + "localhost");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    "localhost");
            System.exit(1);
        }

        System.out.println("Number " + n + " generated - sending to buffer");
    }
}
