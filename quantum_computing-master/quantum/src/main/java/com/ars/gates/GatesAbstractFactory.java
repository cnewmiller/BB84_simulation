package com.ars.gates;
/**
 * Implementation of a Abstract Factory
 * 
 *
 */
public abstract class GatesAbstractFactory {
	public abstract IGate getGate(EGateTypes id);
}
