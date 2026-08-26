package org.condast.symbiot.ui;

import java.util.EnumSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.condast.commons.thread.AbstractExecuteThread;
import org.condast.commons.ui.widgets.logger.LogComposite;
import org.condast.commons.ui.player.PlayerComposite;
import org.condast.commons.ui.player.PlayerImages;
import org.condast.commons.ui.player.PlayerImages.Images;
import org.condast.commons.ui.session.AbstractSessionHandler;
import org.condast.commons.ui.session.SessionEvent;
import org.condast.symbiot.core.test.Organism2D.Form;
import org.condast.symbiotic.core.environment.EnvironmentEvent;
import org.condast.symbiotic.core.environment.IEnvironment;
import org.condast.symbiotic.core.organism.IOrganism;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Spinner;

public class EnvironmentComposite<E extends Enum<E>> extends Composite {
	private static final long serialVersionUID = 1L;

	private Dashboard dashboard;
	private EnvironmentCanvas<E> canvas;
	private Spinner foodSpinner;
	private LogComposite logComposite;
	private Player player;
	
	private ExecuteThread executor;
	private Handler handler;

	public EnvironmentComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, true ));
        
		dashboard = new Dashboard(this, SWT.BORDER);
        dashboard.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, true ));
        dashboard.setEnabled(true);

        canvas = new EnvironmentCanvas<>(this, SWT.BORDER);
        canvas.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, true,2,2 ));
       
        logComposite = new LogComposite(this, SWT.BORDER);
        logComposite.setLayoutData( new GridData( SWT.FILL, SWT.FILL, false, true ));
        logComposite.activate(true);

        player = new Player( this, SWT.BORDER );
        player.setLayoutData( new GridData( SWT.LEFT, SWT.FILL, false, false ));
        
        Group foodGroup = new Group(this, SWT.NONE );
        foodGroup.setText("Food");
        foodGroup.setLayoutData( new GridData( SWT.FILL, SWT.FILL, true, false, 2,1 ));
        foodGroup.setLayout(new FillLayout());
        foodSpinner = new Spinner( foodGroup, SWT.BORDER);
        foodSpinner.setMaximum(100);
        foodSpinner.setSelection(1);
        handler = new Handler(getDisplay());
	}

	private void onUpdateEnvironment( EnvironmentEvent<IOrganism<E>> event ) {
		handler.addData(event.getOrganism());
	}
	
	public void setInput( IEnvironment<IOrganism<E>> environment ) {
		player.setInput(environment);
		canvas.setInput(environment);
		executor = new ExecuteThread( environment);
		executor.setTime(1000);
	}

	private class Player extends PlayerComposite<IEnvironment<IOrganism<E>>> {
		private static final long serialVersionUID = 1L;

		public Player(Composite parent, int style) {
			super(parent, style);
		}

		@Override
		protected EnumSet<Images> setupButtonBar() {
			return EnumSet.of(PlayerImages.Images.START,
					PlayerImages.Images.STOP,
					PlayerImages.Images.NEXT,
					PlayerImages.Images.RESET);
		}
			
		@SuppressWarnings("unchecked")
		@Override
		public void setInput(IEnvironment<IOrganism<E>> input) {
			Button button = (Button) super.getButton( PlayerImages.Images.START);
			button.setEnabled( input != null );
			button = (Button) super.getButton( PlayerImages.Images.STOP);
			button.setEnabled( input == null );
			IEnvironment<IOrganism<E>> environment = super.getInput();
			if( environment != null )
				environment.removeListener(l->onUpdateEnvironment(l));
			super.setInput(input);
			if( input != null )
				input.addListener(l->onUpdateEnvironment(l));
	        dashboard.setInput( (IOrganism<Form>) input.getOrganism());
		}
		
		private void stop() {
			executor.stop();
			//environment.removeListener(handler);
			getButton( PlayerImages.Images.STOP).setEnabled(false);
			getButton( PlayerImages.Images.START).setEnabled(true);
			Button clear = (Button) getButton( PlayerImages.Images.RESET);
			clear.setEnabled( true );
		}

		@Override
		protected Control createButton(PlayerImages.Images type, int style) {
			Button button = new Button( this, SWT.FLAT );
			IEnvironment<IOrganism<E>> environment = super.getInput();
			switch( type ){
			case START:
				button.setEnabled( environment != null );
				break;
			case STOP:
				button.setEnabled(( environment != null ) && executor.isRunning());
				break;
			default:
				button.setEnabled(false);
				break;
			}
			button.setData(type);
			button.addSelectionListener( new SelectionAdapter() {
				private static final long serialVersionUID = 1L;

				@Override
				public void widgetSelected(SelectionEvent e) {
					try{
						IEnvironment<IOrganism<E>> environment = getInput();
						Button button = (Button) e.getSource();
						PlayerImages.Images image = (PlayerImages.Images) button.getData();
						Button clear;
						switch( image ){
						case START:
							//environment.addListener( handler);
							logComposite.clear();
							dashboard.enableAngleCombo(false);
							executor.start();
							getButton( PlayerImages.Images.STOP).setEnabled(true);
							getButton( PlayerImages.Images.NEXT).setEnabled(false);
							button.setEnabled(false);
							clear = (Button) getButton( PlayerImages.Images.RESET);
							clear.setEnabled( false );//!environment.isRunning() || environment.isPaused());
							break;
						case STOP:
							dashboard.enableAngleCombo(true);
							getButton( PlayerImages.Images.NEXT).setEnabled(true);
							stop();
							break;
						case NEXT:
							executor.step();
							clear = (Button) getButton( PlayerImages.Images.RESET);
							clear.setEnabled( true );//!environment.isRunning() || environment.isPaused());
							break;
						case RESET:
							dashboard.enableAngleCombo(true);
							environment.clear();
							getButton( PlayerImages.Images.RESET).setEnabled(false);
							break;
						default:
							break;
						}
					}
					catch( Exception ex ){
						ex.printStackTrace();
					}
				}
			});
			button.setImage( PlayerImages.getImage(type));
			return button;
		}

	}

	private class ExecuteThread extends AbstractExecuteThread{

		private ScheduledExecutorService service;
		private IEnvironment<IOrganism<E>> environment;

		public ExecuteThread( IEnvironment<IOrganism<E>> environment )
		{
			super(true);
			this.environment = environment;		}

		
		@Override
		public ExecutorService onCreateService() {
			service = new ScheduledThreadPoolExecutor(3);
			return service;
		}

		@Override
		public boolean onInitialise() {
			environment.init( foodSpinner.getSelection());
			return false;
		}

		
		@Override
		protected void onStart() {
			int time = super.getTime(); 
			service.scheduleAtFixedRate(()->execute(), time,  time, TimeUnit.MILLISECONDS);
		}


		@Override
		public void onExecute() {
			environment.update();
			if( environment.noFood())
				stop();
		}
		
	}

	private class Handler extends AbstractSessionHandler<IOrganism<E>>{

		protected Handler(Display display) {
			super(display);
		}

		@Override
		protected void onHandleSession(SessionEvent<IOrganism<E>> sevent) {
			canvas.redraw();
		}	
	}
}
