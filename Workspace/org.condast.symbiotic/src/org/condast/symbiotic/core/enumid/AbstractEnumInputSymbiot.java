package org.condast.symbiotic.core.enumid;

import org.condast.symbiotic.core.def.IInputSymbiot;

public abstract class AbstractEnumInputSymbiot<E extends Enum<E>, I extends Object> extends EnumSymbiot<E> implements IInputSymbiot<I> {

	private I input;
	
	protected AbstractEnumInputSymbiot(E form) {
		super(form);
	}

	protected AbstractEnumInputSymbiot(E form, boolean active) {
		super(form, active);
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
	public boolean enableUpdate(String reference) {
		return false;
	}
}
