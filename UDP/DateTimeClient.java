import java.net.*;

public class DateTimeClient {

    public static void main(String[] args) {
         try {
            byte[] sendBuffer;
            byte[] recvBuffer = new byte[512];
            InetAddress ia = InetAddress.getByName("127.0.0.1");

            DatagramSocket socket = new DatagramSocket();
            String msg = "request Datetime";
            sendBuffer = msg.getBytes();

            DatagramPacket sendDP = new DatagramPacket(sendBuffer, sendBuffer.length, ia, 9876);
            socket.send(sendDP);
            
            DatagramPacket recvDP = new DatagramPacket(recvBuffer, recvBuffer.length);
            socket.receive(recvDP);
            
            String s = new String(recvDP.getData(),0,recvDP.getLength());
            System.out.println(s);
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
