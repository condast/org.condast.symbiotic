package org.condast.symbiot.ui;

import org.condast.symbiot.core.Organism;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class Dashboard extends Composite {
	private static final long serialVersionUID = 1L;

	private OrganismMap om;
	
	private Organism organism;
	
	public Dashboard(Composite parent, int style) {
		super(parent, style);
		this.createComposite(parent, style);
	}

	protected void createComposite( Composite parent, int style ) {
		this.setLayout(new GridLayout());
		om = new OrganismMap(this, SWT.BORDER);
		om.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ));
	}
	
	public void setInput( Organism organism ) {
		this.organism = organism;
		om.setInput(organism);
	}
}
