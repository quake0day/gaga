package echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class Justtest extends Thread{
	private static final int TIMEOUT = 3000; //Maximum size of echo datagram
	private static final int ECHOMAX = 255; //Maximum size of echo datagram
	private int port;
	private DatagramSocket socket = null;
	private DatagramSocket client = null;
	private DatagramPacket packet = null;
	 public Justtest ()
	   {
	    System.out.println ("New Communication Thread Started");

	    start();
	   }
	 public void run(){
		try {
				socket = new DatagramSocket(10030);
				//threadPool.submit((new Udpserver(socket)));
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			System.out.println("echoing "+recData);
			System.out.println("to:"+packet.getAddress());
			System.out.println("tyep:udp");
		    try {
				currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }
}
