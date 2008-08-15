package com.lubq.lm.bestwiz.order.builder;

import java.util.List;

import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

public interface OrderBuilder {
	
	//
	void initOrder();
	//
	JhfAliveOrder createOrder(String orderId);
	//
	void writeOrder(JhfAliveOrder order) throws DaoException;
	//
	void writeBatchOrder(List<JhfAliveOrder> orderList);
	
	//
	JhfOrderBind createOrderBind(String orderBindId ,String orderId);
	
	void writeOrderBind(JhfOrderBind bind) throws DaoException;
	//
	List<JhfOrderBind> writeBatchOrderBind(List<JhfOrderBind> bindList);
	
	void service() throws  Exception;

	void finishOrder(JhfOrderBind bind) throws JMSException;
}