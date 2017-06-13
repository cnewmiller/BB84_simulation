package LinuxQuantum.src.keyExchange;

//alice
//generate A: key string of bits
//generate B: encoding sequence of bits
//encode A using hadamard gates, send each qubit

//bob:
//generate B': decoding string of bits
//receive A': individual qubits
//decode A' using B'
//		if one state, do hadamard before measuring, if not, don't

//alice;
//send B

//bob:
//receive B
//loop through B and find the indices where B and B' match
//send these indices

//alice:
//receive indices, that makes the key



//import com.ars.complexnumbers.*;
import LinuxQuantum.src.keyExchange.Photon;

import java.util.Random;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;

public class Bob {
	
	int portNumber;
	Socket aliceSocket;
	
	private byte[] aPrime;
	private byte[] bPrime;
	private byte[] B;
	private Random r;
	
	/*
	 * Sets up the key to be exchanged. After running, A is created AND ENTANGLED/SUPERIMPOSED using B.
	 * 
	 * */
	public void init( int seed, int port) throws IOException{
		portNumber = port;
		
		r = new Random(seed);
		
//		A = new Qubit[numOfQubits];
//		B = new byte[numOfQubits];

	}
	
	public void exchange() throws IOException, ClassNotFoundException{
		System.out.println("Bob: Bob connecting...");
		aliceSocket= new Socket(InetAddress.getLoopbackAddress(), portNumber); //does exchange on localhost
		System.out.println("Bob: Bob connected!");
		
		System.out.println("Bob: Exchange starting...");
		InputStream aliceIn = aliceSocket.getInputStream();
		OutputStream aliceOut = aliceSocket.getOutputStream();

		
		System.out.println("Bob: N about to recieve");
		int n = aliceIn.read();
		System.out.println("Bob: N received, n="+n);
//		aliceOut.write(1);
		
		
		aPrime = new byte[n];
		bPrime = new byte[n];
		B = new byte[n];
		
		ObjectInputStream o = new ObjectInputStream(aliceIn);
		for (int i = 0 ; i<n; i++){
			bPrime[i] = (byte) r.nextInt(2);
			Photon p = (Photon) o.readObject();
			
			//measure each "photon" as it arrives
			aPrime[i] = p.measure( bPrime[i]==1 );
			
		}
		System.out.println("Bob: A received");
		
		aliceOut.write(1); //sends all good
		System.out.println("Bob: all good written");
		
		aliceIn.read(B);
		
		//send indices where i match
		byte[] matches = new byte[n];
		byte countMatches = 0;
		System.out.print("Bob: We matched at \t");
		for (int i = 0;i<n;i++){
			matches[i] = (byte) (bPrime[i]==B[i] ? 1 : 0);
			if (matches[i]==1){
				countMatches++;
				System.out.print(i+",");
			}
		}
		System.out.println();
		
		byte[] finalKey = new byte[countMatches];
		System.out.print("Bob: shared key is \t");
		for (int i = 0, j=0;i<n;i++){
			if (matches[i] ==1){
				finalKey[j++] = aPrime[i];		//get byte from A'
				System.out.print(finalKey[j-1]+",");
			}
			
		}
		
		System.out.println();
		aliceOut.write(countMatches);
		aliceIn.read();
		aliceOut.write(matches);

		aliceSocket.close();
		o.close();
	
		
	}
	
	public void printAprime(){
		System.out.print("Bob: A' (measured data) is \t\t");
		for (int i=0;i<aPrime.length;i++){
			System.out.print(" "+aPrime[i]+" ");
		}
		System.out.println();
	}
	public void printBprime(){
		System.out.print("Bob: B' (random measurement bases) is \t");
		for (int i=0;i<bPrime.length;i++){
			System.out.print(" "+bPrime[i]+" ");
		}
		System.out.println();
	}
	public void printB(){
		System.out.print("Bob: revealed B (alice's bases) is \t");
		for (int i=0;i<B.length;i++){
			System.out.print(" "+B[i]+" ");
		}
		System.out.println();
	}
	


}
