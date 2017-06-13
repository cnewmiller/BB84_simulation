package com.ars.qubits;

import com.ars.complexnumbers.ComplexNumber;
/**
* <h1>QubitOne</h1>
* Representation of the qubit |1>=[0,1].
*/
public class QubitOne extends Qubit {

	/**
	 * Construct a new <code> QubitOne</code> object.
	 */
	public QubitOne() {
		super(new ComplexNumber(0.0, 0.0), new ComplexNumber(1.0, 0.0));
	}

}