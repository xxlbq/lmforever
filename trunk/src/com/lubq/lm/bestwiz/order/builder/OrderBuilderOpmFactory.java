package com.lubq.lm.bestwiz.order.builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrderId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lm.common.util.prop.PropertiesUtil;

public class OrderBuilderOpmFactory extends OrderBuilderAbstractFactory{


	private PropertiesUtil propUtil;

	
	
	public OrderBuilderOpmFactory(String fullPropPath) {

		System.out.println("fullPropPath:"+fullPropPath);
		this.propUtil = initProperty(fullPropPath);
		
	}
	
	
	
	public JhfOrderBind createOrderBind(String orderBindId, String orderId,
			String tradeId) {
		// TODO Auto-generated method stub
		return null;
	}



	public JhfAliveOrder createOrder(String customer)
			throws IdGenerateException {
		return getOpmOrder(customer);
	}

	public void finishBatchOrder(OrderBindInfo bindInfo) throws JMSException {
		
	}

	public void finishOrder(OrderBindInfo bindInfo) throws JMSException {
		
	}

	public String getCustomerId() {
		return propUtil.getStringValue("customerId");
	}

	public List<String> getCustomerIdList() {
		return propUtil.getStringListValue("customerId.list");
	}

	public String getFullPath() {
		return propUtil.getPropFullPath();
	}

	public void initOrder() {
		System.out.println( " Instant order do init() ..." );
		System.out.println( " Instant order init() over ." );
		
	}

	
	public void doOpmOrder() throws Exception{
		service();
	}
	

	public JhfAliveOrder getOpmOrder(String customer) throws IdGenerateException{
		
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

	
	
	
}
