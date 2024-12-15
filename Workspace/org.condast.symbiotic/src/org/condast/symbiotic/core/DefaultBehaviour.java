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
			if(( entry.getKey().equals(symbiot.getId()))) // || !symbiot.enableUpdate(entry.getKey()))
				continue;
			IStressData data = entry.getValue();
			double weight = data.getWeight();
			
			//stressDelta <=0 is good, because this means that the stress is decreasing
			double stressDelta = data.getDelta();

			//There is stress, so we need to do something about it
			//The delta is ALSO increasing, so we need to change direction
			if(( data.getStress() > 0 ) &&  ( stressDelta > 0 )) {
				//the weight is not right
				if( data.getWeight() > 0 )
					weight -= this.weightStep * data.getStress();
				else 
					weight += this.weightStep * data.getStress();
			}else {
				//No stress, or it is getting less, so keep things as they are for now
				//weight += this.weightStep* data.getStress();
			}
			weight = NumberUtils.clipRange(-1, 1, weight);
			data.setWeight(weight);			
		}
	}
}
