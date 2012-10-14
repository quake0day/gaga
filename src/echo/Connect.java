package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connect extends Thread{
	
	private String ipadd;
	private int tcpport;
	public int maxsize = 8;
	private Socket echoSocket = null;
	echoer echo;
	public Connect (String ipaddr, String tcp,echoer echo) throws IOException{
		ipadd = ipaddr;
		tcpport = Integer.parseInt(tcp);
		this.echo = echo;
		

        
        
        ExecutorService threadPool = Executors.newFixedThreadPool(maxsize);
		try {
			//echoSocket = new Socket(ipadd, tcpport);
			threadPool.submit(new Connect(new Socket(ipadd,tcpport)));
			
			System.out.println("Info: The connection between this machine and "+ipadd+" "+tcpport +" is successfully estabilshed");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				e.printStackTrace();
			}
			
		}
		
	}
	
}
