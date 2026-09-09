package org.condast.symbiotic.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;

import org.condast.commons.strings.StringUtils;
import org.condast.commons.ui.session.AbstractSessionHandler;
import org.condast.commons.ui.session.SessionEvent;
import org.condast.commons.ui.widgets.table.AbstractTableComposite;
import org.condast.symbiotic.core.def.IStressData;
import org.condast.symbiotic.core.def.ISymbiot;
import org.condast.symbiotic.ecosystem.organism.IOrganism;
import org.condast.symbiotic.ecosystem.organism.IOrganismListener;
import org.condast.symbiotic.ecosystem.organism.OrganismEvent;
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

public class WeightComposite extends AbstractTableComposite<ISymbiot> {
	private static final long serialVersionUID = 1L;

	private Handler handler;
	private IOrganism<?> organism;
	
	private List<String> columns;
	
	@SuppressWarnings("rawtypes")
	private IOrganismListener listener = e-> onOganismChanged(e);
	
	@SuppressWarnings("rawtypes")
	private void onOganismChanged(OrganismEvent e) {
		handler.addData(organism);
	}

	public WeightComposite(Composite parent, int style) {
		super(parent, false, style);
		this.columns = new ArrayList<>();
        handler = new Handler(getDisplay());
	}

	@Override
	protected void createComposite( Composite parent,int style ){
		super.createComposite(parent, style);
		setContentProvider( ArrayContentProvider.getInstance() );
	}
	
	@Override
	protected void initComposite() {
		super.initComposite();
		SymbiotLabelProvider provider = new SymbiotLabelProvider();
		getTableViewer().setLabelProvider(provider);

		TableViewer viewer = super.getTableViewer();
		viewer.setLabelProvider( new SymbiotLabelProvider() );
	}

	@Override
	protected void createColumns(Composite parent, TableViewer viewer) {
		Collection<ISymbiot> symbiots = this.organism.getSymbiots();
		int index = 0;
		this.columns.clear();
		TableViewerColumn tcol = super.registerColum("ORGANISM", SWT.CENTER, 20, index++);
		tcol.getColumn().setText("Symbiots");
		getTableColumnLayout().setColumnData( tcol.getColumn(), new ColumnWeightData( 20 ));
		columns.add("Symbiots");
		for( ISymbiot symbiot: symbiots){
			tcol = super.registerColum("ORGANISM", SWT.CENTER, 20, index++);
			tcol.getColumn().setText(symbiot.getId());
			getTableColumnLayout().setColumnData( tcol.getColumn(), new ColumnWeightData( 20 ));
			columns.add( StringUtils.prettyString( symbiot.getId()));
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

	public IOrganism<?> getSymbiot() {
		return organism;
	}

	@SuppressWarnings("unchecked")
	public void setInput(IOrganism<?> organism) {
		if( this.organism != null )
			this.organism.removeListener( listener );
		this.organism = organism;
		if( this.organism != null ) {
			this.organism.addListener(listener);
			Collection<ISymbiot> symbiots = this.organism.getSymbiots();
			super.setInput( symbiots.toArray( new ISymbiot[ symbiots.size() ]));
			this.initComposite();
		}
	}

	private class SymbiotLabelProvider extends LabelProvider implements ITableLabelProvider{
		private static final long serialVersionUID = 1L;

		@Override
		public String getColumnText( Object element, int columnIndex ) {
			String retval = null;
			if( columnIndex >= columns.size())
				return retval;
			ISymbiot symbiot = (ISymbiot) element;

			switch( columnIndex ) {
			case 0:
				retval = symbiot.getId();
				break;
			default:
				String column = columns.get( columnIndex );
				IStressData data = symbiot.getStressData(column);
				if( data != null )
					retval = String.format("%,.4f", data.getWeight());
				break;
			}
			return retval;
		}

		@Override
		public Image getColumnImage(Object arg0, int columnIndex) {
			Image image = super.getImage(arg0);
			return image;
		}

	}

	private class Handler extends AbstractSessionHandler<IOrganism<?>>{

		protected Handler(Display display) {
			super(display);
		}

		@Override
		protected void onHandleSession(SessionEvent<IOrganism<?>> sevent) {
			refresh();
		}	
	}	
}
