package echo;

import java.net.Socket;
import java.util.ArrayList;

public class show extends Thread{
	private ArrayList<Socket> clientList;
	private echoer echoer;
	public show(ArrayList<Socket> client,echoer echo){
		clientList = client;
		this.echoer = echo;
		run();
	}
	public void run(){
		System.out.println("conn.ID |    	 IP    	   |    	 hostname		| 		local port		|		remote port     ");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		for(int i=0; i<clientList.size(); i++){
			if(echoer.clients.get(i).isConnected()){
			System.out.print(i+"		");
			System.out.print(clientList.get(i).getInetAddress().getHostAddress()+"		");
			System.out.print(clientList.get(i).getInetAddress().getHostName()+" 				");
			System.out.print(clientList.get(i).getLocalPort()+"  				");
			System.out.println(clientList.get(i).getPort());
			
			} else {
				System.out.print("Close");
				echoer.clients.remove(i);
			}
		}
	}
}

