package org.condast.symbiotic.ecosystem.environment;

public interface IEnvironmentListener<O extends Object> {

	public void notifyEnvironmentChanged( EnvironmentEvent<O> event);
}
