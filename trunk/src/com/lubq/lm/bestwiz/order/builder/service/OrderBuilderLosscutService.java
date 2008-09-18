package com.lubq.lm.bestwiz.order.builder.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bestwiz.jhf.core.bo.bean.OrderInfo;
import cn.bestwiz.jhf.core.bo.contructor.OrderInfoFactory;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrderId;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lubq.lm.bestwiz.order.builder.bean.OrderBuilderMessageVender;

public class OrderBuilderLosscutService extends OrderBuilderAbstractFactory{

	
	
	public JhfAliveOrder createOpenOrder(String customer, String orderBindId)
			throws IdGenerateException {
		// TODO Auto-generated method stub
		return null;
	}

	public void finishOrder() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	public OrderBuilderMessageVender getOrderBuilderMessageVender() {
		// TODO Auto-generated method stub
		return null;
	}

	public void service() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) throws IdGenerateException {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		List<JhfAliveOrder> orderList = new ArrayList<JhfAliveOrder>();
		
		for (int i = 0; i < 500; i++) {
			
			JhfAliveOrder order = getCommonInstantsOrder("006","007");
			orderList.add(order);
		}
		for (int w = 0; w < 10; w++) {
			long begin = System.currentTimeMillis();
			
			for (int i = 0; i < 500; i++) {
				
				
				OrderInfo orderInfo = OrderInfoFactory.getInstance().createInfo(orderList.get(i));
				orderInfoList.add(orderInfo);
				
			}
			long end = System.currentTimeMillis();
			System.out.println("use time:"+(end - begin));
		}

	}
	
	

	public static JhfAliveOrder getCommonInstantsOrder(String customerId,String orderBindId) throws IdGenerateException{
		
		String boardRateStr = "100.00";
		String orderStatuStr = "7";
		String orderTypeStr = "0";

		
		JhfAliveOrder order  = new JhfAliveOrder();
		
		//  ==== PK setup =======> 
		JhfAliveOrderId id = new JhfAliveOrderId();
		id.setOrderId("11111");
		id.setTradeId("22222");
		// < =====================
		
		
		order.setId(id);
		order.setOrderAmount(new BigDecimal("10000"));
		order.setCustomerId(customerId);
		order.setCurrencyPair("USD/JPY");
		
		order.setExecutionType(new BigDecimal("12"));
		order.setOrderPrice(new BigDecimal("100.00"));
		order.setTradeType(new BigDecimal("1"));
		order.setActiveFlag(BigDecimal.ONE);
		
		order.setRevisionNumber(1);
		order.setOrderStatus(new BigDecimal(orderStatuStr));
		order.setSlippage(new BigDecimal("0.99"));
		order.setOrderType(new BigDecimal(orderTypeStr));
		order.setSide(new BigDecimal(String.valueOf(1)));
		order.setCustomerOrderNo("10000");

		
		
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
	
}
