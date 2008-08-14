package com.lubq.lm.bestwiz.order.builder;

import java.util.ArrayList;
import java.util.List;

import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;

public abstract class OrderBuilderAbstractFactory implements OrderBuilder{

	public List<JhfAliveOrder> buildBatchOrder() {
		List<JhfAliveOrder> orderList = new ArrayList<JhfAliveOrder>();
		
		for
		
		JhfAliveOrder order = this.buildOrder();
		
	}



//	public JhfAliveOrder initOrder();

	public OrderBindInfo buildOrderBind() {
		// TODO Auto-generated method stub
		
	}

}
