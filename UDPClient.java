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
	
	private static String userName;
	
    public static void main(String args[]) throws UnknownHostException, SocketException {

        aSocket = new DatagramSocket();
        
        serverIP = InetAddress.getLocalHost().getHostAddress();
        
    	packetSender = new PacketSender(serverIP, serverPort, aSocket);

        Scanner s = new Scanner(System.in);
        
        String aux;

		 new Thread() {
	        @Override
	        public void run() {
	          
	        	while (true) {
	        		try {
						currentThread().sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		
	        		byte[] buffer = new byte[1000];
	                DatagramPacket receive = new DatagramPacket(buffer, buffer.length);

	                try {
						aSocket.receive(receive);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

	                System.out.println("Reply: " + new String(receive.getData()));
	                
	        	}
	           
	        }
	      }.start();
        
        while(true) {
        		
                System.out.println("1 - Login\n2 - Logout\n3 - Retrieve online users\n4 - Send text");
                System.out.print("Insert your action: ");
                
                aux = s.next();
                
                switch (aux) {
	                case "1": 
	                	System.out.println("Loging in. Insert your nickname: ");
	                	userName = s.next();
	                	logIn(userName);
	                	break;
	                case "2": 
	                	logOut();
	                	break;
	                case "3": 
	                	retrieveOnlineUsers();
	                	break;
	                case "4": 
	                	System.out.println("Sending a mensage. Insert target username: ");
	                	String targetUsername = s.next();
	                	System.out.println("Now, insert the mensage: ");
	                	String mensageText = s.next();
	                	sendText(mensageText, userName, targetUsername);
	                	break;
	                default:
	                	System.out.println("Invalid option.");
                }

        }

    }
    
    public static void logIn (String userName) throws UnknownHostException {
  
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
    
    public static void sendText (String text, String sourceUsername, String targetUsername) throws UnknownHostException {

    	Message message = new Message();

    	Gson gson = new Gson();
    	
    	message.setMessageCode(400);
    	
    	message.setMessageText(text);
    	
    	message.setMessageRecipient(targetUsername);
    	
    	message.setMessageSource(sourceUsername);
    	
    	System.out.println("Login JSON: "+gson.toJson(message));
    	
    	packetSender.sendJson(gson.toJson(message));
    	
    }
    
}