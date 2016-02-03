package br.com.biancarosa.producer;

public class Executor {

    /**
     * Executes the Producer application
     *
     * @param args should contain the number of threads
     */
    public static void main(String[] args) {
        int numberOfThreads = 0;
        try {
            numberOfThreads = Integer.parseInt(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Wrong number of args provided");
        } catch (NumberFormatException e) {
            System.err.println("Number of threads must be a number");
        }

        for (int i = 0; i < numberOfThreads; ++i) {
            new Producer().start();
        }

    }

}
