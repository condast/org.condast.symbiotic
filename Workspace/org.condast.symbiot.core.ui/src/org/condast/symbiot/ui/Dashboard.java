package org.condast.symbiot.ui;

import org.condast.symbiot.core.IOrganism;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class Dashboard extends Composite {
	private static final long serialVersionUID = 1L;

	private OrganismComposite oc;
	
	private OrganismMap om;
		
	public Dashboard(Composite parent, int style) {
		super(parent, style);
		this.createComposite(parent, style);
	}

	protected void createComposite( Composite parent, int style ) {
		this.setLayout(new GridLayout());
		om = new OrganismMap(this, SWT.BORDER);
		om.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, true ));

		oc = new OrganismComposite(this, SWT.BORDER);
		oc.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ));
	}
	
	public void setInput( IOrganism organism ) {
		oc.setInput(organism);
		om.setInput(organism);
	}
}
