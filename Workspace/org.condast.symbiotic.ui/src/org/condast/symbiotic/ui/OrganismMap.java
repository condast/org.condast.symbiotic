package org.condast.symbiotic.ui;

import org.condast.commons.ui.session.AbstractSessionHandler;
import org.condast.commons.ui.session.SessionEvent;
import org.condast.commons.ui.widgets.xy.AbstractXYGraph;
import org.condast.symbiotic.design.Organism2D;
import org.condast.symbiotic.ecosystem.organism.IOrganism;
import org.condast.symbiotic.ecosystem.organism.IOrganismListener;
import org.condast.symbiotic.ecosystem.organism.OrganismEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class OrganismMap extends AbstractXYGraph<Double> {
	private static final long serialVersionUID = 1L;

	private IOrganism<Organism2D.Form> organism;

	private Handler handler;

	private IOrganismListener<Organism2D.Form> listener = e-> onOrganismChanged(e);
	
	public OrganismMap(Composite parent, int style) {
		super(parent, style);
        handler = new Handler(getDisplay());
	}

	private void onOrganismChanged( OrganismEvent<Organism2D.Form> event) {
		handler.addData(organism);
	}
	
	public void setInput( IOrganism<Organism2D.Form> organism ) {
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
	
	private class Handler extends AbstractSessionHandler<IOrganism<Organism2D.Form>>{

		protected Handler(Display display) {
			super(display);
		}

		@Override
		protected void onHandleSession(SessionEvent<IOrganism<Organism2D.Form>> sevent) {
			redraw();
		}	
	}
}
