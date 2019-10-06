package Controller;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;

import Model.Message;
import Model.MessageControl;

import java.io.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class UDPClient {

    private static PacketSender packetSender;

    private static String serverIP, userName, messageLog = "";

    private static int serverPort = 6698;

    private static DatagramSocket aSocket;

    private static ChangeListenerUDP listener;

    private static ArrayList<String> onlineUsers;

    public UDPClient() throws SocketException, UnknownHostException {

        aSocket = new DatagramSocket();

        serverIP = InetAddress.getLocalHost().getHostAddress();

        packetSender = new PacketSender(serverIP, serverPort, aSocket);
    }

    public static void main(String args[]) throws UnknownHostException, SocketException {

        Scanner s = new Scanner(System.in);

        String aux;

        listenForNewMessages();

        while (true) {

            System.out.println("1 - Login\n2 - Logout\n3 - Retrieve online users\n4 - Send text");
            System.out.print("Insert your action: ");

            aux = s.next();

            switch (aux) {
                case "1":
                    System.out.println("Loging in. Insert your nickname: ");
                    userName = s.next();
                    //logIn(userName);
                    break;
                case "2":
                    logOut();
                    break;
                case "3":
                    retrieveOnlineUsers();
                    break;
                case "4":
                    System.out.println("Sending a message. Insert target username: ");
                    String targetUsername = s.next();
                    System.out.println("Now, insert the message: ");
                    String messageText = s.next();
                    sendText(messageText, userName, targetUsername);
                    break;
                default:
                    System.out.println("Invalid option.");
            }

        }

    }

    public void logIn(String userName) throws UnknownHostException {

        MessageControl messageControl = new MessageControl();

        Gson gson = new Gson();

        messageControl = new MessageControl(100, userName);

        System.out.println("Login JSON: " + gson.toJson(messageControl));

        packetSender.sendJson(gson.toJson(messageControl));

    }

    public static void logOut() throws UnknownHostException {

        MessageControl messageControl = new MessageControl();

        Gson gson = new Gson();

        messageControl.setMessageCode(200);

        System.out.println("Logout JSON: " + messageControl);

        packetSender.sendJson(gson.toJson(messageControl));

    }

    public static void retrieveOnlineUsers() throws UnknownHostException {

        MessageControl messageControl = new MessageControl();

        Gson gson = new Gson();

        messageControl.setMessageCode(300);

        System.out.println("Waiting online user list from server...");

        packetSender.sendJson(gson.toJson(messageControl));

    }

    public static void sendText(String text, String sourceUsername, String targetUsername) throws UnknownHostException {

        Message message = new Message();

        Gson gson = new Gson();

        message.setMessageCode(400);

        message.setMessageText(text);

        message.setMessageRecipient(targetUsername);

        message.setMessageSource(sourceUsername);

        System.out.println("Login JSON: " + gson.toJson(message));

        packetSender.sendJson(gson.toJson(message));

    }

    public static void checkForNewReceivedMessages() {

        String receivedString;

        byte[] buffer = new byte[1000];
        DatagramPacket receive = new DatagramPacket(buffer, buffer.length);

        try {
            aSocket.receive(receive);

            receivedString = new String(buffer, 0, receive.getLength());

            processReceivedMessage(receivedString);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void processReceivedMessage(String receivedString) {

        Gson gson = new Gson();

        Message messageReceived = gson.fromJson(receivedString, Message.class);

        // online users
        if (messageReceived.getMessageCode() == 300) {

            String plainString;

            plainString = messageReceived.getMessageText().replace("[", "").replace("]", "").replace(" ", "");

            onlineUsers = new ArrayList<String>(Arrays.asList(plainString.split(",")));

            System.out.println("\nOnline users:");
            for (String string : onlineUsers) {
                System.out.println(string.replace("\"", ""));
            }

            if (listener != null) {
                listener.onChangeHappened();
            }

        } else if (messageReceived.getMessageCode() == 400) {

            messageLog
                    = messageLog.concat("\n" + messageReceived.getMessageSource() + ": " + messageReceived.getMessageText());

            if (listener != null) {
                listener.onChangeHappened();
            }
        }

    }

    public static void listenForNewMessages() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    checkForNewReceivedMessages();
                }
            }
        }.start();
    }

    public void setChangeListener(ChangeListenerUDP listener) {
        this.listener = listener;
    }

    public String getMessageLog() {
        return messageLog;
    }

    public void concatMessageLog(String aMessageLog) {
        messageLog = messageLog.concat(aMessageLog);
    }

    public interface ChangeListenerUDP {

        public void onChangeHappened();

    }

    public ArrayList<String> getOnlineUsers() {
        return onlineUsers;
    }
}
