package echo;
import java.net.*; 
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*; 
import java.lang.Object;

import com.sun.corba.se.impl.orbutil.closure.Future;

public class Tcpserver extends Thread
{ 

 private static final long TIMEOUT = 3000;
private SocketChannel clientSocketChannel;
 private Selector selector;
 protected Socket clientSocket;

 public static int count = 0;
 public int maxsize = 8;
 public Socket[] socketArray;
 public ArrayList<Socket> clients = null;
 private echoer echo;
 private int bufSize;
 public Tcpserver (echoer echo) throws IOException, InterruptedException
   {
	 //set the max size of socket pool
	 this.echo = echo;
	 ServerSocket serverSocket = null; 
	 socketArray = new Socket[maxsize];
	 ExecutorService threadPool = Executors.newFixedThreadPool(maxsize);
	 serverSocket = new ServerSocket(10024); 
	 System.out.println ("Connection Socket Created");
     while(true){
    	 threadPool.submit(new Tcpserver(serverSocket.accept()));
     }
     
   }

 private Tcpserver (Socket clientSoc) throws IOException
 {
     clientSocket = clientSoc;
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
}

