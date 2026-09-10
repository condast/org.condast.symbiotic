package org.condast.symbiotic.core.def;

import java.util.Map;

public interface ISymbiot{

	String getId();

	/**
	 * Returns true if this symbiot is active. If not, the
	 * symbiot will not be included in the calculations
	 * and the strategy is not updated
	 * @return
	 */
	public boolean isActive();

	/**
	 * clear the stress and stress data
	 */
	void clear();
	
	void clearStress();

	/**
	 * A symbiot emits a stress signal
	 * @return
	 */
	double getStress();

	/**
	 * Get the (previous stress - current stress) as determined by the last setStress operation
	 * @return
	 */
	double getDeltaStress();	

	/**
	 * Listen to the dynamic stress behaviour
	 * @param listener
	 */
	public void addStressListener( IStressListener listener );
	public void removeStressListener( IStressListener listener );


	/**
	 * add a symbiot that influences the behaviour
	 * @param symbiot
	 */
	IStressData addInfluence( ISymbiot reference);

	/**
	 * add a symbiot that influences the behaviour, and provide an initial weight factor
	 * @param symbiot
	 */
	IStressData addInfluence(ISymbiot target, double initWeight);

	/**
	 * Remove a symbiot that influences the behaviour
	 * @param symbiot
	 */
	void removeInfluence( ISymbiot reference);

	/**
	 * Get the stress data
	 * @param symbiot
	 * @return
	 */
	public IStressData getStressData(String reference);

	/**
	 * Get the overall stress
	 * @param symbiot
	 * @return
	 */
	double getOverallStress();

	/**
	 * Get the overall weight
	 * @param symbiot
	 * @return
	 */
	double getOverallWeight();

	/**
	 * Get the signals that the symbiot uses for stress strategies. This is mainly used by the
	 * symbiot collection
	 * @return
	 */
	Map<String, IStressData> getSignals();

	/**
	 * Get the factor of the symbiot. This is defined as sigma( w.s)
	 * THIS IS THE DEFAULT OUTPUT OF A SYMBIOT 
	 */
	double getFactor();

	/**
	 * Update the stress influence for influencing symbiots
	 * @param symbiot
	 */
	public void update();
}