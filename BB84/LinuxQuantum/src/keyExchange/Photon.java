package LinuxQuantum.src.keyExchange;

import com.ars.qubits.*;

import java.io.Serializable;

import com.ars.algorithms.MeasurementPerformer;
import com.ars.gates.*;

public class Photon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Qubit polarization;
	
	
	
	public Photon(Qubit p){		
		this.polarization = new Qubit(p.getQubit());
	}
	
	public byte measure(boolean doHadamard){
		MeasurementPerformer m = new MeasurementPerformer().configure(this.polarization);
		IGate H = (new GateFactory()).getGate(EGateTypes.E_HadamardGate);
		if (doHadamard){
			this.polarization = H.applyGate(this.polarization, new int[] {0}, null, 1);
		}
		
		this.polarization = m.measure();
		
		//turn qubit into byte
		
		if (this.polarization.equals(new QubitZero())){
			return 0;
		}
		else if (this.polarization.equals(new QubitOne())){
			return 1;
		}
		
		return -1;
//		return this.polarization;
	}
	
	/*
	 * Totally breaks the rules
	 * */
	public String toString(){
		
		if (this.polarization.equals(new QubitZero())){
			return "|0>";
		}
		else if (this.polarization.equals(new QubitOne())){
			return "|1>";
		}
		else if (this.polarization.equals(new QubitPlus())){
			return "|+>";
		}
		else if (this.polarization.equals(new QubitMinus())){
			return "|X>";
		}
		
		
		
		
		return this.polarization.toString();
		
	}
	
}
