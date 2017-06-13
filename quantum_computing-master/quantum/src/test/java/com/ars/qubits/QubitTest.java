package com.ars.qubits;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ars.complexnumbers.ComplexNumber;
import com.ars.quantum.utils.QuantumOperations;

public class QubitTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testQubitOne() {
		ComplexNumber[] expectedQubit = { new ComplexNumber(0.0, 0.0), new ComplexNumber(1.0, 0.0) };
		Qubit q1 = new QubitOne();
		assertArrayEquals(expectedQubit, q1.getQubit());
	}

	@Test
	public void testQubitZero() {
		ComplexNumber[] expectedQubit = { new ComplexNumber(1.0, 0.0), new ComplexNumber(0.0, 0.0) };
		Qubit q1 = new QubitZero();
		assertArrayEquals(expectedQubit, q1.getQubit());
	}

	@Test
	public void testConstructorQubitVector() {
		// This test is just for coverage
		ComplexNumber[] expectedQubit = { new ComplexNumber(1.0, 0.0), new ComplexNumber(0.0, 0.0) };
		Qubit q = new Qubit(expectedQubit);
		assertArrayEquals(expectedQubit, q.getQubit());
	}

	// Test of override methods
	@Test
	public void testToStringMethod() {
		String expectedMessage = "[ " + new ComplexNumber(0.0, 0.0) + " " + new ComplexNumber(1.0, 0.0) + " ]";
		Qubit q1 = new QubitOne();
		assertEquals(0, expectedMessage.compareTo(q1.toString()));
	}

	@Test
	public void testEqualsDifferendQubits() {
		Qubit q1 = new QubitOne();
		Qubit q0 = new QubitZero();
		assertNotEquals(q0, q1);
	}

	@Test
	public void testEqualsDifferentLengthQubits() {
		Qubit q = QuantumOperations.entangle(new QubitOne(), new QubitOne());
		assertNotEquals(q, new QubitOne());
	}

	@Test
	public void testEqualQubits() {
		assertEquals(new QubitOne(), new QubitOne());

		assertEquals(QuantumOperations.entangle(new QubitOne(), new QubitOne()),
				QuantumOperations.entangle(new QubitOne(), new QubitOne()));
	}

	@Test
	public void testNotEqual() {
		assertNotEquals(new QubitOne(), new QubitZero());

		assertNotEquals(QuantumOperations.entangle(new QubitOne(), new QubitOne()),
				QuantumOperations.entangle(new QubitZero(), new QubitOne()));
	}
	
	@Test
	public void testValid(){
		Qubit q=new QubitZero();
		assertTrue(q.isValid());
	}
	
	@Test
	public void testQubitPlus() {
		ComplexNumber[] expectedQubit = { new ComplexNumber(1.0 / Math.sqrt(2), 0.0), new ComplexNumber(1.0 / Math.sqrt(2), 0.0)};
		Qubit q1 = new QubitPlus();
		assertArrayEquals(expectedQubit, q1.getQubit());
	}
	
	@Test
	public void testQubitMinus() {
		ComplexNumber[] expectedQubit = {new ComplexNumber(1.0 / Math.sqrt(2), 0.0), new ComplexNumber(-1.0 / Math.sqrt(2), 0.0) };
		Qubit q1 = new QubitMinus();
		assertArrayEquals(expectedQubit, q1.getQubit());
	}
}
