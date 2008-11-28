package com.lubq.lm.bestwiz.order.builder.bean;

import java.math.BigDecimal;
import java.util.List;

import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;

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
	private int orderBindType;
	
	private int tradeType;
	
	
	private String topOrderId;
	private String settleContractId;
	private boolean isMobile;
	
	private boolean isBlackOrder;
	int slipType;
	
	
	
	private List<JhfAliveOrder> settleOrderList;
	
	public OrderBuilderMessageVender(){
		
	}
	
	





	public boolean isMobile() {
		return isMobile;
	}







	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}







	public List<JhfAliveOrder> getSettleOrderList() {
		return settleOrderList;
	}





	public int getOrderBindType() {
		return orderBindType;
	}







	public void setOrderBindType(int orderBindType) {
		this.orderBindType = orderBindType;
	}







	public void setSettleOrderList(List<JhfAliveOrder> settleOrderList) {
		this.settleOrderList = settleOrderList;
	}





	public String getTopOrderId() {
		return topOrderId;
	}





	public void setTopOrderId(String topOrderId) {
		this.topOrderId = topOrderId;
	}





	public String getSettleContractId() {
		return settleContractId;
	}





	public void setSettleContractId(String settleContractId) {
		this.settleContractId = settleContractId;
	}





	public int getTradeType() {
		return tradeType;
	}





	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
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

		
		StringBuffer  cIdList = new StringBuffer(",customerId List:[");
		
		if(customerIdList != null){
			for (String cId : customerIdList) {
				cIdList.append("."+cId);
			}
		}
		
		buf.append(cIdList +"]");

		return buf.toString();
	}







	/**
	 * @return the isBlackOrder
	 */
	public boolean isBlackOrder() {
		return isBlackOrder;
	}







	/**
	 * @param isBlackOrder the isBlackOrder to set
	 */
	public void setBlackOrder(boolean isBlackOrder) {
		this.isBlackOrder = isBlackOrder;
	}







	/**
	 * @return the slipType
	 */
	public int getSlipType() {
		return slipType;
	}







	/**
	 * @param slipType the slipType to set
	 */
	public void setSlipType(int slipType) {
		this.slipType = slipType;
	}
	
	

}
