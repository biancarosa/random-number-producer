package br.com.biancarosa.producer;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Producer extends Thread {

    private String name;
    private String host;
    private int port;
    static boolean wait = false;
    private static final int WAITING_TIME = 60000;

    public Producer(String name, String host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
    }

    private void sendNumberToBuffer(int n) {
        try {
            Socket echoSocket = new Socket(host, port);
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            out.println(name + "," + n);

            //Get the return message from the server
            InputStream is = echoSocket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            String[] messArgs = message.split("\\?");
            if (messArgs[1].equals("true")) {
                Producer.wait = true;
            } else {
                Producer.wait = false;
            }
            if (messArgs[2].equals("false")) {
                System.out.println(name + " tentou colocar item no Buffer cheio");
            } else {
                System.out.println("Valor " + n + " adicionado em Buffer pelo " + name);
            }
            echoSocket.close();
        } catch (UnknownHostException e) {
            System.err.println("Host desconhecido : " + host);
        } catch (IOException e) {
            System.err.println("Não foi possível conectar ao host " + host);
        }
    }

    @Override
    public void run() {
        super.run();

        while(true) {
            int n = new Random().nextInt();
            sendNumberToBuffer(n);
            try {
                if (Producer.wait) {
                    Thread.sleep(WAITING_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
