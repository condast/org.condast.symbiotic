package org.condast.symbiotic.core;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

public class StressData implements IStressData {

	private double weight;
	private double stress;
	private double previousStress;
	private ISymbiot symbiot;
	
	public StressData(ISymbiot symbiot) {
		this( symbiot, 0f );
	}
	
	private StressData(ISymbiot symbiot, float weight) {
		super();
		this.weight = weight;
		this.previousStress = 0;
		this.symbiot = symbiot;
	}

	@Override
	public String getReference() {
		return this.symbiot.getId();
	}

	@Override
	public double getStress() {
		return this.stress;
	}

	@Override
	public double getWeight() {
		return weight;
	}
	
	@Override
	public void setWeight(double weight) {
		this.weight = NumberUtils.clipRange(-1, 1, weight );
	}

	/**
	 * Get the delta between the new stress and the currently stored stress
	 * @return
	 */
	@Override
	public double getDelta(){
		return this.stress - this.previousStress;
	}
	
	@Override
	public void update () {
		this.previousStress = this.stress;
		this.stress = this.symbiot.getStress();
	}
}
