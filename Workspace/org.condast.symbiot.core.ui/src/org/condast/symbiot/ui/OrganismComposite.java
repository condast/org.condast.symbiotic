package org.condast.symbiot.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.Collection;
import java.util.ArrayList;
import org.condast.commons.strings.StringStyler;
import org.condast.commons.ui.session.AbstractSessionHandler;
import org.condast.commons.ui.session.SessionEvent;
import org.condast.commons.ui.table.AbstractTableComposite;
import org.condast.symbiot.core.IOrganism;
import org.condast.symbiot.core.IOrganismListener;
import org.condast.symbiot.core.OrganismEvent;
import org.condast.symbiotic.core.def.IInputSymbiot;
import org.condast.symbiotic.core.def.IOutputSymbiot;
import org.condast.symbiotic.core.def.ISymbiot;
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
		WEIGHT,
		STRESS,
		STRESS_DELTA,
		FACTOR,
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
	private IOrganism organism;
	
	private IOrganismListener listener = e-> onOrganismChanged(e);
	
	private void onOrganismChanged( OrganismEvent event) {
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

	
	public IOrganism getOrganism() {
		return organism;
	}

	public void setInput(IOrganism organism) {
		if( this.organism != null )
			this.organism.removeListener(listener);
		this.organism = organism;
		if( this.organism != null ) {
			this.organism.addListener(listener);
			Collection<ISymbiot> symbiots = new ArrayList<>( this.organism.getSymbiots());
			symbiots.add(this.organism.toSymbiot());
			super.setInput( symbiots.toArray( new ISymbiot[ symbiots.size() ]));
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
			switch( column){
			case NAME:
				retval = symbiot.getId();
				break;
			case DISTANCE:
				if( symbiot instanceof IInputSymbiot ) {
					IInputSymbiot<Integer> is  = (IInputSymbiot<Integer>) symbiot;
					if( is.getInput() != null )
						retval = is.getInput().toString();
				}
				break;
			case WEIGHT:
				retval = String.format("%,.4f", symbiot.getOverallWeight());
				break;
			case STRESS:
				retval = String.format("%,.4f", symbiot.getStress());
				break;
			case STRESS_DELTA:
				retval = String.format("%,.8f", symbiot.getDeltaStress( true));
				break;
			case FACTOR:
				retval = String.format("%,.8f", symbiot.getFactor());
				break;
			case OUTPUT:
				if( symbiot instanceof IOutputSymbiot ) {
					IOutputSymbiot<Integer> is  = (IOutputSymbiot<Integer>) symbiot;
					if(( is==null ) || ( is.getOutput() == null ))
						return retval;
					retval = is.getOutput().toString();
				}
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

	private class Handler extends AbstractSessionHandler<IOrganism>{

		protected Handler(Display display) {
			super(display);
		}

		@Override
		protected void onHandleSession(SessionEvent<IOrganism> sevent) {
			refresh();
		}	
	}	
}