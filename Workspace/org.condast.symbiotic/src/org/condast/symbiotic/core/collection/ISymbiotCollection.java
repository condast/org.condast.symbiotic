package org.condast.symbiotic.core.collection;

import java.util.Collection;
import java.util.Map;

import org.condast.symbiotic.core.def.ISymbiot;

public interface ISymbiotCollection extends Collection<ISymbiot>{

	/**
	 * Get the cumulated stress from the symbiots
	 * @return
	 */
	Map<String, Double> getCumultatedStress();

	boolean add(ISymbiot symbiot);

	void clear();

	/**
	 *Get the overall stress levels of all the symbiots in the collection
	 * @return
	 */
	Map<String, Map<String, Double>> getStress();

	/**
	 *Get the overall stress levels of all the symbiots in the collection
	 * @return
	 */
	Map<String, Double> getOverallWeight();
	
	/**
	 *Get the average stress levels of all the symbiots in the collection
	 * @return
	 */
	double getAverageStress();

	/**
	 * Current stress - previous stress
	 * @return
	 */
	double getDeltaStress();

	/**
	 * Update the cell based on the other symbiots
	 * @param symbiots
	 */
	void updateSymbiots();
}