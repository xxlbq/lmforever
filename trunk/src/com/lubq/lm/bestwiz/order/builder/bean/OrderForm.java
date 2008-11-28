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
	
	private boolean isMobile;
	
	private boolean isBlackOrder;
	int slipType;
	
	
	
	
	
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
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currencyPair == null) ? 0 : currencyPair.hashCode());
		result = prime * result
				+ ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result
				+ ((customerIdList == null) ? 0 : customerIdList.hashCode());
		result = prime * result + executionType;
		result = prime * result + (isBatch ? 1231 : 1237);
		result = prime * result + (isBlackOrder ? 1231 : 1237);
		result = prime * result + (isMobile ? 1231 : 1237);
		result = prime * result + mode;
		result = prime * result
				+ ((orderAmount == null) ? 0 : orderAmount.hashCode());
		result = prime * result + orderBatchSize;
		result = prime * result + orderBindBatchSize;
		result = prime * result + orderBindType;
		result = prime * result
				+ ((orderPrice == null) ? 0 : orderPrice.hashCode());
		result = prime * result + side;
		result = prime * result + slipType;
		result = prime * result
				+ ((slippage == null) ? 0 : slippage.hashCode());
		result = prime * result + tradeType;
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final OrderForm other = (OrderForm) obj;
		if (currencyPair == null) {
			if (other.currencyPair != null)
				return false;
		} else if (!currencyPair.equals(other.currencyPair))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (customerIdList == null) {
			if (other.customerIdList != null)
				return false;
		} else if (!customerIdList.equals(other.customerIdList))
			return false;
		if (executionType != other.executionType)
			return false;
		if (isBatch != other.isBatch)
			return false;
		if (isBlackOrder != other.isBlackOrder)
			return false;
		if (isMobile != other.isMobile)
			return false;
		if (mode != other.mode)
			return false;
		if (orderAmount == null) {
			if (other.orderAmount != null)
				return false;
		} else if (!orderAmount.equals(other.orderAmount))
			return false;
		if (orderBatchSize != other.orderBatchSize)
			return false;
		if (orderBindBatchSize != other.orderBindBatchSize)
			return false;
		if (orderBindType != other.orderBindType)
			return false;
		if (orderPrice == null) {
			if (other.orderPrice != null)
				return false;
		} else if (!orderPrice.equals(other.orderPrice))
			return false;
		if (side != other.side)
			return false;
		if (slipType != other.slipType)
			return false;
		if (slippage == null) {
			if (other.slippage != null)
				return false;
		} else if (!slippage.equals(other.slippage))
			return false;
		if (tradeType != other.tradeType)
			return false;
		return true;
	}
	
	
	
	
	public boolean isMobile() {
		return isMobile;
	}
	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}
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
		this.mode = ComboConstants.modeTypeArr[mode] ;
		System.out.println("Mode : "+mode);
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
