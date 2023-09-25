package org.condast.symbiotic.core;

import org.condast.symbiotic.core.def.IInputSymbiot;
import org.condast.symbiotic.core.def.ISymbiot;

public abstract class AbstractInputSymbiot<I extends Object> extends Symbiot implements IInputSymbiot<I> {

	private I input;
	
	protected AbstractInputSymbiot(String id) {
		super(id);
	}

	protected AbstractInputSymbiot(String id, float step) {
		super(id, step);
	}

	protected AbstractInputSymbiot(String id, float step, boolean active) {
		super(id, step, active);
	}

	@Override
	public I getInput() {
		return input;
	}

	@Override
	public void setInput(I input) {
		this.input = input;
		this.updateStress(input);
	}

	/**
	 * update the stress based on the input that is provided
	 * @param input
	 * @return
	 */
	protected abstract boolean updateStress( I input );

	/**
	 * input symbiots by default do not react to stress signals form other symbiots
	 */
	@Override
	protected boolean enableSymbiot(ISymbiot reference) {
		return false;
	}
}