package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Monitor extends Thread{
	private int tcpport;
	private int udpport;
	private ArrayList<Socket> client;
	
	public Monitor (int udpportin, int tcpportin, ArrayList<Socket> clients){
	   tcpport = tcpportin;
	   udpport = udpportin;
	   client = clients;
	   }
	 
	 // Judge whether s is a valid IPV4 address
	 public static boolean isIPV4(String s){
		 try{
			 String number = s.substring(0,s.indexOf('.'));
			 if(Integer.parseInt(number) > 255) return false;
			 for(int i=0; i < 2; i++){
				 s = s.substring(s.indexOf('.')+1);
				 //System.out.println(s);
				 number = s.substring(0,s.indexOf('.'));
				 if(Integer.parseInt(number) > 255) return false;
			 }
			 return true;
			
		 } catch(Exception e){
			 return false;
		 }
		 
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
			    System.out.print("Echoer:");
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
			    	Thread connect = new Thread(new Connect(ipaddr,tcp,new echoer()));	
			    	
			    	}
			    }	   
			    else if (userInput.equals("show"))
			    {
			    	//System.out.println("Now you hit show");
			    	Thread shows = new Thread(new show(client,new echoer()));
			    
			    }	        
			    else if (userInput.equals("send"))
			    {
			    	String message = null;
			    	int connid = 0;
			    	// for send command, a user should provide at least 3 parameters
			    	if(command.length < 3){
			    		System.out.println("Usage:send <conn-id> <message>");
			    	}
			    	else{		
			    		try{
			    			connid = Integer.parseInt(command[1]);
			    		} catch(NumberFormatException e){
			    			System.out.println("The conn-id you input is not a valid one ");
			    		}
			    	for(int i=2; i < command.length; i++){
			    		if(message == null){
			    			message = command[i];
			    		}
			    		else{
			    			message = message +" "+ command[i] ;
			    		}
			    	
			    	}

					// create new thread connect to handle this request
			    	// see Send.java for more detail
			    	Thread send = new Thread(new Send(connid,message,new echoer()));	
			    	
			    	}

			    }
			    else if (userInput.equals("sendto"))
			    {
			    	String message = null;
			    	String ipaddr = null;
			    	int udpport = 0;
			    	int err = 0;
			    	// for sendto command, a user should provide at least 3 parameters
			    	if(command.length < 4){
			    		System.out.println("Usage:sendto <ip-address> <udp-port> <message>");
			    		err = 1;
			    	}
			    	else{		
			    		if(isIPV4(command[1])){
			    			ipaddr = command[1];
			    		}
			    		else{
			    			System.out.println("The ip-address you input is not a valid one ");
			    			err = 1;
			    		}
			    		try{
			    			udpport = Integer.parseInt(command[2]);
			    		} catch(NumberFormatException e){
			    			System.out.println("The udp-port you input is not a valid one ");
			    			err = 1;
			    		}
			    	}
				    	for(int i=3; i < command.length; i++){
				    		if(message == null){
				    			message = command[i];
				    		}
				    		else{
				    			message = message +" "+ command[i] ;
				    		}
			    	}
				    	if(err == 0){
				    	//System.out.println(isIPV4("256.244.42.12"));
				    	Thread sendto = new Thread(new Sendto(ipaddr,udpport,message));
				    	}

					// create new thread connect to handle this request
			    	// see Sendto.java for more detail
			    //		
			    }
			    else if (userInput.equals("disconnect")){
			    	int connid = 0;
			    	// for disconnect command, a user should provide at least 2 parameters
			    	if(command.length < 2){
			    		System.out.println("Usage:disconnect <conn-id>");
			    	}
			    	else{		
			    		try{
			    			connid = Integer.parseInt(command[1]);
			    		} catch(NumberFormatException e){
			    			System.out.println("The conn-id you input is not a valid one ");
			    		}
			    		Thread connect = new Thread(new Disconnect(connid,new echoer()));
			    	}
			    	
			    	
			    		
			    }
			    else{

			    System.out.println("unknown command");
			   
			    }
				
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		    try {
				currentThread().sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }

}
