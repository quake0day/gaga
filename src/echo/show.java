package echo;

import java.net.Socket;
import java.util.ArrayList;

public class show extends Thread{
	private ArrayList<Socket> clientList;
	public show(ArrayList<Socket> client){
		clientList = client;
		run();
	}
	public void run(){
		System.out.println("conn.ID |    	 IP    	   |    	 hostname		| 		local port		|		remote port     ");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
		for(int i=0; i<clientList.size(); i++){
			System.out.print(i+"		");
			System.out.print(clientList.get(i).getInetAddress().getHostAddress()+"		");
			System.out.print(clientList.get(i).getInetAddress().getHostName()+" 				");
			System.out.print(clientList.get(i).getLocalPort()+"  				");
			System.out.println(clientList.get(i).getPort());
		}
	}
}

