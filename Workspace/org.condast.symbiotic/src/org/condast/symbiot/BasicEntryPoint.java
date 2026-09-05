package org.condast.symbiot;

import org.condast.symbiot.design.test.Environment;
import org.condast.symbiot.design.test.Organism2D;
import org.condast.symbiotic.ui.EnvironmentComposite;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class BasicEntryPoint extends AbstractEntryPoint {
    private static final long serialVersionUID = 1L;
    
 	@Override
    protected void createContents(Composite parent) {
        parent.setLayout(new FillLayout());
        Environment<Organism2D.Form> environment = new Environment<>(100, 100);
        environment.setOrganism( new Organism2D());
        
        EnvironmentComposite<Organism2D.Form> composite = new EnvironmentComposite<>(parent, SWT.BORDER);
        composite.setInput(environment);      
    }
}
