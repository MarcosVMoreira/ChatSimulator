import java.net.*;
import java.util.LinkedList;

import org.json.JSONObject;

import java.io.*;
public class UDPServer {
    public static void main(String args[]) {
    	
    	LinkedList<Usuario> usuarios = new LinkedList<>();
    	
    	String mensagemRecebida;
    	
    	JSONObject objetoRecebido;
    	
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(6698);
            byte[] buffer = new byte[1000];
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                /*DatagramPacket reply = new DatagramPacket(request.getData(),
                    request.getLength(), request.getAddress(), request.getPort());
                aSocket.send(reply);*/
                
                mensagemRecebida = new String(request.getData());

                
               // objetoRecebido = new JSONObject(mensagemRecebida);
                
                System.out.println("Mensagem recebida: "+mensagemRecebida);
                
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
