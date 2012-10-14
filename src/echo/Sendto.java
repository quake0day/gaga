package echo;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Sendto extends Thread{
	private static final int TIMEOUT = 3000; // Resend timeout (ms)
	private static final int MAXTRIES = 5; // maximum retransmisson tries.
	private String ipaddr;
	private int udpport;
	private String message;
	DatagramSocket socket = null;
	DatagramPacket sendPacket =null;
	DatagramPacket receivePacket = null;
	InetAddress serverAddress = null;
	public Sendto(String ipaddr, int udpport, String message) throws SocketException{
		this.ipaddr = ipaddr;
		this.udpport = udpport;
		this.message = message;
		byte[] bytesToSend = message.getBytes();
		try {
			serverAddress = InetAddress.getByName(ipaddr);
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socket.setSoTimeout(TIMEOUT);
		sendPacket = new DatagramPacket(bytesToSend,bytesToSend.length,serverAddress,udpport);
		
		System.out.println("in sendto");
		run();
	}
	public void run(){
		boolean receivedResponse = false;
		int tries=0;
		//do{
			try {
				socket.send(sendPacket); // send the udp string
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		/*	
			try {
				socket.receive(receivePacket);
				if(!receivePacket.getAddress().equals(serverAddress)){
					throw new IOException("Received packet from an unknown source");
				}
				receivedResponse = true;
			} catch(InterruptedIOException e){
				tries ++;
				System.out.println("Timed out..."+(MAXTRIES - tries ) + " more tries...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		//}while((!receivedResponse) && (tries < MAXTRIES));
		socket.close();
	}

}
