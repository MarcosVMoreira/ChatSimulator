package Controller;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.google.gson.Gson;

public class PacketSender {

    private static String serverIP;
    private static int serverPort;
    private static DatagramSocket socket = null;

    public PacketSender(String serverIP, int serverPort, DatagramSocket socket) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.socket = socket;
    }

    public static void sendJson(String JSONInfo) throws UnknownHostException {

        InetAddress inetAdress = InetAddress.getByName(serverIP);

        Gson gson = new Gson();

        try {

            byte[] m = JSONInfo.getBytes();

            DatagramPacket request = new DatagramPacket(m, m.length, inetAdress, serverPort);

            System.out.println("Sending packet to IP: " + inetAdress + " port: " + serverPort);

            socket.send(request);

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            System.out.println("PacketSender finally");

        }

    }

}
