package com.lubq.lm.bestwiz.order.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;




import com.lm.common.util.prop.PropertiesUtil;

import cn.bestwiz.jhf.core.bo.contructor.OrderBindInfoFactory;
import cn.bestwiz.jhf.core.bo.enums.ChangeReasonEnum;
import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.BaseMainDao;
import cn.bestwiz.jhf.core.dao.DAOFactory;
import cn.bestwiz.jhf.core.dao.OrderDao;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBindId;
import cn.bestwiz.jhf.core.dao.util.DbSessionFactory;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;
import cn.bestwiz.jhf.core.util.DataLogger;
import cn.bestwiz.jhf.core.util.LogUtil;

public abstract class OrderBuilderAbstractFactory implements OrderBuilder{
	
	private static Log log = LogUtil.getLog(OrderBuilderAbstractFactory.class);
	
//	private String orderId;
	private String bindId;
	private boolean isBatch;
	
	private JhfAliveOrder order;
	private JhfOrderBind bind;
	
	int mode = 1 ;

	public JhfOrderBind createOrderBind(String orderBindId,String orderId,String tradeId) {
		
		JhfOrderBind bind = new JhfOrderBind();
		
		JhfOrderBindId id = new JhfOrderBindId();
		id.setOrderBindId(orderBindId);
		id.setOrderId(orderId);
		id.setTradeId(tradeId);
		
		bind.setId(id);
		bind.setActiveFlag(BigDecimal.ONE);
		bind.setInputDate(new Date());
		bind.setUpdateDate(new Date());
		
		return bind;
		
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
		  BaseMainDao bdao = new BaseMainDao();
		  bdao.save(order);
		
	}

	public void writeOrderBind(JhfOrderBind bind) throws DaoException {
		DAOFactory.getOrderDao().createOrderBind(bind);
		
	}

	
	
	public void service() throws Exception {
		
		initOrder();
		
		if( ! isBatch()){
			
			DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
			
			order = createOrder();
			
			String orderbindId = IdGenerateFacade.getOrderBindId();
			
			bind = createOrderBind(orderbindId,order.getId().getOrderId(),order.getId().getTradeId());
			
			writeOrder(order);
			writeOrderBind(bind);
			
			DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
			
			OrderBindInfo bindInfo =  OrderBindInfoFactory.getInstance().createInfo(bind);
			bindInfo = this.setupOrderBindInfo(bindInfo, order);
			
			finishOrder(bindInfo);
			
		}else{
			
			
		}
	
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
	
	private OrderBindInfo setupOrderBindInfo(OrderBindInfo bindInfo,JhfAliveOrder order){
		
		bindInfo.setCurrencyPair(order.getCurrencyPair());
		bindInfo.setProductId(order.getProductId());
		bindInfo.setCustomerId(order.getCustomerId());
		bindInfo.setAmount(order.getOrderAmount());
		bindInfo.setSide(order.getSide().intValue());
		bindInfo.setMode(this.mode);
		
		if(order.getSide().intValue() == 1){
			bindInfo.setTradeAskPrice(order.getOrderPrice());
		}else{
			bindInfo.setTradeBidPrice(order.getOrderPrice());
		}
		
		bindInfo.setType(order.getOrderType().intValue());
		bindInfo.setSlippage(order.getSlippage());
		bindInfo.setPriceId(order.getPriceId());
		
		return bindInfo;
		
	}

}
