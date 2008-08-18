package com.lubq.lm.bestwiz.order.builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrderId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.DestinationConstant;
import cn.bestwiz.jhf.core.jms.SimpleSender;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lm.common.util.prop.PropertiesUtil;
import com.lubq.lm.bestwiz.order.builder.cons.OrderConstants;

public class OrderBuilderOpmFactory extends OrderBuilderAbstractFactory{


	private PropertiesUtil propUtil;

	
	
	public OrderBuilderOpmFactory(String fullPropPath) {

		System.out.println("fullPropPath:"+fullPropPath);
		this.propUtil = readProperty(fullPropPath);
		
	}
	
	
	
//	public JhfOrderBind createOrderBind(String orderBindId, String orderId,
//			String tradeId) {
//		// TODO Auto-generated method stub
//		return null;
//	}



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

	public String getPropFullPath() {
		return propUtil.getPropFullPath();
	}

	public void initOrder() {
		System.out.println( " Instant order do init() ..." );
		System.out.println( " Instant order init() over ." );
		
	}

	
	public void doOpmOrder() throws Exception{
		service();
	}
	

	public JhfAliveOrder getOpmOrder(String customer)
			throws IdGenerateException {

		// String orderPriceStr = "200.10";
		String executionPriceStr = "100.00";
		String boardRateStr = "100.00";
		String tradePriceStr = executionPriceStr;
		String tradeTypeStr = "0";
		String orderStatuStr = "1";
		String orderTypeStr = "0";
//		String executionTypeStr = "12";

		JhfAliveOrder order = new JhfAliveOrder();

		// ===========>
		JhfAliveOrderId id = new JhfAliveOrderId();

		id.setOrderId(IdGenerateFacade.getOrderId());
		id.setTradeId(IdGenerateFacade.getTradeId());
		order.setId(id);

		order.setOrderAmount(new BigDecimal(getPropUtil().getStringValue("opm.order.amount")));
		order.setCustomerId(customer);

		order.setCurrencyPair(getPropUtil().getStringValue("currencyPair"));
		order.setExecutionPrice(new BigDecimal(executionPriceStr));
		order.setExecutionType(new BigDecimal(getPropUtil().getStringValue("opm.order.limeStop")));
		order.setActiveFlag(BigDecimal.ONE);
		order.setOrderPrice(new BigDecimal(getPropUtil().getStringValue("opm.order.orderPrice")));
		order.setRevisionNumber(1);
		order.setOrderStatus(new BigDecimal(orderStatuStr));
		order.setSlippage(null);
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
		// order.setOrderBindId(IdGenerateFacade.getOrderBindId());
		order.setOrderDate("20080818");
		order.setOrderRoute(BigDecimal.ZERO);
		order.setOrderTime("123456");
		order.setPriceId("PID007");
		order.setProductId("A001");// USD/JPY = A001,EUR/JPY = A002
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
		// order.setTopOrderId("520");

		return order;
	}

	
	public static void main(String[] args) throws Exception {
		
		
		OrderBuilderOpmFactory fac = new OrderBuilderOpmFactory(OrderConstants.PROPERTY_FULL_PATH+"\\"+OrderConstants.COMMON_PROPERTY_NAME);
		
//		fac.setBatch(false);
//		fac.setBatch(true);
		
		fac.doOpmOrder();
		
		System.exit(0);
	}



	public void finishOrder() throws JMSException {
		// TODO Auto-generated method stub
		
	}



	public void service() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
