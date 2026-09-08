package org.condast.symbiotic.core.special;

import org.condast.symbiotic.core.Symbiot;

/**
 * A neuron is one of the simplest symbiots, where the factor determines the stress signal that is output to the system
 * @param <E>
 * @param <I>
 * @param <O>
 */
public class Neuron extends Symbiot{

	public Neuron( String formId) {
		this( formId, true);
	}

	public Neuron( String formId, boolean active) {
		super( formId, active);
	}

	/**
	 * The stress of a neuron is simply its factor
	 */
	@Override
	public void update() {
		super.update();		
		double factor = super.getFactor();
		super.setStress(factor);
	}
}