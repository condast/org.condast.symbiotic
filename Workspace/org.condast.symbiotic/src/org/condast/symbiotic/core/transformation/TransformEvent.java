package org.condast.symbiotic.core.transformation;

import java.util.EventObject;

public class TransformEvent<O extends Object> extends EventObject {
	private static final long serialVersionUID = 1L;

	private O output;
	public boolean accept;
	
	public TransformEvent(Object arg0, O output) {
		super(arg0);
		this.output = output;
		this.accept = false;
	}

	/**
	 * Add a flag to tell the sender that the output has been accepted.
	 * This allows for further handling of the package
	 * @return
	 */
	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public O getOutput() {
		return output;
	}
}
