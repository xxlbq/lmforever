package com.lubq.lm.bestwiz.order.builder.bean;

import java.math.BigDecimal;

import org.apache.commons.collections.CollectionUtils;



import com.lm.common.util.prop.PropertiesUtil;
import com.lubq.lm.util.CollectionUtil;
import com.lubq.lm.util.StringUtil;

public class MessageVenderFactory {

	
	 
	
	public static OrderBuilderMessageVender createOrderMsgVender(String fullPropertyPath) {
		
		OrderBuilderMessageVender orderVender = new OrderBuilderMessageVender();
//		PropertiesUtil propUtil = loadProperty(fullPropertyPath);
//		
//		orderVender.setCustomerId(propUtil.getStringValue("customerId"));
//		orderVender.setCustomerIdList(propUtil.getStringListValue("customerId.list"));
//		
//		orderVender.setCurrencyPair(propUtil.getStringValue("currencyPair"));
//		orderVender.setSide(propUtil.getIntValue("side"));
//		orderVender.setOrderBatchSize(propUtil.getIntValue("orderBatchSize"));
//		orderVender.setOrderBindBatchSize(propUtil.getIntValue("orderBindListSize"));
//		orderVender.setDoBatch(propUtil.getBooleanValue("doBatch"));
//		
//		orderVender.setMode(propUtil.getIntValue("mode"));
//		orderVender.setOrderPrice( new BigDecimal( propUtil.getStringValue("orderPrice") ) );
//		orderVender.setAmount(new BigDecimal( propUtil.getStringValue("amount")));
//		orderVender.setSlippage(new BigDecimal (propUtil.getStringValue("slippage")));
//		orderVender.setExecutionType( propUtil.getIntValue("executionType") );
//				
		
		return orderVender;
	}

	
	
	public static OrderBuilderMessageVender createOrderMsgVender(OrderForm of) {
		
		OrderBuilderMessageVender orderVender = new OrderBuilderMessageVender();

		
		orderVender.setCustomerId(  
				StringUtil.customerStringCheck( of.getCustomerId() ) );
		orderVender.setCustomerIdList(
				StringUtil.customerStringListCheck( of.getCustomerIdList()       ));
		
		if(CollectionUtil.isEmpty(orderVender.getCustomerIdList())){
			//TODO
		}
		
		
		orderVender.setCurrencyPair(of.getCurrencyPair());
		orderVender.setSide(of.getSide());
		orderVender.setOrderBatchSize(of.getOrderBatchSize());
		orderVender.setOrderBindBatchSize(of.getOrderBindBatchSize());
		orderVender.setDoBatch(of.isBatch());
		orderVender.setMode(of.getMode());
		orderVender.setOrderPrice(of.getOrderPrice());
		orderVender.setAmount(of.getOrderAmount());
		orderVender.setSlippage(of.getSlippage());
		
//		if(of.isBatch()){
//			
//		}
		
		
		orderVender.setBlackOrder(of.isBlackOrder());
		orderVender.setSlipType(of.getSlipType());
		
		orderVender.setExecutionType(of.getExecutionType());
		orderVender.setMobile(of.isMobile());
		
		orderVender.setTradeType(of.getTradeType());
		
		if(orderVender.getTradeType() == 1){
			orderVender.setOrderBindType(of.getOrderBindType());
		}

		if(of.getExecutionType() == 0 || of.getExecutionType() == 1){
			orderVender.setOrderBindType(10);
		}
		
		if(of.getExecutionType() == 12){
			orderVender.setOrderBindType(12);
		}
		
		if(of.getExecutionType() == 100){
			orderVender.setOrderBindType(100);
		}
		
		return orderVender;
	}
	
	
	public static PropertiesUtil loadProperty(String fullPropPath){
		return new PropertiesUtil(fullPropPath);
	}
	
}
