package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connect extends Thread{
	
	private String ipadd;
	private int tcpport;
	public int maxsize = 50;
	private Socket echoSocket = null;
	echoer echo;
	public Connect (String ipaddr, String tcp,echoer echo) throws IOException{
		ipadd = ipaddr;
		tcpport = Integer.parseInt(tcp);
		this.echo = echo;
        ExecutorService threadPool = Executors.newFixedThreadPool(maxsize);
        boolean connectable = true;
        //System.out.println(ipadd.toString().equals("127.0.0.1"));
        InetAddress addr = null;
        String localip = null;
        addr = InetAddress.getLocalHost();
        localip = addr.getHostAddress().toString();
        if(ipadd.toString().equals("127.0.0.1") || ipadd.toString().equals("localhost") || ipadd.toString().equals(localip)){
        	connectable = false;
        }
        // judge hostname
		try {
			InetAddress ip_connectable = InetAddress.getByName(ipadd);					
		} catch (UnknownHostException e1) {
			System.out.println("Enter valid host name");
			connectable = false;
		}
		
		Iterator<Socket> iter = echo.clients.iterator();
		//  duplicate?
		while(iter.hasNext()){
			if(ipadd.equals(iter.next().getInetAddress().toString().split("/")[1])){
				connectable = false;
			}
		}
        if(connectable){
		try {
			//echoSocket = new Socket(ipadd, tcpport);
			threadPool.submit(new Connect(new Socket(ipadd,tcpport)));
			System.out.println("Info: The connection between this machine and "+ipadd+" "+tcpport +" is successfully estabilshed");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("cannot connect to this Host. Connection refused");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("cannot connect to this Host. Connection refused");
		}
        }
        else{
			System.out.println("Cannot connect to itself and it may have duplicate connection.");
		}

	    
	}
	public Connect(Socket socket) {
		// TODO Auto-generated constructor stub
		echoSocket = socket;
		echo.clients.add(socket);	
		start();
	}

	public void run(){
        PrintWriter out = null;
        BufferedReader in = null;
        String inputLine;
        
		
		try {
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
			try {
				while((inputLine = in.readLine()) != null){
					System.out.println("Server:"+inputLine);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				break;
			}
			
		}
		
	}
	
}
