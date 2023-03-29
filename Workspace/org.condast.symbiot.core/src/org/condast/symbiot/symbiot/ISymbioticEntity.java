package org.condast.symbiot.symbiot;

import java.util.Collection;

import org.condast.symbiotic.core.def.ISymbiot;

public interface ISymbioticEntity<D extends Object> extends ISymbiot{

	D getData();

	/**
	 * Update the cell based on the other symbiots
	 * @param symbiots
	 */
	void update(Collection<ISymbiot> symbiots);

}