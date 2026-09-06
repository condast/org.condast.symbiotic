package org.condast.symbiotic.ui.environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.condast.symbiot.design.Organism2D;
import org.condast.symbiotic.ecosystem.environment.EnvironmentEvent;
import org.condast.symbiotic.ecosystem.environment.IEnvironment;
import org.condast.symbiotic.ecosystem.environment.ILocation;
import org.condast.symbiotic.ecosystem.organism.IOrganism;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class EnvironmentCanvas<E extends Enum<E>> extends Canvas{
	private static final long serialVersionUID = 1L;

	public static final int GRIDX = 100;//meters
	public static final int GRIDY = 20;//meters

	public static final int DEFAULT_TRAIL_FADE = 150;//meters

	private IEnvironment<IOrganism<E>> environment;
	
	private boolean disposed;
	
	private boolean isTrail;
	private boolean isFade;
	private int fadeValue;
	
	private List<TrailData> trail;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public EnvironmentCanvas(Composite parent, Integer style) {
		super(parent, style);
		this.disposed = false;
		this.isTrail = false;
		this.isFade = false;
		this.fadeValue = DEFAULT_TRAIL_FADE;
		setBackground(Display.getCurrent().getSystemColor( SWT.COLOR_WHITE));
		trail = new ArrayList<>();
		this.isTrail = false;
		super.addPaintListener( e->onPaintControl(e));
	}

	public boolean isTrail() {
		return isTrail;
	}

	public void setTrail(boolean isTrail) {
		this.isTrail = isTrail;
		if(!isTrail )
			trail.clear();
	}

	
	public boolean isFade() {
		return isFade;
	}

	public void setFade(boolean isFade) {
		this.isFade = isFade;
	}

	public int getFadeValue() {
		return fadeValue;
	}

	public void setFadeValue(int fadeValue) {
		this.fadeValue = fadeValue;
	}

	@Override
	public Composite getParent(){
		return super.getParent();
	}

	private void onPaintControl(PaintEvent event) {
		try{
			drawField( event.gc );
			drawFood(event.gc);
			drawTrail(event.gc);
			drawOrganism(event.gc);
			event.gc.dispose();
		}
		catch( Exception ex ){
			ex.printStackTrace();
		}
	}

	private void onNotifyEnvironmentChanged(EnvironmentEvent<IOrganism<E>> event) {
		if( disposed || getDisplay().isDisposed() || ( event.getSource() == null ))
			return;
		TrailData td = new TrailData( event.getOrganism().getX(), event.getOrganism().getY(), trail.size() );
		trail.add(td);
		if( isFade && trail.size() > this.fadeValue )
			trail.remove(0);
		getDisplay().asyncExec( new Runnable() {

			@Override
			public void run() {
				redraw();
			}
		});
	}

	public IEnvironment<IOrganism<E>> getInput() {
		return this.environment;
	}

	public void setInput( IEnvironment<IOrganism<E>> environment){
		if( this.environment != null )
			this.environment.removeListener(e->onNotifyEnvironmentChanged((EnvironmentEvent<IOrganism<E>>) e));
		this.environment = (IEnvironment<IOrganism<E>>) environment;
		if( this.environment != null )
			this.environment.addListener(e->onNotifyEnvironmentChanged((EnvironmentEvent<IOrganism<E>>) e));
		this.redraw();
	}

	protected void drawField( GC gc ){
		if( environment == null )
			return;
		Rectangle clientArea = getBounds();
		Color color = gc.getForeground();

		try {
			//The raster
			gc.setForeground( getDisplay().getSystemColor( SWT.COLOR_WIDGET_LIGHT_SHADOW ));
			double rasterx = (double)clientArea.width/(2*this.environment.getLength());
			for( int i=0; i<this.environment.getLength(); i++ ) {
				int xstep = (int)(i*rasterx);
				if( i%10==0) {
					gc.setForeground( getDisplay().getSystemColor( SWT.COLOR_GRAY));
				}else
					gc.setForeground( getDisplay().getSystemColor( SWT.COLOR_WIDGET_LIGHT_SHADOW ));
				gc.drawLine( xstep, 0, xstep, clientArea.height );
			}
			double rastery = (double)clientArea.height/this.environment.getWidth();
			for( int i=0; i<this.environment.getWidth(); i++ ) {
				int ystep = (int)(i*rastery);
				if( i%10==0) {
					gc.setForeground( getDisplay().getSystemColor( SWT.COLOR_GRAY));
				}else
					gc.setForeground( getDisplay().getSystemColor( SWT.COLOR_WIDGET_LIGHT_SHADOW ));
				gc.drawLine( 0, ystep, clientArea.width, ystep );					
			}
		}catch( Exception ex ) {
			ex.printStackTrace();
		}
		gc.setForeground(color);
	}

	protected void drawFood( GC gc ){
		if( environment == null )
			return;
		Color color = gc.getForeground();

		try {
			//The raster
			Iterator<ILocation> iterator = this.environment.iterator();
			gc.setBackground( getDisplay().getSystemColor( SWT.COLOR_DARK_MAGENTA ));					
			IOrganism<E> place = environment.getOrganism();
			int[] pos;
			ILocation food = null;
			if( place != null ) {
				food = environment.getNearestFood(place.getX(), place.getY());
				if( food != null ) {
					pos = scale( food );
					gc.fillOval( pos[0]-5, pos[1]-5, 10, 10);	
				}
			}

			gc.setBackground( getDisplay().getSystemColor( SWT.COLOR_DARK_GREEN ));
			while( iterator.hasNext() ) {			
				ILocation loc = iterator.next();
				if(( loc.equals(food )) || ( loc instanceof Organism2D ))
					continue;					
				pos = scale( loc );
				gc.fillOval( pos[0]-5, pos[1]-5, 10, 10);					
			}
		}catch( Exception ex ) {
			ex.printStackTrace();
		}
		gc.setBackground(color);
	}

	protected void drawOrganism( GC gc ){
		if( environment == null )
			return;
		Color color = gc.getForeground();

		try {
			gc.setBackground( getDisplay().getSystemColor( SWT.COLOR_RED ));					
			IOrganism<E> place = environment.getOrganism();
			if( place != null ) {
				int[] pos = scale( place );
				gc.fillOval( pos[0]-10, pos[1]-10, 20, 20);
			}
		}catch( Exception ex ) {
			ex.printStackTrace();
		}
		gc.setBackground(color);
	}

	protected void drawTrail( GC gc ){
		if( !isTrail || ( environment == null ))
			return;
		Color color = gc.getForeground();
		gc.setBackground( getDisplay().getSystemColor( SWT.COLOR_GREEN ));					

		try {
			trail.forEach( td-> {
				int[] pos = scale( td.getX(), td.getY() );
				gc.fillRectangle(pos[0], pos[1], 4, 4);	
			});
		}catch( Exception ex ) {
			ex.printStackTrace();
		}
		gc.setBackground(color);
	}


	private int[] scale( ILocation place ) {
		return scale( place.getX(), place.getY() );
	}
	
	private int[] scale( int x, int y ) {
		Rectangle clientArea = this.getClientArea();
		int[] result = new int[2];
		result[0] = (int)((double)(( 0.5d + x ) * clientArea.width)/(2*this.environment.getLength()));
		result[1] = (int)((double)(( 0.5d + y ) * clientArea.height)/this.environment.getWidth());
		return result;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void dispose() {
		this.disposed = true;
		super.removePaintListener( e->onPaintControl(e));
		if( this.environment != null )
			this.environment.removeListener(e->onNotifyEnvironmentChanged((EnvironmentEvent<IOrganism<E>>) e));
		super.dispose();
	}
}
