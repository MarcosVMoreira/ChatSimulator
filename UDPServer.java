import java.net.*;
import java.util.LinkedList;


import java.io.*;
public class UDPServer {
    public static void main(String args[]) {
    	
    	LinkedList<User> users = new LinkedList<>();
    	
    	String receivedString;
    	
    	
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(6698);
            byte[] buffer = new byte[1000];
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                
                receivedString = new String(request.getData());
                
                
                
                
                System.out.println("Mensagem recebida: "+receivedString);
                
                System.out.println("Porta: "+request.getSocketAddress());
                
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }
}