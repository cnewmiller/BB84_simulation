package LinuxQuantum.src.keyExchange;

import LinuxQuantum.src.keyExchange.Photon;
import java.net.Socket;
import java.util.Random;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Eve {
	private byte aPrime[];
	private int alicePort;
	private int interceptChance;
	private Random r;
	
	private ServerSocket listenSocket;
	private Socket toAliceSocket;
	private Socket toBobSocket;
	
	public Eve(){
		
	}
	public void init(int evePort, int alicePort, int interceptChance, int seed) throws IOException{
		this.alicePort = alicePort;
		this.interceptChance = interceptChance;
		this.r = new Random(seed);
		
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
		ObjectInputStream aliceObjectIn = new ObjectInputStream(fromAlice);
		ObjectOutputStream bobObjectOut = new ObjectOutputStream(toBob);
		
		for (int i = 0 ; i<N; i++){
			Photon p = (Photon) aliceObjectIn.readObject();
			
			if (r.nextInt(100) < interceptChance) aPrime[i] = p.measure( r.nextBoolean() );
			
			bobObjectOut.writeObject(p);
		}
		
		toAlice.write(fromBob.read());
		
		byte[] aliceB = new byte[N];
		fromAlice.read(aliceB);
		toBob.write(aliceB);
		
		
		
		int mLen = fromBob.read();
		toAlice.write(mLen);
		
		toBob.write(fromAlice.read());
		
		byte[] bobM = new byte[N];
		fromBob.read(bobM);
		toAlice.write(bobM);
		
		
		aliceObjectIn.close();
		bobObjectOut.close();
		fromAlice.close();
		toAlice.close();
		fromBob.close();
		toBob.close();
		
		
	}
	
}
