import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import java.io.*;

public class UDPServer {
	
	private static ArrayList<User> userList;
	
	private static ArrayList<String> nameList;
	
	private static PacketSender packetSender;
	
    public static void main(String args[]) throws UnknownHostException {
    	
    	

    	String receivedString;
    	
    	userList = new ArrayList<>();
    	
    	nameList = new ArrayList<>();
    	
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(6698);
            byte[] buffer = new byte[1000];
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                receivedString = new String(buffer, 0, request.getLength());

                System.out.println("Mensagem recebida: "+receivedString);

                processReceivedMessage(receivedString, request.getAddress(), request.getPort());
                
                System.out.println("Porta: "+request.getPort()+" IP: "+request.getAddress().getHostAddress());
                
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }
    
    public static void processReceivedMessage (String receivedString, InetAddress sourceIP, int sourcePort) throws UnknownHostException {
    	
    	Gson gson = new Gson();
    	
    	Message message = gson.fromJson(receivedString, Message.class);
    	
    	

    	//login
    	if (message.getMessageCode() == 100) {
    		
    		User user = new User();
    		
    		user.setUserName(message.getMessageText());
    		
    		user.setUserIP(sourceIP);
    		
    		user.setUserPort(sourcePort);
    		
    		userList.add(user);
    		
    		nameList.add(message.getMessageText());
    		
    		System.out.println(user.getUserName() + " ficou online.");
    		
    	//logout	
    	} else if (message.getMessageCode() == 200) {
    		
			for (int i = 0; i < userList.size(); i++) {
		        if(userList.get(i).getUserIP().equals(sourceIP)) {
		        	System.out.println(userList.get(i).getUserName() + " ficou offline.");
		        	nameList.remove(userList.get(i).getUserName());
		            userList.remove(i);
		        }
		    }
			
		//online users
    	} else if (message.getMessageCode() == 300) {
    		
    		packetSender = new PacketSender(sourceIP, sourcePort);
    		
    		MessageControl messageControl = new MessageControl();
    		
    		messageControl.setMessageCode(300);
    		
    		messageControl.setMessageText(nameList.toString());
    		
    		packetSender.sendJson(gson.toJson(messageControl));
    		
    	} else if (message.getMessageCode() == 400) {
    		
    		MessageControl messageControl = gson.fromJson(receivedString, MessageControl.class);
    		
    	} else {
    		System.out.println("ERRO: Código não reconhecido.");
    	}
    	
    	
    	
    	
    }
    
    
}