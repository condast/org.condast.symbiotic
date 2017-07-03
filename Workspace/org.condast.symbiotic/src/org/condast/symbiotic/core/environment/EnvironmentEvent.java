package org.condast.symbiotic.core.environment;

import java.util.EventObject;

import org.condast.symbiotic.core.environment.IEnvironmentListener.EventTypes;

public class EnvironmentEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	private EventTypes type;

	public EnvironmentEvent(Object arg0) {
		this( arg0, EventTypes.CHANGED );
	}
	
	public EnvironmentEvent(Object arg0, EventTypes type ) {
		super(arg0);
		this.type = type;
	}

	public EventTypes getType() {
		return type;
	}

}
