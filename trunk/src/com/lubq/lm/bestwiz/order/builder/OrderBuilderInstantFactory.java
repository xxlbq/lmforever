package com.lubq.lm.bestwiz.order.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bestwiz.jhf.core.bo.contructor.OrderBindInfoFactory;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrderId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.dao.util.DbSessionFactory;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.DestinationConstant;
import cn.bestwiz.jhf.core.jms.SimpleSender;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lm.common.util.prop.PropertiesUtil;
import com.lubq.lm.bestwiz.order.builder.cons.OrderConstants;




public class OrderBuilderInstantFactory extends OrderBuilderAbstractFactory{
	//成行注文需要发送 jms 给trader
	private SimpleSender orderRequestSender = null;
	
	private OrderBindInfo singleBindInfo = null;
	
	private List<OrderBindInfo> bindInfoList = null;

	private String propFullPath;
	
	
	public OrderBuilderInstantFactory(SimpleSender sender,String fullPropPath) {
		this.orderRequestSender = sender;
		System.out.println("instants order fullPropPath:"+fullPropPath);
		this.propFullPath = fullPropPath;
	}
	
	
	public PropertiesUtil loadProperty(String fullPropPath){
		return new PropertiesUtil(fullPropPath);
	}

	
	
	public String getPropFullPath() {
		return this.propFullPath;
	}


	
	public JhfAliveOrder createOrder(String customer) throws IdGenerateException {
		return getInstantsOrder(customer);
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
		System.out.println( " Instant order do init() ..." );
		System.out.println( " Instant order init() over ." );
	}

	
	
	public void finishOrder() throws JMSException {
		
		if(getPropUtil().getBooleanValue("isBatch")){
			System.out.println("Multi bind info sending ...");
			afterMultiInstantsOrder(bindInfoList);
			System.out.println("Multi bind info send over .");
		}else{
			System.out.println("Single bind info sending ...");
			afterSingleInstantsOrder(singleBindInfo);
			System.out.println("Single bind info send over .");
		}
	}

	
	
	public void service() throws Exception {

		System.out.println("isBatch:"+getPropUtil().getBooleanValue("isBatch"));
		
		if ( ! getPropUtil().getBooleanValue("isBatch")) {
			DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);

			JhfAliveOrder order = createOrder(getCustomerId());

			String orderbindId = IdGenerateFacade.getOrderBindId();
			JhfOrderBind bind = createOrderBind(orderbindId, order.getId().getOrderId(), order.getId().getTradeId());

			writeOrder(order);

			if (null != bind) {
				writeOrderBind(bind);
			}

			DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
			DbSessionFactory.closeConnection();

			if (null != bind) {

				singleBindInfo = OrderBindInfoFactory.getInstance().createInfo(bind);
				singleBindInfo = setupOrderBindInfo(singleBindInfo, order);

			}

		} else {

			bindInfoList = new ArrayList<OrderBindInfo>();
			// 多个customerId
			List<String> customerIdList = getCustomerIdList();

			for (String cstId : customerIdList) {

				for (int bindi = 0; bindi < getOrderBindListSize(); bindi++) {

					DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);

					List<JhfAliveOrder> orderList = new ArrayList<JhfAliveOrder>();
					List<JhfOrderBind> orderBindList = new ArrayList<JhfOrderBind>();
					// 生成orderBindId ，一个bindId，对应多个orderId
					String orderbindId = IdGenerateFacade.getOrderBindId();

					for (int i = 0; i < getBatchSize(); i++) {
						// 创建order对象，并添加到orderList中
						JhfAliveOrder order = createOrder(cstId);
						orderList.add(order);
						// 创建orderBind对象，并添加到orderBindList中
						JhfOrderBind bind = createOrderBind(orderbindId, order
								.getId().getOrderId(), order.getId()
								.getTradeId());
						orderBindList.add(bind);

					}

					// 将 order 写入db
					writeBatchOrder(orderList);
					// 将 orderbind 写入 db
					writeBatchOrderBind(orderBindList);

					DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);

					// 创建orderBindInfo对象，并添加到orderBindInfoList中
					OrderBindInfo bindInfo = setupMuliOrdersOrderBindInfo(
							orderbindId, orderList);

					bindInfoList.add(bindInfo);
				}

			}


			DbSessionFactory.closeConnection();

		}

	}




	
	
	
	
	public   JhfAliveOrder getInstantsOrder(String customer) throws IdGenerateException{
		
//		String orderPriceStr = "200.10";
		String executionPriceStr = "100.00";
		String boardRateStr = "100.00";
		String tradePriceStr = executionPriceStr;
		String tradeTypeStr = "0";
		String orderStatuStr = "1";
		String orderTypeStr = "0";
		String executionTypeStr = "12";
		
		JhfAliveOrder order  = new JhfAliveOrder();
		
		//  ===========> 
		JhfAliveOrderId id = new JhfAliveOrderId();
		
		id.setOrderId(IdGenerateFacade.getOrderId());
		id.setTradeId(IdGenerateFacade.getTradeId());
		order.setId(id);
		
		order.setOrderAmount(new BigDecimal(getPropUtil().getStringValue("instants.order.amount")));
		
		order.setCustomerId(customer);
		
		order.setCurrencyPair(getPropUtil().getStringValue("currencyPair"));
		order.setExecutionPrice(new BigDecimal(executionPriceStr));
		order.setExecutionType(new BigDecimal(executionTypeStr));
		order.setActiveFlag(BigDecimal.ONE);
		order.setOrderPrice(new BigDecimal(getPropUtil().getStringValue("instants.order.orderPrice")));
		order.setRevisionNumber(1);
		order.setOrderStatus(new BigDecimal(orderStatuStr));
		order.setSlippage(new BigDecimal(getPropUtil().getStringValue("instants.order.Slippage")));
		order.setOrderType(new BigDecimal(orderTypeStr));
		order.setSide(new BigDecimal(getPropUtil().getStringValue("side")));
		order.setCustomerOrderNo(IdGenerateFacade.obtainCustomerOrderNo(getPropUtil().getStringValue("customerId")));
		

		// < ===========
		
		
		order.setActivationType(BigDecimal.ZERO);
		order.setBoardRate(new BigDecimal(boardRateStr));
		order.setChannelId("WEB");
		order.setInputStaffId("lubq");
		order.setLosscutOrderFlag(BigDecimal.ZERO);
		order.setMarketAtClosingFlag(BigDecimal.ZERO);
//		order.setOrderBindId(IdGenerateFacade.getOrderBindId());
		order.setOrderDate("20080815");
		order.setOrderRoute(BigDecimal.ZERO);
		order.setOrderTime("123456");
		order.setPriceId("PID007");
		order.setProductId("A001");//USD/JPY = A001,EUR/JPY = A002	
		order.setTradePrice(new BigDecimal(tradePriceStr));
		order.setInputDate(new Date());
		order.setUpdateDate(new Date());
		order.setUpdateStaffId("lubq");
		order.setOrderDatetime(new Date());
		order.setTradeType(new BigDecimal(tradeTypeStr));
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
//		order.setTopOrderId("520");
		
		
		return order;
	}


	public static void main(String[] args) throws Exception {
		
		SimpleSender sender = SimpleSender.getInstance(DestinationConstant.OrderRequestQueue);
		
		OrderBuilderInstantFactory fac = new OrderBuilderInstantFactory(sender,
				OrderConstants.PROPERTY_FULL_PATH+"\\"+OrderConstants.COMMON_PROPERTY_NAME);
		
		fac.doOrder();
		
		sender.close();
		System.exit(0);
	}
	
	
//	public static void main(String[] args) {
//		String testpath = OrderConstants.PROPERTY_FULL_PATH+"\\"+OrderConstants.COMMON_PROPERTY_NAME;
//		System.out.println("==>:"+testpath);
//		PropertiesUtil pu = new PropertiesUtil(testpath);
//		pu.load();
//		System.out.println(pu.getStringValue("currencyPair"));
//	}
}

