package keyExchange;

import java.io.IOException;
import java.util.Random;

import keyExchange.Alice;
import keyExchange.Bob;
import keyExchange.Eve;


public class KeyExchangeSim {

	public static void main(String[] args) {
		final int alicePort = 30303;
		final int evePort = 30304;
		Random r = new Random();
		final int seed = r.nextInt();
		final Bob bob = new Bob();
		final Alice alice = new Alice();
		final Eve eve = new Eve();
		
		try {
			alice.init(10, seed, alicePort);
			bob.init(seed, evePort);
			eve.init(evePort, alicePort);
			
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
//			alice.printB();
			alice.printAprime();
			bob.printAprime();
			bob.printB();
			bob.printBprime();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
