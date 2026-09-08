package org.condast.symbiot.ui;

import java.util.Iterator;

import org.condast.symbiot.core.ILocation;
import org.condast.symbiot.core.IOrganism;
import org.condast.symbiot.core.Location;
import org.condast.symbiot.core.Organism;
import org.condast.symbiot.core.env.Environment;
import org.condast.symbiot.core.env.EnvironmentEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class EnvironmentCanvas extends Canvas{
	private static final long serialVersionUID = 1L;

	public static final int GRIDX = 100;//meters
	public static final int GRIDY = 20;//meters

	private Environment environment;
	
	private boolean disposed;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public EnvironmentCanvas(Composite parent, Integer style) {
		super(parent, style);
		this.disposed = false;
		setBackground(Display.getCurrent().getSystemColor( SWT.COLOR_WHITE));
		super.addPaintListener( e->onPaintControl(e));
	}

	@Override
	public Composite getParent(){
		return super.getParent();
	}

	private void onPaintControl(PaintEvent event) {
		try{
			drawField( event.gc );
			drawFood(event.gc);
			drawOrganism(event.gc);
			event.gc.dispose();
		}
		catch( Exception ex ){
			ex.printStackTrace();
		}
	}

	private void onNotifyEnvironmentChanged(EnvironmentEvent<Organism> event) {
		if( disposed || getDisplay().isDisposed() || ( event.getSource() == null ))
			return;
		getDisplay().asyncExec( new Runnable() {

			@Override
			public void run() {
				redraw();
			}
		});
	}

	public Environment getInput() {
		return this.environment;
	}

	public void setInput( Environment environment){
		if( this.environment != null )
			this.environment.removeListener(e->onNotifyEnvironmentChanged(e));
		this.environment = environment;
		if( this.environment != null )
			this.environment.addListener(e->onNotifyEnvironmentChanged(e));
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
			double rasterx = (double)clientArea.width/(2*this.environment.getX());
			for( int i=0; i<this.environment.getX(); i++ ) {
				int xstep = (int)(i*rasterx);
				if( i%10==0) {
					gc.setForeground( getDisplay().getSystemColor( SWT.COLOR_GRAY));
				}else
					gc.setForeground( getDisplay().getSystemColor( SWT.COLOR_WIDGET_LIGHT_SHADOW ));
				gc.drawLine( xstep, 0, xstep, clientArea.height );
			}
			double rastery = (double)clientArea.height/this.environment.getY();
			for( int i=0; i<this.environment.getY(); i++ ) {
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
			Iterator<Location> iterator = this.environment.iterator();
			gc.setBackground( getDisplay().getSystemColor( SWT.COLOR_DARK_MAGENTA ));					
			IOrganism place = environment.getOrganism();
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
				if(( loc.equals(food )) || ( loc instanceof Organism ))
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
			IOrganism place = environment.getOrganism();
			if( place != null ) {
				int[] pos = scale( place );
				gc.fillOval( pos[0]-10, pos[1]-10, 20, 20);
			}
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
		result[0] = (int)((double)(( 0.5d + x ) * clientArea.width)/(2*this.environment.getX()));
		result[1] = (int)((double)(( 0.5d + y ) * clientArea.height)/this.environment.getY());
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
		this.environment.removeListener(e->onNotifyEnvironmentChanged(e));
		super.dispose();
	}
}
