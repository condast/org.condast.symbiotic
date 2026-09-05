package org.condast.symbiotic.core.def;

import java.util.Map;

public interface ISymbiot{

	String getId();

	/**
	 * if the symbiot is hidden, then it doesn't have I/O and cannot be pruned
	 * @return
	 */
	boolean isHidden();

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
	void setStress(double stress);

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
	void addInfluence( ISymbiot reference);

	/**
	 * add a symbiot that influences the behaviour, and provide an initial weight factor
	 * @param symbiot
	 */
	void addInfluence(ISymbiot target, double initWeight);

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
	 */
	double getFactor();

	/**
	 * Returns true if the symbiot is isolated from the others, based on the given threshold factor <0,..1>
	 */
	boolean isIsolated(double threshold);

	/**
	 * If true, then the stress data for the given reference will be updated
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