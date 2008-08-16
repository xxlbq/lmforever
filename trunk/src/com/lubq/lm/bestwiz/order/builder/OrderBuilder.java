package com.lubq.lm.bestwiz.order.builder;

import java.util.List;

import com.lm.common.util.prop.PropertiesUtil;

import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

public interface OrderBuilder {
	

	void initOrder();

	JhfAliveOrder createOrder(String customer) throws IdGenerateException;
	JhfOrderBind  createOrderBind(String orderBindId ,String orderId,String tradeId);

	void writeOrder(JhfAliveOrder order) throws DaoException;
	void writeBatchOrder(List<JhfAliveOrder> orderList) throws DaoException;
	
	void writeOrderBind(JhfOrderBind bind) throws DaoException;
	void writeBatchOrderBind(List<JhfOrderBind> bindList) throws DaoException;

	void service() throws  Exception;
	
	PropertiesUtil initProperty(String fullPropPath);
	
	String getFullPath();
	String getCustomerId();
	List<String> getCustomerIdList();

	void finishOrder(OrderBindInfo bindInfo) throws JMSException;
	void finishBatchOrder(OrderBindInfo bindInfo) throws JMSException;
	
}
