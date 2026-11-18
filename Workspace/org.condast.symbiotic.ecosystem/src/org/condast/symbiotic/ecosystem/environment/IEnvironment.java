package org.condast.symbiotic.ecosystem.environment;

import java.util.Iterator;

public interface IEnvironment<O> {

	//The length of the x-axis
	int getLength();

	//The length of the y-axis
	int getWidth();

	//The border of the environement
	ILocation getBorder();

	void addListener(IEnvironmentListener<O> listener);

	void removeListener(IEnvironmentListener<O> listener);

	void clear();

	ILocation get(int x, int y);

	Iterator<ILocation> iterator();

	void init(int amountFood);

	void update();

	/**
	 * \Get or set the organism
	 * @return
	 */
	O getOrganism();	
	void setOrganism( O organism );

	ILocation getNearestFood(int x, int y);

	boolean noFood();
}