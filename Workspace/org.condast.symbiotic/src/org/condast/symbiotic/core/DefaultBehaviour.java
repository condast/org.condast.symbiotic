package org.condast.symbiotic.core;

import java.util.Iterator;
import java.util.Map;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.def.IBehaviour;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

public class DefaultBehaviour implements IBehaviour {

	private double weightStep; //The increase or decrease of stress per cycle

	public DefaultBehaviour(double weightStep) {
		super();
		this.weightStep = weightStep;
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
			if( entry.getKey().equals(symbiot.getId()))
				continue;
			IStressData data = entry.getValue();
			double weight = data.getWeight();
			//stressDelta <=0 is good, because this means that the stress is decreasing
			if( data.getDelta() <= 0 )
				weight -= this.weightStep* data.getStress();
			else
				weight += this.weightStep* data.getStress();
			weight = NumberUtils.clipRange(-1, 1, weight);
			data.setWeight(weight);		
		}
	}

}
