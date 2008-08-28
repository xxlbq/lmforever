//package com.lubq.lm.bestwiz.customer.builder;
//
//import java.util.List;
//
//import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
//import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
//import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
//import cn.bestwiz.jhf.core.jms.exception.JMSException;
//
//import com.lubq.lm.bestwiz.order.builder.bean.OrderBuilderMessageVender;
//
//public class CustomerBuilderFactory implements CustomerBuilder {
//
//	public JhfAliveOrder createOrder(String customer, String orderBindId)
//			throws IdGenerateException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void finishCustomer() throws JMSException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public OrderBuilderMessageVender getOrderBuilderMessageVender() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public void initCustomer() throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void service() throws Exception {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void writeBatchOrder(List<JhfAliveOrder> orderList)
//			throws DaoException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	public void writeOrder(JhfAliveOrder order) throws DaoException {
//		button.addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				new Thread() {
//					public void run() {
//						for (int i = 0; i < 100; i++) {
//							try {
//								sleep(100);
//								System.out.println("123");
//							} catch (InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//						Display.getDefault().asyncExec(new Runnable() {
//
//							@Override
//							public void run() {
//								MessageDialog.openInformation(null, null,
//										"finish");
//							}
//
//						});
//					}
//				}.start();
//
//		
//	}
//		}
//}
