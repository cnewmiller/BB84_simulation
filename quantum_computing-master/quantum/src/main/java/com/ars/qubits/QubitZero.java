package com.ars.qubits;

import com.ars.complexnumbers.ComplexNumber;
/**
* <h1>QubitZero</h1>
* Representation of the qubit |0>=[1,0].
*/
public class QubitZero extends Qubit {

	/**
	 * Construct a new <code> QubitZero</code> object.
	 */
	public QubitZero() {
		super(new ComplexNumber(1.0, 0.0), new ComplexNumber(0.0, 0.0));
	}

}
