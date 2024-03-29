
package com.lubq.lm.bestwiz.order.ui.view;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
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
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

import cn.bestwiz.jhf.core.bo.enums.CustTraderModeEnum;
import cn.bestwiz.jhf.core.bo.enums.TradeTypeEnum;
import cn.bestwiz.jhf.core.jms.DestinationConstant;
import cn.bestwiz.jhf.core.jms.SimpleSender;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lubq.lm.bestwiz.order.builder.bean.MessageVenderFactory;
import com.lubq.lm.bestwiz.order.builder.bean.OrderBuilderMessageVender;
import com.lubq.lm.bestwiz.order.builder.bean.OrderForm;
import com.lubq.lm.bestwiz.order.builder.service.OrderBuilderAbstractFactory;
import com.lubq.lm.bestwiz.order.builder.service.OrderBuilderInstantService;
import com.lubq.lm.bestwiz.order.builder.service.OrderBuilderOpmService;
import com.lubq.lm.util.SWTResourceManager;
import com.lubq.lm.util.StringUtil;

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
public class OrderBuilderView extends org.eclipse.swt.widgets.Composite {

	private Menu menu1;
	private Label bindBatchSize_label;
	private Combo slipType_combo;
	private Label slipType_label;
	private Combo isBlackOrder_combo;
	private Label isBlackOrder_label;
	private Combo isMobile_combo;
	private Label isMobile_label;
	private Combo contract_combo;
	private Label settle_order_label;
	private Combo customerId_combo;
	private Combo orderPrice_combo;
	private Combo settle_pair_combo;
	private Label settle_curPair_label;
	private Combo settel_type_combo;
	private Label settle_type_label;
	private Group settle_order_group;
	private StyledText customerIdlist_list;
	private Label customerIdList_label;
	private Combo bindBatchSize_combo;
	private Button doOrderAndSettle_button;
	private Button doOpenOrder_button;
	private Combo slippage_combo;
	private Label slippage_label;
	private Combo executionType_combo;
	private Label executionType_label;
	private Combo orderAmount_combo;
	private Label orderAmount_label;
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
	private SimpleSender sender = null;
	private static Object lock = new Object();
	
//	private int orderProcessInit = 0;
	private int orderProcessMin = 0;
	private int orderProcessMax = 0;
	public static int orderPrcoessing = 0;
	private boolean processOver = false;
	public static final int nPerOrder = 10 ;
	
	//
	public static int scaling = 1;
	public static int bindCustomerSize = 1;
	//=================
	public static boolean alreadyInit = false;
	
	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	public OrderBuilderView(Composite parent, int style) {
		super(parent, style);
		initGUI();
		
		//init order stuff
//		initOrderBuilder();
	}
	
//	private void initOrderBuilder() {
//		
//		try {
//			sender = SimpleSender.getInstance(DestinationConstant.OrderRequestQueue);
//		} catch (JMSException e) {
//			System.err.println("sender init error ! ");
//			e.printStackTrace();
//		}
//		
//	}

	/**
	* Initializes the GUI.
	*/
	@SuppressWarnings("unchecked")
	private void initGUI() {
		try {
			

			
			this.setSize(750, 800);
			this.setBackground(SWTResourceManager.getColor(192, 192, 192));
			this.addDisposeListener(new DisposeListener(){

				public void widgetDisposed(DisposeEvent de) {
					System.out.println(" widget disposed  ... fire method .");
					if(null != sender){
						
						sender.close();
						System.exit(0);
					}
					
				}
				
			});
			
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
				composite1.setLocation(0, 0);
				GridData composite1LayoutData = new GridData();
				composite1LayoutData.widthHint = 742;
				composite1LayoutData.heightHint = 959;
				
				composite1.setLayoutData(composite1LayoutData);
				composite1.setLayout(null);
				{
					group1 = new Group(composite1, SWT.NONE);
					group1.setLayout(null);
					group1.setText("Customer Info");
					group1.setBounds(21, 21, 644, 70);
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
						customerIdlist_list = new StyledText(group1, SWT.BORDER);
						customerIdlist_list.setBounds(273, 14, 66, 49);
						
					}
					{
						customerId_combo = new Combo(group1, SWT.NONE);
						customerId_combo.setBounds(77, 21, 80, 14);
						customerId_combo.add("00000101",0);
						customerId_combo.add("00003242",1);
						customerId_combo.add("00006701",2);
						
					}
				}
				{
					group2 = new Group(composite1, SWT.NONE);
					group2.setLayout(null);
					group2.setText("Open Order Info");
					group2.setBounds(21, 112, 644, 287);
					
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
						executionType_combo.select(0);
						
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
						side_combo.select(0);
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
						orderBatchSize_combo.add("300", 11);
						orderBatchSize_combo.add("500", 12);
						
						orderBatchSize_combo.select(0);
					}

					{
						isBatch_combo = new Combo(group2, SWT.READ_ONLY);
						isBatch_combo.setBounds(91, 124, 63, 21);
						isBatch_combo.setBackground(SWTResourceManager.getColor(250, 210, 165));
						
						isBatch_combo.add("false", 0);
						isBatch_combo.add("true", 1);
						isBatch_combo.select(0);
						
					}
					{
						mode_label = new Label(group2, SWT.NONE);
						mode_label.setText("mode :");
						mode_label.setBounds(7, 158, 63, 15);
					}
					{
						mode_combo = new Combo(group2, SWT.READ_ONLY);
						mode_combo.setBounds(91, 154, 63, 21);

						
				    	List<CustTraderModeEnum> enList =  CustTraderModeEnum.getEnumList();
				    	
				    	for (int i = 0; i < enList.size(); i++) {
				    		CustTraderModeEnum cenum = enList.get(i);
				    		mode_combo.add(cenum.getName(),i);
							
						}
				    	
				    		
						
						
						
						mode_combo.select(0);
						
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
						orderAmount_combo.add("90000",9);
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
						slippage_combo.setBounds(270, 182, 63, 21);
						

						
						for (int i = 0; i < 100; i++) {
							slippage_combo.add("0."+i,i);
						}
						slippage_combo.select(99);
						
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
						bindBatchSize_combo.add("30",10);
						bindBatchSize_combo.add("50",11);
						bindBatchSize_combo.add("100",12);
						bindBatchSize_combo.add("300",13);
						bindBatchSize_combo.add("500",14);
						bindBatchSize_combo.select(0);
					}
					{
						doOpenOrder_button = new Button(group2, SWT.PUSH
							| SWT.CENTER);
						doOpenOrder_button.setText("\u65b0\u89c4\u6ce8\u6587");
						doOpenOrder_button.setBounds(539, 259, 98, 21);
//						doOpenOrder_button.setData("ACTION_SOURCE", "OPEN");
						doOpenOrder_button
							.addMouseListener(new OpenOrderMouseAdapter());
					}
					{
						orderPrice_combo = new Combo(group2, SWT.NONE);
						orderPrice_combo.setBounds(91, 182, 63, 21);
						
						orderPrice_combo.add("100.00",0);
						orderPrice_combo.add("200.00",1);
						orderPrice_combo.add("10.00",2);
						
						orderPrice_combo.select(1);
						
					}
					{
						isMobile_label = new Label(group2, SWT.NONE);
						isMobile_label.setText("is Mobile");
						isMobile_label.setBounds(180, 218, 84, 14);
					}
					{
						isMobile_combo = new Combo(group2, SWT.NONE);
						isMobile_combo.setBounds(270, 215, 63, 21);
						
						isMobile_combo.add("false",0);
						isMobile_combo.add("true",1);
						isMobile_combo.select(0);
						
					}
					{
						isBlackOrder_label = new Label(group2, SWT.NONE);
						isBlackOrder_label.setText("isBlackOrder :");
						isBlackOrder_label.setBounds(7, 249, 84, 14);
					}
					{
						isBlackOrder_combo = new Combo(group2, SWT.NONE);
						isBlackOrder_combo.setBounds(91, 245, 63, 21);
						
						isBlackOrder_combo.add("false",0);
						isBlackOrder_combo.add("true",1);
						isBlackOrder_combo.select(0);
					}
					{
						slipType_label = new Label(group2, SWT.NONE);
						slipType_label.setText("slipType :");
						slipType_label.setBounds(180, 245, 63, 21);
					}
					{
						slipType_combo = new Combo(group2, SWT.NONE);
						slipType_combo.setBounds(269, 245, 63, 21);
						
						slipType_combo.add("1", 0);
						slipType_combo.add("2", 1);
						slipType_combo.add("3", 2);

						slipType_combo.select(0);
					}
				}
				{
					process_label = new Label(composite1, SWT.NONE);
					process_label.setText("processing ... ");
					process_label.setBounds(7, 720, 63, 14);
					process_label.setVisible(false);
					
				}
				{
					order_progressBar = new ProgressBar(composite1, SWT.HORIZONTAL | SWT.SMOOTH);
					order_progressBar.setBounds(7, 749, 728, 14);
					order_progressBar.setMinimum(orderProcessMin);
//					order_progressBar.setMaximum(orderProcessMax);
					order_progressBar.setVisible(false);
					
					
					
					
					
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
					settle_order_group = new Group(composite1, SWT.NONE);
					settle_order_group.setLayout(null);
					settle_order_group.setText("Settle Order Info");
					settle_order_group.setBounds(22, 434, 644, 203);
					{
						doOrderAndSettle_button = new Button(settle_order_group,SWT.PUSH | SWT.CENTER);
						doOrderAndSettle_button.setText("\u51b3\u8ba1\u6ce8\u6587");
						doOrderAndSettle_button.setBounds(539, 17, 98, 21);
						doOrderAndSettle_button.addMouseListener(new SettleOrderMouseAdapter());
					}
					{
						settle_type_label = new Label(
							settle_order_group,
							SWT.NONE);
						settle_type_label.setText("settle type :");
						settle_type_label.setBounds(14, 24, 63, 15);
					}
					{
						settel_type_combo = new Combo(settle_order_group,SWT.READ_ONLY);
						settel_type_combo.setBounds(91, 21, 63, 21);
						settel_type_combo.add("NORMAL",0);
						settel_type_combo.add("EMER",1);
						settel_type_combo.add("LOSSCUT",2);
						settel_type_combo.select(0);
					}
					{
						settle_curPair_label = new Label(
							settle_order_group,
							SWT.NONE);
						settle_curPair_label.setText("settle currency pair :");
						settle_curPair_label.setBounds(170, 24, 100, 15);
					}
					{
						settle_pair_combo = new Combo(
							settle_order_group,
							SWT.NONE);
						settle_pair_combo.setBounds(274, 21, 63, 21);
						settle_pair_combo.add("USD/JPY", 0);
						settle_pair_combo.add("EUR/JPY", 1);
						settle_pair_combo.add("EUR/USD", 2);
						settle_pair_combo.add("AUD/USD", 3);
						settle_pair_combo.add("ALL",4);
						settle_pair_combo.addSelectionListener(new SelectionAdapter() {
						      public void widgetSelected(SelectionEvent event) {
						          // When the button is clicked, close the child shell
						          System.out.println(" select is done:"
						        		  + "currencyPair:"+settle_pair_combo.getText()
						        		  + "customerId:"+customerId_combo.getText());
						          
						        }
						      });
							
						
						
						
					}
					{
						settle_order_label = new Label(
							settle_order_group,
							SWT.NONE);
						settle_order_label.setText("settle order :");
						settle_order_label.setBounds(357, 24, 63, 15);
					}
					{
						contract_combo = new Combo(settle_order_group, SWT.NONE);
						contract_combo.setBounds(427, 21, 63, 21);
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
	


	protected void submitOpenOrderForm(OrderForm of) {
		
//		System.out.println("========:"+this.getCustomerIdlist_list().getText());
		
		String 	cId 					= this.getCustomerId_combo().getText();
		java.util.List<String> cIdList 	= StringUtil.splitString(this.getCustomerIdlist_list().getText(),"\r\n");
		String 	currencyPair 			= this.getCurrencyPair_combo().getText();
		int 	sideIndex 				= this.getSide_combo().getSelectionIndex();
		int     orderBatchSize 			= Integer.parseInt( this.getOrderBatchSize_combo().getText() );
		int     orderBindBatchSize 		= Integer.parseInt( this.getBindBatchSize_combo().getText() );
		boolean isBatch 				= Boolean.valueOf( this.getIsBatch_combo().getText() );
		int  	mode 					= this.getMode_combo().getSelectionIndex();
		BigDecimal orderPrice 			= new BigDecimal( this.getOrderPrice_combo().getText() );
		BigDecimal orderAmount 			= new BigDecimal(this.getOrderAmount_combo().getText());
		
		BigDecimal slippage 			= new BigDecimal(this.getSlippage_combo().getText());
		int executionTypeIndex 			= this.getExecutionType_combo().getSelectionIndex();
		
		int tradeType 					= TradeTypeEnum.TRADE_OPEN_ENUM.getValue();
		boolean isMobile 				= Boolean.parseBoolean( this.getIsMobile_combo().getText());
		boolean isBlackOrder			= Boolean.parseBoolean( this.isBlackOrder_combo.getText());
		int slipType					= Integer.parseInt(this.getSlipType_combo().getText());
		
		of.setCustomerId(cId);
		of.setCustomerIdList(cIdList);
		of.setCurrencyPair(currencyPair);
		of.setSide(sideIndex);
		of.setOrderBatchSize(orderBatchSize);
		of.setOrderBindBatchSize(orderBindBatchSize);
		of.setBatch(isBatch);
		of.setMode(mode);
		of.setOrderPrice(orderPrice);
		of.setOrderAmount(orderAmount);
		of.setSlippage(slippage);
		of.setExecutionType(executionTypeIndex);		

		of.setOrderBindType(executionTypeIndex);
		of.setTradeType(tradeType);
		of.setMobile(isMobile);
		
		of.setBlackOrder(isBlackOrder);
		of.setSlipType(slipType);
	}

	
	
	protected void submitSettleOrderForm(OrderForm of) {
		
//		System.out.println("========:"+this.getCustomerIdlist_list().getText());
		
		String 	cId 	= this.getCustomerId_combo().getText();
		java.util.List<String> cIdList = StringUtil.splitString(this.getCustomerIdlist_list().getText(),"\r\n");
		String 	settleCurrencyPair = this.getSettle_pair_combo().getText();
		
//		int 	sideIndex 	= this.getSide_combo().getSelectionIndex();
//		int     orderBatchSize = Integer.parseInt( this.getOrderBatchSize_combo().getText() );
//		int     orderBindBatchSize = Integer.parseInt( this.getBindBatchSize_combo().getText() );
//		boolean isBatch = Boolean.valueOf( this.getIsBatch_combo().getText() );
//		int  	mode = this.getMode_combo().getSelectionIndex();
//		BigDecimal orderPrice = new BigDecimal( this.getOrderPrice_text().getText() );
//		BigDecimal orderAmount = new BigDecimal(this.getOrderAmount_combo().getText());
//		
//		BigDecimal slippage = new BigDecimal(this.getSlippage_combo().getText());
		int executionTypeIndex = this.getSettel_type_combo().getSelectionIndex();
//		
//		int tradeType = this.orderTradeType;
		
//		for (String string : cIdList) {
//			
//			System.out.println("FOR  cid:"+string);
//		}
		
		
		
		
		of.setCustomerId(cId);
		of.setCustomerIdList(cIdList);
		of.setCurrencyPair(settleCurrencyPair);
//		of.setSide(sideIndex);
//		of.setOrderBatchSize(orderBatchSize);
//		of.setOrderBindBatchSize(orderBindBatchSize);
//		of.setBatch(isBatch);
//		of.setMode(mode);
//		of.setOrderPrice(orderPrice);
//		of.setOrderAmount(orderAmount);
//		of.setSlippage(slippage);
		
		of.setOrderBindType(executionTypeIndex);
		//it is    index
		of.setExecutionType( 0 );		
//
		of.setTradeType(TradeTypeEnum.TRADE_SETTLE_ENUM.getValue());
		
	}
	
	
	
	private void showProcessStuff() {
		this.process_label.setVisible(true);
		this.order_progressBar.setVisible(true);
		
	}
	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		
		Display display = Display.getDefault();
		Shell shell = new Shell(display ,SWT.CLOSE |SWT.MIN |SWT.MAX |SWT.TITLE);

		OrderBuilderView inst = new OrderBuilderView(shell, SWT.SYSTEM_MODAL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
//			inst.pack();
//			shell.pack();
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
	
	private void doOpenOrder_buttonMouseDown(OrderForm of) {
		
		System.out.println("button fire .....");
		final OrderForm form = of;
		new Thread() {
			public void run() {

				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						showProcessStuff();
//						new OrderRuningOperation().start();
						new LongRunningOperation().start();
					}

				});
				
				doUIOpenorder(form);
				
			}
			
		}.start();

	


		

		
	}



	protected void doUIOpenorder(OrderForm of) {
		
		try {
			sender = SimpleSender.getInstance(DestinationConstant.OrderRequestQueue);
		} catch (JMSException e1) {
			e1.printStackTrace();
		}
		
		OrderBuilderMessageVender orderVender = MessageVenderFactory.createOrderMsgVender(of);
		OrderBuilderAbstractFactory fac = null;
		
		switch (orderVender.getExecutionType()) {
		case 12:
			fac = new OrderBuilderInstantService(sender,orderVender);
			break;
			
		case 0:
			fac = new OrderBuilderOpmService(orderVender);
			break;
			
		case 1:
			fac = new OrderBuilderOpmService(orderVender);
			break;
			
		case 8:
			System.out.println("losscut execution type:"+ orderVender.getExecutionType());
			break;
			
		default:
			System.out.println("other execution type:"+ orderVender.getExecutionType());
			break;
		}
		
		
		
		try {
			fac.doOrder();
		} catch (Exception e) {
			System.out.println(" doOrder() error ! ");
			e.printStackTrace();
		}

		
//		sender.close();
		
		
	}

	/**
	 * This class simulates a long-running operation
	 */
	class LongRunningOperation extends Thread {

		public LongRunningOperation() {
		}

		public void run() {
			
//			while (orderProcessInit < 10) {
//
//				increaseOrderProcess(orderProcessInit);
//				
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//
//					e.printStackTrace();
//				}
//			}

			// Perform work here--this operation just sleeps
			while (orderPrcoessing <= orderProcessMax) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						
						if (order_progressBar.getSelection() >= orderProcessMax) {
							// 通知前台线程 改变 label
							System.out.println("process bar will finished .....");
							process_label.setText("finished ");
							processOver = true;
//							return;
						}

						
						// Increment the progress bar
						order_progressBar.setSelection(getOrderProcess());
						System.out.println(" progressBar.getSelection()="+ order_progressBar.getSelection()
								+ " , orderProcessMax ="+orderProcessMax);

					}
				});
				

				if(processOver){
					orderPrcoessing = 0;
					break;
				}
			}
		}
	}
	
	
// /**
// * This class simulates a long-running operation
// */
// class OrderRuningOperation extends Thread {
// // private Display display;
// // private ProgressBar progressBar;
////	  private Label label;
////	  int processNumber ;
//	  public OrderRuningOperation() {
////	    this.display = display;
////	    this.progressBar = app.getOrder_progressBar();
////	    this.label = app.getProcess_label();
////		  this.processNumber = orderProcess;
//	  }
//	  
//	  public void run() {
//		  while(orderProcessInit < 10){
//			  
//			  increaseOrderProcess(orderProcessInit);
//			  try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//
//				e.printStackTrace();
//			}
//		  }
//			  
//	  }
//	  
//
//	}


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



	/**
	 * @return the slipType_combo
	 */
	public Combo getSlipType_combo() {
		return slipType_combo;
	}

	/**
	 * @param slipType_combo the slipType_combo to set
	 */
	public void setSlipType_combo(Combo slipType_combo) {
		this.slipType_combo = slipType_combo;
	}

	public Combo getOrderPrice_combo() {
		return orderPrice_combo;
	}

	public void setOrderPrice_combo(Combo orderPrice_combo) {
		this.orderPrice_combo = orderPrice_combo;
	}

	public Combo getMode_combo() {
		return mode_combo;
	}

	public void setMode_combo(Combo mode_combo) {
		this.mode_combo = mode_combo;
	}



	public Combo getCustomerId_combo() {
		return customerId_combo;
	}

	public void setCustomerId_combo(Combo customerId_combo) {
		this.customerId_combo = customerId_combo;
	}

	public Combo getSettle_pair_combo() {
		return settle_pair_combo;
	}

	public void setSettle_pair_combo(Combo settle_pair_combo) {
		this.settle_pair_combo = settle_pair_combo;
	}

	public Combo getSettel_type_combo() {
		return settel_type_combo;
	}

	public void setSettel_type_combo(Combo settel_type_combo) {
		this.settel_type_combo = settel_type_combo;
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




	public Combo getBindBatchSize_combo() {
		return bindBatchSize_combo;
	}

	public void setBindBatchSize_combo(Combo bindBatchSize_combo) {
		this.bindBatchSize_combo = bindBatchSize_combo;
	}

	

	public Combo getIsMobile_combo() {
		return isMobile_combo;
	}

	public void setIsMobile_combo(Combo isMobile_combo) {
		this.isMobile_combo = isMobile_combo;
	}

	public StyledText getCustomerIdlist_list() {
		return customerIdlist_list;
	}

	public void setCustomerIdlist_list(StyledText customerIdlist_list) {
		this.customerIdlist_list = customerIdlist_list;
	}

	public ProgressBar getOrder_progressBar() {
		return order_progressBar;
	}

	public void setOrder_progressBar(ProgressBar order_progressBar) {
		this.order_progressBar = order_progressBar;
	}

	public Label getProcess_label() {
		return process_label;
	}

	public void setProcess_label(Label process_label) {
		this.process_label = process_label;
	}

	public int getOrderProcess() {
		return orderPrcoessing;
	}

	public static void increaseOrderProcess(int increase) {
		synchronized (lock) {
			OrderBuilderView.orderPrcoessing = OrderBuilderView.orderPrcoessing + increase;
			System.out.println("orderPrcoessing   ==== :"+orderPrcoessing);
		}

	}
	
	private void doOrderAndSettle_buttonMouseUp(MouseEvent evt) {
		System.out.println("doOrderAndSettle_button.mouseUp, event=" + evt);
		//TODO add your code for doOrderAndSettle_button.mouseUp
	}
	
	
	
	class OpenOrderMouseAdapter extends MouseAdapter{

		@Override
		public void mouseDoubleClick(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseDoubleClick(e);
		}

		@Override
		public void mouseDown(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseDown(e);
		}



			
		@Override
		public void mouseUp(MouseEvent e) {
			
			OrderForm of = new OrderForm();
			// System.out.println("TEXT:"+customerIdlist_list.getText());
			submitOpenOrderForm(of);
			
			if (of.isBatch()) {
				scaling = of.getOrderBatchSize() * of.getOrderBindBatchSize()
						* of.getCustomerIdList().size();
				bindCustomerSize = of.getOrderBindBatchSize()
						* of.getCustomerIdList().size();
				orderProcessMax = nPerOrder * scaling;
				order_progressBar.setMaximum(orderProcessMax);
			} else {
				orderProcessMax = nPerOrder;
				order_progressBar.setMaximum(orderProcessMax);
			}

			System.out.println("===============> Maximum:" + orderProcessMax
					+ ",orderprocessing:" + orderPrcoessing);

			doOpenOrder_buttonMouseDown(of);

		}

	}

	
	
	class SettleOrderMouseAdapter extends MouseAdapter{

		@Override
		public void mouseDoubleClick(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseDoubleClick(e);
		}

		@Override
		public void mouseDown(MouseEvent e) {
			// TODO Auto-generated method stub
			super.mouseDown(e);
		}



			
		@Override
		public void mouseUp(MouseEvent e) {

			
			OrderForm of = new OrderForm();
			// System.out.println("TEXT:"+customerIdlist_list.getText());
			submitSettleOrderForm(of);

			if (of.isBatch()) {
				scaling = of.getOrderBatchSize() * of.getOrderBindBatchSize()
						* of.getCustomerIdList().size();
				bindCustomerSize = of.getOrderBindBatchSize()
						* of.getCustomerIdList().size();
				orderProcessMax = nPerOrder * scaling;
				order_progressBar.setMaximum(orderProcessMax);
			} else {
				orderProcessMax = nPerOrder;
				order_progressBar.setMaximum(orderProcessMax);
			}

			System.out.println("===============> Maximum:" + orderProcessMax
					+ ",orderprocessing:" + orderPrcoessing);

			doSettleOrder_buttonMouseDown(of);

		}

		private void doSettleOrder_buttonMouseDown(OrderForm of) {
			
			System.out.println("button fire .....");
			final OrderForm form = of;
			new Thread() {
				public void run() {

					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							showProcessStuff();
//							new OrderRuningOperation().start();
							new LongRunningOperation().start();
						}

					});
					
					doUIOpenorder(form);
					
				}
				
			}.start();

		
			
		}

	}
}
