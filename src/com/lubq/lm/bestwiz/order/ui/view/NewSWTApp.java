package com.lubq.lm.bestwiz.order.ui.view;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressIndicator;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class NewSWTApp extends org.eclipse.swt.widgets.Composite {

	private Menu menu1;
	private ProgressBar order_progressBar;
	private Label process_label;
	private Combo mode_combo;
	private Label mode_label;
	private Label side_label;
	private Combo orderBatchSize;
	private Combo isBatch_combo;
	private Label isBatch_label;
	private Combo side_combo;
	private Combo currencyPair_combo;
	private Label orderBatchSize_label;
	private Label customerId_label;
	private Label currencyPair_label;
	private Group group2;
	private Group group1;
	private Text customerId_text;
	private MenuItem aboutMenuItem;
	private MenuItem contentsMenuItem;
	private Menu helpMenu;
	private MenuItem helpMenuItem;
	private Composite composite1;
	private MenuItem exitMenuItem;
	private MenuItem closeFileMenuItem;
	private MenuItem saveFileMenuItem;
	private MenuItem newFileMenuItem;
	private MenuItem openFileMenuItem;
	private Menu fileMenu;
	private MenuItem fileMenuItem;

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public NewSWTApp(Composite parent, int style) {
		super(parent, style);
		initGUI();
	}
	
	/**
	* Initializes the GUI.
	*/
	private void initGUI() {
		try {
			this.setSize(801, 777);
			this.setBackground(SWTResourceManager.getColor(192, 192, 192));
			GridLayout thisLayout = new GridLayout(1, true);
			thisLayout.marginWidth = 5;
			thisLayout.marginHeight = 5;
			thisLayout.numColumns = 1;
			thisLayout.makeColumnsEqualWidth = true;
			thisLayout.horizontalSpacing = 5;
			thisLayout.verticalSpacing = 5;
			this.setLayout(thisLayout);
			{
				composite1 = new Composite(this, SWT.NONE);
				GridData composite1LData = new GridData();
				composite1LData.widthHint = 721;
				composite1LData.heightHint = 630;
				composite1.setLayoutData(composite1LData);
				composite1.setLayout(null);
				{
					group1 = new Group(composite1, SWT.NONE);
					group1.setLayout(null);
					group1.setText("customer info");
					group1.setBounds(21, 21, 224, 70);
					{
						customerId_text = new Text(group1, SWT.NONE);
						customerId_text.setBounds(77, 21, 56, 14);
					}
					{
						customerId_label = new Label(group1, SWT.NONE);
						customerId_label.setText("customer id :");
						customerId_label.setBounds(8, 21, 63, 15);
					}
				}
				{
					group2 = new Group(composite1, SWT.NONE);
					group2.setLayout(null);
					group2.setText("order info");
					group2.setBounds(21, 112, 595, 196);
					{
						currencyPair_label = new Label(group2, SWT.NONE);
						currencyPair_label.setText("currency pair :");
						currencyPair_label.setBounds(8, 18, 70, 15);
					}
					{
						side_label = new Label(group2, SWT.NONE);
						side_label.setText("side :");
						side_label.setBounds(7, 48, 63, 15);
					}
					{
						orderBatchSize_label = new Label(group2, SWT.NONE);
						orderBatchSize_label.setText("order batch size :");
						orderBatchSize_label.setBounds(238, 77, 84, 14);
					}
					{
						currencyPair_combo = new Combo(group2, SWT.NONE);
						currencyPair_combo.setBounds(91, 14, 63, 21);
						currencyPair_combo.add("USD/JPY", 0);
						currencyPair_combo.add("EUR/JPY", 1);
						currencyPair_combo.add("EUR/USD", 2);
						currencyPair_combo.add("AUD/USD", 3);
						
						currencyPair_combo.select(0);
					}
					{

						side_combo = new Combo(group2, SWT.SINGLE | SWT.BORDER|SWT.READ_ONLY);
						side_combo.setBounds(91, 44, 63, 21);
						side_combo.add("买", 0);
						side_combo.add("卖", 1);
						side_combo.select(0);
//						side_combo.set
					}
					{
						orderBatchSize = new Combo(group2, SWT.NONE);
						orderBatchSize.setBounds(322, 70, 63, 21);
						orderBatchSize.add("1", 0);
						orderBatchSize.add("10", 1);
						orderBatchSize.add("20", 2);
						orderBatchSize.add("30", 3);
						orderBatchSize.add("40", 4);
						orderBatchSize.add("50", 5);
						orderBatchSize.add("60", 6);
						orderBatchSize.add("70", 7);
						orderBatchSize.add("80", 8);
						orderBatchSize.add("90", 9);
						orderBatchSize.add("100", 10);
						
						orderBatchSize.select(0);
					}
					{
						isBatch_label = new Label(group2, SWT.NONE);
						isBatch_label.setText("is batch order :");
						isBatch_label.setBounds(7, 78, 77, 14);
					}
					{
						isBatch_combo = new Combo(group2, SWT.DROP_DOWN);
						isBatch_combo.setBounds(91, 74, 63, 21);
						
						isBatch_combo.add("false", 0);
						isBatch_combo.add("true", 1);
					}
					{
						mode_label = new Label(group2, SWT.NONE);
						mode_label.setText("mode :");
						mode_label.setBounds(7, 108, 63, 14);
					}
					{
						mode_combo = new Combo(group2, SWT.READ_ONLY);
						mode_combo.setBounds(91, 104, 63, 21);
						mode_combo.add("normal",0);
						mode_combo.add("stay pos",1);
						mode_combo.add("manual",2);
						
						
						
						mode_combo.addSelectionListener(new SelectionAdapter( ) {
				            public void widgetSelected(SelectionEvent e) {
				            	
				            	System.out.println("mode selected text :"+mode_combo.getText());
				            	System.out.println("mode selected value:"+mode_combo.getSelectionIndex());
				            	
				            }
				        });

						
						
					}
				}
				{
					process_label = new Label(composite1, SWT.NONE);
					process_label.setText("processing ... ");
					process_label.setBounds(7, 588, 63, 14);
				}
				{
					order_progressBar = new ProgressBar(composite1, SWT.HORIZONTAL | SWT.SMOOTH);
					order_progressBar.setBounds(7, 609, 539, 14);
					order_progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
					order_progressBar.setMinimum(0);
					order_progressBar.setMaximum(30);
					
					new LongRunningOperation(display, pb1).start();

					
				}
			}
			{
				menu1 = new Menu(getShell(), SWT.BAR);
				getShell().setMenuBar(menu1);
				{
					fileMenuItem = new MenuItem(menu1, SWT.CASCADE);
					fileMenuItem.setText("File");
					{
						fileMenu = new Menu(fileMenuItem);
						{
							openFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							openFileMenuItem.setText("Open");
						}
						{
							newFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							newFileMenuItem.setText("New");
						}
						{
							saveFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							saveFileMenuItem.setText("Save");
						}
						{
							closeFileMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							closeFileMenuItem.setText("Close");
						}
						{
							exitMenuItem = new MenuItem(fileMenu, SWT.CASCADE);
							exitMenuItem.setText("Exit");
						}
						fileMenuItem.setMenu(fileMenu);
					}
				}
				{
					helpMenuItem = new MenuItem(menu1, SWT.CASCADE);
					helpMenuItem.setText("Help");
					{
						helpMenu = new Menu(helpMenuItem);
						{
							contentsMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							contentsMenuItem.setText("Contents");
						}
						{
							aboutMenuItem = new MenuItem(helpMenu, SWT.CASCADE);
							aboutMenuItem.setText("About");
						}
						helpMenuItem.setMenu(helpMenu);
					}
				}
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		NewSWTApp inst = new NewSWTApp(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		
		
		
//		Display.getCurrent(display.asyncExec(new Runnable() {
//			public void run() {
//			//Inform the indicator that some amount of work has been done
//			indicator.worked(1);
//			}
//			}));
//
//		
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		
		
	}

	
	
	/**
	 * This class simulates a long-running operation
	 */
	class LongRunningOperation extends Thread {
	  private Display display;
	  private ProgressBar progressBar;

	  public LongRunningOperation(Display display, ProgressBar progressBar) {
	    this.display = display;
	    this.progressBar = progressBar;
	  }
	  public void run() {
	    // Perform work here--this operation just sleeps
	    for (int i = 0; i < 30; i++) {
	      try {
	        Thread.sleep(1000);
	      } catch (InterruptedException e) {
	        // Do nothing
	      }
	      display.asyncExec(new Runnable() {
	        public void run() {
	          if (progressBar.isDisposed()) return;

	          // Increment the progress bar
	          progressBar.setSelection(progressBar.getSelection() + 1);
	        }
	      });
	    }
	  }

}
