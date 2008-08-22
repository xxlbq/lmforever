package com.lubq.lm.bestwiz.order.builder.bean;

import java.math.BigDecimal;
import java.util.List;

public class OrderBuilderMessageVender {
	
	private String customerId ;
	private List<String> customerIdList;
	private String currencyPair;
	private int side;

	
	
	private int orderBatchSize;
	private int orderBindBatchSize;



	private boolean doBatch;
	private int mode;


	private BigDecimal orderPrice;
	private BigDecimal amount;
	private BigDecimal slippage;

	private int executionType ;

	
	
	public OrderBuilderMessageVender(){
		
	}
	
	
	
	
	
	public boolean isDoBatch() {
		return doBatch;
	}

	public void setDoBatch(boolean doBatch) {
		this.doBatch = doBatch;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<String> getCustomerIdList() {
		return customerIdList;
	}

	public void setCustomerIdList(List<String> customerIdList) {
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

	public void setSide(int side) {
		this.side = side;
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getSlippage() {
		return slippage;
	}

	public void setSlippage(BigDecimal slippage) {
		this.slippage = slippage;
	}

	public int getExecutionType() {
		return executionType;
	}

	public void setExecutionType(int executionType) {
		this.executionType = executionType;
	}





	@Override
	public String toString() {
		
		StringBuffer buf = new StringBuffer("OrderBuilderMessageVender:");
		
		buf.append("customerId:"+customerId );
		buf.append(",currencyPair:"+currencyPair );
		buf.append(",side:"+side );
		buf.append(",orderBatchSize:"+orderBatchSize );
		buf.append(",orderBindBatchSize:"+orderBindBatchSize );
		buf.append(",doBatch:"+doBatch );
		buf.append(",mode:"+mode );
		buf.append(",orderPrice:"+orderPrice );
		buf.append(",amount:"+amount );
		buf.append(",slippage:"+slippage );
		buf.append(",executionType:"+executionType );
		buf.append(",customerId:"+customerId );

		
		StringBuffer  cIdList = new StringBuffer("customerId List:");
		
		if(customerIdList != null){
			for (String cId : customerIdList) {
				cIdList.append("."+cId);
			}
		}
		
		buf.append(cIdList);

		return buf.toString();
	}
	
	

}