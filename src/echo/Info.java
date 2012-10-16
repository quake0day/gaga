package echo;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.xml.stream.events.Attribute;

public class Info extends Thread{
	private int udpport;
	private int tcpport;
	 public Info (int udp, int tcp)
	   {
		 udpport = udp;
		 tcpport = tcp;
	    start();
	   }
	 


	 
	 public void run(){
	     InetAddress addr = null;
	     boolean ipable = false;
	     String ipaddr = null;
	     // Get local IP address throught 8.8.8.8
		 try{
				InetAddress IP = null;
				try {
					DatagramSocket udpsocket = null;
					udpsocket = new DatagramSocket();
					udpsocket.connect(InetAddress.getByName("8.8.8.8"), 53); // Using public DNS google
					IP = udpsocket.getLocalAddress();
					udpsocket.close();
				} catch (SocketException e1) {
					System.out.println("cannot get IP address through 8.8.8.8");
				}
				catch (UnknownHostException e1) {
					System.out.println("cannot get IP address through 8.8.8.8");
				}
				ipaddr = IP.toString();
			 ipable = true;
		 } catch(Exception e){
			 ipable = false;
			 //System.out.println("No Internet connection and cannot link to public DNS 8.8.8.8. Using local IP address instead.");
		 }
		try {
			
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 


		 System.out.println ("IP address	hostname				udp port		tcp port");
		 if(ipable == true){
			 System.out.print(ipaddr+"	");
		 }else{
			 System.out.print(addr.getHostAddress()+"	");
		 }
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