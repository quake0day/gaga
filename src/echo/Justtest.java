package echo;

import java.net.Socket;

public class Justtest extends Thread{
	 public Justtest ()
	   {
	    System.out.println ("New Communication Thread Started");
	    start();
	   }
	 public void run(){
		 System.out.println ("Runrunrun");
		    try {
				currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }
}
