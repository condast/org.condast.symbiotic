package org.condast.symbiotic.core.transformation;

import org.condast.symbiotic.core.def.ITransformer;

public interface IModelTransformer<M, I, O extends Object> extends ITransformer<I,O>{

	/**
	 * Get the model for this tranformation
	 * @return
	 */
	public M getModel();
}