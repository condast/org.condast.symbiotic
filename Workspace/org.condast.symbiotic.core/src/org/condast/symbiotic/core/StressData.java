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
	private double previousStress;
	private ISymbiot target;

	public StressData(ISymbiot target) {
		this( target, IStressData.DEFAULT_WEIGHT_STEP, 0d );
	}

	public StressData(ISymbiot target, double weightStep) {
		this( target, weightStep, 0d );
	}
	
	public StressData(ISymbiot target, double weightStep, double weight) {
		super();
		this.weightStep = weightStep;
		this.weight = weight;
		this.previousWeight = weight;
		this.previousStress = 0;
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
		double weight = Math.abs( data.getWeight());
		return ( weight >= th);
	}

	/**
	 * Update the specific weight.
	 * In this case the weight is adjusted according to the local stress delta
	 * @param data
	 */
	protected void updateWeight() {
		double step = isJump(0.5) ? weightStep/DEFAULT_ZERO_ADJUST: this.weightStep;
		double stressDelta = getDelta();
		
		//stressDelta <=0 is good, because this means that the stress is decreasing
		if( weight < 0) {
			weight += step * stressDelta;
		}else {
			weight -= step * stressDelta;
		}
		weight = NumberUtils.clipRange(-1, 1, weight);				
	}


	/**
	 * The weights are updated if the stress is not zero
	 */
	@Override
	public boolean update ( boolean learning ) {
		boolean retval = Math.abs( getDelta()) > Double.MIN_VALUE;
		if( !retval && !learning )
			return false;
		
		this.previousStress = this.stress;
		this.stress = this.target.getStress();
		this.updateWeight();
		return true;
	}
}
