package org.condast.symbiotic.core.def;

public interface IStressData {

	float getWeight();

	float getCurrentStress();

	void setData(float weight);

	float getDelta();

}