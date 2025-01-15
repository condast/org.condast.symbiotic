package org.condast.symbiotic.core;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

public class StressData implements IStressData {

	private double weight;
	private double previousWeight;

	private double stress;
	private double previousStress;
	private ISymbiot symbiot;
	
	public StressData(ISymbiot symbiot) {
		this( symbiot, 0f );
	}
	
	private StressData(ISymbiot symbiot, float weight) {
		super();
		this.weight = weight;
		this.previousWeight = weight;
		this.previousStress = 0;
		this.symbiot = symbiot;
	}

	@Override
	public String getReference() {
		return this.symbiot.getId();
	}

	@Override
	public void clear() {
		this.weight = 0;
		this.previousWeight = 0;
		this.stress = 0;
		this.previousStress = 0;
	}

	@Override
	public double getStress() {
		return this.stress;
	}

	/**
	 * if true, then the PREVIOUS stress is approximately zero. 
	 * This can be used for fine tuning around the zero value
	 * at the first iteration
	 * @param factor
	 * @return
	 */
	@Override
	public boolean isZero() {
		return Math.abs( this.previousStress ) < Double.MIN_VALUE;
	}

	/**
	 * if true, then the change in stress is larger than the factor. This happens, for instance
	 * at the first iteration
	 * @param factor
	 * @return
	 */
	@Override
	public boolean isJump( double factor ) {
		double jump = this.getDelta() /  this.stress;
		return Math.abs(jump ) > factor;
	}
	
	@Override
	public double getWeight() {
		return weight;
	}
	
	@Override
	public void setWeight(double weight) {
		this.previousWeight = this.weight;
		this.weight = NumberUtils.clipRange(-1, 1, weight );
	}

	/**
	 * weight minus previous weight
	 * @return
	 */
	@Override
	public double getWeightDelta() {
		return this.previousWeight - weight;
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
		if(Math.abs( this.previousStress - this.stress ) > Double.MIN_VALUE )
			this.previousStress = this.stress;
		this.stress = this.symbiot.getStress();
	}
}
