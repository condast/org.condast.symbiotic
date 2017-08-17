package org.condast.symbiotic.core.ecosystem;

import java.util.ArrayList;
import java.util.Collection;

import org.condast.symbiotic.core.def.IActor;
import org.condast.symbiotic.core.def.INeighbourhood;

public class EcoSystem<I,O extends Object> {

	private Collection<IActor<I,O>> actors;
	private Collection<INeighbourhood<I,O>> neighbourhoods;

	public EcoSystem() {
		actors = new ArrayList<>();
		neighbourhoods = new ArrayList<>();
	}
	
	public void addActor( IActor<I,O> actor ){
		actors.add(actor);
	}

	public void removeActor( IActor<I,O> actor ){
		actors.remove(actor);
	}

	protected void addNeighbourhood( IActor<I,O> actor, INeighbourhood<I,O> neighbourhood ){
		actor.addInput( neighbourhood.getOutput() );
		this.neighbourhoods.add( neighbourhood );
	}

	protected void removeNeighbourhood( IActor<I,O> actor, INeighbourhood<I,O> neighbourhood ){
		actor.removeInput( neighbourhood.getOutput() );
		this.neighbourhoods.remove( neighbourhood );
	}
}
