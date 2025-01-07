package org.condast.symbiotic.core;

import java.util.Iterator;
import java.util.Map;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IBehaviour;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

public class DefaultBehaviour implements IBehaviour {

	private double weightStep; //The increase or decrease of stress per cycle
	
	private boolean inclusive;
	
	public DefaultBehaviour(double weightStep) {
		this( weightStep, false );
	}
	
	public DefaultBehaviour(double weightStep, boolean inclusive) {
		super();
		this.weightStep = weightStep;
		this.inclusive = inclusive;
	}
	@Override
	public boolean isInclusive() {
		return inclusive;
	}

	@Override
	public double getWeightStep() {
		return weightStep;
	}

	@Override
	public void updateSymbiot(ISymbiot symbiot) {
		Map<String, IStressData> signals = symbiot.getSignals();
		Iterator<Map.Entry<String, IStressData>> iterator = signals.entrySet().iterator();
		while( iterator.hasNext()) {
			Map.Entry<String, IStressData> entry = iterator.next();
			if( entry.getKey().equals(symbiot.getId()) || (!this.inclusive && !symbiot.enableUpdate(entry.getKey())))
				continue;
			IStressData data = entry.getValue();
			double weight = data.getWeight();
			
			//stressDelta <=0 is good, because this means that the stress is decreasing
			double stressDelta = data.getDelta();
			if( weight < 0) {
				weight += this.weightStep * stressDelta;
			}else {
				weight -= this.weightStep * stressDelta;
			}
			weight = NumberUtils.clipRange(-1, 1, weight);
			data.setWeight(weight);			
		}
	}
}
