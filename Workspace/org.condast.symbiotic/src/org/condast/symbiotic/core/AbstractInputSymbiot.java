package org.condast.symbiotic.core;

import org.condast.symbiotic.core.def.IInputSymbiot;

public abstract class AbstractInputSymbiot<I extends Object> extends Symbiot implements IInputSymbiot<I> {

	private I input;
	
	public AbstractInputSymbiot(String id) {
		super(id);
	}

	public AbstractInputSymbiot(String id, float step) {
		super(id, step);
	}

	public AbstractInputSymbiot(String id, float step, boolean active) {
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
}
