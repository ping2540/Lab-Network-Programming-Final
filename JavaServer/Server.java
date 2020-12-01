
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    Socket s = null;
    static volatile HashMap<String, String> db = new HashMap<String, String>();

    public Server(Socket s) {
        this.s = s;
    }

    public void run() {
        try {
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            PrintWriter pw = new PrintWriter(out);
            String msg;
            if (br.readLine().equals("add")) {
                int count = 0;
                String[] filename = new String[2];
                while ((msg = br.readLine()) != null) {
                    if (msg.equals("OK")) {
                        break;
                    }
                    filename[count++] = msg;
                }
                db.put(filename[0], filename[1]);
                pw.println("OK");
            } else {
                msg = br.readLine();
                if (db.containsKey(msg)) {
                    pw.println(db.get(msg));
                } else {
                    pw.println("N/A");
                }
            }
            pw.flush();
            br.close();
            in.close();
            pw.close();
            out.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerSocket serv = null;
        ExecutorService es = Executors.newFixedThreadPool(100);
        while (true) {
            try {
                serv = new ServerSocket(23410);
                Socket s = serv.accept();
                Server st = new Server(s);
                es.execute(st);
            } catch (Exception e) {
            }
        }
    }
}
