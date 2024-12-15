package org.condast.symbiotic.core.environment;

public interface IEnvironmentListener<O extends Object> {

	public void notifyEnvironmentChanged( EnvironmentEvent<O> event);
}
