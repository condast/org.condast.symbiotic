package org.condast.symbiotic.core.transformation;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.condast.symbiotic.core.def.ITransformer;

public abstract class AbstractTimedTransformer<M, I, O, B extends Object> implements ITransformer<Map.Entry<I,Date>, O> {

	//The inputs are augmented with an end time) to completion
	private Map<I, Date> times;

	public AbstractTimedTransformer( ) {
		this.times = new HashMap<I, Date>();
	}

	/**
	 * add the current time to to the input
	 */
	public boolean addInput( I input, Date completion ) {
		this.times.put( input, completion );
		return true;
	}

	protected abstract O onCompleted( I input );

	@Override
	public O transform( Iterator<Map.Entry<I, Date>> inputs) {
		Date now = Calendar.getInstance().getTime();
		O retval = null;
		while( inputs.hasNext() ){
			Map.Entry<I, Date> entry = inputs.next();
			if( times.get( entry.getValue() ).after( now ))
				continue;
			times.remove( entry.getKey());
			retval = onCompleted(entry.getKey());
		}
		return retval;
	}

	@Override
	public boolean addInput( Map.Entry<I, Date> input) {
		return true;
	}

	@Override
	public boolean removeInput( Map.Entry<I, Date> input) {
		return true;
	}
}