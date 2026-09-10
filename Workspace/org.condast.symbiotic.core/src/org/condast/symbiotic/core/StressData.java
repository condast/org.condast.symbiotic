package org.condast.symbiotic.core;

import java.util.Collection;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

public class StressData implements IStressData {

	private double weight;
	private double previousWeight;
	private double weightStep; //The increase or decrease of stress per cycle

	private double stress;
	private ISymbiot target;

	public StressData(ISymbiot target) {
		this( target, IStressData.DEFAULT_WEIGHT_STEP, IStressData.DEFAULT_INITIAL_WEIGHT );
	}

	public StressData(ISymbiot target, double weightStep) {
		this( target, weightStep, IStressData.DEFAULT_INITIAL_WEIGHT );
	}
	
	public StressData(ISymbiot target, double weightStep, double weight) {
		super();
		this.weightStep = weightStep;
		this.weight = weight;
		this.previousWeight = 0;
		this.target = target;
	}

	@Override
	public ISymbiot getTarget() {
		return target;
	}

	@Override
	public String getReference() {
		return this.target.getId();
	}

	@Override
	public void clear() {
		this.weight = IStressData.DEFAULT_INITIAL_WEIGHT;
		this.previousWeight = 0;
		this.stress = 0;
	}

	@Override
	public double getStress() {
		return this.stress;
	}

	protected void setStress(double stress) {
		this.stress = stress;
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
		return Math.abs( this.stress ) < Double.MIN_VALUE;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	protected void setWeight(double weight) {
		this.weight = weight;
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
		return this.target.getStress() - this.stress;
	}

	/**
	 * Returns true if the stress is increasing
	 * @return
	 */
	public boolean isIncreasing(){
		return Math.abs( target.getStress() ) > Math.abs( this.stress );
	}

	/**
	 * Returns true if the given symbiot is isolated from the target, by the given threshold factor (0..100)
	 */
	@Override
	public boolean isIsolated( ISymbiot symbiot, int threshold) {
		Collection<IStressData> signals = symbiot.getSignals().values();
		if(( signals == null ) || signals.isEmpty())
			return true;

		double th = ((double)threshold)/MAX_PERCENT;
		IStressData data = target.getStressData( symbiot.getId());
		if( data == null )
			return true;
		double weight = Math.abs( data.getWeight());
		return ( weight < th);
	}

	/**
	 * Update the specific weight.
	 * In this case the weight is adjusted according to the local stress delta
	 * @param data
	 */
	protected void updateWeightOld() {
		double stressDelta = getDelta();
		
		//stressDelta <=0 is good, because this means that the stress is decreasing
		if( weight <= 0) {
			weight -= this.weightStep * stressDelta;
		}else {
			weight += this.weightStep * stressDelta;
		}
		weight = NumberUtils.clipRange(-1, 1, weight);				
	}

	/**
	 * Update the specific weight.
	 * In this case the weight is adjusted according to the local stress delta
	 * @param data
	 */
	protected void updateWeight() {
		double sign = isIncreasing()?-1d:1d;
		
		double offset = sign * this.weightStep * Math.abs( stress ); 
		if( weight <= 0) {
			weight -= offset;
		}else {
			weight += offset;
		}
		weight = NumberUtils.clipRange(-1, 1, weight);				
	}

	/**
	 * The weights are updated if the stress is not zero
	 */
	@Override
	public boolean update () {
		boolean retval = Math.abs( getDelta()) > Double.MIN_VALUE;
		if( !retval )
			return false;
		this.stress = this.target.getStress();
		this.updateWeight();
		return true;
	}
}
