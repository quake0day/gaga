package echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class Udpserver extends Thread{
	private static final int TIMEOUT = 3000; //Maximum size of echo datagram
	private static final int ECHOMAX = 255; //Maximum size of echo datagram
	private int port;
	private DatagramSocket socket = null;
	private DatagramSocket client = null;
	private DatagramPacket packet = null;
	 public Udpserver (int udpport)
	   {
		 this.port = udpport;
	    start();
	   }
	 public void run(){
		try {
				socket = new DatagramSocket(port);
				//threadPool.submit((new Udpserver(socket)));
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				System.out.println("You're using a port that cannot start UDP transmission. Program halt");
				System.exit(1);
			}
		packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
		 while(true){
			try {
				socket.receive(packet);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//System.out.println ("Runrunrun");
			String recData = new String(packet.getData());
			System.out.println("	echoing: "+recData);
			System.out.println("	To: IP = "+packet.getAddress().toString());
			System.out.println("	type:udp");
			try {
				socket.send(packet);
				packet.setLength(ECHOMAX);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    try {
				currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }
}
