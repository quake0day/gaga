package echo;
import java.net.*; 
import java.io.*; 


public class echoer extends Thread{
	protected Socket clientSocket;
	private static Thread server;
	private static String tcpport;
	private static String udpport;
	public static void main(String[] args) throws IOException, InterruptedException {
		if (args.length > 0){
            tcpport = args[0];
			udpport = args[1];
		}
		else
		{
			tcpport = "999";
			udpport = "8888";
		}
		
		String serverHostname = new String ("localhost");
		 
		 //acted as a server when start

		 try { 
         	 Thread thread = new Thread(new Monitor(tcpport,udpport));
             thread.start();

			 server = new Thread(new server());
			 //server.start();
	        } 
	    catch (IOException e) 
	        { 
	         System.err.println("Could not listen on port: 10008."); 
	         System.exit(1); 
	        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	}


 

	


	 }

}

