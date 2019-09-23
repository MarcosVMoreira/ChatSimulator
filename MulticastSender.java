

import java.net.*;
import java.io.*;

public class MulticastSender {
    public static void main(String args[]) {
        MulticastSocket s = null;
        try {
            InetAddress group = InetAddress.getByName("228.5.6.7");
            s = new MulticastSocket();
            s.joinGroup(group);
            String msg = new String("Marcos entrou na sala");
            DatagramPacket messageOut = new DatagramPacket(msg.getBytes(), msg.length(), group, 6789);
            s.send(messageOut);
            s.leaveGroup(group);
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
