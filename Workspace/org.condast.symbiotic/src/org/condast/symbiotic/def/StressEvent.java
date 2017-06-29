package org.condast.symbiotic.def;

import java.util.EventObject;

public class StressEvent extends EventObject {
	private static final long serialVersionUID = 1L;

	public StressEvent( ISymbiot symbiot) {
		super(symbiot);
	}

}
