package org.condast.symbiotic.core.enumid;

import org.condast.symbiotic.core.def.IOutputSymbiot;

public class EnumOutputSymbiot<E extends Enum<E>, O extends Object> extends EnumSymbiot<E> implements IOutputSymbiot<O> {

	private O output;
	
	protected EnumOutputSymbiot(E form) {
		super(form);
	}

	protected EnumOutputSymbiot(E form, float step) {
		super(form, step);
	}

	protected EnumOutputSymbiot(E form, float step, boolean active) {
		super(form, step, active);
	}

	@Override
	public O getOutput() {
		return output;
	}

	protected void setOutput(O output) {
		this.output = output;
	}
}
