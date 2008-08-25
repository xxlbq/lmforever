package com.lubq.lm.bestwiz.order.ui.view;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressIndicator;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.jms.DestinationConstant;
import cn.bestwiz.jhf.core.jms.SimpleSender;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lm.common.util.number.NumberCommonUtil;
import com.lubq.lm.bestwiz.order.builder.OrderBuilderInstantFactory;
import com.lubq.lm.bestwiz.order.builder.bean.MessageVenderFactory;
import com.lubq.lm.bestwiz.order.builder.bean.OrderBuilderMessageVender;
import com.lubq.lm.util.SWTResourceManager;

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
	private Label bindBatchSize_label;
	private List customerIdlist_list;
	private Label customerIdList_label;
	private Combo bindBatchSize_combo;
	private Button cancel_button;
	private Button doOrder_button;
	private Combo slippage_combo;
	private Label slippage_label;
	private Combo executionType_combo;
	private Label executionType_label;
	private Combo orderAmount_combo;
	private Label orderAmount_label;
	private Text orderPrice_text;
	private Label orderPrice_label;
	private ProgressBar order_progressBar;
	private Label process_label;
	private Combo mode_combo;
	private Label mode_label;
	private Label side_label;
	private Combo orderBatchSize_combo;
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

	
	//=================
	private SimpleSender sender;
	
	//=================
	
	
	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public NewSWTApp(Composite parent, int style) {
		super(parent, style);
		initGUI();
		
		//init order stuff
//		initOrderBuilder();
	}
	
	private void initOrderBuilder() {
		
		try {
			sender = SimpleSender.getInstance(DestinationConstant.OrderRequestQueue);
		} catch (JMSException e) {
			System.err.println("sender init error ! ");
			e.printStackTrace();
		}
		
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
					group1.setBounds(21, 21, 644, 70);
					{
						customerId_text = new Text(group1, SWT.NONE);
						customerId_text.setBounds(77, 21, 56, 14);
						
					}
					{
						customerId_label = new Label(group1, SWT.NONE);
						customerId_label.setText("customer id :");
						customerId_label.setBounds(8, 21, 63, 15);
					}
					{
						customerIdList_label = new Label(group1, SWT.NONE);
						customerIdList_label.setText("customer id List :");
						customerIdList_label.setBounds(180, 21, 84, 15);
					}
					{
						customerIdlist_list = new List(group1, SWT.NONE);
						customerIdlist_list.setBounds(273, 14, 66, 49);
					}
				}
				{
					group2 = new Group(composite1, SWT.NONE);
					group2.setLayout(null);
					group2.setText("order info");
					group2.setBounds(21, 112, 644, 273);
					
					{
						executionType_label = new Label(group2, SWT.NONE);
						executionType_label.setText("execution type :");
						executionType_label.setBounds(7, 28, 80, 15);
						executionType_label.setBackground(SWTResourceManager.getColor(182, 254, 223));
					}
					{
						executionType_combo = new Combo(group2, SWT.READ_ONLY);
						executionType_combo.setBounds(91, 24, 63, 21);
						executionType_combo.setBackground(SWTResourceManager.getColor(182, 254, 223));
						
						executionType_combo.add("成行",0);
						executionType_combo.add("LIMIT",1);
						executionType_combo.add("STOP",2);
						executionType_combo.add("LOSSCUT",3);
						
					}
					
					
					{
						currencyPair_label = new Label(group2, SWT.NONE);
						currencyPair_label.setText("currency pair :");
						currencyPair_label.setBounds(7, 68, 70, 15);
					}

					{
						orderBatchSize_label = new Label(group2, SWT.NONE);
						orderBatchSize_label.setText("order batch size :");
						orderBatchSize_label.setBounds(180, 128, 84, 15);
						orderBatchSize_label.setBackground(SWTResourceManager.getColor(250, 210, 165));
						
					}
					{
						currencyPair_combo = new Combo(group2, SWT.READ_ONLY);
						currencyPair_combo.setBounds(91, 64, 63, 21);
						currencyPair_combo.add("USD/JPY", 0);
						currencyPair_combo.add("EUR/JPY", 1);
						currencyPair_combo.add("EUR/USD", 2);
						currencyPair_combo.add("AUD/USD", 3);
						
						currencyPair_combo.select(0);
					}
					
					{
						side_label = new Label(group2, SWT.NONE);
						side_label.setText("side :");
						side_label.setBounds(7, 98, 63, 15);
					}
					
					{

						side_combo = new Combo(group2, SWT.SINGLE | SWT.BORDER|SWT.READ_ONLY);
						side_combo.setBounds(91, 94, 63, 21);
						side_combo.add("买", 0);
						side_combo.add("卖", 1);
						side_combo.select(1);
//						side_combo.set
					}
					
					
					{
						isBatch_label = new Label(group2, SWT.NONE);
						isBatch_label.setText("is batch order :");
						isBatch_label.setBounds(7, 128, 80, 15);
						isBatch_label.setBackground(SWTResourceManager.getColor(250, 210, 165));
					}
					
					
					
					
					
					{
						orderBatchSize_combo = new Combo(group2, SWT.NONE);
						orderBatchSize_combo.setBounds(270, 124, 63, 21);
						orderBatchSize_combo.add("1", 0);
						orderBatchSize_combo.add("10", 1);
						orderBatchSize_combo.add("20", 2);
						orderBatchSize_combo.add("30", 3);
						orderBatchSize_combo.add("40", 4);
						orderBatchSize_combo.add("50", 5);
						orderBatchSize_combo.add("60", 6);
						orderBatchSize_combo.add("70", 7);
						orderBatchSize_combo.add("80", 8);
						orderBatchSize_combo.add("90", 9);
						orderBatchSize_combo.add("100", 10);
						
						orderBatchSize_combo.select(0);
					}

					{
						isBatch_combo = new Combo(group2, SWT.READ_ONLY);
						isBatch_combo.setBounds(91, 124, 63, 21);
						isBatch_combo.setBackground(SWTResourceManager.getColor(250, 210, 165));
						
						isBatch_combo.add("false", 0);
						isBatch_combo.add("true", 1);
					}
					{
						mode_label = new Label(group2, SWT.NONE);
						mode_label.setText("mode :");
						mode_label.setBounds(7, 158, 63, 15);
					}
					{
						mode_combo = new Combo(group2, SWT.READ_ONLY);
						mode_combo.setBounds(91, 154, 63, 21);
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
					{
						orderPrice_label = new Label(group2, SWT.NONE);
						orderPrice_label.setText("order price :");
						orderPrice_label.setBounds(7, 188, 63, 15);
					}
					{
						orderPrice_text = new Text(group2, SWT.NONE);
						orderPrice_text.setBounds(91, 184, 63, 21);
					}
					{
						orderAmount_label = new Label(group2, SWT.NONE);
						orderAmount_label.setText("amount :");
						orderAmount_label.setBounds(7, 218, 63, 15);
					}
					{
						orderAmount_combo = new Combo(group2, SWT.DROP_DOWN);
						orderAmount_combo.setBounds(91, 214, 63, 21);
						
						
						orderAmount_combo.add("请自定义输入",0);
						orderAmount_combo.add("10000",1);
						orderAmount_combo.add("20000",2);
						orderAmount_combo.add("30000",3);
						orderAmount_combo.add("40000",4);
						orderAmount_combo.add("50000",5);
						orderAmount_combo.add("60000",6);
						orderAmount_combo.add("70000",7);
						orderAmount_combo.add("80000",8);
						orderAmount_combo.add("90000t",9);
						orderAmount_combo.add("100000",10);
						
						orderAmount_combo.select(1);
	
					}

					{
						slippage_label = new Label(group2, SWT.NONE);
						slippage_label.setText("slippage :");
						slippage_label.setBounds(180, 187, 84, 14);
					}
					{
						slippage_combo = new Combo(group2, SWT.NONE);
						slippage_combo.setBounds(270, 184, 63, 21);
						

						
						for (int i = 0; i < 100; i++) {
							slippage_combo.add("0."+i,i);
						}
						
					}
					{
						bindBatchSize_label = new Label(group2, SWT.NONE);
						bindBatchSize_label.setText("bind batch size :");
						bindBatchSize_label.setBounds(359, 128, 80, 15);
						bindBatchSize_label.setBackground(SWTResourceManager.getColor(250, 210, 165));
					}
					{
						bindBatchSize_combo = new Combo(group2, SWT.NONE);
						bindBatchSize_combo.setBounds(455, 124, 63, 21);
						bindBatchSize_combo.add("1",0);
						bindBatchSize_combo.add("2",1);
						bindBatchSize_combo.add("3",2);
						bindBatchSize_combo.add("4",3);
						bindBatchSize_combo.add("5",4);
						bindBatchSize_combo.add("6",5);
						bindBatchSize_combo.add("7",6);
						bindBatchSize_combo.add("8",7);
						bindBatchSize_combo.add("9",8);
						bindBatchSize_combo.add("10",9);
					}
				}
				{
					process_label = new Label(composite1, SWT.NONE);
					process_label.setText("processing ... ");
					process_label.setBounds(7, 588, 63, 14);
				}
				{
					order_progressBar = new ProgressBar(composite1, SWT.HORIZONTAL | SWT.SMOOTH);
					order_progressBar.setBounds(7, 609, 707, 14);
					order_progressBar.setMinimum(0);
					order_progressBar.setMaximum(10);
					
		
					
					new LongRunningOperation(this.getDisplay(), order_progressBar).start();
					
					new FrontUIRuningOperation(this.getDisplay(), order_progressBar ,process_label).start();
					
//					Display.getDefault().syncExec(new Runnable(){
//						public void run(){
//							
//							while(true){
//								try {
//							        Thread.sleep(1000);
//							        
//							        if(order_progressBar.getSelection() >= 10 ){
//							        	System.out.println(" process is over.");
//							        	process_label.setText(" process finished .");
//							        }
//							      } catch (InterruptedException e) {
//							        // Do nothing
//							      }
//							}
//							
//							
//						}
//					}
//					);
					
				}
				{
					doOrder_button = new Button(composite1, SWT.PUSH
						| SWT.CENTER);
					doOrder_button.setText("\u6ce8\u6587");
					doOrder_button.setBounds(436, 544, 100, 20);
					doOrder_button.addMouseListener(new MouseAdapter() {
						public void mouseDown(MouseEvent evt) {
							doOrder_buttonMouseDown(evt);
						}
					});
				}
				{
					cancel_button = new Button(composite1, SWT.PUSH
						| SWT.CENTER);
					cancel_button.setText("\u53d6\u6d88");
					cancel_button.setBounds(546, 545, 100, 20);
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
	
	private void doOrder_buttonMouseDown(MouseEvent evt) {
		System.out.println("doOrder_button.mouseDown, event=" + evt);
		
		System.out.println("execution type : " + executionType_combo.getText() + 
				           " ---> index:"+executionType_combo.getSelectionIndex());
		
		
		OrderBuilderMessageVender orderVender = MessageVenderFactory.createOrderMsgVender(this);
		OrderBuilderInstantFactory fac = new OrderBuilderInstantFactory(sender,orderVender);
		
		try {
			fac.doOrder();
		} catch (Exception e) {
			System.out.println(" doOrder() error ! ");
			e.printStackTrace();
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
	          if (progressBar.getSelection() >= 10) {
	        	  //通知前台线程 改变 label
	        	  System.out.println(" progressBar.getSelection()="+progressBar.getSelection());      	  
	        	  return;
	          }

	          // Increment the progress bar
	          progressBar.setSelection(progressBar.getSelection() + 2 );
	          
//	          System.out.println();
	          
	        }
	      });
	    }
	  }
	}
	
	
	/**
	 * This class simulates a long-running operation
	 */
	class FrontUIRuningOperation extends Thread {
	  private Display display;
	  private ProgressBar progressBar;
	  private Label label;

	  public FrontUIRuningOperation(Display display, ProgressBar progressBar,Label process_label) {
	    this.display = display;
	    this.progressBar = progressBar;
	    this.label = process_label;
	  }
	  
	  public void run() {
	    // Perform work here--this operation just sleeps
	    for (int i = 0; i < 50; i++) {
	      try {
	        Thread.sleep(1000);
	      } catch (InterruptedException e) {
	        // Do nothing
	      }
	      display.asyncExec(new Runnable() {
	        public void run() {
	          if (progressBar.getSelection() >= 10) {
	        	  //通知前台线程 改变 label
	        	  System.out.println("FrontUI thread running.");  
	        	  label.setText("finished ");
	        	  return;
	          }

	          
	        }
	      });
	    }
	  }
	}


	public Combo getSlippage_combo() {
		return slippage_combo;
	}

	public void setSlippage_combo(Combo slippage_combo) {
		this.slippage_combo = slippage_combo;
	}

	public Combo getOrderAmount_combo() {
		return orderAmount_combo;
	}

	public void setOrderAmount_combo(Combo orderAmount_combo) {
		this.orderAmount_combo = orderAmount_combo;
	}

	public Text getOrderPrice_text() {
		return orderPrice_text;
	}

	public void setOrderPrice_text(Text orderPrice_text) {
		this.orderPrice_text = orderPrice_text;
	}

	public Combo getMode_combo() {
		return mode_combo;
	}

	public void setMode_combo(Combo mode_combo) {
		this.mode_combo = mode_combo;
	}



	public Combo getExecutionType_combo() {
		return executionType_combo;
	}

	public void setExecutionType_combo(Combo executionType_combo) {
		this.executionType_combo = executionType_combo;
	}

	public Combo getOrderBatchSize_combo() {
		return orderBatchSize_combo;
	}

	public void setOrderBatchSize_combo(Combo orderBatchSize_combo) {
		this.orderBatchSize_combo = orderBatchSize_combo;
	}

	public Combo getIsBatch_combo() {
		return isBatch_combo;
	}

	public void setIsBatch_combo(Combo isBatch_combo) {
		this.isBatch_combo = isBatch_combo;
	}

	public Combo getSide_combo() {
		return side_combo;
	}

	public void setSide_combo(Combo side_combo) {
		this.side_combo = side_combo;
	}

	public Combo getCurrencyPair_combo() {
		return currencyPair_combo;
	}

	public void setCurrencyPair_combo(Combo currencyPair_combo) {
		this.currencyPair_combo = currencyPair_combo;
	}

	public Text getCustomerId_text() {
		return customerId_text;
	}

	public void setCustomerId_text(Text customerId_text) {
		this.customerId_text = customerId_text;
	}
	
	public List getCustomerIdlist_list() {
		return customerIdlist_list;
	}

	public Combo getBindBatchSize_combo() {
		return bindBatchSize_combo;
	}

	public void setBindBatchSize_combo(Combo bindBatchSize_combo) {
		this.bindBatchSize_combo = bindBatchSize_combo;
	}

	public void setCustomerIdlist_list(List customerIdlist_list) {
		this.customerIdlist_list = customerIdlist_list;
	}

}
