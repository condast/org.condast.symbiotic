package org.condast.symbiotic.core.transformer;

import java.util.Collection;
import java.util.Iterator;

import org.condast.symbiotic.core.def.ITransformer;

public class TransformerWrapper<I, O extends Object> implements ITransformer<I, O> {

	private ITransformer<I, O> transformer;
	
	protected TransformerWrapper( ITransformer<I, O> transformer) {
		this.transformer = transformer;
	}

	/**
	 * Allow clearing of the inputs
	 */
	public void clearInputs(){
		this.transformer.clearInputs();
	}

	@Override
	public boolean addInput(I input) {
		return this.transformer.addInput(input);
	}

	@Override
	public boolean removeInput(I input) {
		return this.transformer.removeInput( input);
	}

	@Override
	public Collection<I> getInputs() {
		return this.transformer.getInputs();
	}

	@Override
	public O transform(Iterator<I> inputs) {
		return this.transformer.transform(inputs);
	}
}
