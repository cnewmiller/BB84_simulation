package com.ars.qubits;

import com.ars.complexnumbers.ComplexNumber;
/**
* <h1>QubitPlus</h1>
*/
public class QubitPlus extends Qubit {
	/**
	 * Construct a new <code> QubitPlus</code> object.
	 */
	public QubitPlus() {
		super(new ComplexNumber(1.0 / Math.sqrt(2), 0.0), new ComplexNumber(1.0 / Math.sqrt(2), 0.0));
	}

}
