package org.condast.symbiotic.core.def;

public interface IStressData {

	String getReference();

	/**
	 * Get the current stress of the symbiot
	 * @return
	 */
	double getStress();

	/**
	 * Get the current stress minus the previous
	 * @return
	 */
	double getDelta();


	double getWeight();

	void setWeight( double weight );

	void update();
}