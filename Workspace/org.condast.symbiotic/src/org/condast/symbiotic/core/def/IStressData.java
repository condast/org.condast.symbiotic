package org.condast.symbiotic.core.def;

public interface IStressData {

	String getReference();
	
	double getWeight();

	void setWeight( double weight );

	double getStress();

	double getDelta();

	void update();
}