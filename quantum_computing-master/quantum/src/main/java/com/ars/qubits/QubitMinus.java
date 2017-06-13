package com.ars.qubits;

import com.ars.complexnumbers.ComplexNumber;
/**
* <h1>QubitMinus</h1>
*/
public class QubitMinus extends Qubit {
	/**
	 * Construct a new <code> QubitMinus</code> object.
	 */
	public QubitMinus() {
		super(new ComplexNumber(1.0 / Math.sqrt(2), 0.0), new ComplexNumber(-1.0 / Math.sqrt(2), 0.0));
	}

}
