package org.condast.symbiot.ui;

import org.condast.commons.ui.session.AbstractSessionHandler;
import org.condast.commons.ui.session.SessionEvent;
import org.condast.commons.ui.table.ITableEventListener;
import org.condast.commons.ui.table.TableEvent;
import org.condast.symbiot.core.test.AngleControl;
import org.condast.symbiot.core.test.Organism2D;
import org.condast.symbiot.core.test.AngleControl.AngleBehaviour;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.organism.IOrganism;
import org.condast.symbiotic.core.organism.IOrganismListener;
import org.condast.symbiotic.core.organism.OrganismEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Group;

public class Dashboard extends Composite {
	private static final long serialVersionUID = 1L;

	private OrganismComposite oc;
	
	private OrganismMap om;
	private SymbiotComposite sm;
	
	private Label lblAngleLabel;
	private Combo angleCombo;
	
	private Handler handler;

	private ITableEventListener<ISymbiot> listener = e-> onSymbiotSelected(e);

	public Dashboard(Composite parent, int style) {
		super(parent, style);
		this.createComposite(parent, style);
		this.oc.addTableEventListener(listener);
		this.handler = new Handler( this.getDisplay());
	}

	protected void createComposite( Composite parent, int style ) {
		this.setLayout(new GridLayout());

		Group grpOrganism = new Group(this, SWT.NONE);
		grpOrganism.setLayout(new GridLayout(4, false));
		grpOrganism.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		grpOrganism.setText("Organism:");
		
		Label lblAngle_1 = new Label(grpOrganism, SWT.NONE);
		lblAngle_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		lblAngle_1.setText("Angle:");
		
		lblAngleLabel = new Label(grpOrganism, SWT.NONE);
		lblAngleLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		lblAngleLabel.setText("Rest");
		
		Label lblAngle_2 = new Label(grpOrganism, SWT.NONE);
		lblAngle_2.setText("Behaviour:");
		
		this.angleCombo = new Combo(grpOrganism, SWT.NONE );
		this.angleCombo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));		
		this.angleCombo.setItems( AngleControl.AngleBehaviour.items());
		this.angleCombo.select(0);
		
		oc = new OrganismComposite(this, SWT.BORDER);
		oc.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ));

		TabFolder tabFolder = new TabFolder(this, SWT.BORDER);
		tabFolder.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true ));

		TabItem tabItem = new TabItem(tabFolder, SWT.NULL);
		tabItem.setText("Symbiot");
		sm = new SymbiotComposite(tabFolder, SWT.BORDER);
		sm.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, true ));
		tabItem.setControl(sm);

		tabItem = new TabItem(tabFolder, SWT.NULL);
		tabItem.setText("Weights");
		om = new OrganismMap(tabFolder, SWT.BORDER);
		om.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, true ));
		tabItem.setControl(om);
	}
	
	public void enableAngleCombo( boolean enable ) {
		this.angleCombo.setEnabled(enable);
	}
	
	private void onSymbiotSelected( TableEvent<ISymbiot> event) {
		sm.setInput(event.getData());
	}

	public void setInput( IOrganism<Organism2D.Form> organism ) {
		oc.setInput(organism);
		om.setInput(organism);
		sm.setInput(oc.getInput());
		organism.addListener( handler);
	}

	@Override
	public void dispose() {
		this.oc.removeTableEventListener(listener);
		this.oc.dispose();
		this.om.dispose();
		this.sm.dispose();
		super.dispose();
	}

	private class Handler extends AbstractSessionHandler<OrganismEvent<Organism2D.Form>> implements IOrganismListener<Organism2D.Form>{

		protected Handler(Display display) {
			super(display);
		}

		@Override
		public void notifyOrganismChanged(OrganismEvent<Organism2D.Form> event) {
			super.addData(event);
		}

		@Override
		protected void onHandleSession(SessionEvent<OrganismEvent<Organism2D.Form>> sevent) {
			IOrganism<Organism2D.Form> organism = sevent.getData().getOrganism();
			if( organism instanceof Organism2D) {
				AngleControl ac = (AngleControl) organism.getSymbiot( Organism2D.Form.ANGLE);
				ac.setBehaviour(AngleBehaviour.values()[ angleCombo.getSelectionIndex()]);
			}
			
			lblAngleLabel.setText( organism.log());
			sm.refresh();
		}		
	}
}
