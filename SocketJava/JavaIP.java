
import java.net.*;
import java.io.*;

public class JavaIP {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java JavaIP<hostname/IP>");
            System.exit(0);
        }
        try {
            InetAddress[] ad = InetAddress.getAllByName(args[0]);
            for (int i = 0; i < ad.length; i++) {
                System.out.println("IP = " + ad[i].getHostAddress());
                System.out.println("HOST = " + ad[i].getHostName());
            }
        } catch (Exception e) {
            System.out.println("Error:unknown host or IP address");
        }
    }
}
 