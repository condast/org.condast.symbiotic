package org.condast.symbiotic.core;

import org.condast.symbiotic.core.def.IOutputSymbiot;

public class OutputSymbiot<O extends Object> extends Symbiot implements IOutputSymbiot<O> {

	private O output;
	
	protected OutputSymbiot(String id) {
		super(id);
	}

	protected OutputSymbiot(String id, float step) {
		super(id, step);
	}

	protected OutputSymbiot(String id, float step, boolean active) {
		super(id, step, active);
	}

	@Override
	public O getOutput() {
		return output;
	}

	protected void setOutput(O output) {
		this.output = output;
	}
}
