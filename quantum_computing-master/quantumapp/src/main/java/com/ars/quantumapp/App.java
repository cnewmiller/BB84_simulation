package com.ars.quantumapp;

import com.ars.complexnumbers.ComplexNumber;

public class App {
	private static Thread runner;

	public static void main(String[] args) {
		 runner=new Thread(new GroverRunner());
		 runner.start();
	}

}
