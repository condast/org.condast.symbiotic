package org.condast.symbiotic.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.condast.symbiotic.def.ISymbiot;

public abstract class AbstractBehaviour<I,O extends Object> implements IBehaviour<I,O> {

	public static final int DEFAULT_RANGE = 10;
	
	private Map<ISymbiot, Float> symbiots;
	private boolean includeOwner;
	private ISymbiot owner;
	private int range;

	protected AbstractBehaviour() {
		this( DEFAULT_RANGE, false );
	}
	
	protected AbstractBehaviour( int range, boolean includeOwner ) {
		symbiots = new HashMap<ISymbiot, Float>();
		this.range = range;
		this.includeOwner = includeOwner;
	}

	@Override
	public int getRange() {
		return range;
	}

	@Override
	public ISymbiot getOwner() {
		return owner;
	}

	@Override
	public void setOwner(ISymbiot owner) {
		this.owner = owner;
	}

	protected abstract float onUpdate( ISymbiot symbiot, float currentStress );
	
	@Override
	public boolean updateStress(ISymbiot symbiot) {
		if( !symbiot.isActive() ){
			symbiots.remove(symbiot);
			return false;
		}else if( !this.includeOwner && ( this.owner.equals( symbiot ))){
			return false;
		}
		symbiots.put(symbiot, onUpdate( symbiot, symbiots.get( symbiot )));
		return true;
	}

	protected abstract O onUpdateValue( ISymbiot symbiot, O current );

	@Override
	public O calculate(I input) {
		Iterator<Map.Entry<ISymbiot, Float>> iterator = symbiots.entrySet().iterator();
		O retval = null;
		while( iterator.hasNext()){
			Map.Entry<ISymbiot, Float> entry = iterator.next();
			retval = onUpdateValue( entry.getKey(), retval);
		}
		return retval;
	}

}
