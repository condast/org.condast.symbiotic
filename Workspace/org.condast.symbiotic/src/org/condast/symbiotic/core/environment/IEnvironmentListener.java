package org.condast.symbiotic.core.environment;

public interface IEnvironmentListener {

	public enum EventTypes{
		INITIALSED,
		CHANGED;
	}
	
	public void notifyEnvironmentChanged( EnvironmentEvent event );
}
