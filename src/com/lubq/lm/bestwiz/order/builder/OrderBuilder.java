package com.lubq.lm.bestwiz.order.builder;

import java.util.List;

import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;

public interface OrderBuilder {
	
	JhfAliveOrder buildOrder();
	List<JhfAliveOrder> buildBatchOrder();
	
	OrderBindInfo buildOrderBind();
	
}
