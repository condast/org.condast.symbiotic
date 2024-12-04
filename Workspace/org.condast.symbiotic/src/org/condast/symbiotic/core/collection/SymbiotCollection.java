package org.condast.symbiotic.core.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.condast.commons.number.NumberUtils;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.def.IBehaviour;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;

public class SymbiotCollection implements ISymbiotCollection{

	private Collection<ISymbiot> symbiots;
	
	/**
	 * The average stress of all the symbiots
	 */
	private double stress;

	private double weightStep; //The increase or decrease of stress per cycle

	public SymbiotCollection(  ) {
		this( DEFAULT_WEIGHT_STEP);
	}
	
	public SymbiotCollection( double weightStep ) {
		this.stress = 0;
		this.weightStep = weightStep;
		symbiots = new ArrayList<ISymbiot>();
	}

	public double getWeightStep() {
		return weightStep;
	}

	/**
	 * returns the symbiot with the given identifier
	 * @param identifier
	 * @return
	 */
	@Override
	public ISymbiot get( String identifier ) {
		for( ISymbiot symbiot: symbiots ) {
			if( symbiot.getId().equals(identifier))
				return symbiot;
		}
		return null;
	}
	
	/**
	 *Get the overall stress levels of all the symbiots in the collection
	 * @return
	 */
	@Override
	public Map<String, Map<String,Double>> getStress(){
		Map<String, Map<String,Double>> results = new HashMap<String, Map<String,Double>>();
		for( ISymbiot symbiot: symbiots ){
			Map<String, Double> stress = new HashMap<>();
			for( ISymbiot child: symbiots ){
				Double strss = symbiot.getStressData( child.getId() ).getStress();
				stress.put( child.getId(), strss);
				symbiot.getStressData( symbiot.getId() );
			}
			results.put(symbiot.getId(), stress);
			symbiot.update();
		}
		return results;
	}

	/**
	 *Get the average stress levels of all the symbiots in the collection
	 * @return
	 */
	@Override
	public double getAverageStress(){
		return this.stress;
	}

	/**
	 * Get the cumulated stress from the symbiots
	 * @return
	 */
	@Override
	public Map<String,Double> getCumultatedStress(){
		Map<String, Double> stress = new HashMap<>();
		for( ISymbiot symbiot: this.symbiots )
			stress.put( symbiot.getId(), symbiot.getStress() );
		return stress;
	}

	public ISymbiot add( String id, IBehaviour behaviour ){
		ISymbiot symbiot = new Symbiot( id );
		behaviour.setOwner(symbiot);
		this.add( symbiot );
		return symbiot;
	}

	@Override
	public boolean add( ISymbiot symbiot ){
		return symbiots.add( symbiot);
	}

	public boolean remove( Object symbiot ){
		return symbiots.remove( symbiot );
	}

	public int size(){
		return symbiots.size();
	}

	@Override
	public boolean addAll(Collection<? extends ISymbiot> c) {
		boolean retval = true;
		for( ISymbiot symbiot: c )
			retval &= add( symbiot );
		return retval;
	}

	@Override
	public void clear() {
		symbiots.clear();
	}

	@Override
	public boolean contains(Object o) {
		return symbiots.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return symbiots.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return symbiots.isEmpty();
	}

	@Override
	public Iterator<ISymbiot> iterator() {
		return symbiots.iterator();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean retval = true;
		for( Object symbiot: c )
			retval &= remove( symbiot );
		return retval;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return symbiots.retainAll(c);
	}

	@Override
	public Object[] toArray() {
		return symbiots.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return symbiots.toArray(a);
	}

	/**
	 * Update the 
	 * @param reference
	 * @param dt
	 */
	protected void updateStress(ISymbiot source ) {
		Map<String, IStressData> signals = source.getSignals();
		Iterator<Map.Entry<String, IStressData>> iterator = signals.entrySet().iterator();
		while( iterator.hasNext()) {
			Map.Entry<String, IStressData> entry = iterator.next();
			if( entry.getKey().equals(source.getId()))
				continue;
			IStressData data = entry.getValue();
			double weight = data.getWeight();
			//stressDelta <=0 is good, because this means that the stress is decreasing
			if( data.getDelta() <= 0 )
				weight -= this.weightStep* data.getStress();
			else
				weight += this.weightStep* data.getStress();
			weight = NumberUtils.clipRange(-1, 1, weight);
			data.setWeight(weight);		
		}
	}

	/**
	 * update the symbiots
	 */
	@Override
	public void updateSymbiots() {
		if(( symbiots == null ) || symbiots.isEmpty())
			return;
		double result = 0;

		Iterator<ISymbiot> iterator = symbiots.iterator();
		while( iterator.hasNext() ) {
			ISymbiot source = iterator.next();
			result += source.getStress();
			updateStress( source );
			source.update();
		}
		result/=symbiots.size();
		this.stress= result;
	}
	
	public static double getStress( double reference ) {
		double result = reference;
		if( Math.abs( reference )< Double.MIN_VALUE ) {
			result = ( reference < 0)? -Double.MIN_VALUE: Double.MIN_VALUE;			
		}
		return result;
	}

}