package cz.zcu.fav.ups.test.client;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Testovací třída pro připojení k serveru
 */
public class Client {

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("Použití: java Client Host Port");
            System.exit(1);
        }

        String host = args[0];
        String port = args[1];

        System.out.printf("Zkousim vytvorit spojeni s hostem: %s a portem: %s%n", host, port);
        Socket socket1 = new Socket(InetAddress.getByName(host), Integer.parseInt(port));
//        Socket socket2 = new Socket(InetAddress.getByName(host), Integer.parseInt(port));
        InetAddress address = socket1.getInetAddress();
        System.out.printf("Připojuji se na: %s se jmenem: %s%n", address.getHostAddress(), address.getHostName());

        DataOutputStream outputStream1 = new DataOutputStream(socket1.getOutputStream());
//        DataOutputStream outputStream2 = new DataOutputStream(socket2.getOutputStream());
        for (int i = 0; i < 1000; i++) {
            System.out.printf("Posílám: %d%n", i);
            outputStream1.writeBytes("1. cislo: " + i);
            //outputStream2.writeBytes("2. cislo: " + i);
            //outputStream1.writeInt(i);
            Thread.sleep(10);
        }
        //outputStream1.write("hello world".getBytes());
//        ObjectOutput output = new ObjectOutputStream(socket1.getOutputStream());
//        output.writeInt(151);
        outputStream1.close();
        socket1.close();
    }
}
