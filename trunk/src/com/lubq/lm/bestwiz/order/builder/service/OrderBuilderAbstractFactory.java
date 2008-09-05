package com.lubq.lm.bestwiz.order.builder.service;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import com.lubq.lm.bestwiz.order.builder.dao.OrderBuilderDao;
import com.lubq.lm.bestwiz.order.ui.view.NewSWTApp;

import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.BaseMainDao;
import cn.bestwiz.jhf.core.dao.DAOFactory;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;

public abstract class OrderBuilderAbstractFactory implements OrderBuilder{
	
// private PropertiesUtil propUtil;
	
// private boolean isBatch;
//	
// int mode = 1 ;

	private static Object lock = new Object();
	public static final String ORDER_PREFIX = "LUBQ";
	private static int ORDER_NUMBER = 1;
	
	private static DecimalFormat df = new DecimalFormat("0000000");
	
	
	public void initOrder() {
		
		System.out.println( " select like %LUBQ% order and delete them  ..." );
		int deleteOldOrder = OrderBuilderDao.deleteOrder(OrderBuilderAbstractFactory.ORDER_PREFIX);
		int deleteOldExecution = OrderBuilderDao.deleteExecution(OrderBuilderAbstractFactory.ORDER_PREFIX);
		int deleteHis = OrderBuilderDao.deleteOrderStatusHis(OrderBuilderAbstractFactory.ORDER_PREFIX);
		int deleteContract = OrderBuilderDao.deleteContract(OrderBuilderAbstractFactory.ORDER_PREFIX);
//		
		System.out.println( " old order     delete :"+deleteOldOrder);
		System.out.println( " old Execution delete :"+deleteOldExecution);
		System.out.println( " old orderHis  delete :"+deleteHis);
		System.out.println( " old Contract  delete :"+deleteContract);

// int deleteHedgeCustTrade = OrderBuilderDao.deleteHedgeCustTrade();
// int deleteSysPosInsert = OrderBuilderDao.deleteSysPosInsert();
	}

	/** JhfAliveOrder createOrder(String customer) throws IdGenerateException; * */
	
	
// public JhfOrderBind createOrderBind(String orderBindId,String orderId,String
// tradeId) {
//		
// JhfOrderBind bind = new JhfOrderBind();
//		
// JhfOrderBindId id = new JhfOrderBindId();
// id.setOrderBindId(orderBindId);
// id.setOrderId(orderId);
// id.setTradeId(tradeId);
//		
// bind.setId(id);
// bind.setActiveFlag(BigDecimal.ONE);
// bind.setInputDate (new Date());
// bind.setUpdateDate(new Date());
//		
// return bind;
//		
// }
	





	public void writeOrder(JhfAliveOrder order) throws DaoException {
// DAOFactory.getOrderDao().createOrder(order);
		BaseMainDao bdao = new BaseMainDao();
		bdao.save(order);
		
	}

	public void writeBatchOrder(List<JhfAliveOrder> orderList) throws DaoException {
		for (JhfAliveOrder jhfAliveOrder : orderList) {
			writeOrder(jhfAliveOrder);
		}
	}
	
	
// public void writeOrderBind(JhfOrderBind bind) throws DaoException {
// DAOFactory.getOrderDao().createOrderBind(bind);
// }
	

// public void writeBatchOrderBind(List<JhfOrderBind> bindList) throws
// DaoException {
// for (JhfOrderBind jhfOrderBind : bindList) {
// writeOrderBind(jhfOrderBind);
// }
// }
	
	
	public void doOrder() throws Exception{
		
// propUtil = loadProperty(getPropFullPath());
		
		initOrder();
		service();
		finishOrder();
	}

	
	
	/** void finishOrder(OrderBindInfo bindInfo) throws JMSException; * */
	/** void finishBatchOrder(OrderBindInfo bindInfo) throws JMSException; * */
	
	
	
	

	

	protected OrderBindInfo buildSingleOrderBindInfo(String orderBindId,JhfAliveOrder order){
		
		OrderBindInfo bindInfo = new OrderBindInfo();
		
		bindInfo.setOrderBindId(orderBindId);
		bindInfo.setOrderId(order.getId().getOrderId());
		bindInfo.setTradeId(order.getId().getTradeId());
		
		bindInfo.setCurrencyPair(order.getCurrencyPair());
		bindInfo.setProductId(order.getProductId());
		bindInfo.setCustomerId(order.getCustomerId());
		bindInfo.setAmount(order.getOrderAmount());
		bindInfo.setSide(order.getSide().intValue());
		bindInfo.setMode(getOrderBuilderMessageVender().getMode());
		
		if(order.getSide().intValue() == 1){
			bindInfo.setTradeAskPrice(order.getOrderPrice());
		}else{
			bindInfo.setTradeBidPrice(order.getOrderPrice());
		}
		
		bindInfo.setType(order.getExecutionType().intValue());
		bindInfo.setSlippage(order.getSlippage());
		bindInfo.setPriceId(order.getPriceId());
		
		return bindInfo;
		
	}
	

	protected OrderBindInfo setupMuliOrdersOrderBindInfo(String id,List<JhfAliveOrder> orderList ){
		
		OrderBindInfo bindInfo = new OrderBindInfo();
		
		JhfAliveOrder order = orderList.get(0);

		BigDecimal sumAmount =BigDecimal.ZERO ;
		
		
		for (JhfAliveOrder jhfAliveOrder : orderList) {
			sumAmount = sumAmount.add( jhfAliveOrder.getOrderAmount());
		}
		
		bindInfo.setOrderBindId(id);
		bindInfo.setAmount(sumAmount);
		bindInfo.setCurrencyPair(order.getCurrencyPair());
		bindInfo.setProductId(order.getProductId());
		bindInfo.setCustomerId(order.getCustomerId());
		bindInfo.setSide(order.getSide().intValue());
		bindInfo.setMode(getOrderBuilderMessageVender().getMode());
		
		if(order.getSide().intValue() == 1){
			bindInfo.setTradeAskPrice(order.getOrderPrice());
		}else{
			bindInfo.setTradeBidPrice(order.getOrderPrice());
		}
			
		bindInfo.setType(order.getExecutionType().intValue());
		bindInfo.setSlippage(null);
		bindInfo.setPriceId(null);
		bindInfo.setMobileFlag(false);
		// ===============
	
		bindInfo.setCustomerId(order.getCustomerId());
		bindInfo.setTradeId(null);
		bindInfo.setTimequoteId(null);

		return bindInfo;
		
	}
	
	public static  String  createOrderId(String customer){
		
		StringBuffer orderId = new StringBuffer(customer);
		orderId.append(ORDER_PREFIX);
		orderId.append(df.format(ORDER_NUMBER));
		
		synchronized (lock) {
			ORDER_NUMBER ++ ;
		}
		
		return orderId.toString();
	}
	
	
	protected String getTopOrderId(){
		String topId = null;
		
		return topId;
	}

	protected String getSettleContractId(){
		String settleContractId = null;
			
		return settleContractId;
	}
	
}
