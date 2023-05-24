package org.condast.symbiotic.core.def;

public interface IStressData {

	double getWeight();

	double getCurrentStress();

	void setData(double weight);

	double getDelta();

}