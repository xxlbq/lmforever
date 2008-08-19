package com.lubq.lm.bestwiz.order.ui.view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
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
	private Label side_label;
	private Combo side_combo;
	private Combo currencyPair_combo;
	private Text text1;
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
			this.setSize(728, 474);
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
				composite1LData.widthHint = 707;
				composite1LData.heightHint = 378;
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
						side_label.setBounds(7, 49, 63, 15);
						side_label.setSize(70, 15);
					}
					{
						orderBatchSize_label = new Label(group2, SWT.NONE);
						orderBatchSize_label.setText("order batch size :");
						orderBatchSize_label.setBounds(7, 81, 84, 14);
					}
					{
						text1 = new Text(group2, SWT.NONE);
						text1.setBounds(91, 82, 56, 14);
					}
					{
						currencyPair_combo = new Combo(group2, SWT.NONE);
						currencyPair_combo.setText("combo1");
						currencyPair_combo.setBounds(91, 14, 63, 21);
					}
					{
						side_combo = new Combo(group2, SWT.NONE);
						side_combo.setBounds(91, 42, 63, 21);
						side_combo.setItems(new java.lang.String[] {"买","卖"});
//						side_combo.set
					}
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
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

}
