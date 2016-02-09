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

    public void sendNumberToBuffer(int n) {
        String host = "127.0.0.1";
        try {
            Socket echoSocket = new Socket(host, 8000);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.println(name + "," + n);
            System.out.println("Number " + n + " sent to buffer");
            echoSocket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + host);
        }
    }

    @Override
    public void run() {
        super.run();

        while(true) {
            int n = new Random().nextInt();
            System.out.println("Number " + n + " generated - sending to buffer");
            sendNumberToBuffer(n);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
