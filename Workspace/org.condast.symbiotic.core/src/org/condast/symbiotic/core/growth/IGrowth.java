package org.condast.symbiotic.core.growth;

import org.condast.symbiotic.core.collection.ISymbiotCollection;

public interface IGrowth {

	public static long DEFAULT_START = 10;

	public boolean spawn( ISymbiotCollection collection );

	public boolean prune( ISymbiotCollection collection );
}
