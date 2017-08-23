package org.condast.symbiotic.core.transformer;

import org.condast.symbiotic.core.def.ITransformer;

public interface IModelTransformer<M, I, O extends Object> extends ITransformer<I,O>{

	/**
	 * Get the model for this tranformation
	 * @return
	 */
	public M getModel();
}