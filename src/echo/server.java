package echo;
import java.net.*; 
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.io.*; 
import java.lang.Object;

public class server extends Thread
{ 

 //private SocketChannel clientSocketChannel;
// private Selector selector;
 protected Socket clientSocket;
 public Socket[] socketpool = null;
 public int count = 0;
 public int maxsize = 8;


 public server () throws IOException, InterruptedException
   {
	 //set the max size of socket pool
	 
	 ServerSocket serverSocket = null; 
	 socketpool = new Socket[maxsize];
	 
	 

	 serverSocket = new ServerSocket(10024); 
	 System.out.println ("Connection Socket Created");
	 // Create the selector
	 //selector = Selector.open();
	 //ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); 
	 //serverSocketChannel.socket().bind(new InetSocketAddress(10021));
	 // set it to non-blocking mode
	 //serverSocketChannel.configureBlocking(false);
     //System.out.println ("Connection Socket Created");
     //System.out.println ("Waiting for Connection");

     
     // Register the channel with selector, listening for all events
     //socketChannel.register(selector, socketChannel.validOps());
     while(true){
    	 //ThreadGroup clientgroup = new ThreadGroup("clientgroup");
    	 Thread client = new Thread(new server(serverSocket.accept())); 
    	 
    // clientSocketChannel = serverSocketChannel.accept(); 
     if(clientSocket != null){
    	 System.out.println ("New Communication Thread Started");
    	 start();
         //do something with socketChannel...
     }
     else
     {
    	 //System.out.println ("nuuuuuuuuu");
    	// int activeCount = clientgroup.activeCount();
    	// System.out.println(activeCount);
    	 currentThread();
		 Thread.sleep(100);
    	 //Thread.yield();
     }
     }
     
     
   }

 private server (Socket clientSoc)
 {
  clientSocket = clientSoc;
  socketpool[count] = clientSoc;
  start();
 }

 public void run()
   {
	 System.out.println("Info: The connection between this machine and "+socketpool[count].getInetAddress()+" "+socketpool[count].getPort() +" is successfully estabilshed");
	
	 
    if (count >= maxsize)
    {
    	count = count % maxsize;
    }
    count ++;
    System.out.println(count);


    try { 
         PrintWriter outServer = new PrintWriter(clientSocket.getOutputStream(), 
                                      true); 
         BufferedReader inServer = new BufferedReader( 
                 new InputStreamReader( clientSocket.getInputStream())); 

         String inputLine; 

         while ((inputLine = inServer.readLine()) != null) 
             { 
             if (inputLine.equals("Bye.")) 
                 break; 
             if (inputLine.equals("info")) 
           	  outServer.println("III");
             else{
              System.out.println ("Server: " + inputLine); 
              outServer.println(inputLine); 
             }
             
             } 

         outServer.close(); 
         inServer.close(); 
         clientSocket.close(); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Problem with Communication Server");
         System.exit(1); 
        } 
        
    }
    
} 