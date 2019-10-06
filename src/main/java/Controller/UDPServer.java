package Controller;

import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import Model.Message;
import Model.MessageControl;
import Model.User;

import java.io.*;

public class UDPServer {

    private static ArrayList<User> userList;

    private static ArrayList<String> nameList;

    private static PacketSender packetSender;

    private static DatagramSocket aSocket = null;

    public static void main(String args[]) throws UnknownHostException {

        userList = new ArrayList<>();

        nameList = new ArrayList<>();

        messageReceiver();

    }

    public static void processReceivedMessage(String receivedString, InetAddress sourceIP, int sourcePort)
            throws UnknownHostException {

        Gson gson = new Gson();

        Message messageReceived = gson.fromJson(receivedString, Message.class);

        // login
        if (messageReceived.getMessageCode() == 100) {

            User user = new User();

            user.setUserName(messageReceived.getMessageText());

            user.setUserIP(sourceIP);

            user.setUserPort(sourcePort);

            userList.add(user);

            nameList.add(messageReceived.getMessageText());

            System.out.println(user.getUserName() + " is online.");

            // logout
        } else if (messageReceived.getMessageCode() == 200) {

            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserIP().equals(sourceIP)) {
                    System.out.println(userList.get(i).getUserName() + " is offline.");
                    nameList.remove(userList.get(i).getUserName());
                    userList.remove(i);
                }
            }

            // online users
        } else if (messageReceived.getMessageCode() == 300) {

            packetSender = new PacketSender(sourceIP.getHostAddress(), sourcePort, aSocket);

            MessageControl messageControl = new MessageControl();

            messageControl.setMessageCode(300);

            messageControl.setMessageText(gson.toJson(nameList));

            packetSender.sendJson(gson.toJson(messageControl));

        } else if (messageReceived.getMessageCode() == 400) {

            Message message = new Message();

            String recipientName = messageReceived.getMessageRecipient();

            String[] recipientIpAndPort = findIPAndPortByUsername(recipientName);

            if (recipientIpAndPort != null) {
                packetSender = new PacketSender(recipientIpAndPort[0], Integer.parseInt(recipientIpAndPort[1]), aSocket);

                message.setMessageCode(400);

                message.setMessageText(messageReceived.getMessageText());

                message.setMessageSource(messageReceived.getMessageSource());

                message.setMessageRecipient(recipientName);

                packetSender.sendJson(gson.toJson(message));
            } else {
                System.out.println("Recipient user not found.");
            }

        } else {
            System.out.println("ERROR: invalid messageCode.");
        }

    }

    public static String findUsernameByIP(String userIP) {

        for (User user : userList) {
            if (user.getUserIP().equals(userIP)) {
                return user.getUserName();
            }
        }

        return null;
    }

    public static String[] findIPAndPortByUsername(String userName) {

        String[] ipAndPort = new String[2];

        for (User user : userList) {
            if (user.getUserName().equals(userName)) {
                ipAndPort[0] = user.getUserIP().getHostAddress();
                ipAndPort[1] = Integer.toString(user.getUserPort());
                return ipAndPort;
            }
        }

        return null;
    }

    public static void messageReceiver() {

        String receivedString;

        try {
            aSocket = new DatagramSocket(6698);

            byte[] buffer = new byte[1000];
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                receivedString = new String(buffer, 0, request.getLength());

                processReceivedMessage(receivedString, request.getAddress(), request.getPort());

            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {

            if (aSocket != null) {
                System.out.println("Closing UDPServer socket...");
                aSocket.close();
            }
        }
    }

}
