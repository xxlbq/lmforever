package com.lubq.lm.bestwiz.customer.builder.service;

import java.util.List;

import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lubq.lm.bestwiz.customer.builder.bean.CustomerBuilderMessageVender;


public class CustomerBuilderService implements CustomerBuilder{

	
	
	
	public JhfAliveOrder createCustomer(String customer, String orderBindId)
			throws IdGenerateException {
		String customerId = IdGenerateFacade.getCustomerId();
		
		System.out.println(customerId);
		return null;
	}

	public void finishCustomer() throws JMSException {
		// TODO Auto-generated method stub
		
	}

	public CustomerBuilderMessageVender getCustomerBuilderMessageVender() {
		// TODO Auto-generated method stub
		return null;
	}

	public void initCustomer() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void service() throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void writeBatchCustomer(List<JhfAliveOrder> orderList)
			throws DaoException {
		// TODO Auto-generated method stub
		
	}

	public void writeCustomer(JhfAliveOrder order) throws DaoException {
		// TODO Auto-generated method stub
		
	}
	
	
	public static void main(String[] args) throws IdGenerateException {
		CustomerBuilderService s = new CustomerBuilderService();
		s.createCustomer("", "");
	}
	
}