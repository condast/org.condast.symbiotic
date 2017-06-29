package org.condast.symbiotic.core;

import java.util.EventObject;

public class TransformEvent<O extends Object> extends EventObject {
	private static final long serialVersionUID = 1L;

	private O output;
	
	public TransformEvent(Object arg0, O output) {
		super(arg0);
		this.output = output;
	}

	protected O getOutput() {
		return output;
	}
}
