package org.condast.symbiotic.core;

import java.util.Iterator;
import java.util.Map;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IBehaviour;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

public class DefaultBehaviour implements IBehaviour {

	private double weightStep; //The increase or decrease of stress per cycle
	
	private double stress, deltaStress; //The total stress of all the symbiots, normalised
	
	private boolean inclusive;
	
	public DefaultBehaviour(double weightStep) {
		this( weightStep, false );
	}
	
	public DefaultBehaviour(double weightStep, boolean inclusive) {
		super();
		this.weightStep = weightStep;
		this.inclusive = inclusive;
		this.stress = 0;
		this.deltaStress = 0;
	}
	@Override
	public boolean isInclusive() {
		return inclusive;
	}

	@Override
	public double getWeightStep() {
		return weightStep;
	}

	public double getDeltaStress() {
		return stress - this.deltaStress;
	}

	@Override
	public void setStress(double stress) {
		this.deltaStress = this.stress;
		this.stress = stress;
	}

	/**
	 * Update the specific weight.
	 * In this case the weight is adjusted according to the local stress delta
	 * @param data
	 */
	protected void updateWeight( IStressData data ) {
		double weight = data.getWeight();
		
		double stressDelta = data.getDelta();
		double step = data.isJump(0.5) ? this.weightStep/DEFAULT_ZERO_ADJUST: this.weightStep;

		//stressDelta <=0 is good, because this means that the stress is decreasing
		if( weight < 0) {
			weight += step * stressDelta;
		}else {
			weight -= step * stressDelta;
		}
		weight = NumberUtils.clipRange(-1, 1, weight);
		data.setWeight(weight);					
	}

	/**
	 * Update the specific weight.
	 * In this case the weight is adjusted according to the local stress delta, only if the global stress is increasing
	 * @param data
	 */
	protected void updateWeightGlobal( IStressData data ) {
		if( getDeltaStress() < -Double.MIN_VALUE )
			return;
		updateWeight(data);
	}

	@Override
	public void updateWeights(ISymbiot symbiot) {
		Map<String, IStressData> signals = symbiot.getSignals();
		Iterator<Map.Entry<String, IStressData>> iterator = signals.entrySet().iterator();
		while( iterator.hasNext()) {
			Map.Entry<String, IStressData> entry = iterator.next();
			if( entry.getKey().equals(symbiot.getId()) || (!this.inclusive && !symbiot.enableUpdate(entry.getKey())))
				continue;
			updateWeight(entry.getValue());	
		}
	}
}
