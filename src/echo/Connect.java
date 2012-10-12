package echo;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connect extends Thread{
	
	private String ipadd;
	private int tcpport;
	public Connect (String ipaddr, String tcp){
		ipadd = ipaddr;
		tcpport = Integer.parseInt(tcp);
	    start();
	}
	public void run(){
		
		Socket echoSocket = null;
		try {
			echoSocket = new Socket(ipadd, tcpport);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Info: The connection between this machine and "+ipadd+" "+tcpport +" is successfully estabilshed");
	}
	
}
