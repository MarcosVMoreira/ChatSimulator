import java.net.*;
import java.util.Scanner;


import com.google.gson.Gson;

import java.io.*;

public class UDPClient {
    public static void main(String args[]) {
        // args fornece o conteúdo da mensagem e o nome de host do servidor
        DatagramSocket aSocket = null;
        
        User user = new User();
        Message message = new Message();
        
        Gson userJSON = new Gson();
        Gson messageJSON = new Gson();
        
        String userString;
        String messageString;

        Scanner s = new Scanner(System.in);
        
        String userName;
        
        while(true) {
        	try {
            	System.out.print("Loging in. Inser your username: ");
            	
            	
            	userName = s.nextLine();
            	
            	user.setUserName(userName);
            	
            	userString = userJSON.toJson(user);
            	
            	System.out.println("Meu json criado: "+userJSON);
            	
            	message.setMessageCode(100);

            	
            	
            	
                aSocket = new DatagramSocket();
                byte[] m = userName.getBytes();

                InetAddress aHost = InetAddress.getLocalHost();
                int serverPort = 6698;
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
                aSocket.send(request);

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