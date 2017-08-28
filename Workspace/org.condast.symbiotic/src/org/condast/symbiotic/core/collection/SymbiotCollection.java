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

public class SymbiotCollection implements Collection<ISymbiot>{

	private Collection<ISymbiot> symbiots;
	
	/**
	 * Listeners to a change in the stress levels
	 */
	private Collection<IStressListener> listeners;

	public SymbiotCollection() {
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
	 * Get the cumulated stress from the symbiots
	 * @return
	 */
	public Map<String,Float> getCumultatedStress(){
		Map<String, Float> stress = new HashMap<String, Float>();
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
	 *Get the overall stress levels of all the symbiots in the collection
	 * @return
	 */
	public Map<String, Map<String,Float>> getStress(){
		Map<String, Map<String,Float>> results = new HashMap<String, Map<String,Float>>();
		for( ISymbiot symbiot: symbiots ){
			Map<String, Float> stress = new HashMap<String, Float>();
			for( ISymbiot child: symbiots ){
				Float strss = symbiot.getStressData( child ).getCurrentStress();
				stress.put( child.getId(), strss);
				symbiot.getStressData( symbiot );
			}
			results.put(symbiot.getId(), stress);
		}
		return results;
	}
}
