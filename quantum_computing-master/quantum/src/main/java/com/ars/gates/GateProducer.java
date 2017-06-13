package com.ars.gates;

/**
 * 
 * Implementation of Gate Producer.
 *
 */
public class GateProducer {

	/**
	 * Return a new <code>GateFactory</code> object.
	 * @return GatesFactory
	 */
	public static GatesAbstractFactory getGateFactory() {
		return new GateFactory();
	}
}
