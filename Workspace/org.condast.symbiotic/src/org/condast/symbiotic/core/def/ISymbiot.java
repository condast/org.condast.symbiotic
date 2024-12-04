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

	void clearStress();

	/**
	 * A symbiot emits a stress signal
	 * @return
	 */
	double getStress();
	void setStress(double stress);

	/**
	 * Get the (previous stress - current stress) as determined by the last setStress operation
	 * @return
	 */
	double getDeltaStress();	

	public void addStressListener( IStressListener listener );
	public void removeStressListener( IStressListener listener );


	/**
	 * add a symbiot that influences the behaviour
	 * @param symbiot
	 */
	void addInfluence( ISymbiot reference);

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
	 */
	double getFactor();

	/**
	 * If true, then the stress data for the given refeence will be updated
	 * @param reference
	 * @return
	 */
	public boolean enableUpdate( String reference );
	
	/**
	 * Update the stress influence for influencing symbiots
	 * @param symbiot
	 */
	public void update();
}