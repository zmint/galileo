package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Net {
	
	private Net(){
		// private constructor, so you cannot create an instance of it
	}

	public static void sendACK() {
		// System.out.println("sendACK currently unimplemented");
	}

	public static void sendRobotCmd(String IP, int PORT, int command) {
		byte[] data = new byte[1];
		data[0] = (byte) command;
		send(IP, PORT, data);
	}

	public static void send(String IP, int PORT, byte[] sendData) {
		// check if data part is too big
		if (sendData.length > NetSettings.getBufferSize()) {
			System.err.println("Couldn't send packet, cause it exceeds "
					+ "the maximum of " + NetSettings.getBufferSize());
		}

		try {
			// Prepare network
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(IP);

			// create and send packet
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, PORT);
			clientSocket.send(sendPacket);

			clientSocket.close();
		} catch (SocketException e) {
			System.err.println("Couldn't initialize DatagramSocket. "
					+ e.getMessage());
		} catch (UnknownHostException e) {
			System.err.println("Couldn't send packet to " + IP + ":" + PORT
					+ " command: " + sendData + " " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Couldn't send packet to " + IP + ":" + PORT
					+ " command: " + sendData + " " + e.getMessage());
		}
	}

	public static byte[] receive(int PORT) {
		byte[] receiveData = new byte[NetSettings.getBufferSize()];

		try {
			DatagramSocket serverSocket = new DatagramSocket(PORT);
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			serverSocket.receive(receivePacket);
			receiveData = receivePacket.getData();

			serverSocket.close();
		} catch (SocketException e) {
			System.err.println("Couldn't initialize DatagramSocket. "
					+ e.getMessage());
		} catch (IOException e) {
			System.err.println("Error receiving packet on port " + PORT + ". "
					+ e.getMessage());
		}
		
		return receiveData;
	}
}
