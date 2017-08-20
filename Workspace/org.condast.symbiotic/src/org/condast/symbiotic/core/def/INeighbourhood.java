package org.condast.symbiotic.core.def;

public interface INeighbourhood<I,O extends Object> extends ITransformation<O,I> {

	public enum Types{
		IN,
		OUT;
	}
}
