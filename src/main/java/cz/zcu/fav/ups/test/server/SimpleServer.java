package cz.zcu.fav.ups.test.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Třída představuje jednoduchý testovací server, který slouží jako konzole, do které se píčí příkazy
 */
public class SimpleServer {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new SimpleServer().launch();
    }

    private void launch() {
        try {
            ServerSocket serverSocket = new ServerSocket(20000);
            System.out.println("Server started on port: 20000");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Připojil se klient");
            BufferedOutputStream outputStream = new BufferedOutputStream(clientSocket.getOutputStream());
            BufferedInputStream inputStream = new BufferedInputStream(clientSocket.getInputStream());
            new InputThread(inputStream).start();
            System.out.print("Vložte příkaz: ");
            String input;
            while(!(input = scanner.next()).equals("exit")) {
                outputStream.write(input.getBytes());
                outputStream.flush();
                System.out.print("\nVložte příkaz: ");
            }

            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class InputThread extends Thread {

        private final BufferedInputStream inputStream;

        private final byte[] buffer = new byte[1024];

        private InputThread(BufferedInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            boolean running = true;
            while(running) {
                try {
                    while(inputStream.read(buffer) != -1) {
                        String readed = new String(buffer);
                        System.out.println(readed);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    running = false;
                }

                System.out.println("Sakra, dostal jsem se až na konec");
            }
        }
    }
}
