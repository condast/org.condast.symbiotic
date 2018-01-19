package org.condast.symbiotic.core.environment;

public interface IEnvironmentListener<D extends Object> {

	public enum EventTypes{
		INITIALSED,
		CHANGED,
		OUT_OF_BOUNDS;
	}
	
	public void notifyEnvironmentChanged( EnvironmentEvent<D> event );
}
