

import java.net.*;
import java.io.*;

public class MulticastReceiver {
    public static void main(String args[]) {
        MulticastSocket s = null;
        try {
            InetAddress group = InetAddress.getByName("228.5.6.7");
            s = new MulticastSocket(6789);
            s.joinGroup(group);
            byte[] buffer = new byte[1000];
            DatagramPacket messageIn = new DatagramPacket(buffer, buffer.length);
            while(true) { // obtém mensagens de outros participantes do grupo
                s.receive(messageIn);
                System.out.println(":)" + new String(messageIn.getData(),0,messageIn.getLength()));
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (s != null)
                s.close();
        }
    }
}

