package org.condast.symbiotic.core.environment;

import java.util.EventObject;

public class EnvironmentEvent<O> extends EventObject {
	private static final long serialVersionUID = 1L;
	private O organism;
	
	public EnvironmentEvent(IEnvironment<O> source, O organism) {
		super(source);
		this.organism = organism;
	}

	public O getOrganism() {
		return organism;
	}
}
