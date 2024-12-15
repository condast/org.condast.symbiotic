package org.condast.symbiot.ui;

import org.condast.commons.ui.session.AbstractSessionHandler;
import org.condast.commons.ui.session.SessionEvent;
import org.condast.commons.ui.table.ITableEventListener;
import org.condast.commons.ui.table.TableEvent;
import org.condast.symbiot.core.IOrganism;
import org.condast.symbiot.core.IOrganism.Form;
import org.condast.symbiot.core.IOrganismListener;
import org.condast.symbiot.core.OrganismEvent;
import org.condast.symbiot.symbiot.AngleControl;
import org.condast.symbiot.symbiot.AngleControl.AngleBehaviour;
import org.condast.symbiot.symbiot.Flagellum;
import org.condast.symbiotic.core.def.ISymbiot;
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

	public void setInput( IOrganism organism ) {
		oc.setInput(organism);
		om.setInput(organism);
		organism.addListener( handler);
	}

	@Override
	public void dispose() {
		this.oc.removeTableEventListener(listener);
		super.dispose();
	}

	private class Handler extends AbstractSessionHandler<OrganismEvent> implements IOrganismListener{

		protected Handler(Display display) {
			super(display);
		}

		@Override
		public void notifyOrganismChanged(OrganismEvent event) {
			super.addData(event);
		}

		@Override
		protected void onHandleSession(SessionEvent<OrganismEvent> sevent) {
			IOrganism organism = sevent.getData().getOrganism();
			AngleControl ac = (AngleControl) organism.getSymbiot( Form.ANGLE);
			ac.setBehaviour(AngleBehaviour.values()[ angleCombo.getSelectionIndex()]);
		
			StringBuilder builder = new StringBuilder();	
			builder.append(ac.getAngle().name());
			
			Flagellum leftFlagellum = (Flagellum) organism.getSymbiot( Form.LEFT_FLAGELLUM);
			Flagellum rightFlagellum = (Flagellum) organism.getSymbiot( Form.RIGHT_FLAGELLUM);
			builder.append(": (");
			builder.append(leftFlagellum.getOutput());
			builder.append(",");
			builder.append(rightFlagellum.getOutput());
			builder.append(")");
			lblAngleLabel.setText( builder.toString());
			sm.refresh();
		}		
	}
}
