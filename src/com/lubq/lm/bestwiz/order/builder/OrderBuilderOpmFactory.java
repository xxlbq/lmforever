package com.lubq.lm.bestwiz.order.builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrderId;
import cn.bestwiz.jhf.core.dao.util.DbSessionFactory;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.exception.JMSException;

import com.lm.common.util.prop.PropertiesUtil;
import com.lubq.lm.bestwiz.order.builder.bean.MessageVenderFactory;
import com.lubq.lm.bestwiz.order.builder.bean.OrderBuilderMessageVender;
import com.lubq.lm.bestwiz.order.builder.cons.OrderConstants;

public class OrderBuilderOpmFactory extends OrderBuilderAbstractFactory{

	private OrderBuilderMessageVender orderMessageVender =null;
	
	public OrderBuilderOpmFactory(OrderBuilderMessageVender orderVender) {
		this.orderMessageVender = orderVender;
		System.out.println(orderVender.toString());
	}
	



	public OrderBuilderMessageVender getOrderBuilderMessageVender() {
		return orderMessageVender;
	}



//
//	public PropertiesUtil loadProperty(String fullPropPath) {
//		return new PropertiesUtil(fullPropPath);
//	}



	public JhfAliveOrder createOrder(String customer)
			throws IdGenerateException {
		return getOpmOrder(customer);
	}



	

	public JhfAliveOrder getOpmOrder(String customer)
			throws IdGenerateException {

		// String orderPriceStr = "200.10";
		String executionPriceStr = "100.00";
		String boardRateStr = "100.00";
		String tradePriceStr = executionPriceStr;
		String tradeTypeStr = "0";
		String orderStatuStr = "1";
		String orderTypeStr = "0";
//		String executionTypeStr = "12";

		JhfAliveOrder order = new JhfAliveOrder();

		// ===========>
		JhfAliveOrderId id = new JhfAliveOrderId();

		id.setOrderId(IdGenerateFacade.getOrderId());
		id.setTradeId(IdGenerateFacade.getTradeId());
		order.setId(id);

		order.setOrderAmount(orderMessageVender.getAmount());
		order.setCustomerId(customer);

		order.setCurrencyPair(orderMessageVender.getCurrencyPair());
		order.setExecutionPrice(new BigDecimal(executionPriceStr));
		order.setExecutionType(new BigDecimal(String.valueOf(orderMessageVender.getExecutionType())));
		order.setActiveFlag(BigDecimal.ONE);
		order.setOrderPrice(orderMessageVender.getOrderPrice());
		order.setRevisionNumber(1);
		order.setOrderStatus(new BigDecimal(orderStatuStr));
		order.setSlippage(null);
		order.setOrderType(new BigDecimal(orderTypeStr));
		order.setSide(new BigDecimal(String.valueOf(orderMessageVender.getSide())));
		order.setCustomerOrderNo(IdGenerateFacade.obtainCustomerOrderNo(orderMessageVender.getCustomerId()));

		// < ===========

		order.setActivationType(BigDecimal.ZERO);
		order.setBoardRate(new BigDecimal(boardRateStr));
		order.setChannelId("WEB");
		order.setInputStaffId("lubq");
		order.setLosscutOrderFlag(BigDecimal.ZERO);
		order.setMarketAtClosingFlag(BigDecimal.ZERO);
		// order.setOrderBindId(IdGenerateFacade.getOrderBindId());
		order.setOrderDate("20080818");
		order.setOrderRoute(BigDecimal.ZERO);
		order.setOrderTime("123456");
		order.setPriceId("PID007");
		order.setProductId("A001");// USD/JPY = A001,EUR/JPY = A002
		order.setTradePrice(new BigDecimal(tradePriceStr));
		order.setInputDate(new Date());
		order.setUpdateDate(new Date());
		order.setUpdateStaffId("lubq");
		order.setOrderDatetime(new Date());
		order.setTradeType(new BigDecimal(tradeTypeStr));
		order.setChangeReason(new BigDecimal("1"));
		order.setForceRelationId(order.getCustomerOrderNo());
		order.setForceRelationFlag(BigDecimal.ZERO);
		order.setCancelRejectFlag(BigDecimal.ZERO);
		order.setCustExecutionType(new BigDecimal("0"));
		order.setExpirationType(new BigDecimal("3"));
		order.setStPriceId(BigDecimal.ZERO);
		order.setInputStaffId("system");
		order.setAgentStaffId("system");
		order.setActivationDate(new Date());
		// order.setTopOrderId("520");

		return order;
	}

	

	
	public void initOrder() {
		System.out.println( " Instant order do init() ..." );
		System.out.println( " Instant order init() over ." );
		
	}

	public void finishOrder() throws JMSException {
		System.out.println("Opm Order finishing   ...");
		System.out.println("Opm Order finish   over .");
	}



	public void service() throws Exception {

		System.out.println("isBatch:"+orderMessageVender.isDoBatch());
		
		if ( ! orderMessageVender.isDoBatch() ) {
			DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);

			JhfAliveOrder order = createOrder(orderMessageVender.getCustomerId());

//			String orderbindId = IdGenerateFacade.getOrderBindId();
//			JhfOrderBind bind = createOrderBind(orderbindId, order.getId().getOrderId(), order.getId().getTradeId());

			writeOrder(order);

//			if (null != bind) {
//				writeOrderBind(bind);
//			}

			DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
			DbSessionFactory.closeConnection();

//			if (null != bind) {
//
//				singleBindInfo = OrderBindInfoFactory.getInstance().createInfo(bind);
//				singleBindInfo = setupOrderBindInfo(singleBindInfo, order);
//
//			}

		} else {

//			bindInfoList = new ArrayList<OrderBindInfo>();
			// 多个customerId
			List<String> customerIdList = orderMessageVender.getCustomerIdList();

			for (String cstId : customerIdList) {

//				for (int bindi = 0; bindi < getOrderBindListSize(); bindi++) {

					DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);

					List<JhfAliveOrder> orderList = new ArrayList<JhfAliveOrder>();
//					List<JhfOrderBind> orderBindList = new ArrayList<JhfOrderBind>();
					// 生成orderBindId ，一个bindId，对应多个orderId
//					String orderbindId = IdGenerateFacade.getOrderBindId();

					for (int i = 0; i < orderMessageVender.getOrderBatchSize(); i++) {
						// 创建order对象，并添加到orderList中
						JhfAliveOrder order = createOrder(cstId);
						orderList.add(order);
						// 创建orderBind对象，并添加到orderBindList中
//						JhfOrderBind bind = createOrderBind(orderbindId, order
//								.getId().getOrderId(), order.getId()
//								.getTradeId());
//						orderBindList.add(bind);

					}

					// 将 order 写入db
					writeBatchOrder(orderList);
					// 将 orderbind 写入 db
//					writeBatchOrderBind(orderBindList);

					DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);

					// 创建orderBindInfo对象，并添加到orderBindInfoList中
//					OrderBindInfo bindInfo = setupMuliOrdersOrderBindInfo(
//							orderbindId, orderList);
//
//					bindInfoList.add(bindInfo);
				}

//			}


			DbSessionFactory.closeConnection();

		}

	}
	
	public static void main(String[] args) throws Exception {
		
		String fullPropertyPath = OrderConstants.PROPERTY_FULL_PATH+"\\"+OrderConstants.COMMON_PROPERTY_NAME;
		OrderBuilderMessageVender orderVender = MessageVenderFactory.createOrderMsgVender(fullPropertyPath);
		OrderBuilderOpmFactory fac = new OrderBuilderOpmFactory(orderVender);
		
		fac.doOrder();
		
		System.exit(0);

	}

	
}
