package org.condast.symbiot.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.Collection;
import java.util.Map;
import java.util.ArrayList;
import org.condast.commons.strings.StringStyler;
import org.condast.commons.ui.session.AbstractSessionHandler;
import org.condast.commons.ui.session.SessionEvent;
import org.condast.commons.ui.table.AbstractTableComposite;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.IStressListener;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.core.def.StressEvent;
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

public class SymbiotComposite extends AbstractTableComposite<IStressData> {
	private static final long serialVersionUID = 1L;

	private enum Columns{
		NAME,
		WEIGHT,
		STRESS,
		STRESS_DELTA;

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
	private ISymbiot symbiot;
	
	private IStressListener listener = e-> onSymbiotChanged(e);
	
	private void onSymbiotChanged( StressEvent event) {
		handler.addData(symbiot);
	}
	
	public SymbiotComposite(Composite parent, int style) {
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
	protected void onSetInput(IStressData[] leaf) {
		//Map<Form, ISymbiot> design = this.organism.getDesign();
		
	}

	@Override
	protected void onHeaderClicked(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected int compareTables(int columnIndex, IStressData o1, IStressData o2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ISymbiot getSymbiot() {
		return symbiot;
	}

	public void setInput(ISymbiot symbiot) {
		if( this.symbiot != null )
			this.symbiot.removeStressListener(listener);
		this.symbiot = symbiot;
		if( this.symbiot != null ) {
			this.symbiot.addStressListener(listener);
			Map<ISymbiot, IStressData> signals = this.symbiot.getSignals();
			Collection<IStressData> symbiots = new ArrayList<>(signals.values() );
			super.setInput( symbiots.toArray( new IStressData[ symbiots.size() ]));
		}
	}

	private class SymbiotLabelProvider extends LabelProvider implements ITableLabelProvider{
		private static final long serialVersionUID = 1L;

		@Override
		public String getColumnText( Object element, int columnIndex ) {
			String retval = null;
			Columns column = Columns.values()[ columnIndex ];
			IStressData stress = (IStressData) element;
			switch( column){
			case NAME:
				retval = stress.getReference().getId();
				break;
			case WEIGHT:
				retval = String.format("%,.4f", stress.getWeight());
				break;
			case STRESS:
				retval = String.format("%,.4f", stress.getCurrentStress());
				break;
			case STRESS_DELTA:
				retval = String.format("%,.8f", stress.getDelta());
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

	private class Handler extends AbstractSessionHandler<ISymbiot>{

		protected Handler(Display display) {
			super(display);
		}

		@Override
		protected void onHandleSession(SessionEvent<ISymbiot> sevent) {
			refresh();
		}	
	}	
}
