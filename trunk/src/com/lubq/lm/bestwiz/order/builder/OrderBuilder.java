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
	
	//order 执行前初始化方法
	void initOrder() throws  Exception;

	JhfAliveOrder createOrder(String customer) throws IdGenerateException;
	JhfOrderBind  createOrderBind(String orderBindId ,String orderId,String tradeId);

	void writeOrder(JhfAliveOrder order) throws DaoException;
	void writeBatchOrder(List<JhfAliveOrder> orderList) throws DaoException;
	void writeOrderBind(JhfOrderBind bind) throws DaoException;
	void writeBatchOrderBind(List<JhfOrderBind> bindList) throws DaoException;

	//order 执行的主方法
	void service() throws  Exception;
	
	//读取 order 属性设置的 property ，返回PropertiesUtil
	PropertiesUtil loadProperty(String fullPropPath);
	//获得 property 文件的 路径
	String getPropFullPath();
	
	//获得order一些属性的方法
	String getCustomerId();
	List<String> getCustomerIdList();

	//order 执行完成后的方法
//	void finishOrder(OrderBindInfo bindInfo) throws JMSException;
	void finishOrder() throws JMSException;
	
}
