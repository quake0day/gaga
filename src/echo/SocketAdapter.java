package echo;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/* part of the codes are based on blog.csd.net/thch213/article/details/3304467
 * However, Si Chen re-write most of them
 * All rights reserved */
public class SocketAdapter extends Socket{
	// connection status
	private boolean status = true;
	
	// default construction function
	public SocketAdapter(){
		super();
	}
	public SocketAdapter(String host, int port) throws UnknownHostException, IOException{
		super(host,port);
	}
	// judge whether this socket this free
	public boolean isFree(){
		return status;
	}
	
	// when use this socket, we set it as busy (false)
	public void setBusy(){
		this.status=false;
	}
	
	// when client close this connection, we need set the status to free (true)
	public void close(){
		this.status = true;
	}
	
	public void destory(){
		close();
	}

}
