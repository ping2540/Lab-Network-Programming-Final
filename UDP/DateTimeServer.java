
import java.net.*;
import java.util.*;

public class DateTimeServer {

    public static void main(String[] args) {

        try {
            DatagramSocket socket = new DatagramSocket(9876);
            while (true) {
                byte[] recvBuffer = new byte[8000];

                DatagramPacket dp = new DatagramPacket(recvBuffer, recvBuffer.length);
                socket.receive(dp);
                
                dp.setData(((new Date()).toString()).getBytes() , 0,(((new Date()).toString()).getBytes()) .length);
                DatagramPacket dp2 = new DatagramPacket(dp.getData(), dp.getLength(), dp.getAddress(), dp.getPort());
                socket.send(dp2);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
