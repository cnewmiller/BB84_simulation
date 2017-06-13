package LinuxQuantum.src.keyExchange;

import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import LinuxQuantum.src.keyExchange.Alice;
import LinuxQuantum.src.keyExchange.Bob;
import LinuxQuantum.src.keyExchange.Eve;


public class KeyExchangeSim {

	public static void main(String[] args) {
		
//		Console c = System.console();
		BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("how many bits should we use: ");
//		int numOfBits = 128; //limit 255, annoying but made it simpler
		int numOfBits=16, eveInterceptChance=0;
		try {
			numOfBits = Integer.parseInt(c.readLine());
			
			System.out.println("How many bits should Eve measure: ");
			eveInterceptChance = Integer.parseInt(c.readLine());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		
		final int alicePort = 30303;
		final int evePort = 30304;
		
		
		Random r = new Random();
		final int seed = r.nextInt();
		final Bob bob = new Bob();
		final Alice alice = new Alice();
		final Eve eve = new Eve();
		
		try {
			alice.init(numOfBits, seed, alicePort);
			bob.init(seed, evePort);
			eve.init(evePort, alicePort, eveInterceptChance, seed);
			
			Thread a = new Thread ( new Runnable(){
				@Override
				public void run() {
					try {
						alice.exchange();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
			
			Thread b = new Thread ( new Runnable(){
				@Override
				public void run() {
					try {
						bob.exchange();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
			Thread e = new Thread ( new Runnable(){
				@Override
				public void run() {
					try {
						eve.intercept();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			});
			
			a.start();
			e.start();
			b.start();
			
			a.join();
			e.join();
			b.join();
			
			alice.printA();
			alice.printAprime();
			bob.printAprime();
			bob.printB();
			bob.printBprime();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
