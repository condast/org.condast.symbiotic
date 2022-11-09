package org.condast.symbiot;

import org.condast.symbiot.core.env.Environment;
import org.condast.symbiot.ui.EnvironmentComposite;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;


public class BasicEntryPoint extends AbstractEntryPoint {
    private static final long serialVersionUID = 1L;
    
    private Environment environment;

	@Override
    protected void createContents(Composite parent) {
        parent.setLayout(new FillLayout());
        environment = new Environment(100, 100);
       
        EnvironmentComposite composite = new EnvironmentComposite(parent, SWT.BORDER);
        composite.setInput(environment);      
    }
}
