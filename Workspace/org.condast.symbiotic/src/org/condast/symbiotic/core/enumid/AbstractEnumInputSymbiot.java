package org.condast.symbiotic.core.enumid;

import org.condast.symbiotic.core.def.IInputSymbiot;
import org.condast.symbiotic.core.def.ISymbiot;

public abstract class AbstractEnumInputSymbiot<E extends Enum<E>, I extends Object> extends EnumSymbiot<E> implements IInputSymbiot<I> {

	private I input;
	
	protected AbstractEnumInputSymbiot(E form) {
		super(form);
	}

	protected AbstractEnumInputSymbiot(E form, float step) {
		super(form, step);
	}

	protected AbstractEnumInputSymbiot(E form, float step, boolean active) {
		super(form, step, active);
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
