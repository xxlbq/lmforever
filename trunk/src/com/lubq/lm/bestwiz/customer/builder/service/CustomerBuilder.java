package com.lubq.lm.bestwiz.customer.builder.service;

import java.util.List;

import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lubq.lm.bestwiz.customer.builder.bean.CustomerBuilderMessageVender;

public interface CustomerBuilder {
	//order 执行前初始化方法
	void initCustomer() throws  Exception;

	JhfAliveOrder createCustomer(String customer,String orderBindId) throws IdGenerateException;
	void writeCustomer(JhfAliveOrder order) throws DaoException;
	void writeBatchCustomer(List<JhfAliveOrder> orderList) throws DaoException;

	//order 执行的主方法
	void service() throws  Exception;

	CustomerBuilderMessageVender getCustomerBuilderMessageVender();

	//order 执行完成后的方法
	void finishCustomer() throws JMSException;
	
}
