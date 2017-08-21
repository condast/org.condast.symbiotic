package org.condast.symbiotic.core.transformation;

import java.util.ArrayList;
import java.util.Collection;

import org.condast.symbiotic.core.def.ITransformer;

public abstract class AbstractTransformer<I, O extends Object> implements ITransformer<I, O> {

	private Collection<I> inputs;
	
	protected AbstractTransformer() {
		inputs = new ArrayList<I>();
	}

	/**
	 * Allow clearing of the inputs
	 */
	public void clearInputs(){
		this.inputs.clear();
	}

	@Override
	public boolean addInput(I input) {
		return inputs.add(input);
	}

	@Override
	public boolean removeInput(I input) {
		return inputs.remove( input);
	}

	@Override
	public Collection<I> getInputs() {
		return inputs;
	}
}