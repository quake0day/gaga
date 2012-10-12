package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Monitor extends Thread{
	private String tcpport;
	private String udpport;
	 public Monitor (String udpportin, String tcpportin){
	   tcpport = tcpportin;
	   udpport = udpportin;
	   }
	 public void run(){
	     PrintWriter out = null;
	     BufferedReader in = null;
	     BufferedReader stdIn = new BufferedReader(
                 new InputStreamReader(System.in));
	     String userInput;
	     
		 while(true)
		 { 
		 try {
			 	//obtain user input
			    userInput = stdIn.readLine();
			    // split user input by space and save it to an matrix
			    String[] command = userInput.split(" "); 
			    // the first character should be a command
			    userInput = command[0];
			    if (userInput.equals("Bye."))
			    {
			        break;
			    }
			    else if (userInput.equals("info"))
			    {
			    	// create new thread info to handle this request
			    	// see Info.java for more detail
			 	    Thread info = new Thread(new Info(udpport,tcpport));			     
			    }
			    else if (userInput.equals("connect"))
			    {
			    	// for connect command, a user should provide 3 parameter
			    	if(command.length != 3){
			    		System.out.println("Usage:connect <ip-address> <tcp-port>");
			    	}
			    	else{		
			    	String ipaddr = command[1];
			    	String tcp = command[2];
			    	// create new thread connect to handle this request
			    	// see Connect.java for more detail
			    	Thread connect = new Thread(new Connect(ipaddr,tcp));	
			    	}
			    }	   
			    else if (userInput.equals("show"))
			    {
			    	System.out.println("Now you hit show");
			 	 Thread thread = new Thread(new Justtest());
			     thread.start();

			    }	        
			    else if (userInput.equals("send"))
			    {
			    	System.out.println("Now you hit send");

			    }
			    else if (userInput.equals("sendto"))
			    {
			    	System.out.println("Now you hit sendto");

			    }
			    else{

			    System.out.println("unknown command");
			    }
				
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		    try {
				currentThread().sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }

}
