package com.lubq.lm.bestwiz.customer.builder.service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.lm.common.util.str.StringCommonUtil;
import com.lubq.lm.bestwiz.customer.builder.bean.CustomerDependencyBuilderMessageVender;
import com.lubq.lm.util.PasswordCharsBean;
import com.lubq.lm.util.StringUtil;
import com.lubq.lm.util.ValidatorUtil;

import cn.bestwiz.jhf.core.bo.bean.CustomerInfo;
import cn.bestwiz.jhf.core.bo.enums.AccountOpenStatusEnum;
import cn.bestwiz.jhf.core.bo.enums.CorporationTypeEnum;
import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.dao.bean.main.JhfAliveOrder;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.jms.exception.JMSException;
import cn.bestwiz.jhf.core.util.DateHelper;




public class CustomerBuilderService implements CustomerBuilder{

	private CustomerDependencyBuilderMessageVender custMsg;
	
	


	public CustomerDependencyBuilderMessageVender getCustomerBuilderMessageVender() {
		// TODO Auto-generated method stub
		return null;
	}



	public void service() throws Exception {
		AccountOpenService as  = new AccountOpenService();
        /** 1.保存用户基本信息到JHF_CUSTOMER ,JHF_PERSONAL表 */
		/** 2.保存用户详细信息到JHF_CUST_BANK_ACCOUNT表 */
		
		CustomerInfo custInfo = buildCustomerInfo(custMsg);
		as.openAccount(custInfo);
		System.out.println("customer detail :"+StringCommonUtil.reflectionToString(custInfo));
	}

	private CustomerInfo buildCustomerInfo(CustomerDependencyBuilderMessageVender custMsg) throws Exception{
		
		 String frontDate = DateHelper.formatDate(DateHelper
                 .getSystemTimestamp());
		
		CustomerInfo info = new CustomerInfo();
		
//		cInfo.setCustomerId(IdGenerateFacade.getCustomerId());
		
		
//		generateLoginId(info);
		
		info.setUpdateStaffId("LUBQ");
		info.setApplicationDate(frontDate);
		//#0未送付 #1送付済み
		info.setDocumentSendStatus(new BigDecimal("1"));
		/*#0 申込み前 
		#1 書類待ち 
		#2 口座開設申請中 
		#3 審査差戻 由于资料不齐，从審査部被驳回的状态
		#4 不備確認中 書類不齐或者正在向顾客确认事项中，等待回答的状态
		#5 口座開設不受理  
		#6 口座開設済：入金待ち 
		#7 口座開設済 初回最低保証金１０万円入金完成的状态
		#8 口座開設済：取引済 口座開設后，最初约定了新規注文的状态。（目的是在affiliate承认的时候，把握有无交易情况）
		#9 解約  
		#10 自動取消 口座申込form受取：经过3个月、本人確認書類未受取 
		#11自動解約 本人確認書類OK：经过3个月、未入金
		#12口座開設取消 #1～#4・#6的Status下，取消帐户开设*/
		info.setAccountOpenStatus(new BigDecimal("7"));
		info.setDateTime(DateHelper.getSystemTimestamp());
		info.setAccountStatusChangeDatetime(info.getDateTime());
		//#0:個人 #1:法人
		info.setCorporationType(new BigDecimal("0"));
		info.setFirstName("SUN");
		info.setFirstNameKana("WUKONG");
		info.setLastName("ZHU");
		info.setLastNameKana("BAJIE");
		info.setBirthdayYear("2000");
		info.setBirthdayMonth("12");
		info.setBirthdayDay("31");
		info.setZipCode1("12345678");
		info.setCity1("NEW YORK");
		info.setSection1("NOTANY");
		info.setSectionKana1("NOTANY");
		info.setHouseNumber1("NOTANY");
		info.setHouseNumberKana1("NOTANY");
		
		info.setTel1("119110");
		info.setAnnualIncome(new BigDecimal("5"));
		info.setFinancialAssets(new BigDecimal("5"));
		info.setFirstTransferAmount(new BigDecimal("5"));
		
		info.setFinancialInstitutionCode("0000");
		info.setBranchCode("811");
		info.setEmailPc("abc@efg.com");
		info.setEmailMobile("abc@efg.com");
		
		
		
		
//		System.out.println(StringCommonUtil.reflectionToString(info));
		
		return info;
	}

	

	public JhfAliveOrder createCustomerDependency(String customer,
			String orderBindId) throws IdGenerateException {
		String customerId = IdGenerateFacade.getCustomerId();
		
		System.out.println(customerId);
		return null;
	}

	public void finishCustomerDependency() throws JMSException {
		System.out.println("finishCustomerDependency  begin ...");
		System.out.println("finishCustomerDependency  over .");
		
	}

	public CustomerDependencyBuilderMessageVender getCustomerDependencyBuilderMessageVender() {
		// TODO Auto-generated method stub
		return null;
	}

	public void initCustomerDependency() throws Exception {
		System.out.println("initCustomerDependency  begin ...");
		System.out.println("initCustomerDependency  over .");
		
	}

	public void writeBatchCustomerDependency(List<JhfAliveOrder> orderList)
			throws DaoException {
		// TODO Auto-generated method stub
		
	}

	public void writeCustomerDependency(JhfAliveOrder order)
			throws DaoException {
		// TODO Auto-generated method stub
		
	}
	
	public void doCustomer(){
		try {
			initCustomerDependency();
			service();
			finishCustomerDependency();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR FIRE IN doCustomer() .. ");
		}
	}
	
	
  
	
	
	public static void main(String[] args) throws IdGenerateException {
		CustomerBuilderService s = new CustomerBuilderService();
		s.doCustomer();
	}

}