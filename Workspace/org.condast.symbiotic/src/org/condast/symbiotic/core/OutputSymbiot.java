package org.condast.symbiotic.core;

import org.condast.symbiotic.core.def.IOutputSymbiot;

public class OutputSymbiot<O extends Object> extends Symbiot implements IOutputSymbiot<O> {

	private O output;
	
	protected OutputSymbiot(String id) {
		super(id);
	}

	protected OutputSymbiot(String id, boolean active) {
		super(id, active);
	}

	@Override
	public O getOutput() {
		return output;
	}

	protected void setOutput(O output) {
		this.output = output;
	}
}
