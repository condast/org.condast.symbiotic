package org.condast.symbiotic.core.def;

public interface IStressData {

	ISymbiot getReference();
	
	double getWeight();

	void setWeight( double weight );
	
	double getCurrentStress();

	double getDelta();

	void update();

}