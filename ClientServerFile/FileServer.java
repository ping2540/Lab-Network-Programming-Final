
import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.*;

public class FileServer implements Runnable {

     Socket s = null;

    public FileServer(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            InputStream in = s.getInputStream();
            OutputStream out = s.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            PrintWriter pw = new PrintWriter(out);
            int num = 0;
            String msg = null;
            String filename[] = new String[2];
            while ((msg = br.readLine()) != null) {
                filename[num++] = msg;
                if (num == 2) {
                    break;
                } else if (filename[0].equals("list")) {
                    break;
                }
            }
            if (filename[0].equals("upload")) {
                filename[1] = (new File(filename[1])).getName();
                File f = new File(filename[1]);
                pw.println("OK");
                pw.flush();

                FileOutputStream fout = new FileOutputStream(f);
                byte[] b = new byte[65536];
                int size;
                while ((size = in.read(b)) > 0) {
                    fout.write(b, 0, size);
                }
                fout.flush();
                fout.close();
                in.close();
            } else if (filename[0].equals("download")) {

                File f = new File(filename[1]);
                if (!f.exists()) {
                    pw.println("NOK");
                    pw.flush();
                } else {
                    pw.println("OK");
                    pw.flush();

                    FileInputStream fin = new FileInputStream(f);
                    byte[] buffer = new byte[65536];
                    int size;
                    while ((size = fin.read(buffer)) > 0) {
                        out.write(buffer, 0, size);
                    }
                    out.flush();
                    fin.close();
                }
            } else {
                File fx = new File("./");
                String[] filenamee = fx.list();
                for (int i = 0; i < filenamee.length; i++) {
                    pw.println(filenamee[i]);
                }
                pw.flush();
            }
            br.close();
            in.close();
            out.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServerSocket serv = null;
        ExecutorService es = Executors.newFixedThreadPool(10);
        try {
            serv = new ServerSocket(6789);
        } catch (Exception e) {
            System.exit(0);
        }
        while (true) {
            try {
                Socket s = serv.accept();
                FileServer st = new FileServer(s);
                es.execute(st);
            } catch (Exception e) {
            }
        }
    }
}
