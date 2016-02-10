package br.com.biancarosa.producer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Producer extends Thread {

    private String name;
    static boolean wait = false;

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

            //Get the return message from the server
            InputStream is = echoSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println("Message received from the server : " + message);

            String[] messArgs = message.split("\\?");
            if (messArgs[1].equals("true")) {
                Producer.wait = true;
            } else {
                Producer.wait = false;
            }
            if (messArgs[2].equals("false")) {
                Thread.sleep(6000);
                System.out.println("try again later");
                sendNumberToBuffer(n); // try again
            }

            echoSocket.close();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + host);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
                if (Producer.wait) {
                    Thread.sleep(60000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
