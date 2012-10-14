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

public class server extends Thread
{ 

 private static final long TIMEOUT = 3000;
private SocketChannel clientSocketChannel;
 private Selector selector;
 protected Socket clientSocket;

 public static int count = 0;
 public int maxsize = 8;
 public Socket[] socketArray;
 public ArrayList<Socket> clients = null;
// List<Client> clients = new ArrayList<Client>();
 private echoer echo;
 private int bufSize;
 public server (echoer echo) throws IOException, InterruptedException
   {/*
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


     
     // Register the channel with selector, listening for all events
     //socketChannel.register(selector, socketChannel.validOps());
     while(true){
    	 //ThreadGroup clientgroup = new ThreadGroup("clientgroup");
    	 //Thread client = new Thread(new server(serverSocket.accept())); 
    	// pool.submit((Callable<String>) new server(serverSocket.accept()));
    
    	 threadPool.submit(new server(serverSocket.accept()));
   
    	 
    // clientSocketChannel = serverSocketChannel.accept(); 
    
     }
     */
     
   }

 private server (Socket clientSoc) throws IOException
 {
/*
	 System.out.println ("I'm still runnnn");

  //clientSocket = clientSoc;
 // clients.add(clientSocket);
  // callback function
	 

     echo.clients.add(clientSoc);

     System.out.println("Info: The connection between this machine and "+
			 clientSoc.getInetAddress()+" "+clientSoc.getPort() +" is successfully estabilshed");
 */
  start();
 }
 


 public void run()
   {
	 
	 // Create the selector
	 try {
		selector = Selector.open();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 ServerSocketChannel serverSocketChannel = null;
	try {
		serverSocketChannel = ServerSocketChannel.open();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	 try {
		serverSocketChannel.socket().bind(new InetSocketAddress(10021));
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 // set it to non-blocking mode
	 try {
		serverSocketChannel.configureBlocking(false);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 try {
		serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
	} catch (ClosedChannelException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	 
	 while(true){
		 try {
			if(selector.select(TIMEOUT) == 0){
				 continue;
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
				currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 Iterator<SelectionKey> KeyIter = selector.selectedKeys().iterator();
		 ByteBuffer readBuff = ByteBuffer.allocate(1024);
		 final int PACKAGE_SIZE = 10;
		 while(KeyIter.hasNext()){
			 SelectionKey key = KeyIter.next(); // key is bit mask
			 if(key.isAcceptable()){
				 System.out.println("I'm HERE");
				 try {
					SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
					clntChan.configureBlocking(false);
					
					clntChan.register(key.selector(), SelectionKey.OP_READ,ByteBuffer.allocate(bufSize));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
			 }
			if(key.isReadable()){
				
				SocketChannel clntChan = (SocketChannel) key.channel();
				Socket client = clntChan.socket();
				//ByteBuffer buf = (ByteBuffer) key.attachment();
				long bytesRead = -1;
				try {
					bytesRead = clntChan.read(readBuff);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				key.interestOps(SelectionKey.OP_READ);
				System.out.println("client IP:"+client.getInetAddress().getHostAddress());
				if(readBuff.position() >= PACKAGE_SIZE)
				{
					readBuff.flip();
					DataInputStream dis = new DataInputStream(new ByteArrayInputStream(readBuff.array()));
					try {
						System.out.println(dis.readInt());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					BufferedReader d = new BufferedReader(new InputStreamReader(dis));
					try {
						System.out.println(d.readLine());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					key.cancel();

					readBuff.clear();
				} else {
					try {
						clntChan.register(selector,SelectionKey.OP_READ);
					} catch (ClosedChannelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			//	String received = Charset.forName("UTF-8").newDecoder().decode(bytesRead).toString();
				
				if(bytesRead == -1){
		
						System.out.println("nothing");
						continue;
					
				} else if (bytesRead > 0){
					key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
				}
				
			}
			KeyIter.remove();
		 }
	 }
	 // craeta a handler that will implement the protocol
	// TCPProtocol protocol = new EchoSelectorProtocol(BUFSIZE);
     //System.out.println ("Connection Socket Created");
     //System.out.println ("Waiting for Connection");
	 
	 /*
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
        
    }*/
 
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
}

