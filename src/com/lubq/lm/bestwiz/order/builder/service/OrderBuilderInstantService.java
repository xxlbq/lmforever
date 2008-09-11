package com.lubq.lm.bestwiz.order.builder.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;





import cn.bestwiz.jhf.core.bo.enums.OrderTypeEnum;
import cn.bestwiz.jhf.core.bo.enums.TradeTypeEnum;
import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.DAOFactory;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveContract;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrderId;
import cn.bestwiz.jhf.core.dao.util.DbSessionFactory;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.DestinationConstant;
import cn.bestwiz.jhf.core.jms.SimpleSender;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lubq.lm.bestwiz.order.builder.bean.MessageVenderFactory;
import com.lubq.lm.bestwiz.order.builder.bean.OrderBuilderMessageVender;
import com.lubq.lm.bestwiz.order.builder.cons.OrderConstants;
import com.lubq.lm.bestwiz.order.builder.dao.OrderBuilderDao;
import com.lubq.lm.bestwiz.order.ui.view.OrderBuilderView;
import com.lubq.lm.util.CommonExtension;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;




public class OrderBuilderInstantService extends OrderBuilderAbstractFactory{
	//成行注文需要发送 jms 给trader
	private SimpleSender orderRequestSender 			= null;
	private OrderBindInfo singleBindInfo 				= null;
	private List<OrderBindInfo> muliBindInfoList 			= null;
	private OrderBuilderMessageVender orderMessageVender= null;
	

	
	public OrderBuilderInstantService(SimpleSender sender,OrderBuilderMessageVender orderVender) {
		this.orderRequestSender = sender;
		this.orderMessageVender = orderVender;
		
		System.out.println(orderVender.toString());
		
//		System.out.println("instants order fullPropPath:"+fullPropPath);
//		this.propFullPath = fullPropPath;
	}
	
	
//	public PropertiesUtil loadProperty(String fullPropPath){
//		return new PropertiesUtil(fullPropPath);
//	}
//
//	
//	
//	public String getPropFullPath() {
//		return this.propFullPath;
//	}


	
		
	public JhfAliveOrder createOpenOrder(String customer,String orderBindId) throws IdGenerateException {
		return getCommonInstantsOrder(customer,orderBindId);
	}

	public JhfAliveOrder createSettleOrder(String customer,String orderBindId,JhfAliveContract contract) throws IdGenerateException {
		return getSettleInstantsOrder(customer,orderBindId,contract);
	}



	private void afterSingleInstantsOrder(OrderBindInfo bindInfo) throws JMSException {
		
		System.out.println( " Instant Single Order Do Finishing  ..." );
		orderRequestSender.sendMessage(bindInfo);
		System.out.println( " Instant Single Order Finish over ." );
	}

	
	private void afterMultiInstantsOrder(List<OrderBindInfo> bindInfoList) throws JMSException {
		
		System.out.println( " Instant Multi Order Do Finishing  ..." );
		for (OrderBindInfo orderBindInfo : bindInfoList) {
			orderRequestSender.sendMessage(orderBindInfo);
		}
		System.out.println( " Instant Multi Order Finish over ." );
	}
	
	

	
	public void initOrder() {
		
		if(OrderBuilderView.alreadyInit == true){
			return;
		}
		
		System.out.println( " Instant order do init() ..." );
		super.initOrder();
		//self method be here
		OrderBuilderView.increaseOrderProcess(2 * OrderBuilderView.scaling); 
		System.out.println( " Instant order init() over ." );
	}

	
	
	public void finishOrder() throws JMSException {
		
		if(getOrderBuilderMessageVender().isDoBatch()){
			if(getOrderBuilderMessageVender().getTradeType() == 1){
				System.out.println(" ==============  settle order   .  not send msg  ");
				return;
			}
			System.out.println("Multi bind info sending ...");
			afterMultiInstantsOrder(muliBindInfoList);
			
			System.out.println("Multi bind info send over .");
		}else{
			System.out.println("Single bind info sending ...");
			afterSingleInstantsOrder(singleBindInfo);
			
			System.out.println("Single bind info send over .");
		}
		
		OrderBuilderView.increaseOrderProcess(2 * OrderBuilderView.scaling); 
		
		OrderBuilderView.alreadyInit = true;
	}

	
	
	
	
//	public OrderBindInfo createOrderBindInfo(String orderBindId,
//			JhfAliveOrder order) {
//		
//		return null;
//	}
	
	

	
	public void service() throws Exception {

		System.out.println("isBatch:"+getOrderBuilderMessageVender().isDoBatch());
		
		//新规
		if(orderMessageVender.getTradeType() == 0){

			
			if ( ! orderMessageVender.isDoBatch()) {
				
				DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
	
				String orderbindId = IdGenerateFacade.getOrderBindId();
				JhfAliveOrder order = createOpenOrder(orderMessageVender.getCustomerId(),orderbindId);
				OrderBuilderView.increaseOrderProcess(1 * OrderBuilderView.scaling); 
				
				singleBindInfo = buildSingleOrderBindInfo(orderbindId,orderMessageVender.getOrderBindType(), order);
				OrderBuilderView.increaseOrderProcess(1 * OrderBuilderView.scaling); 
				
				writeOrder(order);
				OrderBuilderView.increaseOrderProcess(4 * OrderBuilderView.scaling); 
	
	//			if (null != bind) {
	//				writeOrderBind(bind);
	//			}
	
				DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
				DbSessionFactory.closeConnection();
	
	//			if (null != bind) {
	//
	//				singleBindInfo = OrderBindInfoFactory.getInstance().createInfo(bind);
	//				singleBindInfo = setupOrderBindInfo(singleBindInfo, order);
	//
	//			}
	
			} else {
	
				muliBindInfoList = new ArrayList<OrderBindInfo>();
				// 多个customerId
				List<String> customerIdList = orderMessageVender.getCustomerIdList();
	
				for (String cstId : customerIdList) {
	
					for (int bindi = 0; bindi < orderMessageVender.getOrderBindBatchSize(); bindi++) {
	
						DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
	
						List<JhfAliveOrder> batchOrderList = new ArrayList<JhfAliveOrder>();
	//					List<JhfOrderBind> orderBindList = new ArrayList<JhfOrderBind>();
						// 生成orderBindId ，一个bindId，对应多个orderId
						String orderbindId = IdGenerateFacade.getOrderBindId();
						int orderBatchSize = orderMessageVender.getOrderBatchSize();
						for (int i = 0; i < orderBatchSize; i++) {
							// 创建order对象，并添加到orderList中
							JhfAliveOrder order = createOpenOrder(cstId,orderbindId);
							batchOrderList.add(order);
							OrderBuilderView.increaseOrderProcess(4); 
	
							// 创建orderBind对象，并添加到orderBindList中
	//						JhfOrderBind bind = createOrderBind(orderbindId, order
	//								.getId().getOrderId(), order.getId()
	//								.getTradeId());
	//						orderBindList.add(bind);
	
						}
						System.out.println("after order increase  orderbindid:"+orderbindId+" , orderPrcoessing:" +OrderBuilderView.orderPrcoessing);
						// 将 order 写入db
						writeBatchOrder(batchOrderList);
						OrderBuilderView.increaseOrderProcess(1 * batchOrderList.size() ); 
						// 将 orderbind 写入 db
	//					writeBatchOrderBind(orderBindList);
	
						DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
	
						// 创建orderBindInfo对象，并添加到orderBindInfoList中
						OrderBindInfo bindInfo = setupMuliOrdersOrderBindInfo(orderbindId,orderMessageVender.getOrderBindType(), batchOrderList);
						OrderBuilderView.increaseOrderProcess( 1 * batchOrderList.size() );
						muliBindInfoList.add(bindInfo);
					}
					System.out.println("after customer:"+cstId+" , orderPrcoessing:"+OrderBuilderView.orderPrcoessing);
				}
	
	
				DbSessionFactory.closeConnection();
	
			}
		
		
		}else{//决计

			
			muliBindInfoList = new ArrayList<OrderBindInfo>();
			// 多个customerId
			List<String> customerIdList = orderMessageVender.getCustomerIdList();

			if(CommonExtension.collectionIsEmpty((customerIdList))){
				customerIdList.add(orderMessageVender.getCustomerId());
			}
			
			for (String cstId : customerIdList) {

//				for (int bindi = 0; bindi < orderMessageVender.getOrderBindBatchSize(); bindi++) {

				
				
					DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);

					List<JhfAliveOrder> orderList = new ArrayList<JhfAliveOrder>();
					List<JhfAliveContract> contractList = getContractByCustomerId(orderMessageVender);
					
//					List<JhfOrderBind> orderBindList = new ArrayList<JhfOrderBind>();
					// 生成orderBindId ，一个bindId，对应多个orderId
					String orderbindId = IdGenerateFacade.getOrderBindId();
					int contractSize = contractList.size();
					
					for (int i = 0; i < contractSize; i++) {
						// 创建order对象，并添加到orderList中
						JhfAliveOrder order = createSettleOrder(cstId,orderbindId,contractList.get(i));
						System.out.println("settle order :"+order);
						orderList.add(order);
						OrderBuilderView.increaseOrderProcess(4); 

						// 创建orderBind对象，并添加到orderBindList中
//						JhfOrderBind bind = createOrderBind(orderbindId, order
//								.getId().getOrderId(), order.getId()
//								.getTradeId());
//						orderBindList.add(bind);

						
						
						//更新 amountSetting
						System.out.println("order amount :"+order.getOrderAmount());
						DAOFactory.getContractDao().changeSettleOrderToContract(order);
					}
					
					System.out.println("after order increase  orderbindid:"+orderbindId+" , orderPrcoessing:" +OrderBuilderView.orderPrcoessing);
					// 将 order 写入db
					writeBatchOrder(orderList);
					OrderBuilderView.increaseOrderProcess(1 * orderList.size() ); 
					// 将 orderbind 写入 db
//					writeBatchOrderBind(orderBindList);

					DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);

					// 创建orderBindInfo对象，并添加到orderBindInfoList中
					OrderBindInfo bindInfo = setupMuliOrdersOrderBindInfo(orderbindId,orderMessageVender.getOrderBindType(), orderList);
					OrderBuilderView.increaseOrderProcess( 1 * orderList.size() );
					muliBindInfoList.add(bindInfo);
//				}
				System.out.println("after customer:"+cstId+" , orderPrcoessing:"+OrderBuilderView.orderPrcoessing);
			}


			DbSessionFactory.closeConnection();

		
		}

	}




	
	
	
	
	public JhfAliveOrder getCommonInstantsOrder(String customerId,String orderBindId) throws IdGenerateException{
		
		String boardRateStr = "100.00";
		String orderStatuStr = "7";
		String orderTypeStr = "0";

		
		JhfAliveOrder order  = new JhfAliveOrder();
		
		//  ==== PK setup =======> 
		JhfAliveOrderId id = new JhfAliveOrderId();
		id.setOrderId(createOrderId(customerId));
		id.setTradeId(IdGenerateFacade.getTradeId());
		// < =====================
		
		
		order.setId(id);
		order.setOrderAmount(orderMessageVender.getAmount());
		order.setCustomerId(customerId);
		order.setCurrencyPair(orderMessageVender.getCurrencyPair());
		
		order.setExecutionType(new BigDecimal(String.valueOf(orderMessageVender.getExecutionType())));
		order.setOrderPrice(orderMessageVender.getOrderPrice());
		order.setTradeType(BigDecimal.valueOf(orderMessageVender.getTradeType()));
		order.setActiveFlag(BigDecimal.ONE);
		
		order.setRevisionNumber(1);
		order.setOrderStatus(new BigDecimal(orderStatuStr));
		order.setSlippage(orderMessageVender.getSlippage());
		order.setOrderType(new BigDecimal(orderTypeStr));
		order.setSide(new BigDecimal(String.valueOf( orderMessageVender.getSide() )));
		order.setCustomerOrderNo(IdGenerateFacade.obtainCustomerOrderNo(orderMessageVender.getCustomerId()));

		
		
		order.setActivationType(BigDecimal.ZERO);
		order.setBoardRate(new BigDecimal(boardRateStr));
		order.setChannelId("WEB");
		order.setInputStaffId("lubq");
		order.setLosscutOrderFlag(BigDecimal.ZERO);
		order.setMarketAtClosingFlag(BigDecimal.ZERO);
		order.setOrderBindId(orderBindId);
		order.setOrderDate("20080815");
		order.setOrderRoute(BigDecimal.ZERO);
		order.setOrderTime("123456");
		order.setPriceId("PID007");
		order.setProductId("A001");//USD/JPY = A001,EUR/JPY = A002	
//		order.setTradePrice(new BigDecimal(tradePriceStr));
		order.setInputDate(new Date());
		order.setUpdateDate(new Date());
		order.setUpdateStaffId("[lubq]");
		order.setOrderDatetime(new Date());
		order.setChangeReason(new BigDecimal("1"));
		order.setForceRelationId(order.getCustomerOrderNo());
		order.setForceRelationFlag(BigDecimal.ZERO);
		order.setCancelRejectFlag(BigDecimal.ZERO);
		order.setCustExecutionType(new BigDecimal("0"));
		order.setExpirationType(new BigDecimal("-1"));
		order.setStPriceId(BigDecimal.ZERO);
		order.setInputStaffId("system");
		order.setAgentStaffId("system");
		order.setActivationDate(new Date());
		order.setTopOrderId("520");
		
		
		return order;
	}

	
	public   JhfAliveOrder getSettleInstantsOrder(String customerId,String orderBindId,JhfAliveContract contract) throws IdGenerateException{
		
		orderMessageVender.setAmount(contract.getAmountNoSettled());
		orderMessageVender.setSide(contract.getSide().negate().intValue());
		if(orderMessageVender.getSide() == 1){
			orderMessageVender.setOrderPrice(new BigDecimal("200"));
		}else{
			orderMessageVender.setOrderPrice(new BigDecimal("1"));
		}
		
		orderMessageVender.setSlippage(new BigDecimal("0.99"));
		
		orderMessageVender.setDoBatch(true);
		
		JhfAliveOrder order = getCommonInstantsOrder(customerId,orderBindId);
		
		order.setTradeType(BigDecimal.valueOf( TradeTypeEnum.TRADE_SETTLE_ENUM.getValue()) );
		order.setTopOrderId(contract.getOrderId());
		order.setSettleContractId(contract.getContractId());
//		order.setOrderAmount(contract.getAmountNoSettled());
//		order.setSide(contract.getSide().negate());
		
		System.out.println("orderId:"+order.getId().getOrderId()+",orderSide:"+order.getSide()
				+",contractId:"+contract.getContractId() + ",contractSide:"+contract.getSide()
				+",orderPrice:"+contract.getOrderPrice());
		return order;
	}
	

	public OrderBuilderMessageVender getOrderBuilderMessageVender() {
		
		return this.orderMessageVender;
	}


	
	private List<JhfAliveContract> getContractByCustomerId(OrderBuilderMessageVender msg){
		List<JhfAliveContract> contractList = null;
		try {
			contractList = DAOFactory.getContractDao().getContractsByCustomerId(msg.getCustomerId());
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return contractList;
	}
	
	
//	public static void main(String[] args) {
//		String testpath = OrderConstants.PROPERTY_FULL_PATH+"\\"+OrderConstants.COMMON_PROPERTY_NAME;
//		System.out.println("==>:"+testpath);
//		PropertiesUtil pu = new PropertiesUtil(testpath);
//		pu.load();
//		System.out.println(pu.getStringValue("currencyPair"));
//	}
	
	

	public static void main(String[] args) throws Exception{
		
		SimpleSender sender = SimpleSender.getInstance(DestinationConstant.OrderRequestQueue);
		
		String fullPropertyPath = OrderConstants.PROPERTY_FULL_PATH+"\\"+OrderConstants.COMMON_PROPERTY_NAME;
		OrderBuilderMessageVender orderVender = MessageVenderFactory.createOrderMsgVender(fullPropertyPath);
		OrderBuilderInstantService fac = new OrderBuilderInstantService(sender,orderVender);
		
		fac.doOrder();
		
		sender.close();
		System.exit(0);
	}
}

