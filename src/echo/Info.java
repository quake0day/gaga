package echo;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Info extends Thread{
	private String udpport="999";
	private String tcpport;
	 public Info (String udp, String tcp)
	   {
		 udpport = udp;
		 tcpport = tcp;
	    start();
	   }
	 public void run(){

		 System.out.println ("IP address	hostname				udp port		tcp port");
	     InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	    System.out.print(addr.getHostAddress()+"	");
		System.out.print(addr.getHostName()+"		");
		System.out.print(udpport+"			");
		System.out.println(tcpport);
		    try {
				currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	 }
}