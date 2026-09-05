package org.condast.symbiotic.core.growth;

import java.util.Iterator;

import org.condast.commons.Utils;
import org.condast.symbiotic.core.collection.ISymbiotCollection;
import org.condast.symbiotic.core.def.ISymbiot;

public class DefaultGrowth implements IGrowth {

	private long start;
	
	private int threshold_percent;
	
	public DefaultGrowth() {
		this( DEFAULT_START, HiddenSymbiot.DEFAULT_THRESHOLD_PERCENT);
	}

	public DefaultGrowth(long start, int threshold_percent) {
		super();
		this.start = start;
		this.threshold_percent = threshold_percent;
	}

	@Override
	public boolean spawn(ISymbiotCollection symbiots) {
		long counter = symbiots.getCounter();
		if(( counter < start) || Utils.assertNull(symbiots))
			return false;
		return true;

	}

	@Override
	public boolean prune(ISymbiotCollection symbiots) {
		long counter = symbiots.getCounter();
		if(( counter < start) || Utils.assertNull(symbiots))
			return false;
		Iterator<ISymbiot> iterator = symbiots.iterator();
		boolean retval =  false;
		while( iterator.hasNext()) {
			ISymbiot symbiot = iterator.next();
			if( HiddenSymbiot.isIsolated( symbiot, this.threshold_percent)) {
				symbiots.remove(symbiot);
				retval = true;
			}
		}
		return retval;
	}

}
