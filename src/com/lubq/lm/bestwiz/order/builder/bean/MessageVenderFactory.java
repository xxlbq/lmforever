package com.lubq.lm.bestwiz.order.builder.bean;

import java.math.BigDecimal;
import java.util.List;

import com.lm.common.util.prop.PropertiesUtil;
import com.lubq.lm.bestwiz.order.builder.cons.ComboConstants;
import com.lubq.lm.bestwiz.order.ui.view.NewSWTApp;

public class MessageVenderFactory {

	
	 
	
	public static OrderBuilderMessageVender createOrderMsgVender(String fullPropertyPath) {
		
		OrderBuilderMessageVender orderVender = new OrderBuilderMessageVender();
		PropertiesUtil propUtil = loadProperty(fullPropertyPath);
		
		orderVender.setCustomerId(propUtil.getStringValue("customerId"));
		orderVender.setCustomerIdList(propUtil.getStringListValue("customerId.list"));
		
		orderVender.setCurrencyPair(propUtil.getStringValue("currencyPair"));
		orderVender.setSide(propUtil.getIntValue("side"));
		orderVender.setOrderBatchSize(propUtil.getIntValue("orderBatchSize"));
		orderVender.setOrderBindBatchSize(propUtil.getIntValue("orderBindListSize"));
		orderVender.setDoBatch(propUtil.getBooleanValue("doBatch"));
		orderVender.setMode(propUtil.getIntValue("mode"));
		orderVender.setOrderPrice( new BigDecimal( propUtil.getStringValue("orderPrice") ) );
		orderVender.setAmount(new BigDecimal( propUtil.getStringValue("amount")));
		orderVender.setSlippage(new BigDecimal (propUtil.getStringValue("slippage")));
		orderVender.setExecutionType( propUtil.getIntValue("executionType") );
				
		
		return orderVender;
	}

	
	
	public static OrderBuilderMessageVender createOrderMsgVender(NewSWTApp app) {
		
		OrderBuilderMessageVender orderVender = new OrderBuilderMessageVender();
		
		String 	cId 	= app.getCustomerId_text().getText();
//		List<String> cIdList = app.getCustomer
		String 	currencyPair = app.getCurrencyPair_combo().getText();
		int 	sideIndex 	= app.getSide_combo().getSelectionIndex();
		int     orderBatchSize = Integer.parseInt( app.getOrderBatchSize_combo().getText() );
//		int     orderBindBatchSize = Integer.parseInt( app.getOrder.getText() );
		boolean isBatch = Boolean.valueOf( app.getIsBatch_combo().getText() );
		int  	mode = app.getMode_combo().getSelectionIndex();
		BigDecimal orderPrice = new BigDecimal( app.getOrderPrice_text().getText() );
		BigDecimal orderAmount = new BigDecimal(app.getOrderAmount_combo().getText());
		
		BigDecimal slippage = new BigDecimal(app.getSlippage_combo().getText());
		int executionType = app.getExecutionType_combo().get
		
		
		
		
		orderVender.setCustomerId(cId);
//		orderVender.setCustomerIdList(propUtil.getStringListValue("customerId.list"));
		
		orderVender.setCurrencyPair(currencyPair);
		orderVender.setSide(ComboConstants.sideArr[sideIndex]);
		orderVender.setOrderBatchSize(orderBatchSize);
//		orderVender.setOrderBindBatchSize(propUtil.getIntValue("orderBindListSize"));
		orderVender.setDoBatch(isBatch);
		orderVender.setMode(mode);
		orderVender.setOrderPrice(orderPrice);
		orderVender.setAmount(orderAmount);
		orderVender.setSlippage(slippage);
		orderVender.setExecutionType( propUtil.getIntValue("executionType") );
		
		return orderVender;
	}
	
	
	public static PropertiesUtil loadProperty(String fullPropPath){
		return new PropertiesUtil(fullPropPath);
	}
	
}
