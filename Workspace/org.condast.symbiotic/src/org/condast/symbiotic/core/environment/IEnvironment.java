package org.condast.symbiotic.core.environment;

import java.util.Iterator;

public interface IEnvironment<O> {

	int getX();

	int getY();

	void addListener(IEnvironmentListener<O> listener);

	void removeListener(IEnvironmentListener<O> listener);

	void clear();

	ILocation get(int x, int y);

	Iterator<ILocation> iterator();

	void init(int amountFood);

	void update();

}