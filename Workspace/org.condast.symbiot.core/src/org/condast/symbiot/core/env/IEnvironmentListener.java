package org.condast.symbiot.core.env;

public interface IEnvironmentListener<O extends Object> {

	public void notifyEnvironmentChanged( EnvironmentEvent<O> event);
}
