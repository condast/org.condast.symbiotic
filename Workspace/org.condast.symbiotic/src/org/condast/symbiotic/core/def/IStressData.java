package org.condast.symbiotic.core.def;

public interface IStressData {

	String getReference();
	
	double getWeight();

	void setWeight( double weight );
	
	double getCurrentStress();

	double getDelta();

	double getStress();

	void update();
}