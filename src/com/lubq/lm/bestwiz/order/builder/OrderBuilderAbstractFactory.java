package com.lubq.lm.bestwiz.order.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import cn.bestwiz.jhf.core.bo.contructor.OrderBindInfoFactory;
import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.BaseMainDao;
import cn.bestwiz.jhf.core.dao.DAOFactory;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBind;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderBindId;
import cn.bestwiz.jhf.core.dao.util.DbSessionFactory;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.jms.bean.OrderBindInfo;


import com.lm.common.util.prop.PropertiesUtil;

public abstract class OrderBuilderAbstractFactory implements OrderBuilder{
	
//	private static Log log = LogUtil.getLog(OrderBuilderAbstractFactory.class);
	
	private boolean isBatch;
	
	int mode = 1 ;

	public JhfOrderBind createOrderBind(String orderBindId,String orderId,String tradeId) {
		
		JhfOrderBind bind = new JhfOrderBind();
		
		JhfOrderBindId id = new JhfOrderBindId();
		id.setOrderBindId(orderBindId);
		id.setOrderId(orderId);
		id.setTradeId(tradeId);
		
		bind.setId(id);
		bind.setActiveFlag(BigDecimal.ONE);
		bind.setInputDate (new Date());
		bind.setUpdateDate(new Date());
		
		return bind;
		
	}

	public void writeBatchOrder(List<JhfAliveOrder> orderList) throws DaoException {
		for (JhfAliveOrder jhfAliveOrder : orderList) {
			writeOrder(jhfAliveOrder);
		}
	}

	public void writeBatchOrderBind(List<JhfOrderBind> bindList) throws DaoException {
		for (JhfOrderBind jhfOrderBind : bindList) {
			writeOrderBind(jhfOrderBind);
		}
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
			
			JhfAliveOrder order = createOrder(getCustomerId());
			
			String orderbindId = IdGenerateFacade.getOrderBindId();
			JhfOrderBind bind = createOrderBind(orderbindId,order.getId().getOrderId(),order.getId().getTradeId());
			
			writeOrder(order);
			writeOrderBind(bind);
			
			DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
			DbSessionFactory.closeConnection();
			
			OrderBindInfo bindInfo =  OrderBindInfoFactory.getInstance().createInfo(bind);
			bindInfo = this.setupOrderBindInfo(bindInfo, order);
			
			finishOrder(bindInfo);
			
		}else{
			
			List<OrderBindInfo> orderBindInfoList = new ArrayList<OrderBindInfo>(); 
			//多个customerId 
			List<String> customerIdList = getCustomerIdList();
			
			
			for (String cstId : customerIdList) {
				
				for(int bindi = 0; bindi < getOrderBindListSize(); bindi++){
					
					DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
					
					List<JhfAliveOrder> orderList = new ArrayList<JhfAliveOrder>();
					List<JhfOrderBind> orderBindList = new ArrayList<JhfOrderBind>();
					//生成orderBindId ，一个bindId，对应多个orderId
					String orderbindId = IdGenerateFacade.getOrderBindId();
					
					for (int i = 0; i < getBatchSize(); i++) {
						//创建order对象，并添加到orderList中
						JhfAliveOrder order = createOrder(cstId);
						orderList.add(order);
						//创建orderBind对象，并添加到orderBindList中
						JhfOrderBind bind = createOrderBind(orderbindId,order.getId().getOrderId(),order.getId().getTradeId());
						orderBindList.add(bind);
						//创建orderBindInfo对象，并添加到orderBindInfoList中
						OrderBindInfo bindInfo =  OrderBindInfoFactory.getInstance().createInfo(bind);
						bindInfo = this.setupOrderBindInfo(bindInfo, order);
						orderBindInfoList.add(bindInfo);
					}
					//将 order 写入db
					writeBatchOrder(orderList);
					//将 orderbind 写入 db
					writeBatchOrderBind(orderBindList);
	
					DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
					
				}
	
			}
			
			for (OrderBindInfo orderBindInfo : orderBindInfoList) {
				finishOrder(orderBindInfo);
			}

			DbSessionFactory.closeConnection();
			
		}
	
	}

	
	private int getOrderBindListSize() {
		return initProperty(getFullPath()).getIntValue("orderBindListSize");
	}
	
	public int getBatchSize(){
		return initProperty(getFullPath()).getIntValue("batchSize");
	}
	
	


	public PropertiesUtil initProperty(String fullPropPath){
		return new PropertiesUtil(fullPropPath);
	}

	public boolean isBatch() {
		return isBatch;
	}

	public void setBatch(boolean isBatch) {
		this.isBatch = isBatch;
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
