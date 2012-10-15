package echo;

import java.io.IOException;
import java.net.Socket;

public class Disconnect extends Thread{
	private int connid;
	private echoer echoer;
	public Disconnect(int connid, echoer echo){
		this.connid = connid;
		this.echoer = echo;
		run();
	}
	public void run(){
		Socket soc = null;
		
		soc = echoer.clients.get(connid);
		echoer.clients.remove(connid);
		try {		
			soc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("cannot close this id...");
		}
		System.out.println("The connection (ID:"+connid+") is successfully disconnect :)");
		
	}

}
