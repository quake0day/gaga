package echo;
import java.net.*; 
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*; 
import java.lang.Object;

import com.sun.corba.se.impl.orbutil.closure.Future;

public class server extends Thread
{ 

 //private SocketChannel clientSocketChannel;
// private Selector selector;
 protected Socket clientSocket;

 public static int count = 0;
 public int maxsize = 8;
 public Socket[] socketArray;
 public ArrayList<Socket> clients = null;
// List<Client> clients = new ArrayList<Client>();
 private echoer echo;
 public server (echoer echo) throws IOException, InterruptedException
   {
	 //set the max size of socket pool
	 this.echo = echo;
	 
	 
	 //clients = client;
	 
	 ServerSocket serverSocket = null; 
	 socketArray = new Socket[maxsize];
	 ExecutorService threadPool = Executors.newFixedThreadPool(maxsize);
	 //List<Future<String>> futures = new ArrayList<Future<String>>(10);
	 CompletionService<String> pool = new ExecutorCompletionService<String>(threadPool);
	 

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
    	 //Thread client = new Thread(new server(serverSocket.accept())); 
    	// pool.submit((Callable<String>) new server(serverSocket.accept()));
    	 threadPool.submit(new server(serverSocket.accept()));
    	 
    	 
    // clientSocketChannel = serverSocketChannel.accept(); 
    
     }
     
     
   }

 private server (Socket clientSoc) throws IOException
 {

	 System.out.println ("I'm still runnnn");

  //clientSocket = clientSoc;
 // clients.add(clientSocket);
  // callback function
	 

     echo.clients.add(clientSoc);

     System.out.println("Info: The connection between this machine and "+
			 clientSoc.getInetAddress()+" "+clientSoc.getPort() +" is successfully estabilshed");
 
  start();
 }
 


 public void run()
   {
	PrintWriter outServer = null;
	int index = echo.clients.size()-1;
    try { 
         outServer = new PrintWriter(echo.clients.get(index).getOutputStream(), 
                                      true); 
         BufferedReader inServer = new BufferedReader( 
                 new InputStreamReader( echo.clients.get(index).getInputStream())); 

         String inputLine; 
         
         while ((inputLine = inServer.readLine()) != null) 
             { 
             if (inputLine.equals("Bye.")) 
                 break; 
             else{
              System.out.println ("Server: " + inputLine); 
              outServer.println(inputLine); 
             }
  
             } 

         outServer.close(); 
         inServer.close(); 
         echo.clients.get(index).close(); 
        } 
    catch (IOException e) 
        { 
         System.err.println("Problem with Communication Server");
         System.exit(1); 
        } 
        
    }
 
 /*
  class Client implements Runnable{
		
		private Socket s = null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bConnected = false;
		private String sendmsg = null;
		
		Client(Socket s){
			this.s = s;
			try{
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;
				
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		
		public void send (String str){
			try{
				dos.writeUTF(str+"");
				dos.flush();
			} catch (IOException e){
				clients.remove(this);
				System.out.println("The other computer already disconnect..");
				
			}
		}

		@Override
		public void run() {
			try{
				while(bConnected){
					String str = dis.readUTF();
					
				}
			}catch(SocketException e){
				System.out.println("client is closed");
				clients.remove(this);
			} catch(EOFException e){
				System.out.println("client is closed");
				clients.remove(this);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			finally{
				try{
					if (dis != null) dis.close();
					if (dos != null) dos.close();
					if (s != null) s.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		 
	}*/

    
}

