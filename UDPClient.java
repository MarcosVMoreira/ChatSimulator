import java.net.*;
import java.util.Scanner;
import java.io.*;

public class UDPClient {
    public static void main(String args[]) {
        // args fornece o conteúdo da mensagem e o nome de host do servidor
        DatagramSocket aSocket = null;

        Scanner s = new Scanner(System.in);
        
        String msg;
        
        while(true) {
        	try {
            	System.out.print("Mensagem: ");
            	
            	msg = s.nextLine();
                aSocket = new DatagramSocket();
                byte[] m = msg.getBytes();
                //InetAddress aHost = InetAddress.getByName("172.16.104.89");
                InetAddress aHost = InetAddress.getLocalHost();
                int serverPort = 6698;
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                aSocket.send(request);
                //aSocket = new DatagramSocket(6545);
                byte[] buffer = new byte[1000];
                DatagramPacket receive = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(receive);
                System.out.println("Reply: " + new String(receive.getData()));
            } catch (SocketException e) {
                System.out.println("Socket: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            } finally {
                if (aSocket != null)
                    aSocket.close();
            }
        }
        
    }
}
