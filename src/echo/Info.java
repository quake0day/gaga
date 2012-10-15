package echo;

import java.net.InetAddress;
import java.net.Socket;
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
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		 try{
			 Hashtable env = new Hashtable();
			 env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
			 env.put("java.naming.provider.url",    "dns://8.8.8.8");
			 DirContext ictx = new InitialDirContext(env);
			 Attributes attrs1 = ictx.getAttributes(addr.getHostName(), new String[] {"A"});
			// Attributes attrs2 = ictx.getAttributes("host2", new String[] {"A"});
			 ipaddr = attrs1.toString().split(": ")[1];
			 ipaddr = ipaddr.split("}")[0];
			 System.out.println(ipaddr);
			 ipable = true;
		 } catch(Exception e){
			 ipable = false;
			 //System.out.println("No Internet connection and cannot link to public DNS 8.8.8.8. Using local IP address instead.");
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