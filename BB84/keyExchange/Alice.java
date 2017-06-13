package keyExchange;

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
import com.ars.gates.*;
import com.ars.qubits.*;
import keyExchange.Photon;

import java.util.Random;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Alice {
	
	private int portNumber;
	private ServerSocket aliceSocket;
	private Socket bobSocket;
	
	private byte[] A;
	private Photon[] aPrime;
	private byte[] B;
	private GateFactory gateFactory;
	private IGate Hadamard;
	
	
	/*
	 * Sets up the key to be exchanged. After running, A is created AND ENTANGLED/SUPERIMPOSED using B.
	 * 
	 * */
	public void init(int numOfQubits, int seed, int port) throws IOException{
		portNumber = port;
		aliceSocket= new ServerSocket(portNumber);
		
		
		A = new byte[numOfQubits];
		aPrime = new Photon[numOfQubits];
		B = new byte[numOfQubits];
		gateFactory = new GateFactory();
		Hadamard = gateFactory.getGate(EGateTypes.E_HadamardGate);
		
		Random r = new Random(seed);
		
		for (int i = 0;i<numOfQubits;i++){
			int Abit = r.nextInt(2);
			byte Bbit = (byte) r.nextInt(2); //either 0 or 1
			B[i] = Bbit;
//			System.out.println("pre superposition: Abit is "+Abit + ", Bbit is "+Bbit);
			if (Abit == 0){
				A[i]=0;
				aPrime[i] = new Photon( (Bbit == 1 ? Hadamard.applyGate(new QubitZero(), new int[] {0}, null, 1) : new QubitZero()) );
				
			}
			else{ //Abit == 1
				A[i]=1;
				aPrime[i] = new Photon( (Bbit == 1 ? Hadamard.applyGate(new QubitOne(), new int[] {0}, null, 1) : new QubitOne()) );
			}
			
//			System.out.println("Abit: "+A[i]);
			
//			System.out.println("post superposition: Abit is "+A[i].toString());
		}
		
	}
	
	public void exchange() throws IOException{
		System.out.println("Alice: Alice waiting...");
		bobSocket = aliceSocket.accept();
//		int g = bobSocket.getInputStream().read();
		System.out.println("Alice: Bob is connected! "+bobSocket.isConnected());
		
		
		InputStream bobIn = bobSocket.getInputStream();
		OutputStream bobOut = bobSocket.getOutputStream();
		
		//limit 256, that's annoying
		bobOut.write(A.length); //connection opens, bob gets the number of qubits to use
		System.out.println("Alice: N written");

//		bobIn.read();
		
		ObjectOutputStream o = new ObjectOutputStream(bobOut);
		for (int i=0;i<A.length;i++){
			o.writeObject(aPrime[i]);
		}
		System.out.println("Alice: A' written");
//		bobOut.write(B.length);
		
		int cont = bobIn.read(); //actually a single byte
		if (cont==0){
			throw new IOException("Bob says something went wrong");
		}
		System.out.println("Alice: Bob replied");
		//bob says it's all fine, send out B
		
		// length is already sent, B.len = A.len
		bobOut.write(B); //send B
		
		byte[] matches = new byte[A.length];
		byte numMatches = (byte) bobIn.read();
		bobOut.write(1);
		bobIn.read(matches);
		
		byte[] finalKey = new byte[numMatches];
		System.out.print("Alice: final key is \t");
		for (int i=0, j=0;i<A.length;i++){
			if (matches[i] == 1){
				finalKey[j++]=A[i];
				System.out.print(finalKey[j-1]+",");
			}
		}
		
		
		System.out.println();
		
//		System.out.println("Alice: Exchange done!");
		
		bobSocket.close();
		aliceSocket.close();
		
	}
	
	public void printA(){
		System.out.print("Alice: A (keystring) is \t\t");
		for (int i=0;i<A.length;i++){
			System.out.print(" "+A[i]+" ");
		}
		System.out.println();
	}
	public void printAprime(){
		System.out.print("Alice: A' (entangled data) is \t\t");
		for (int i=0;i<aPrime.length;i++){
			System.out.print(aPrime[i].toString()+"");
		}
		System.out.println();
	}
	public void printB(){
		System.out.print("Alice: B (encoding string) is \t\t");
		for (int i=0;i<B.length;i++){
			System.out.print(" "+B[i]+" ");
		}
		System.out.println();
	}
	
	
//	
//	public static void main(String[] args) {
//		Alice alice = new Alice();
//		try {
//			alice.init(10, 1, 30303);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//applygate(whichQubittoApplyTo, positionOfQubitAffectedInRegister, nullIGuess, numberOfEntangledBits)
//		
//		
////		IGate gateH = g.getGate(EGateTypes.E_HadamardGate);
////		Qubit q1 =new Qubit(QUBIT_1.getQubit());
////		Qubit q0 =new Qubit(QUBIT_0.getQubit());
//		
//		
//		
////		q1 = gateH.applyGate(q1, new int[] {0}, null, 1);
////		q0 = gateH.applyGate(q0, new int[] {0}, null, 1);
//		
////		System.out.println("Qubit 0 nonhadamard is " + q0.toString());
////		System.out.println("Qubit 1 hadamard is " + q1.toString());
//		
////		q0 = gateH.applyGate(q0, new int[] {0}, null, 1);
//		
////		MeasurementPerformer measurementPerformer = new MeasurementPerformer().configure(q0);
////		q0 = measurementPerformer.measure();
//		
//		
//		
////		System.out.println("Qubit 0 is " + q0);
////		System.out.println("Qubit 1 result1 is " + measurementResults[1]);
//		
//		
//		
//	}

}
