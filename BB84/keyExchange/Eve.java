package keyExchange;

import com.ars.gates.*;
import com.ars.qubits.*;
import keyExchange.Photon;

import java.util.Random;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Eve {
	private byte aliceKey[];
	private byte aPrime[];
	private byte B[];
	private int evePort;
	private int alicePort;
	
	private ServerSocket listenSocket;
	private Socket toAliceSocket;
	private Socket toBobSocket;
	
	public Eve(){
		
	}
	public void init(int evePort, int alicePort) throws IOException{
		this.evePort = evePort;
		this.alicePort = alicePort;
		listenSocket=new ServerSocket(evePort);
	}
	public void intercept() throws IOException, ClassNotFoundException{
		System.out.println("Eve: starting...");
		toBobSocket = listenSocket.accept();
		toAliceSocket = new Socket(InetAddress.getLoopbackAddress(), alicePort);
		InputStream fromAlice = toAliceSocket.getInputStream();
		OutputStream toAlice = toAliceSocket.getOutputStream();
		
		InputStream fromBob = toBobSocket.getInputStream();
		OutputStream toBob = toBobSocket.getOutputStream();
		
		System.out.println("Eve: connected");
		
		int N = fromAlice.read();
		System.out.println("Eve: N="+N);
		toBob.write(N);
		
		
		
		
		aPrime = new byte[N];
		
		ObjectInputStream aliceObjectIn = new ObjectInputStream(fromAlice), bobObjectIn = new ObjectInputStream(fromBob);
		ObjectOutputStream aliceObjectOut = new ObjectOutputStream(toAlice), bobObjectOut = new ObjectOutputStream(toBob);;
		
		for (int i = 0 ; i<N; i++){
			
			Photon p = (Photon) aliceObjectIn.readObject();
			
			aPrime[i] = p.measure( false );
			
			bobObjectOut.writeObject(aPrime[i]);
		}
		
		toAlice.write(fromBob.read());
		
		byte[] aliceB = new byte[N], bobC = new byte[N];
		fromAlice.read(aliceB);
		toBob.write(aliceB);
		
		fromBob.read(bobC);
		toAlice.write(bobC);
		
		toBob.write(fromAlice.read());
		
		
		
		
	}
	
	
	/*
	 * eve needs to:
	 * 	take alice's input
	 * 	store it
	 * 	send it on to bob
	 * 	take bob's input
	 * 	store it
	 * 	send it on to alice
	 *
	 * 
	 * */
	
}
