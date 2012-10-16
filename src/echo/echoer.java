package echo;
import java.net.*; 
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*; 



public class echoer extends Thread{
	protected Socket clientSocket;
	private static Thread server;
	private static int tcpport;
	private static int udpport;
	public static ArrayList<Socket> clients = new ArrayList<Socket>();
	public static void main(String[] args) throws IOException, InterruptedException {
		int maxsize=9;
		ExecutorService threadPool = Executors.newFixedThreadPool(maxsize);
		if (args.length > 0){
			
				tcpport = Integer.parseInt(args[0]);
				udpport = Integer.parseInt(args[1]);
				if(tcpport < 0 && tcpport > 60000 && udpport < 0 && udpport > 60000){
					System.err.println("Error when try to listen to tcp-port:"+tcpport+" and udpport:"+udpport);
					System.exit(1);
				}
				else if (tcpport == udpport){
					System.err.println("Error when try to listen to tcp-port:"+tcpport+" and udpport:"+udpport);
					System.exit(1);
				}
		}
		else
		{

			System.out.println("Usage: java Echoer <tcp-port> <udp-port>");
			System.exit(1);
		}
		
		String serverHostname = new String ("localhost");
		 
		 //acted as a server when start

		 try { 
			 /*
         	 Thread thread = new Thread(new Monitor(tcpport,udpport,clients));
             thread.start();
             
			 Thread udpserver = new Thread(new Udpserver(10029));
			 Thread server = new Thread(new server(new echoer()));
			 */
			 echoer echoer = new echoer();
			 threadPool.submit(new Monitor(tcpport,udpport,clients));
		//	 Thread quake1 = new Thread(new Justtest());

			 Thread quake = new Thread(new Udpserver(udpport));
			 Thread quake0day = new Thread(new Tcpserver(tcpport,echoer));
			 quake.start();
		//	 quake1.start();
			 //quake0day.start();
			 
			 
			
			 

			 

			 //server.start();
	        } 
	    catch (IOException e) 
	        { 
	         System.err.println("Error when try to listen to tcp-port:"+tcpport+" and udpport:"+udpport); 
	         System.exit(1); 
	        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
	}

 

	


	 }
	 public void process(){
		 System.out.println("Callme back:");
	 }

}

