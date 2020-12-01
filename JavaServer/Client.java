
import java.io.*;
import java.net.*;

public class Client {

    static InputStream in = null;
    static OutputStream out = null;
    static BufferedReader br = null;
    static PrintWriter pw = null;
    static Socket s = null;

    public static void main(String[] args) {
        try {
            if (args[0].toLowerCase().equals("add") || args[0].toLowerCase().equals("search")) {
                args[0] = args[0].toLowerCase();
                if (args[0].equals("add")) {
                    if (args.length != 3) {
                        System.out.println("Command not found.");
                        System.exit(0);
                    }
                } else if (args[0].equals("search")) {
                    if (args.length != 2) {
                        System.out.println("Command not found.");
                        System.exit(0);
                    }
                }
                for (int i = 1; i < args.length; i++) {
                    if ((args[i].equals("add") || args[i].equals("search")) || args.equals("OK")) {
                        System.out.println("Command not found.");
                        System.exit(0);
                    }
                }
                s = new Socket("127.0.0.1", 23410);
                in = s.getInputStream();
                out = s.getOutputStream();
                br = new BufferedReader(new InputStreamReader(in));
                pw = new PrintWriter(out);
                for (int i = 0; i < args.length; i++) {
                    if (i == 2) {
                        String firstLetter = args[i].substring(0, 1);
                        String remainingLetters = args[i].substring(1, args[i].length());
                        firstLetter = firstLetter.toUpperCase();
                        args[i] = firstLetter + remainingLetters;
                        pw.println(args[i]);
                        pw.flush();
                        break;
                    } else {
                        pw.println(args[i]);
                        pw.flush();
                    }
                }
                pw.println("OK");
                pw.flush();
                String response = br.readLine();
                System.out.println(response);
                in.close();
                out.close();
                s.close();
            } else {
                System.out.println("Command not found.");
            }
        } catch (Exception e) {
            System.out.println("Command not found.");
        }
    }
}
