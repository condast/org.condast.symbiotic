package org.condast.symbiot;

import org.condast.symbiot.core.onedim.Environment1D;
import org.condast.symbiot.core.onedim.Organism1D;
import org.condast.symbiot.ui.EnvironmentComposite;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class EntryPoint1D extends AbstractEntryPoint {
    private static final long serialVersionUID = 1L;
    
    private Environment1D environment;

	@Override
    protected void createContents(Composite parent) {
        parent.setLayout(new FillLayout());
        environment = new Environment1D(100, 100);
       
        EnvironmentComposite<Organism1D.Form> composite = new EnvironmentComposite<>(parent, SWT.BORDER);
        composite.setInput(environment);      
    }
}
