package org.condast.symbiot.core.env;

import java.util.EventObject;

public class EnvironmentEvent<O> extends EventObject {
	private static final long serialVersionUID = 1L;
	private O organism;
	
	public EnvironmentEvent(Environment source, O organism) {
		super(source);
		this.organism = organism;
	}

	public O getOrganism() {
		return organism;
	}
}
