package org.condast.symbiot;

import org.condast.symbiot.core.test.Organism2D;
import org.condast.symbiot.core.test2.Environment1D;
import org.condast.symbiot.ui.EnvironmentComposite;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class EntryPointTest3 extends AbstractEntryPoint {
    private static final long serialVersionUID = 1L;
    
 	@Override
    protected void createContents(Composite parent) {
        parent.setLayout(new FillLayout());
        Environment1D environment = new Environment1D(100, 100);
        environment.setOrganism( new Organism2D());
       
        EnvironmentComposite<Organism2D.Form> composite = new EnvironmentComposite<>(parent, SWT.BORDER);
        composite.setInput(environment);      
    }
}
