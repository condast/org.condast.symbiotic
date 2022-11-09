package org.condast.symbiot.ui;

import org.condast.commons.ui.session.AbstractSessionHandler;
import org.condast.commons.ui.session.SessionEvent;
import org.condast.commons.ui.xy.AbstractXYGraph;
import org.condast.symbiot.core.IOrganismListener;
import org.condast.symbiot.core.Organism;
import org.condast.symbiot.core.OrganismEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class OrganismMap extends AbstractXYGraph<Double> {
	private static final long serialVersionUID = 1L;

	private Organism organism;

	private Handler handler;

	private IOrganismListener listener = e-> onOrganismChanged(e);
	
	public OrganismMap(Composite parent, int style) {
		super(parent, style);
        handler = new Handler(getDisplay());
	}

	private void onOrganismChanged( OrganismEvent event) {
		handler.addData(organism);
	}
	
	public void setInput( Organism organism ) {
		if( this.organism != null )
			this.organism.removeListener(listener);
		this.organism = organism;
		if( this.organism != null )
			this.organism.addListener(listener);
	}
	
	@Override
	protected Color onSetForeground(GC gc) {
		return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE);
	}

	@Override
	protected Color onSetBackground(GC gc) {
		return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE);
	}

	@Override
	protected int onGetXValue(int xpos, int xmax) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int onPaint(GC gc, int xprev, int yprev, int xcor, int xpos, int ypos, Double value) {
		Rectangle rect = getClientArea();
		int y = yprev-(int)(rect.height * value/getMaxValue() );
		if( xprev == 0 )
			return y;
		gc.drawLine( xprev, ypos, xcor, y );
		return y;
	}

	@Override
	protected void onCompleted(GC gc) {
		// TODO Auto-generated method stub	
	}
	
	private class Handler extends AbstractSessionHandler<Organism>{

		protected Handler(Display display) {
			super(display);
		}

		@Override
		protected void onHandleSession(SessionEvent<Organism> sevent) {
			redraw();
		}	
	}
}
