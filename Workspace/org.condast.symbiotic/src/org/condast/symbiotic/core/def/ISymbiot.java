package org.condast.symbiotic.core.def;

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

	double increaseStress();

	double decreaseStress();

	/**
	 * Get the overall stress
	 * @param symbiot
	 * @return
	 */
	public IStressData getStressData(ISymbiot symbiot);

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
}