package com.ars.complexnumbers;

import java.io.Serializable;

/**
* <h1>ComplexNumber</h1>
* ComplexNumber class represents the data type for complex numbers.

* @author  Mihai Seba
* @version 1.0
 
*/
public class ComplexNumber implements Serializable {

	/**
	 * The imaginary, Im(z), part of the <code>ComplexNumber</code>.
	 */
	private double			imaginary;
	
	/**
	 * The real,Re(z), part of the <code>ComplexNumber</code>.
	 */
	private double			real;

	/**
	 * Constructs a new <code>ComplexNumber</code> object.
	 * @param real the real part, Re(z), of the complex number
	 * @param imaginary the imaginary part, Im(z), of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.imaginary = imaginary;
		this.real = real;
	}

	/**
	 * Constructs a new <code>ComplexNumber</code> object with both real and imaginary parts 0.
	 */
	public ComplexNumber() {
		this.imaginary = 0.0;
		this.real = 0.0;
	}


	/**
	 * Set the real and the imaginary part of the complex number.
	 * @param real the real part, Re(z), of the complex number
	 * @param imaginary the imaginary,Im(z), of the complex number
	 */
	public void set(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}
	
	/**
	 * Set the real part of the complex number.
	 * @param real the real part, Re(z), of the complex number
	 */
	public void setReal(double real) {
		this.real = real;
	}

	/**
	 * Return the real part of the complex number.
	 * @return Re(z), the real part of the complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Return the imaginary part of the complex number.
	 * @return Im(z), the imaginary part of the complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Set the imaginary part of the complex number.
	 * @param imaginary the imaginary part, Im(z), of the complex number
	 */
	public void setImaginary(double imaginary) {
		this.imaginary = imaginary;
	}

	/**
	 * Check if passed <code>ComplexNumber</code> is equal to the current.
	 * @param o the complex number to be checked
	 * @return true if the two complex numbers are equals, otherwise false
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof ComplexNumber) {
			return (Double.compare(((ComplexNumber) o).real, real) == 0
					&& Double.compare(((ComplexNumber) o).imaginary, imaginary) == 0);
		}
		return false;
	}

	/**
	 * Return a string representation of the complex number.
	 * @return string the representation of the complex number
	 */
	@Override
	public String toString() {
		return (this.imaginary < 0 ? (String.format("%f%fi", real, imaginary))
				: (String.format("%f+%fi", real, imaginary)));
	}

	/**
	 * Calculates the argument of the current complex number.
	 * @return arg(z) the argument of the complex number.
	 */
	public double getArg() {
		return Math.atan2(this.imaginary, this.real);
	}
}
