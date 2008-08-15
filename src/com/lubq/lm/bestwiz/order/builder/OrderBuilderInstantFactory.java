package com.lubq.lm.bestwiz.order.builder;

import java.math.BigDecimal;
import java.util.Date;

import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrderId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.DestinationConstant;
import cn.bestwiz.jhf.core.jms.SimpleSender;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

public class OrderBuilderInstantFactory extends OrderBuilderAbstractFactory{

	
	public void initOrder() {
		System.out.println( " Instant order do init() ..." );
		System.out.println( " Instant order init() over ." );
	}

	
	
	public JhfAliveOrder createOrder() throws IdGenerateException {
		return getTempOrder();
	}





	public void finishOrder(OrderBindInfo bindInfo) throws JMSException {
		System.out.println( " Instant order do finish() ..." );
		SimpleSender sender = SimpleSender.getInstance(DestinationConstant.OrderRequestQueue);
		sender.sendMessage(bindInfo);
		sender.close();
		System.out.println( " Instant order finish() over ." );
	}

	
	public void doInstantOrder() throws Exception{
		service();
	}
	
	
	public static void main(String[] args) throws Exception {
		
		
		OrderBuilderInstantFactory fac = new OrderBuilderInstantFactory();
		JhfAliveOrder order  = fac.getTempOrder();
		
		fac.setBatch(false);
		fac.setOrder(order);
//		fac.set
		
		
		fac.doInstantOrder();
	}
	
	
	public   JhfAliveOrder getTempOrder() throws IdGenerateException{
		
		String orderPriceStr = "200.10";
		String executionPriceStr = "100.00";
		String boardRateStr = "100.00";
		String sideStr     = "1";
		String slippageStr = "0.01";
		String tradePriceStr = executionPriceStr;
		String customerId = "00000101";
		String tradeTypeStr = "0";
		String orderStatuStr = "1";
		String orderTypeStr = "0";
		String executionTypeStr = "12";
		
		JhfAliveOrder order  = new JhfAliveOrder();
		
		order.setActivationType(BigDecimal.ZERO);
		order.setActiveFlag(BigDecimal.ONE);
		
		order.setBoardRate(new BigDecimal(boardRateStr));
		order.setCancelRejectFlag(BigDecimal.ONE);
		order.setChannelId("WEB");
		order.setCurrencyPair("USD/JPY");
		order.setCustomerId(customerId);
		order.setExecutionPrice(new BigDecimal(executionPriceStr));
		order.setExecutionType(new BigDecimal(executionTypeStr));
		order.setExpirationType(new BigDecimal("3"));
		
		order.setInputStaffId("lubq");
		order.setLosscutOrderFlag(BigDecimal.ZERO);
		order.setMarketAtClosingFlag(BigDecimal.ZERO);
		order.setNegoId("520");
		order.setOrderAmount(BigDecimal.valueOf(10000));
		order.setOrderBindId(IdGenerateFacade.getOrderBindId());
		order.setOrderDate("20080815");
		
		JhfAliveOrderId id = new JhfAliveOrderId();
		id.setOrderId(IdGenerateFacade.getOrderId());
		id.setTradeId(IdGenerateFacade.getTradeId());
		order.setId(id);
		
		
		order.setOrderPrice(new BigDecimal(orderPriceStr));
		order.setOrderRoute(BigDecimal.ZERO);
		order.setOrderStatus(new BigDecimal(orderStatuStr));
		order.setOrderTime("154938");
		order.setOrderType(new BigDecimal(orderTypeStr));
		order.setPriceId("PID007");
		//USD/JPY = A001,EUR/JPY = A002
		order.setProductId("A001");	
		
		order.setSide(new BigDecimal(sideStr));
		order.setSlippage(new BigDecimal(slippageStr));
		order.setTopOrderId("520");
		
		order.setTradePrice(new BigDecimal(tradePriceStr));
		order.setInputDate(new Date());
		order.setUpdateDate(new Date());
		order.setUpdateStaffId("lubq");
		order.setCustomerOrderNo(IdGenerateFacade.obtainCustomerOrderNo(customerId));

		order.setOrderDatetime(new Date());
		order.setTradeType(new BigDecimal(tradeTypeStr));
		order.setChangeReason(new BigDecimal("1"));
		//web,mobile,ajax
		order.setChannelId("1");
		
		
		return order;
	}
	

	
}

