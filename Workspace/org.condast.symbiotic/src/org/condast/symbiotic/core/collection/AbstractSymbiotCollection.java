package org.condast.symbiotic.core.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.def.IBehaviour;
import org.condast.symbiotic.core.def.IStressListener;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.def.StressEvent;

public abstract class AbstractSymbiotCollection implements ISymbiotCollection{

	private Collection<ISymbiot> symbiots;
	private double averageStress;
	private double deltaStress;//current stress - previous stress

	/**
	 * Listeners to a change in the stress levels
	 */
	private Collection<IStressListener> listeners;

	public AbstractSymbiotCollection() {
		this.averageStress = 0;
		this.deltaStress = 0;
		symbiots = new ArrayList<ISymbiot>();
		listeners = new ArrayList<IStressListener>();
	}

	public void addStressListener(IStressListener listener) {
		this.listeners.add(listener );
	}

	public void removeStressListener(IStressListener listener) {
		this.listeners.remove( listener );
	}

	protected void notifyStressLevels( StressEvent event){
		for( IStressListener listener: listeners )
			listener.notifyStressChanged( event );
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
				Double strss = symbiot.getStressData( child ).getCurrentStress();
				stress.put( child.getId(), strss);
				symbiot.getStressData( symbiot );
			}
			results.put(symbiot.getId(), stress);
		}
		return results;
	}

	/**
	 * Current stress - previous stress
	 * @return
	 */
	@Override
	public double getDeltaStress() {
		return deltaStress;
	}

	/**
	 *Get the average stress levels of all the symbiots in the collection
	 * @return
	 */
	@Override
	public double getAverageStress(){
		double result = 0;
		for( ISymbiot symbiot: symbiots )
			result += symbiot.getStress();
		return result/symbiots.size();
	}

	/**
	 *Get the overall stress levels of all the symbiots in the collection
	 * @return
	 */
	@Override
	public Map<String, Double> getOverallWeight(){
		Map<String, Double> weights = new HashMap<>();
		for( ISymbiot symbiot: symbiots ){
			Double weight = symbiot.getOverallWeight();
			weights.put( symbiot.getId(), weight);
			symbiot.getStressData( symbiot );
		}
		return weights;
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
	 * Update the given symbiot, based on the reference symbiot
	 * @param symbiots
	 */
	public abstract void updateSymbiot( ISymbiot symbiot, ISymbiot reference );

	/**
	 * update the current
	 */
	@Override
	public void updateSymbiots() {
		if(( symbiots == null ) || symbiots.isEmpty())
			return;

		Iterator<ISymbiot> iterator = symbiots.iterator();
		while( iterator.hasNext() ) {
			ISymbiot source = iterator.next();
			symbiots.forEach( s->{
				if( !source.equals(s))
					updateSymbiot(source, s);
			});	
		}
		double stress=  getAverageStress();
		this.deltaStress = this.averageStress - stress;
		this.averageStress = stress;
	}
}