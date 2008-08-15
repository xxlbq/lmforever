package com.lubq.lm.bestwiz.order.builder;

import java.util.ArrayList;
import java.util.List;

import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.DAOFactory;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.dao.util.DbSessionFactory;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;

public abstract class OrderBuilderAbstractFactory implements OrderBuilder{

	private String orderId;
	private String bindId;
	private boolean isBatch;
	
	private JhfAliveOrder order;
	private JhfOrderBind bind;
	
	public JhfAliveOrder createOrder(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	public JhfOrderBind createOrderBind(String orderBindId,String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeBatchOrder(List<JhfAliveOrder> orderList) {
		// TODO Auto-generated method stub
		
	}

	public List<JhfOrderBind> writeBatchOrderBind(List<JhfOrderBind> bindList) {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeOrder(JhfAliveOrder order) throws DaoException {
//		DAOFactory.getOrderDao().createOrder(order);

		
	}

	public void writeOrderBind(JhfOrderBind bind) throws DaoException {
//		DAOFactory.getOrderDao().createOrderBind(bind);
		
	}

	
	
	public void service() throws Exception {
		
		initOrder();
		
		if( ! isBatch()){
			
//			DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
			
			order = createOrder(orderId);
//			bind = createOrderBind(IdGenerateFacade.getOrderBindId(),orderId);
			
			writeOrder(order);
			writeOrderBind(bind);
			
//			DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
			
			finishOrder(bind);
			
		}else{
			
			
		}
	
	}

	
	
	
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public boolean isBatch() {
		return isBatch;
	}

	public void setBatch(boolean isBatch) {
		this.isBatch = isBatch;
	}

	public JhfAliveOrder getOrder() {
		return order;
	}

	public void setOrder(JhfAliveOrder order) {
		this.order = order;
	}

	public JhfOrderBind getBind() {
		return bind;
	}

	public void setBind(JhfOrderBind bind) {
		this.bind = bind;
	}

}
