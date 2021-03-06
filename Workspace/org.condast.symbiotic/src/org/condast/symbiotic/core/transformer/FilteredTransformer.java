package org.condast.symbiotic.core.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.condast.symbiotic.core.def.ITransformer;
import org.condast.symbiotic.core.filter.ITransformFilter;

public class FilteredTransformer<I, O extends Object> extends TransformerWrapper<I, O> {

	private Collection<ITransformFilter<I,O>> filters;

	public FilteredTransformer() {
		this( null );	
	}
	
	public FilteredTransformer( ITransformer<I,O> transformer ) {
		super( transformer );
		filters = new ArrayList<ITransformFilter<I,O>>();
	}

	public void addFilter( ITransformFilter<I,O> filter ){
		this.filters.add( filter );
	}

	public void removeFilter( ITransformFilter<I,O> filter ){
		this.filters.remove( filter);
	}
	
	@Override
	public boolean addInput(I input) {
		for( ITransformFilter<I,O> filter: filters ){
			if( !filter.accept(input))
				return false;
		}
		return super.addInput(input);
	}

	/**
	 * Put a filter on each transformation. If true, the transformer is
	 * candidate for transformation. If false, the the transformation will stop
	 * and the output thus far will be returned
	 * @param transformer
	 * @return
	 */	
	@Override
	public O transform(Iterator<I> inputs) {
		O output = null;
		for( ITransformFilter<I,O> filter: filters ){
			if( !filter.acceptTransform(inputs))
				return null;
			output = super.transform(inputs);
			if( output != null )
				return output;
		}
		return output;
	}
}
