package org.condast.symbiotic.core.def;

import java.util.Map;

public interface ISymbiot{

	/**
	 * the default step for increasing or decreasing the weight of stress signals
	 */
	public static double DEFAULT_WEIGHT_STEP = 0.01d;

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
	 * If strict is false, then the actual stress is returned if the delta is zero. This is to prevent
	 * the system from not optimising if the delta is zero  
	 * @return
	 */
	double getDeltaStress( boolean strict);	

	public void addStressListener( IStressListener listener );
	public void removeStressListener( IStressListener listener );


	/**
	 * add a symbiot that influences the behaviour
	 * @param symbiot
	 */
	void addInfluence(ISymbiot symbiot);

	/**
	 * Get the stress data
	 * @param symbiot
	 * @return
	 */
	public IStressData getStressData(ISymbiot symbiot);

	/**
	 * Update the stress influence for influencing symbiots
	 * @param symbiot
	 */
	public void updateStress();

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
	Map<ISymbiot, IStressData> getSignals();

	/**
	 * Get the factor of the symbiot. This is defined as sigma( w.s)
	 */
	double getFactor();
}