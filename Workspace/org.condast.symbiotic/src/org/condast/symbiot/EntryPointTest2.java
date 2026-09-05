package org.condast.symbiot;

import org.condast.symbiot.design.test.Organism2D;
import org.condast.symbiot.design.test2.Environment1D;
import org.condast.symbiot.design.test2.Organism1D2Flagellum;
import org.condast.symbiotic.ui.EnvironmentComposite;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class EntryPointTest2 extends AbstractEntryPoint {
    private static final long serialVersionUID = 1L;
    
 	@Override
    protected void createContents(Composite parent) {
        parent.setLayout(new FillLayout());
        Environment1D environment = new Environment1D(100, 100);
        environment.setOrganism( new Organism1D2Flagellum());
       
        EnvironmentComposite<Organism2D.Form> composite = new EnvironmentComposite<>(parent, SWT.BORDER);
        composite.setInput(environment);      
    }
}
