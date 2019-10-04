import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

import java.io.*;

public class UDPClient {
	
	private static PacketSender packetSender;
	
	private static String serverIP;
	
	private static int serverPort = 6698;

	private static DatagramSocket aSocket;
	
    public static void main(String args[]) throws UnknownHostException, SocketException {
        // args fornece o conteúdo da mensagem e o nome de host do servidor

        aSocket = new DatagramSocket();
        
        serverIP = InetAddress.getLocalHost().getHostAddress();
        
        System.out.println("Porta enviada: "+aSocket.getLocalPort()+" Port: "+aSocket.getPort());
        
        
        
        Scanner s = new Scanner(System.in);
        
        String aux;

        
        while(true) {
        	try {
        		
        		
            	
                System.out.println("Loging in. Inser your nickname: ");
                
                aux = s.next();
                
                logIn(aux);
                
                logIn("Jose");
                
                logIn("Maria");
                
                retrieveOnlineUsers();
                
                /*logOut();
                
                retrieveOnlineUsers();
                
                sendText("texto a ser enviado", "Usuario alvo");*/

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
    
    public static void logIn (String userName) throws UnknownHostException {
    	
    	packetSender = new PacketSender(serverIP, serverPort, aSocket);
    	
    	MessageControl messageControl = new MessageControl();

    	Gson gson = new Gson();
    	
    	messageControl = new MessageControl(100, userName);
    	
    	System.out.println("Login JSON: "+gson.toJson(messageControl));
    	
    	packetSender.sendJson(gson.toJson(messageControl));
    	
    }
    
    public static void logOut () throws UnknownHostException {

    	MessageControl messageControl = new MessageControl();

    	Gson gson = new Gson();

    	messageControl.setMessageCode(200);
    	
    	System.out.println("Logout JSON: "+messageControl);
    	
    	packetSender.sendJson(gson.toJson(messageControl));
    	
    }
    
    public static void retrieveOnlineUsers () throws UnknownHostException {

    	MessageControl messageControl = new MessageControl();

    	Gson gson = new Gson();

    	messageControl.setMessageCode(300);
    	
    	System.out.println("Waiting online user list from server...");
    	
    	packetSender.sendJson(gson.toJson(messageControl));
    	
    }
    
    public static void sendText (String text, String targetUsername) throws UnknownHostException {

    	Message message = new Message();

    	Gson gson = new Gson();
    	
    	message.setMessageCode(400);
    	
    	message.setMessageText(text);
    	
    	message.setMessageRecipient(targetUsername);
    	
    	System.out.println("Login JSON: "+gson.toJson(message));
    	
    	packetSender.sendJson(gson.toJson(message));
    	
    }
    
    
    
}