package echo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Send extends Thread {
	private echoer echoer;
	private int id;
	private String sendmessage;
	public Send(String connid, String message,echoer echo){
		this.echoer = echo;
		id = Integer.parseInt(connid);
		sendmessage = message;
		run();
	}
	public void run(){
		PrintWriter outServer = null;
        try {
			outServer = new PrintWriter(echoer.clients.get(id).getOutputStream(), 
			        true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Wrong conn-id");
		} 
       System.out.println("sendMessage is :"+sendmessage);
		outServer.println(sendmessage);
	}

}
