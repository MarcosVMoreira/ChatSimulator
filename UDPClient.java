import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;


import com.google.gson.Gson;

import java.io.*;

public class UDPClient {
	
	private static InetAddress serverIP;
	private static int serverPort = 6698;
	
    public static void main(String args[]) {
        // args fornece o conteúdo da mensagem e o nome de host do servidor

        DatagramSocket aSocket = null;
        
        Scanner s = new Scanner(System.in);
        
        String aux;

        
        while(true) {
        	try {
        		
        		serverIP = InetAddress.getLocalHost();
        		
        		aSocket = new DatagramSocket();
            	
                System.out.println("Loging in. Inser your nickname: ");
                
                aux = s.next();
                
                sendText("testamndo", "");

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
    
    public static void logIn (String userName) {
    	
    	User user = new User();
    	Message message = new Message();

    	Gson gson = new Gson();

    	user.setUserName(userName);

    	message.setMessageCode(100);
    	
    	message.setMessageText(gson.toJson(user));
    	
    	System.out.println("Login JSON: "+gson.toJson(user));
    	
    	sendJson(gson.toJson(message));
    	
    }
    
    public static void logOut () {

    	Message message = new Message();

    	Gson gson = new Gson();

    	message.setMessageCode(200);
    	
    	System.out.println("Logout JSON: "+message);
    	
    	sendJson(gson.toJson(message));
    	
    }
    
    public static void retrieveOnlineUsers () {

    	Message message = new Message();

    	Gson gson = new Gson();

    	message.setMessageCode(300);
    	
    	System.out.println("Waiting online user list from server...");
    	
    	sendJson(gson.toJson(message));
    	
    }
    
    public static void sendText (String text, String target) throws UnknownHostException {

    	Message message = new Message();

    	Gson gson = new Gson();

    	InetAddress inetTarget = InetAddress.getByName(target);
    	
    	message.setMessageCode(400);
    	
    	message.setMessageText(text);
    	
    	message.setMessageRecipient(inetTarget);
    	
    	System.out.println("Login JSON: "+gson.toJson(message));
    	
    	sendJson(gson.toJson(message));
    	
    }
    
    public static void sendJson (String JSONInfo) {
    	
    	DatagramSocket socket = null;
    	Gson gson = new Gson();
    	
    	try {
    		
			socket = new DatagramSocket();
			
			byte[] m = JSONInfo.getBytes();

	        DatagramPacket request = new DatagramPacket(m, m.length, serverIP, serverPort);
	        
	        System.out.println("Sending packet to IP: "+serverIP.getHostAddress()+" port: "+serverPort);
	        
	        socket.send(request);

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 socket.close();
		}

    }
    
}