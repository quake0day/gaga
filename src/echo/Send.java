package echo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Send extends Thread {
	private echoer echoer;
	private int id;
	private String sendmessage;
	public Send(int connid, String message,echoer echo){
		this.echoer = echo;
		id = connid;
		sendmessage = message;
		run();
	}
	public void run(){
		PrintWriter outServer = null;
        try {
			outServer = new PrintWriter(echoer.clients.get(id).getOutputStream(), 
			        true);
			outServer.println(sendmessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("The conn-id you input is not a valid one ");
		} 
        //System.out.println("sendMessage is :"+sendmessage);
		
	}

}
