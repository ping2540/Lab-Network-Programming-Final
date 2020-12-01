
import java.io.*;
import java.net.*;

public class FileClient {

    static InputStream in = null;
    static OutputStream out = null;
    static BufferedReader br = null;
    static PrintWriter pw = null;
    static Socket s = null;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("command not found");
            System.exit(0);
        } else if (args.length == 1) {
            if (!args[0].toLowerCase().equals("list")) {
                System.out.println("command not found");
                System.exit(0);
            }
        } else if (args.length != 2) {
            System.out.println("error");
            System.exit(0);
        }
        if (args[0].toLowerCase().equals("upload")) {
            File f = new File(args[1]);
            if (!f.exists()) {
                System.out.println(args[1] + " not found");
                System.exit(0);
            }
            try {
                s = new Socket("127.0.0.1", 6789);
                in = s.getInputStream();
                out = s.getOutputStream();
                br = new BufferedReader(new InputStreamReader(in));
                pw = new PrintWriter(out);
                pw.println(args[0].toLowerCase());
                pw.println(args[1]);
                pw.flush();
                String response = br.readLine();
                if (response.equals("OK")) {
                    FileInputStream fin = new FileInputStream(f);
                    byte[] buffer = new byte[65536];
                    int size;
                    while ((size = fin.read(buffer)) > 0) {
                        out.write(buffer, 0, size);
                    }
                    out.flush();
                    fin.close();
                }
                in.close();
                out.close();
                s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (args[0].toLowerCase().equals("download")) {
            try {
                s = new Socket("127.0.0.1", 6789);

                in = s.getInputStream();
                out = s.getOutputStream();

                br = new BufferedReader(new InputStreamReader(in));
                pw = new PrintWriter(out);

                pw.println(args[0].toLowerCase());
                pw.println(args[1]);
                pw.flush();

                String response = br.readLine();
                if (response.equals("OK")) {
                    File f = new File((new File(args[1])).getName());
                    FileOutputStream fout = new FileOutputStream(f);

                    byte[] b = new byte[65536];
                    int size;
                    while ((size = in.read(b)) > 0) {
                        fout.write(b, 0, size);
                    }
                    fout.flush();
                    fout.close();
                } else {
                    System.out.println(args[1] + " No file found on server");
                }
                in.close();
                out.close();
                s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (args[0].toLowerCase().equals("list")) {
            try {
                s = new Socket("127.0.0.1", 6789);
                in = s.getInputStream();
                out = s.getOutputStream();
                br = new BufferedReader(new InputStreamReader(in));
                pw = new PrintWriter(out);
                pw.println(args[0].toLowerCase());
                pw.flush();
                String filename;
                while ((filename = br.readLine()) != null) {
                    System.out.println(filename);
                }
                in.close();
                out.close();
                s.close();
            } catch (Exception e) {
            }
        } else {
            System.out.println("command not found");
        }
    }
}
