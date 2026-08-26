package org.condast.symbiot.core;

import java.util.EventObject;

public class OrganismEvent extends EventObject {
	private static final long serialVersionUID = 1L;
	
	public OrganismEvent(IOrganism source) {
		super(source);
	}

	public IOrganism getOrganism() {
		return (IOrganism) super.getSource();
	}
}
