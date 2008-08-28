package com.lubq.lm.bestwiz.customer.builder;

import java.util.List;

import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lubq.lm.bestwiz.order.builder.bean.OrderBuilderMessageVender;

public interface CustomerBuilder {
	//order 执行前初始化方法
	void initCustomer() throws  Exception;

	JhfAliveOrder createOrder(String customer,String orderBindId) throws IdGenerateException;
	void writeOrder(JhfAliveOrder order) throws DaoException;
	void writeBatchOrder(List<JhfAliveOrder> orderList) throws DaoException;

	//order 执行的主方法
	void service() throws  Exception;

	OrderBuilderMessageVender getOrderBuilderMessageVender();

	//order 执行完成后的方法
	void finishCustomer() throws JMSException;
	
}
