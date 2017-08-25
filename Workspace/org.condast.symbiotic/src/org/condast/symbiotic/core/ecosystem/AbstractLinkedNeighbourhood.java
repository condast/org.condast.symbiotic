package org.condast.symbiotic.core.ecosystem;

import java.util.ArrayList;
import java.util.Collection;

import org.condast.commons.Utils;
import org.condast.symbiotic.core.def.ITransformation;
import org.condast.symbiotic.core.transformation.ITransformListener;
import org.condast.symbiotic.core.transformation.TransformEvent;

public abstract class AbstractLinkedNeighbourhood<I,O extends Object> implements ITransformListener<I> {

	private Collection<ITransformation<O,?>> transformations;
	
	protected AbstractLinkedNeighbourhood() {
		this.transformations = new ArrayList<ITransformation<O,?>>();
	}

	public void addTransformation( ITransformation<O,?> transformation ){
		this.transformations.add(  transformation );
	}

	public void removeTransformation( ITransformation<O,?> transformation ){
		this.transformations.remove( transformation );
	}

	protected ITransformation<O,?> getTransformation( String id ) {
		if( Utils.assertNull(id))
			return null;
		for( ITransformation<O,?> transformation: this.transformations ){
			if( id.equals( transformation.getName() ))
				return transformation;
		}
		return null;
	}

	protected abstract void onChange( ITransformation<O,?> transformation, TransformEvent<I> event);
	
	@Override
	public void notifyChange(TransformEvent<I> event) {
		for( ITransformation<O,?> transformation: this.transformations )
			this.onChange(transformation, event);
	}
	
	

}
