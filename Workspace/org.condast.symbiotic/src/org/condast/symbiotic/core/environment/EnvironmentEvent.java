package org.condast.symbiotic.core.environment;

import java.util.EventObject;

import org.condast.symbiotic.core.environment.IEnvironmentListener.EventTypes;

public class EnvironmentEvent<D extends Object> extends EventObject {
	private static final long serialVersionUID = 1L;

	private EventTypes type;
	
	private D data;

	public EnvironmentEvent(Object arg0) {
		this( arg0, EventTypes.CHANGED, null );
	}

	public EnvironmentEvent(Object arg0, D data) {
		this( arg0, EventTypes.CHANGED, data );
	}

	public EnvironmentEvent(Object arg0, EventTypes type, D data ) {
		super(arg0);
		this.type = type;
		this.data = data;
	}

	public EventTypes getType() {
		return type;
	}

	public D getData() {
		return data;
	}
}
