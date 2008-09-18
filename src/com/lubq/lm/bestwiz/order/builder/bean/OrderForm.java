package com.lubq.lm.bestwiz.order.builder.bean;

import java.math.BigDecimal;

import com.lubq.lm.bestwiz.order.builder.cons.ComboConstants;


public class OrderForm {
	
	private String 	customerId;
	private java.util.List<String> customerIdList;
	private String 	currencyPair;
	private int 	side ;
	private int     orderBatchSize;
	private int     orderBindBatchSize;
	private boolean isBatch;
	private int  	mode;
	private BigDecimal orderPrice;
	private BigDecimal orderAmount;
	
	private BigDecimal slippage;
	private int executionType;
	private int orderBindType;
	
	
	
	private int tradeType;
	

	
	
	public int getTradeType() {
		return tradeType;
	}
	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public java.util.List<String> getCustomerIdList() {
		return customerIdList;
	}
	public void setCustomerIdList(java.util.List<String> customerIdList) {
		this.customerIdList = customerIdList;
	}
	public String getCurrencyPair() {
		return currencyPair;
	}
	public void setCurrencyPair(String currencyPair) {
		this.currencyPair = currencyPair;
	}
	public int getSide() {
		return side;
	}
	public void setSide(int sideIndex) {
		this.side = ComboConstants.sideArr[sideIndex];
	}
	public int getOrderBatchSize() {
		return orderBatchSize;
	}
	public void setOrderBatchSize(int orderBatchSize) {
		this.orderBatchSize = orderBatchSize;
	}
	public int getOrderBindBatchSize() {
		return orderBindBatchSize;
	}
	public void setOrderBindBatchSize(int orderBindBatchSize) {
		this.orderBindBatchSize = orderBindBatchSize;
	}
	public boolean isBatch() {
		return isBatch;
	}
	public void setBatch(boolean isBatch) {
		this.isBatch = isBatch;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	public BigDecimal getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getSlippage() {
		return slippage;
	}
	public void setSlippage(BigDecimal slippage) {
		this.slippage = slippage;
	}
	public int getExecutionType() {
		return this.executionType;
	}
	public void setExecutionType(int executionTypeIndex) {
		this.executionType = ComboConstants.executionTypeArr[executionTypeIndex];
		System.out.println("execution TYPE : "+executionType);
	}
	

	
	public int getOrderBindType() {
		return orderBindType;
	}
	public void setOrderBindType(int orderBindTypeIndex) {
		this.orderBindType = ComboConstants.orderBindTypeArr[orderBindTypeIndex];
	}
	
	
	
	
	
	
	
}
