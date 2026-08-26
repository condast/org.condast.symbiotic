package org.condast.symbiot.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.List;
import java.util.ArrayList;
import org.condast.commons.strings.StringStyler;
import org.condast.commons.ui.session.AbstractSessionHandler;
import org.condast.commons.ui.session.SessionEvent;
import org.condast.commons.ui.widgets.table.AbstractTableComposite;
import org.condast.commons.ui.widgets.table.ITableEventListener.TableEvents;
import org.condast.commons.ui.widgets.table.TableEvent;
import org.condast.symbiot.core.organism.Eye;
import org.condast.symbiot.core.test.AngleControl;
import org.condast.symbiot.core.test.Organism2D;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.organism.IOrganism;
import org.condast.symbiotic.core.organism.IOrganismListener;
import org.condast.symbiotic.core.organism.OrganismEvent;
import org.condast.symbiotic.core.process.IProcess;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;

import org.eclipse.swt.graphics.Image;

public class OrganismComposite extends AbstractTableComposite<ISymbiot> {
	private static final long serialVersionUID = 1L;

	private enum Columns{
		NAME,
		DISTANCE,
		ANGLE,
		WEIGHT,
		STRESS,
		STRESS_DELTA,
		FACTOR,
		NORM_OUTPUT,
		OUTPUT;

		@Override
		public String toString() {
			return StringStyler.prettyString( super.name());
		}
		
		public int getWeight() {
			int weight = 10;
			switch( this ) {
			case NAME:
				weight = 20;
				break;
			default:
				break;
			}
			return weight;
		}	
	}
	
	private Handler handler;
	private IOrganism<Organism2D.Form> organism;
	
	private IOrganismListener<Organism2D.Form> listener = e-> onOrganismChanged(e);
	
	private void onOrganismChanged( OrganismEvent<Organism2D.Form> event) {
		handler.addData(organism);
	}
	
	public OrganismComposite(Composite parent, int style) {
		super(parent, style);
		setContentProvider( ArrayContentProvider.getInstance() );
		SymbiotLabelProvider provider = new SymbiotLabelProvider();
		getTableViewer().setLabelProvider(provider);
        handler = new Handler(getDisplay());
	}

	@Override
	protected void createComposite( Composite parent,int style ){
		super.createComposite(parent, style);
		TableViewer viewer = super.getTableViewer();
		viewer.setLabelProvider( new SymbiotLabelProvider() );
	}
	
	@Override
	protected void createColumns(Composite parent, TableViewer viewer) {
		for( Columns column: Columns.values()){
			TableViewerColumn tcol = super.registerColum(column.toString(), SWT.CENTER, column.getWeight( ), column.ordinal());
			tcol.getColumn().setText(column.toString());
			getTableColumnLayout().setColumnData( tcol.getColumn(), new ColumnWeightData( column.getWeight()));
		}
	}
	
	@Override
	protected void prepare() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initTableColumnLayout(TableColumnLayout tclayout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onSetInput(ISymbiot[] leaf) {
		//Map<Form, ISymbiot> design = this.organism.getDesign();
		
	}

	@Override
	protected void onHeaderClicked(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int compareTables(int columnIndex, ISymbiot o1, ISymbiot o2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public IOrganism<Organism2D.Form> getOrganism() {
		return organism;
	}
	
	@Override
	public ISymbiot getInput() {
		return super.getInput();
	}

	public void setInput(IOrganism<Organism2D.Form> organism) {
		if( this.organism != null )
			this.organism.removeListener(listener);
		this.organism = organism;
		if( this.organism == null )
			return;
		this.organism.addListener(listener);
		List<ISymbiot> symbiots = new ArrayList<>( this.organism.getSymbiots());
		symbiots.add(this.organism.toSymbiot());
		super.setInput( symbiots.toArray( new ISymbiot[ symbiots.size() ]));
		for( int i=0; i<symbiots.size(); i++ ) {
			ISymbiot symbiot = symbiots.get(i);
			if( !symbiot.getId().contains("FLAGELLUM"))
				continue;
			super.setSelection(i);
			super.notifyTableEvent( new TableEvent<>(this, TableEvents.SELECT, symbiot ));
			return;
		}
	}

	private class SymbiotLabelProvider extends LabelProvider implements ITableLabelProvider{
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		@Override
		public String getColumnText( Object element, int columnIndex ) {
			String retval = null;
			Columns column = Columns.values()[ columnIndex ];
			ISymbiot symbiot = (ISymbiot) element;
			IProcess<?,?> process = null;

			switch( column){
			case NAME:
				retval = symbiot.getId();
				break;
			case DISTANCE:
				if( symbiot instanceof Eye ) {
					Eye<Organism2D.Form> is  = (Eye<Organism2D.Form>) symbiot;
					retval = String.valueOf( is.getInput() );
				}
				break;
			case ANGLE:
				if( symbiot instanceof Eye ) {
					Eye<Organism2D.Form> eye  = (Eye<Organism2D.Form>) symbiot;
					retval = String.valueOf( eye.getAngle());
				} else 	if( symbiot instanceof AngleControl ) {
					AngleControl ac  = (AngleControl) symbiot;
					retval = String.valueOf( StringStyler.prettyString( ac.getAngle().name() ));
				}
				break;
			case WEIGHT:
				retval = String.format("%,.4f", symbiot.getOverallWeight());
				break;
			case STRESS:
				retval = String.format("%,.4f", symbiot.getStress());
				break;
			case STRESS_DELTA:
				retval = String.format("%,.6f", symbiot.getDeltaStress());
				break;
			case FACTOR:
				retval = String.format("%,.6f", symbiot.getFactor());
				break;
			case NORM_OUTPUT:
				if(!(symbiot instanceof IProcess ))
					break;
				process = (IProcess<?, ?>) symbiot;
				retval = String.format("%,.6f", process.getNormalisedOutput());
				break;
			case OUTPUT:
				if(!(symbiot instanceof IProcess ))
					break;
				process = (IProcess<?, ?>) symbiot;
				retval = process.getOutput().toString();
				break;
			default:
				break;
			}
			return retval;
		}
		
		@Override
		public Image getColumnImage(Object arg0, int columnIndex) {
			Image image = super.getImage(arg0);
			Columns column = Columns.values()[ columnIndex ];
			switch( column){
			default:
				break;
			}
			return image;
		}

	}

	private class Handler extends AbstractSessionHandler<IOrganism<Organism2D.Form>>{

		protected Handler(Display display) {
			super(display);
		}

		@Override
		protected void onHandleSession(SessionEvent<IOrganism<Organism2D.Form>> sevent) {
			refresh();
		}	
	}	
}
