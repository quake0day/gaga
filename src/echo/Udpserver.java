package echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Udpserver extends Thread{
	private static final int TIMEOUT = 3000; //Maximum size of echo datagram
	private static final int ECHOMAX = 255; //Maximum size of echo datagram
	private int port;
	private DatagramSocket socket = null;
	private DatagramSocket client = null;
	private DatagramPacket packet = null;
	public Udpserver(int port) throws IOException, InterruptedException{
		//System.out.println("fuck I'm stuck here");
		this.port = port;
		int maxsize=8;
		ExecutorService threadPool = Executors.newFixedThreadPool(maxsize);
		// Create a selector to multiplex client connections.

		

		
		/*
		
		
		try {
			socket = new DatagramSocket(port);
			threadPool.submit((new Udpserver(socket)));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		
		
	}
	public void run(){
		Selector selector = null;
		try {
			selector = Selector.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DatagramChannel channel = null;
		try {
			channel = DatagramChannel.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			channel.configureBlocking(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			channel.socket().bind(new InetSocketAddress(port));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			channel.register(selector, SelectionKey.OP_READ,new ClientRecord());
		} catch (ClosedChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		while(true){
		//	if(selector.select(TIMEOUT) == 0){
			//	System.out.print(".aaa");
				//continue;
			//}
			/*
			  try {
					currentThread().sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				*/
		    System.out.println("tyep:udp");
			Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
			while(keyIter.hasNext()){
				
				SelectionKey key = keyIter.next();
				
				if(key.isReadable())
					try {
						handleRead(key);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				keyIter.remove();	
			}
			
		}
		/*
		while(true){
			System.out.println("cuk");

		}
		*/
	}
	public static void handleRead(SelectionKey key) throws IOException{
		
		DatagramChannel channel = (DatagramChannel) key.channel();
		ClientRecord clntRec = (ClientRecord) key.attachment();
		clntRec.buffer.clear();
		clntRec.clientAddress = channel.receive(clntRec.buffer);
		if(clntRec.clientAddress != null){
			key.interestOps(SelectionKey.OP_WRITE);
			clntRec.buffer.flip();
			String received = Charset.forName("UTF-8").newDecoder().decode(clntRec.buffer).toString();
			System.out.println("echoing "+received);
			System.out.println("to:"+clntRec.clientAddress);
			System.out.println("tyep:udp");
		}
	}
	static class ClientRecord{
		public SocketAddress clientAddress;
		public ByteBuffer buffer = ByteBuffer.allocate(ECHOMAX);
	}
	/*
	
	private Udpserver(DatagramSocket socket){
		client = socket;
		packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
		
		run();
	}
	public void run(){
		
		String recData = null;
		
		while(true){ // this thread run forever, receiving and echoing datagrams
			
			try {
				client.receive(packet);
				System.out.println("fuck I'm stuck heresdfas");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} // Receive packet from client
			recData = new String(packet.getData());
			System.out.println("echoing "+recData);
			System.out.println("to:"+packet.getAddress());
			System.out.println("tyep:udp");
			
			
			
		}
		
		
	}*/

}
