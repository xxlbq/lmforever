package com.lubq.lm.bestwiz.order.builder;

import java.math.BigDecimal;
import java.util.Vector;

import cn.bestwiz.jhf.core.bo.bean.OrderInfo;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.jms.DestinationConstant;
import cn.bestwiz.jhf.core.jms.SimpleSender;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

public class OrderBuilderInstantFactory extends OrderBuilderAbstractFactory{

	
	public void initOrder() {
		System.out.println( " Instant order do init() ..." );
		
	}

	public void finishOrder(JhfOrderBind bind) throws JMSException {
		System.out.println( " Instant order do finish() ..." );
		SimpleSender sender = SimpleSender.getInstance(DestinationConstant.OrderRequestQueue);
		sender.sendMessage(bind);
	}

	
	public void doInstantOrder() throws Exception{
		service();
	}
	
	
	public static void main(String[] args) throws Exception {
		
		JhfAliveOrder order  = getTempOrder();
		
		OrderBuilderInstantFactory fac = new OrderBuilderInstantFactory();
		fac.setBatch(false);
		
		
		
		fac.doInstantOrder();
	}
	
	
	public static  JhfAliveOrder getTempOrder(){
		JhfAliveOrder order  = new JhfAliveOrder();
		
		order.setActivationType(BigDecimal.ZERO);
		order.setActiveFlag(BigDecimal.ONE);
		
		order.setBoardRate(BigDecimal.valueOf(115.68));
		order.setCancelRejectFlag(BigDecimal.ONE);
		order.setChannelId("WEB");
		order.setCurrencyPair("USD/JPY");
		order.setCustomerId("00000101");
		order.setExecutionPrice(BigDecimal.valueOf(115.68));
		order.setExecutionType(new BigDecimal("12"));
		order.setExpirationType(new BigDecimal("3"));
		
		order.setInputStaffId("lubq");
		order.setLosscutOrderFlag(BigDecimal.ZERO);
		order.setMarketAtClosingFlag(BigDecimal.ZERO);
		order.setNegoId("520");
		order.setOrderAmount(BigDecimal.valueOf(10000));
		order.setOrderBindId(IdGenerateFacade.getOrderBindId());
		order.setOrderDate("20080815");
		order.setOrderId(IdGenerateFacade.getOrderId());
		order.setOrderPrice(Rate.getAskRate());
		order.setOrderRoute(0);
		order.setOrderStatus(0);
		order.setOrderTime("2006-10-25 10:15:38");
		order.setOrderType(0);
		order.setPriceId("520");
		order.setProductId("S044");	
		
		order.setSide(-1);
		order.setSlippage(BigDecimal.valueOf(0.05));
		order.setOrderStatus(0);
		order.setTopOrderId("520");
		order.setTradeID(IdGenerateFacade.getTradeId());
		order.setTradePrice(BigDecimal.valueOf(125.6));
		order.setUpdateDateTime("2006-10-25 10:15:38");
		order.setUpdateStaffId("zuolin");
//		m_orderInfo.setCustomerOrderNumber(IdGenerateFacade.getCustomerOrderNo(m_orderInfo.getOrderId()));
		//新规通常注文
		Vector<OrderInfo> vcOrder=new Vector<OrderInfo>();	
		vcOrder.add(0,m_orderInfo);
		
    	// 保留orderbind
		OrderBindInfo orderBindInfo = new OrderBindInfo();
		Vector<OrderBindInfo> vcOrderBind=new Vector<OrderBindInfo>();
		orderBindInfo.setOrderBindId(m_orderInfo.getOrderBindId());
		orderBindInfo.setTradeId(m_orderInfo.getTradeID());
		orderBindInfo.setOrderId(m_orderInfo.getOrderId());
		vcOrderBind.add(0,orderBindInfo);
		return order;
	}
}

