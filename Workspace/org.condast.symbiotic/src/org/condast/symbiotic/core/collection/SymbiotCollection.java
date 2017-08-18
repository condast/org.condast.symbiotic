package org.condast.symbiotic.core.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.condast.symbiotic.core.IBehaviour;
import org.condast.symbiotic.core.Symbiot;
import org.condast.symbiotic.core.def.IStressListener;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.def.StressEvent;

public class SymbiotCollection implements Collection<ISymbiot>{

	private Collection<ISymbiot> symbiots;
	
	private IStressListener listener = new IStressListener(){

		@Override
		public void notifyStressChanged(StressEvent event) {
			for( ISymbiot symbiot: symbiots )
				symbiot.updateStressLevels(symbiot);
			notifyStressLevels(event);
		}
	};

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ISymbiot add( String id, IBehaviour<?,?> behaviour ){
		ISymbiot symbiot = new Symbiot( id, behaviour);
		this.add( symbiot );
		return symbiot;
	}

	public boolean add( ISymbiot symbiot ){
		symbiot.addStressListener(listener);
		return symbiots.add( symbiot);
	}

	public boolean remove( Object symbiot ){
		((ISymbiot) symbiot).removeStressListener(listener);
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
}
