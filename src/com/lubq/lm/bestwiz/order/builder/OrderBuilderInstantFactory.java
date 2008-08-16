package com.lubq.lm.bestwiz.order.builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.lm.common.util.prop.PropertiesUtil;
import com.lubq.lm.bestwiz.order.builder.cons.OrderConstants;


import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrderId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBindId;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.DestinationConstant;
import cn.bestwiz.jhf.core.jms.SimpleSender;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

public class OrderBuilderInstantFactory extends OrderBuilderAbstractFactory{

	private SimpleSender orderRequestSender = null;
	private PropertiesUtil propUtil;

	
	
	public OrderBuilderInstantFactory(SimpleSender sender,String fullPropPath) {
		this.orderRequestSender = sender;
		
		System.out.println("fullPropPath:"+fullPropPath);
		this.propUtil = initProperty(fullPropPath);
		
	}
	
	public PropertiesUtil initProperty(String fullPropPath){
		return new PropertiesUtil(fullPropPath);
	}

	
	
	public String getFullPath() {
		return propUtil.getPropFullPath();
	}

	public void initOrder() {
		System.out.println( " Instant order do init() ..." );
		System.out.println( " Instant order init() over ." );
	}

	
	
	public JhfAliveOrder createOrder(String customer) throws IdGenerateException {
		return getInstantsOrder(customer);
	}


	public JhfOrderBind createOrderBind(String orderBindId,String orderId,String tradeId) {
		
		JhfOrderBind bind = new JhfOrderBind();
		
		JhfOrderBindId id = new JhfOrderBindId();
		id.setOrderBindId(orderBindId);
		id.setOrderId(orderId);
		id.setTradeId(tradeId);
		
		bind.setId(id);
		bind.setActiveFlag(BigDecimal.ONE);
		bind.setInputDate (new Date());
		bind.setUpdateDate(new Date());
		
		return bind;
		
	}


	public void finishOrder(OrderBindInfo bindInfo) throws JMSException {
		
		System.out.println( " Instant order do finish() ..." );
		orderRequestSender.sendMessage(bindInfo);
		System.out.println( " Instant order finish() over ." );
	}

	
	public void doInstantOrder() throws Exception{
		service();
	}
	
	
	public void finishBatchOrder(OrderBindInfo bindInfo) throws JMSException {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) throws Exception {
		
		SimpleSender sender = SimpleSender.getInstance(DestinationConstant.OrderRequestQueue);
		
		OrderBuilderInstantFactory fac = new OrderBuilderInstantFactory(sender,
				OrderConstants.PROPERTY_FULL_PATH+"\\"+OrderConstants.COMMON_PROPERTY_NAME);
		
//		fac.setBatch(false);
		fac.setBatch(true);
		
		fac.doInstantOrder();
		
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
		
		order.setOrderAmount(new BigDecimal(propUtil.getStringValue("instants.order.amount")));
		
		order.setCustomerId(customer);
		
		order.setCurrencyPair(propUtil.getStringValue("currencyPair"));
		order.setExecutionPrice(new BigDecimal(executionPriceStr));
		order.setExecutionType(new BigDecimal(executionTypeStr));
		order.setActiveFlag(BigDecimal.ONE);
		order.setOrderPrice(new BigDecimal(propUtil.getStringValue("instants.order.orderPrice")));
		order.setRevisionNumber(1);
		order.setOrderStatus(new BigDecimal(orderStatuStr));
		order.setSlippage(new BigDecimal(propUtil.getStringValue("instants.order.Slippage")));
		order.setOrderType(new BigDecimal(orderTypeStr));
		order.setSide(new BigDecimal(propUtil.getStringValue("side")));
		order.setCustomerOrderNo(IdGenerateFacade.obtainCustomerOrderNo(propUtil.getStringValue("customerId")));
		

		// < ===========
		
		
		order.setActivationType(BigDecimal.ZERO);
		order.setBoardRate(new BigDecimal(boardRateStr));
		order.setChannelId("WEB");
		order.setInputStaffId("lubq");
		order.setLosscutOrderFlag(BigDecimal.ZERO);
		order.setMarketAtClosingFlag(BigDecimal.ZERO);
		order.setOrderBindId(IdGenerateFacade.getOrderBindId());
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
//		order.setTopOrderId("520");
		
		
		return order;
	}

	
	
	public String getCustomerId() {
		return propUtil.getStringValue("customerId");
	}

	public List<String> getCustomerIdList() {
		return propUtil.getStringListValue("customerId.list");
	}
	

	
}

