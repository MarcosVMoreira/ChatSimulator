import java.net.*;
import java.util.Scanner;


import com.google.gson.Gson;

import java.io.*;

public class UDPClient {
	
	
	
    public static void main(String args[]) {
        // args fornece o conteúdo da mensagem e o nome de host do servidor
        DatagramSocket aSocket = null;
        
        Scanner s = new Scanner(System.in);
        
        String aux;

        
        while(true) {
        	try {
        		
        		aSocket = new DatagramSocket();
            	
                System.out.println("Loging in. Inser your nickname: ");
                
                aux = s.next();
                
                InetAddress aHost = InetAddress.getLocalHost();
                
                logIn(aux, aHost, 6698);

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
    
    public static void logIn (String userName, InetAddress host, int serverPort) {
    	
    	User user = new User();
    	Message message = new Message();

    	Gson gson = new Gson();

    	user.setUserName(userName);

    	message.setMessageCode(100);
    	
    	message.setMessageText(gson.toJson(user));
    	
    	System.out.println("Login JSON: "+gson.toJson(user));
    	
    	sendJson(gson.toJson(message), host, serverPort);
    	
    }
    
    
    
    public static void sendJson (String JSONInfo, InetAddress host, int serverPort) {
    	
    	DatagramSocket aSocket = null;
    	Gson gson = new Gson();
    	
    	try {
			aSocket = new DatagramSocket();
			
			byte[] m = JSONInfo.getBytes();

	        DatagramPacket request = new DatagramPacket(m, m.length, host, serverPort);
	        
	        System.out.println("Sending packet to IP: "+host.getHostAddress()+" port: "+serverPort);
	        
	        aSocket.send(request);

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 aSocket.close();
		}

    }
    
}