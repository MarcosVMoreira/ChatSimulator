import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import com.google.gson.Gson;

public class PacketSender {

	private static InetAddress serverIP;
	private static int serverPort;

	public PacketSender(InetAddress serverIP, int serverPort) {
		this.serverIP = serverIP;
		this.serverPort = serverPort;
	}

	public static void sendJson(String JSONInfo) {

		DatagramSocket socket = null;
		Gson gson = new Gson();

		try {

			socket = new DatagramSocket();

			byte[] m = JSONInfo.getBytes();

			DatagramPacket request = new DatagramPacket(m, m.length, serverIP, serverPort);

			System.out.println("Sending packet to IP: " + serverIP.getHostAddress() + " port: " + serverPort);
			

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
