
import java.net.*;
import java.io.*;

public class PortScan {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java PortScan<hostname/IP>");
            System.exit(0);
        }
        for (int j = 70; j <= 100; j++) {
            try {
                Socket socket = new Socket(args[0], j);
                System.out.println("Port " + socket.getPort() + ": open");
            } catch (Exception E) {
                System.out.println(E);
            }
        }
    }
}
