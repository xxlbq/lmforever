package com.lubq.lm.bestwiz.customer.builder.service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.LockMode;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import com.lubq.lm.util.PasswordCharsBean;
import com.lubq.lm.util.ValidatorUtil;
//import org.apache.commons.validator.GenericValidator;

import cn.bestwiz.jhf.core.bo.bean.CustomerInfo;
import cn.bestwiz.jhf.core.bo.enums.AccountActiveStatusEnum;
import cn.bestwiz.jhf.core.bo.enums.AccountOpenStatusEnum;
import cn.bestwiz.jhf.core.bo.enums.AccountStatusEnum;
import cn.bestwiz.jhf.core.bo.enums.AppPropertyKey;
import cn.bestwiz.jhf.core.bo.enums.AppPropertyType;
import cn.bestwiz.jhf.core.bo.enums.BoolEnum;
import cn.bestwiz.jhf.core.bo.enums.ConstraintTypeEnum;
import cn.bestwiz.jhf.core.bo.enums.CorporationTypeEnum;
import cn.bestwiz.jhf.core.bo.enums.CustBankAccountWithdrawalFlagEnum;
import cn.bestwiz.jhf.core.bo.enums.MailActionIdEnum;
import cn.bestwiz.jhf.core.bo.enums.MailAddressTypeEnum;
import cn.bestwiz.jhf.core.bo.enums.MailPriorityEnum;
import cn.bestwiz.jhf.core.bo.enums.VirtualAccountStatusEnum;
import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.bo.exceptions.ServiceException;
import cn.bestwiz.jhf.core.control.AdminControl;
import cn.bestwiz.jhf.core.dao.BaseMainDao;
import cn.bestwiz.jhf.core.dao.CashflowDao;
import cn.bestwiz.jhf.core.dao.ConfigDao;
import cn.bestwiz.jhf.core.dao.CustomerDao;
import cn.bestwiz.jhf.core.dao.DAOFactory;
import cn.bestwiz.jhf.core.dao.LockHelper;
import cn.bestwiz.jhf.core.dao.MailDao;
import cn.bestwiz.jhf.core.dao.ProductDao;
import cn.bestwiz.jhf.core.dao.bean.main.JhfArtificial;
import cn.bestwiz.jhf.core.dao.bean.main.JhfBankBranch;
import cn.bestwiz.jhf.core.dao.bean.main.JhfBankBranchId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCashBalance;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCashBalanceId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustBankAccount;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustBankAccountId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustomer;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustomerInfoChangeLog;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustomerStatus;
import cn.bestwiz.jhf.core.dao.bean.main.JhfGroupProductBand;
import cn.bestwiz.jhf.core.dao.bean.main.JhfLeverageGroup;
import cn.bestwiz.jhf.core.dao.bean.main.JhfLeverageGroupId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfMailAction;
import cn.bestwiz.jhf.core.dao.bean.main.JhfMailActionMap;
import cn.bestwiz.jhf.core.dao.bean.main.JhfMailActionMapId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfMailAddress;
import cn.bestwiz.jhf.core.dao.bean.main.JhfMailAddressId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfPersonal;
import cn.bestwiz.jhf.core.dao.bean.main.JhfVirtualAccountNo;
import cn.bestwiz.jhf.core.dao.exception.DataBaseConnectionException;
import cn.bestwiz.jhf.core.dao.util.DbSessionFactory;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.idgenerate.exception.IdGenerateException;
import cn.bestwiz.jhf.core.mail.BizMailService;
import cn.bestwiz.jhf.core.service.AccountService;
import cn.bestwiz.jhf.core.service.CoreService;
import cn.bestwiz.jhf.core.service.CtiService;
import cn.bestwiz.jhf.core.service.ServiceFactory;
import cn.bestwiz.jhf.core.service.exception.AccountException;
import cn.bestwiz.jhf.core.service.exception.CoreException;
import cn.bestwiz.jhf.core.service.exception.TreasureException;
import cn.bestwiz.jhf.core.util.BeanUtils;
import cn.bestwiz.jhf.core.util.DateHelper;
import cn.bestwiz.jhf.core.util.LogUtil;
import cn.bestwiz.jhf.core.util.OgnlUtil;
//import cn.bestwiz.jhf.frontdesk.lib.enums.AccountClassificationEnumFlash;
//import cn.bestwiz.jhf.frontdesk.lib.security.AdminServiceException;
//import cn.bestwiz.jhf.frontdesk.trade.biz.bean.CustomerBean;
//import cn.bestwiz.jhf.frontdesk.trade.biz.logic.LoadCustomerSettingHandler;
//import cn.bestwiz.jhf.web.common.enums.CorporationTypeEnum;
//import cn.bestwiz.jhf.web.common.enums.DocumentSendStatusEnum;
//import cn.bestwiz.jhf.web.util.CsLogEnum;

/**
 * create customer information
 * 
 * @author JHF Team <jhf@bestwiz.cn>
 * 
 * 
 * @copyright 2006-2007, BestWiz(Dalian) Co.,Ltd
 */
public class AccountOpenService {
    private Log logger = LogUtil.getLog(AccountOpenService.class);

    private CustomerDao m_customerDao = DAOFactory.getCustomerDao();// 用户操作dao类

    private MailDao m_mailDao = DAOFactory.getMailDao();// 邮件操作dao类

    private CashflowDao m_cashflowDao = DAOFactory.getCashflowDao();//
    // 用户金额操作dao类
    //
    private ProductDao m_productDao = DAOFactory.getProductDao();//
    // 产品信息操作dao类

    private ConfigDao m_configDao = DAOFactory.getConfigDao();// 配置信息操作dao类

    private static final String CURRENCYCODE_CONSTANT = "JPY";

    private static final BaseMainDao baseMainDao = new BaseMainDao();
    private static final int ZERO = 0;

    /**
     * 口座开设
     * 
     * @param customer
     * @throws AdminServiceException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    /**
     * @author lubq <lubq@bestwiz.cn>
     * @param customer
     * @throws Exception
     */
    public void openAccount(CustomerInfo customer) throws Exception {

        logger.info("AccountService.openAccount start : \r\n" + customer);

        try {
            // 用户口座开设在同一事务中开始。此后分步操作无需重新开启事务
            DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
//            long count = countCustomerByMailAddress(customer.getEmailPc(), null);
//
//            if (count > 0) {
//                throw new Exception(
//                        "openAccount duplicate emailPc error");
//            }

            // // 获取系统的默认group为后续操作准备数据
            String groupId = getDefaultGroupId();

            // customer,customer_status用到的staffId(社员Id)
            String updateStaffId = customer.getUpdateStaffId();

            // 系统操作员Id
            if (GenericValidator.isBlankOrNull(updateStaffId)) {
                updateStaffId = getSystemStaffId();
                customer.setUpdateStaffId(updateStaffId);
            }

            String frontDate = DateHelper.formatDate(DateHelper
                    .getSystemTimestamp());
            customer.setApplicationDate(frontDate);

            
           /* private static final int ALL_VALUE = 2;
            // #0 未送付
            private static final int NO = 0;

            // #1 送付済み
            private static final int DONE = 1;*/
            customer.setDocumentSendStatus(new BigDecimal("0"));

            customer.setAccountOpenStatus(new BigDecimal(
                    AccountOpenStatusEnum.OPEN.getValue()));

            customer.setDateTime(DateHelper.getSystemTimestamp());

            customer.setAccountStatusChangeDatetime(customer.getDateTime());

            /** 1.保存用户基本信息到JHF_CUSTOMER表 */
            storeCustomer(customer);

            /** 2.保存用户详细信息到JHF_CUST_BANK_ACCOUNT表 */
            storeCustBankAccount(customer);

            /** 3.保存用户邮件信息 */
            storeCustomerMailAddress(customer);

            
            
             /** 4.保存用户详细信息到JHF_CUSTOMER_STATUS表，其中group注意是defaultGroup。 */
             storeCustomerStatus(customer, groupId);

             /** 5.保存邮件模板绑定信息 */
//             storeMailActionMap(customer);
             DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
             
             
             DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
             /** 6.保存levagegroup信息 */
             storeLeverageGroup(customer, groupId);
            
             /** 7.保存cash_balance信息 */
             storeCashBalance(customer);

            // 事务完成后进行提交操作，如果有错误，所有操作回滚。
            DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
        } catch (Exception e) {
            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
            throw e;
        }

        // 发送邮件
//        sendMail(customer);
        
        // cti 同步
//        updateCti(customer);

        // try {
        // AdminControl.clearDaoCache(JhfLeverageGroup.class);
        // } catch (Exception e) {
        // throw new AdminServiceException(
        // AdminServiceException.CLEARDAOCACHE_ERROR,
        // "openAccount clearDaoCache error", e);
        // }

//        customer.organizeShowCustomerInfo();

        logger.info("AccountService.openAccount end : \r\n" + customer);
    }

    /**
     * 用户情报设定操作 : 1.根据用户id检索数据库中此用户id是否已经存在，若存在提示错误并退出，若不存在继续执行
     * 2.将CustomerInfo对象转为JHFCustomer对象； 3.将用户情报信息更新到JHF_CUSTOMER表。
     * 
     * @param customer
     *            CustomerInfo对象
     * @throws DaoException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IdGenerateException
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void storeCustomer(CustomerInfo customer) throws DaoException,
            IllegalAccessException, InvocationTargetException,
            NoSuchMethodException, IdGenerateException {
    	
    	System.out.println("=== storeCustomer  begin ... ");
    	
        // JhfCustomer existCust = null;
        // List<JhfCustomerStatus> list = null;// 判断用户信息是否存在用list

        // /** 根据id检索customerId是否存在 */
        // existCust = m_customerDao.obtainCustomer(customer.getCustomerId());
        //
        // if (existCust != null) {
        // // 用户id已经存在，中止操作并提示错误
        // m_log
        // .error("[AccountService.openAccount()] customer already exist!");
        // throw new AdminServiceException(
        // AdminServiceException.DUPLICATE_RECORD_ERROR,
        // "[AccountService.openAccount()] customer alreadey exist!");
        // }

        // /** 根据id检索待插入的用户loginId是否已经存在 */
        // list = obtainCustomerStatusByLoginId(customer.getLoginId());
        //
        // if (list != null && list.size() != 0) {
        // // 用户id已经存在，中止操作并提示错误
        // m_log
        // .error("[AccountService.openAccount()] customer already exist!");
        // throw new AdminServiceException(
        // AdminServiceException.DUPLICATE_RECORD_ERROR,
        // "[AccountService.openAccount()] customer alreadey exist!");
        // }

        // 保存用户信息到db
        // 将实体bean转换为hibernate数据库操作bean
        JhfCustomer jhfCustomer;
        

/*        private static final int PERSONAL = 0;

        private static final int LEGAL = 1;*/
        if (1 == customer
                .getCorporationType().intValue()) {
            jhfCustomer = new JhfArtificial();
        } else {
            jhfCustomer = new JhfPersonal();
        }

        BeanUtils.copyProperties(jhfCustomer, customer);

        jhfCustomer.setAgreeFlag(new BigDecimal(BoolEnum.BOOL_YES_ENUM
                .getValue()));

        jhfCustomer.setInputStaffId(customer.getUpdateStaffId());// 操作者

        String customerId = IdGenerateFacade.getCustomerId();
        jhfCustomer.setCustomerId(customerId);
        customer.setCustomerId(customerId);


        m_customerDao.storeCustomer(jhfCustomer);
        System.out.println("=== storeCustomer  over ... ");
    }

    /**
     * 保存用户详细信息到JHF_CUST_BANK_ACCOUNT表
     * 
     * @param customer
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws DaoException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void storeCustBankAccount(CustomerInfo customer)
            throws Exception {
    	
    	System.out.println("=== storeCustBankAccount  begin ... ");
        customer.setVirtualAccountFlag(new BigDecimal(BoolEnum.BOOL_NO_ENUM
                .getValue()));

        JhfBankBranchId jhfBankBranchId = new JhfBankBranchId();
        OgnlUtil.copy(customer, jhfBankBranchId, null);

        JhfBankBranch jhfBankBranch = (JhfBankBranch) m_customerDao.get(JhfBankBranch.class, jhfBankBranchId);

        if (jhfBankBranch == null) {
            throw new Exception(
                    "storeCustBankAccount error: jhfBankBranch is null! financialInstitutionCode="
                            + customer.getFinancialInstitutionCode()
                            + ", branchCode=" + customer.getBranchCode());
        }

        customer.setFinancialInstitutionName(jhfBankBranch
                .getFinancialInstitutionName());
        customer.setFinInstitutionNameKana(jhfBankBranch
                .getFinInstitutionNameKana());
        customer.setBranchName(jhfBankBranch.getBranchName());
        customer.setBranchNameKana(jhfBankBranch.getBranchNameKana());

        JhfCustBankAccount custBankAccount = new JhfCustBankAccount();// 数据库操作bean
        OgnlUtil.copy(customer, custBankAccount, null);

        JhfCustBankAccountId id = new JhfCustBankAccountId();
        id.setCustomerId(customer.getCustomerId());
        id.setCurrencyCode(CURRENCYCODE_CONSTANT);
        id.setWithdrawalFlag(new BigDecimal(
                CustBankAccountWithdrawalFlagEnum.BOTH_ENUM.getValue()));

        custBankAccount.setId(id);

        custBankAccount.setInputStaffId(customer.getUpdateStaffId());

        m_customerDao.save(custBankAccount);
        
        System.out.println("=== storeCustBankAccount  over ... ");
    }

    /**
     * 保存用户邮件信息到JHF_MAIL_ADDRESS表 现系统每个用户有两个邮件信息,主邮件和次邮件,同事插入
     * 
     * @param customer
     * @throws AdminServiceException
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     * @throws DaoException
     */
    private void storeCustomerMailAddress(CustomerInfo customer)
            throws DaoException {
    	
    	System.out.println("=== storeCustomerMailAddress  begin ... ");
    	
        // 1.插入PC邮件信息
        m_mailDao.insertMailAddress(buildJhfMailAddress(customer,
                MailPriorityEnum.PRIMARY.getValue(),
                MailAddressTypeEnum.ADDR_PC_MAIL_ENUM, customer.getEmailPc()));

        m_mailDao.insertMailAddress(buildJhfMailAddress(customer,
                MailPriorityEnum.SECONDARY.getValue(),
                MailAddressTypeEnum.ADDR_PC_MAIL_ENUM, null));

        // 2.插入Mobile邮件信息
        m_mailDao.insertMailAddress(buildJhfMailAddress(customer,
                MailPriorityEnum.PRIMARY.getValue(),
                MailAddressTypeEnum.ADDR_MOBILE_MAIL_ENUM, customer
                        .getEmailMobile()));

        // m_mailDao.insertMailAddress(buildJhfMailAddress(customer,
        // MailPriorityEnum.SECONDARY.getValue(),
        // MailAddressTypeEnum.ADDR_MOBILE_MAIL_ENUM, null));
        
        System.out.println("=== storeCustomerMailAddress  over ... ");
    }

    /**
     * 根据邮件的主次和类型不同分别生成对应的JhfMailAddress实例
     * 
     * @param customer
     * @param priority
     * @param mailAddressType
     * @param mail
     * @return JhfMailAddress
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private JhfMailAddress buildJhfMailAddress(CustomerInfo customer,
            int priority, MailAddressTypeEnum mailAddressType, String mail) {

        return buildJhfMailAddress(customer, priority, mailAddressType, mail,
                new JhfMailAddress());
    }

    /**
     * 根据邮件的主次和类型不同分别生成对应的JhfMailAddress实例
     * 
     * @param customer
     * @param priority
     * @param mailAddressType
     * @param mail
     * @param mailAddress
     * @return JhfMailAddress
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private JhfMailAddress buildJhfMailAddress(CustomerInfo customer,
            int priority, MailAddressTypeEnum mailAddressType, String mail,
            JhfMailAddress mailAddress) {

        JhfMailAddressId id = mailAddress.getId();

        if (id == null) {
            id = new JhfMailAddressId();
            id.setCustomerId(customer.getCustomerId());// 待开户用户id
            id.setMailAddressPriority(new BigDecimal(priority));// 邮件优先级
            id.setMailAddressType(new BigDecimal(mailAddressType.getValue()));// 邮件类型,pc,mobile
            mailAddress.setId(id);

            mailAddress.setInputStaffId(customer.getUpdateStaffId());// 执行操作人员
        }

        mailAddress.setUpdateStaffId(customer.getUpdateStaffId());// 执行操作人员

        if (mail != null) {
            mailAddress.setMailAddress(mail);// 邮件地址
        } else {
            mailAddress.setMailAddress("");
        }

        return mailAddress;
    }

     /**
		 * 保存用户详细信息到JHF_CUSTOMER_STATUS表 其中groupId全部为defaultGroup
		 * 
		 * @param customer
		 * @param groupId
		 * @throws IllegalAccessException
		 * @throws InvocationTargetException
		 * @throws NoSuchMethodException
		 * @throws DaoException
		 * 
		 * @author yaolin <yaolin@bestwiz.cn>
		 */
	private void storeCustomerStatus(CustomerInfo customer, String groupId)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, DaoException {

		System.out.println("=== storeCustomerStatus  begin ... ");
		
		BigDecimal enable = new BigDecimal(
				ConstraintTypeEnum.TYPE_ENABLE_ALL_ENUM.getValue());

		JhfCustomerStatus status = new JhfCustomerStatus();// 数据库操作bean
		String frontDate = customer.getApplicationDate();
	    String password = "111111111111";
		BeanUtils.copyProperties(status, customer);

	    String originalPassword = null;
	  	try {
	  		originalPassword = AccountService.encodeOriginalPassword(password);
	  	} catch (UnsupportedEncodingException e1) {
	  		// TODO Auto-generated catch block
	  		e1.printStackTrace();
	  	}
	  	
		status.setGroupId(groupId);// groupId默认是defaultGroup

		status.setInputStaffId(customer.getUpdateStaffId());
		status.setAccountStatus(new BigDecimal("0"));
		status.setAccountActiveStatus(new BigDecimal("0"));


		status.setAccountActiveStatusDate(frontDate);
		status.setPasswordUpdateDate(frontDate);

		status.setOldLoginPassword(null);// 旧密码
		status.setUpperAmount(null);
		
        status.setWithdrawalConstraint(enable); // 出金限制
        status.setLoginConstraint(enable); // 登陆限制
        status.setOpenBuyConstraint(enable); // 新规买限制
        status.setOpenSellConstraint(enable); // 新规卖限制
        status.setCloseBuyConstraint(enable); // 决计买限制
        status.setCloseSellConstraint(enable); // 决计卖限制
        status.setStraddleOptionFlag(enable); // 两建
        status.setLosscutConstrant(enable); // Losscut
        

        status.setLoginPassword(AccountService.encodePassword(password));
        status.setOriginalPassword(originalPassword);
        // all #26 add
        status.setAlertRatioFlag(BigDecimal.ZERO);
        status.setLosscutRatioFlag(BigDecimal.ZERO);
        status.setAlertRatio(BigDecimal.ZERO);
        status.setLosscutRatio(BigDecimal.ZERO);
        status.setAlertMailCount(BigDecimal.ONE);
        status.setLosscutMailCount(BigDecimal.ZERO);


        generateLoginId(customer);
        status.setLoginId(customer.getLoginId());

		try {
			assignVirtualAccountNo(customer);
			status.setVirtualAccountNo(customer.getVirtualAccountNo());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		customer.setAccountActiveStatusDate(frontDate);
		customer.setPasswordUpdateDate(frontDate);
      try {
		customer.setAccountOpenDate(ServiceFactory.getCoreService().getFrontDate());
	} catch (CoreException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

      	customer.setCustomerOrderNo(BigDecimal.ZERO + "");



      m_customerDao.storeCustomerStatus(status);
      
      System.out.println("=== storeCustomerStatus  over ... ");
      System.out.println(" FINISH CUSTOMER STATUS  MSG :");
      System.out.println(" MSG :" +"/r/n"+status.toString());
  }
		

	
	
	
	
	

//    /**
//     * 将账户状态改为开设完了
//     * 
//     * @param jhfCustomer
//     * @return boolean 是否执行了入金完了
//     * @throws DaoException
//     * @throws ServiceException
//     * 
//     * @author yaolin <yaolin@bestwiz.cn>
//     */
//    private boolean accountOpenComplete(JhfCustomer jhfCustomer) throws DaoException, ServiceException {
//
//        if (!isDepositWait(jhfCustomer)) {
//            return false;
//        }
//
//        String customerId = jhfCustomer.getCustomerId();
//
//        JhfCustomerStatus status = (JhfCustomerStatus) customerDao.get(JhfCustomerStatus.class, customerId,
//                LockMode.UPGRADE);
//
//        if (status == null) {
//            throw new TreasureException(TreasureException.CUSTOMER_LOCK_ERROR, "CUSTOMER_LOCK_ERROR! customerId = "
//                    + customerId);
//        }
//
//        jhfCustomer.setAccountOpenStatus(new BigDecimal(AccountOpenStatusEnum.ACCOUNT_OPEN_ENUM.getValue()));
//
//        status.setAccountStatus(new BigDecimal(AccountStatusEnum.ACCOUNT_NORMAL.getValue()));
//
//        status.setAccountActiveStatus(new BigDecimal(AccountActiveStatusEnum.NORMAL_ENUM.getValue()));
//
//
//
//        jhfCustomer.setAccountOpenFinishDate(frontDate);
//
//        jhfCustomer.setAccountStatusChangeDate(frontDate);
//
//        jhfCustomer.setAccountStatusChangeDatetime(DateHelper.getSystemTimestamp());
//
//        customerDao.update(jhfCustomer);
//        customerDao.update(status);
//
//        return true;
//
//    }
	
	
	
	
	
	
	/**
	 * 保存用户邮件模板对应信息到JHF_MAIL_ACCTION_MAP
	 * 对每一个JHF_MAIL_ADDRESS和JHF_MAIL_ACTION建立一条关联数据。也就是说默认上客户可以收到所有的 对顾客的MAIL.
	 * 
	 * @param customer
	 * @throws AdminServiceException
	 * 
	 * @author mengfj <mengfj@bestwiz.cn>
	 * @author yaolin <yaolin@bestwiz.cn>
	 * @throws DaoException
	 */
	private void storeMailActionMap(CustomerInfo customer) throws DaoException {
		
		System.out.println("=== storeMailActionMap  begin ... customerId:" +customer.getCustomerId() );
		
		List<cn.bestwiz.jhf.core.dao.bean.main.JhfMailAction> list = null;

		// 获取所有的mailActionId进行mail的映射操作
		list = m_mailDao.getAllMailAction();
		for (JhfMailAction mailAction : list) {
			// PC邮件信息绑定
			
			System.out.println("customerId:"+customer.getCustomerId()
					+",mailactionMap id:"+mailAction.getId()
					.getMailActionId());
			
			m_mailDao
					.buildMailActionMap(createMailActionMap(customer,
							MailPriorityEnum.PRIMARY.getValue(),
							MailAddressTypeEnum.ADDR_PC_MAIL_ENUM, customer
									.getEmailPc(), mailAction.getId()
									.getMailActionId()));

			// Mobile邮件信息绑定
			m_mailDao.buildMailActionMap(createMailActionMap(customer,
					MailPriorityEnum.PRIMARY.getValue(),
					MailAddressTypeEnum.ADDR_MOBILE_MAIL_ENUM, customer
							.getEmailMobile(), mailAction.getId()
							.getMailActionId()));
		}
		
		System.out.println("=== storeMailActionMap  begin ... ");
	}
	
	
	
	
	
	
	
	
	
	
	
	
    
     /**
		 * 根据邮件主次和类型不同,生成对应的数据库操作需要的mailActionMap bean
		 * 
		 * @param customer
		 * @param priority
		 * @param mailAddressType
		 * @param mail
		 * @param mailActionId
		 * @return JhfMailActionMap
		 * 
		 * @author mengfj <mengfj@bestwiz.cn>
		 * @author yaolin <yaolin@bestwiz.cn>
		 */
	private JhfMailActionMap createMailActionMap(
			CustomerInfo customer,int priority, MailAddressTypeEnum mailAddressType,
			String mail,String mailActionId) {
	     JhfMailActionMap mailActionMap = new JhfMailActionMap();// 数据库操作bean
	    
	     cn.bestwiz.jhf.core.dao.bean.main.JhfMailActionMapId mapId = new JhfMailActionMapId();
	     mapId.setCustomerId(customer.getCustomerId());// 待开户用户id
	     mapId.setMailAddressType(new BigDecimal(mailAddressType.getValue()));//
	     //邮件类型
	     mapId.setMailActionId(mailActionId);// 对应的mailActionId
	     mapId.setMailAddressPriority(new BigDecimal(priority));// 邮件优先级
	     mailActionMap.setId(mapId);
	    
	     mailActionMap.setInputStaffId(customer.getUpdateStaffId());// 操作人员
	     mailActionMap.setUpdateStaffId(customer.getUpdateStaffId());// 操作人员
    
	     return mailActionMap;
    }
    //
     /**
		 * 保存用户与所对应的product信息到JHF_LEVERAGE_GROUP
		 * 
		 * @param customer
		 * @param groupId
		 * @throws DaoException
		 * 
		 * @author mengfj <mengfj@bestwiz.cn>
		 * @author yaolin <yaolin@bestwiz.cn>
		 */
	private void storeLeverageGroup(CustomerInfo customer, String groupId)
			throws DaoException {
		
		System.out.println("=== storeLeverageGroup  begin ... ");
		System.out.println("GROUP ID:"+groupId);
		List<JhfGroupProductBand> list = null;// 用户对应组别的所有product信息

		// 检索用户所在组的所有product信息
		list = m_productDao.getProductByGroup(groupId);

		for (JhfGroupProductBand groupProductBand : list) {
			JhfLeverageGroup group = new JhfLeverageGroup();

			JhfLeverageGroupId leverageGroupId = new JhfLeverageGroupId();
			leverageGroupId.setCustomerId(customer.getCustomerId());
			leverageGroupId.setProductId(groupProductBand.getId()
					.getProductId());
			group.setId(leverageGroupId);

			group.setCurrencyPair(groupProductBand.getCurrencyPair());

			group.setInputStaffId(customer.getUpdateStaffId());
			group.setUpdateStaffId(customer.getUpdateStaffId());

			m_customerDao.storeLeverageGroup(group);
		}
		
		
		System.out.println("=== storeLeverageGroup  begin ... ");
	}
	
	
	
	
    
     /**
     * 生成用户的帐户余额信息.新开户默认都是0
     *
     * @param customer
     * @throws DaoException
     *
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
     private void storeCashBalance(CustomerInfo customer) throws DaoException {

    	 System.out.println("=== storeCashBalance  begin ... ");
    	 
		JhfCashBalance balance = new JhfCashBalance();// 数据库操作的balance bean

		JhfCashBalanceId id = new JhfCashBalanceId();// balance id
		id.setCustomerId(customer.getCustomerId());// 用户id
		id.setCurrencyCode(CURRENCYCODE_CONSTANT);//
		// JHF_CURRENCY.ACCOUNT_STATUS为1的货币才放在JHF_CASHBALANCE中，本期只有JPY的ACCOUNT_STATUS为1。
		balance.setId(id);

		/** 新开户用户默认的金额全部为零 */
		balance.setPrePreBalance(new BigDecimal(ZERO));
		balance.setPreviousBalance(new BigDecimal(ZERO));
		balance.setCashBalance(new BigDecimal("1000000000"));

		balance.setInputStaffId(customer.getUpdateStaffId());// 操作员(新增)
		balance.setUpdateStaffId(customer.getUpdateStaffId());// 操作员(更新)

		// 保存calanceBalance信息到db
		m_cashflowDao.createCashBalance(balance);
		
		
		System.out.println("=== storeCashBalance  begin ... ");
	}
    
     /**
		 * 获取默认的用户分组(系统目前全部为defaultGroup
		 * 
		 * @return
		 * @throws AdminServiceException
		 * 
		 * @author mengfj <mengfj@bestwiz.cn>
		 * @author yaolin <yaolin@bestwiz.cn>
		 */
     private String getDefaultGroupId() throws Exception {
		String groupId = null;// group,默认全部是defaultGroup从appProperty中取

		try {
			groupId = m_configDao.getAppPropertyValue(
					AppPropertyType.JHF_GROUP, AppPropertyKey.DEFAULT_GROUP);
		
		} catch (Exception e1) {
			throw new Exception(
					
					"getDefaultGroupId Database error", e1);
		}
		return groupId;
	}

    /**
	 * 获取系统操作员Id
	 * 
	 * @return String
	 * @throws AdminServiceException
	 * 
	 * @author mengfj <mengfj@bestwiz.cn>
	 * @author yaolin <yaolin@bestwiz.cn>
	 */
    private String getSystemStaffId() throws Exception {
        String staffId = null;

        try {

            staffId = ServiceFactory.getAccountService().getSystemStaffId();

        } catch (Exception e) {
            throw new Exception(
                 
                    "getSystemStaffId Database error");
        }

        return staffId;
    }

    // /**
    // * 更新用户信息: 1.修改保存用户基本信息到JHF_CUSTOMER表 2.修改保存用户详细信息到JHF_CUSTOMER_STATUS表。
    // * 3.修改保存用户邮件信息
    // *
    // * @param customer
    // * @throws AdminServiceException
    // *
    // * @author mengfj <mengfj@bestwiz.cn>
    // * @author yaolin <yaolin@bestwiz.cn>
    // */
    // public void updateAccount(CustomerInfo customer)
    // throws AdminServiceException {
    // try {
    // DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
    //
    // // customer,customer_status用到的staffId(社员Id)
    // String inputStaffId = customer.getInputStaffId();
    //
    // // 系统操作员Id
    // if (GenericValidator.isBlankOrNull(inputStaffId)) {
    // inputStaffId = getSystemStaffId();
    // customer.setInputStaffId(inputStaffId);
    // }
    //
    // String loginPassword = encodePassword(customer.getLoginPassword());
    // customer.setLoginPassword(loginPassword);
    //
    // /** 1.修改保存用户基本信息到JHF_CUSTOMER表 (暂时无需修改) */
    // updateCustomer(customer);//
    // /** 2.修改保存用户详细信息到JHF_CUSTOMER_STATUS表。 */
    // updateCustomerStatus(customer);
    // /** 3.修改保存用户邮件信息 */
    // updateCustomerAddress(customer);
    //
    // // /** 4.修改保存邮件模板绑定信息 (暂时无需修改) */
    // // storeMailActionMap(customer);
    // // /** 5.修改保存levagegroup信息 (暂时无改动) */
    // // storeLeverageGroup(customer, groupId);
    // // /** 6.修改保存cash_balance信息 (暂时无改动) */
    // // storeCashBalance(customer);
    //
    // DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
    // } catch (AdminServiceException e) {
    // DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
    // m_log.error("[AccountService.updateAccount()] error!", e);
    // throw e;
    // } catch (Exception e) {
    // DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
    // m_log.error("[AccountService.updateAccount()] error!", e);
    // throw new AdminServiceException(
    // AdminServiceException.DATA_ACCESS_ERROR, e);
    // }
    // }
    //
    // /**
    // * 更新用户基本信息到JHF_CUSTOMER表
    // *
    // * @param customer
    // * @param systemStaffId
    // * @throws AdminServiceException
    // */
    // private void updateCustomer(CustomerInfo customer)
    // throws AdminServiceException {
    //
    // try {
    // JhfCustomer jhfCustomer = (JhfCustomer) m_customerDao.get(
    // JhfCustomer.class, customer.getCustomerId(),
    // LockMode.UPGRADE);
    //
    // if (jhfCustomer == null) {
    // throw new AdminServiceException(
    // AdminServiceException.BANK_OR_BRANCH_ERROR,
    // "[AccountService.updateAccount()] customer isn't exist!");
    // }
    //
    // String name = customer.getCustFirstName(); // 顾客姓名
    //
    // if (GenericValidator.isBlankOrNull(name))
    // return;
    //
    // jhfCustomer.setCustFirstName(name);
    //
    // jhfCustomer.setUpdateStaffId(customer.getInputStaffId());// 更新者
    //
    // m_customerDao.update(jhfCustomer);
    // } catch (DaoException e) {
    // m_log.error("[AccountService.updateCustomer()] error!", e);
    // throw new AdminServiceException(
    // AdminServiceException.DATA_ACCESS_ERROR, e);
    // }
    // }
    //
    // /**
    // * 修改用户详细信息: 检验用户是否对密码做了修改,如果修改则更新,如果没有则不做操作
    // *
    // * @param customer
    // * @throws AdminServiceException
    // *
    // * @author mengfj <mengfj@bestwiz.cn>
    // * @author yaolin <yaolin@bestwiz.cn>
    // */
    // private void updateCustomerStatus(CustomerInfo customer)
    // throws AdminServiceException {
    //
    // JhfCustomerStatus status = null;
    //
    // try {
    // // 检索用户密码,看密码是否发生修改,若未变化则不必做修改操作,否则修改用户密码
    // status = m_customerDao.obtainCustomerStatus(customer
    // .getCustomerId());
    //
    // if (status == null) {
    // throw new AdminServiceException(
    // AdminServiceException.BANK_OR_BRANCH_ERROR,
    // "[AccountService.updateAccount()] customer isn't exist!");
    // }
    //
    // if (customer.getLoginPassword() != null) {
    // status.setOldLoginPassword(status.getLoginPassword());// 旧密码更新
    // status.setLoginPassword(customer.getLoginPassword());// 新密码更新
    // status.setPasswordUpdateDate(DateHelper.formatDate(customer
    // .getDateTime()));// 密码更新时间
    //
    // m_customerDao.updateCustomerStatus(status);
    // }
    // } catch (DaoException e) {
    // m_log.error("[AccountService.updateCustomerStatus()] error!", e);
    // throw new AdminServiceException(
    // AdminServiceException.DATA_ACCESS_ERROR, e);
    // }
    // }
    //
    // /**
    // * 更新用户邮件信息
    // *
    // * @param customer
    // * @throws AdminServiceException
    // *
    // * @author mengfj <mengfj@bestwiz.cn>
    // * @author yaolin <yaolin@bestwiz.cn>
    // */
    // private void updateCustomerAddress(CustomerInfo customer)
    // throws AdminServiceException {
    //
    // try {
    // // 更新主邮件地址
    // String mainEmail = customer.getMainEmail();
    // if (mainEmail != null)
    // m_mailDao.updateMailAddress(updateJhfMailAddress(customer,
    // MailPriorityEnum.PRIMARY.getValue(), mainEmail));
    //
    // // 更新次邮件地址
    // String secondEmail = customer.getSecondEmail();
    // if (secondEmail != null)
    // m_mailDao.updateMailAddress(updateJhfMailAddress(customer,
    // MailPriorityEnum.SECONDARY.getValue(), secondEmail));
    // } catch (DaoException e) {
    // m_log.error("[AccountService.updateCustomerAddress()]error!", e);
    // throw new AdminServiceException(
    // AdminServiceException.DATA_ACCESS_ERROR, e);
    // }
    // }
    //
    // /**
    // * 根据邮件的主次不同分别更新对应的JhfMailAddress实例
    // *
    // * @param customer
    // * @param priority
    // * @param mail
    // * @return JhfMailAddress
    // * @throws DaoException
    // * @throws AdminServiceException
    // *
    // * @author yaolin <yaolin@bestwiz.cn>
    // */
    // private JhfMailAddress updateJhfMailAddress(CustomerInfo customer,
    // int priority, String mail) throws DaoException,
    // AdminServiceException {
    //
    // JhfMailAddress mailAddress = obtainJhfMailAddress(customer
    // .getCustomerId(), priority);
    //
    // if (mailAddress == null) {
    // throw new AdminServiceException(
    // AdminServiceException.BANK_OR_BRANCH_ERROR,
    // "[AccountService.updateAccount()] customer isn't exist!");
    // }
    //
    // return buildJhfMailAddress(customer, priority, mail, mailAddress);
    // }
    //
    // /**
    // * 获取用户对应的的JhfMailAddress实例
    // *
    // * @param customerId
    // * 用户id
    // * @param priority
    // * 邮件的主次优先级
    // * @return JhfMailAddress
    // *
    // * @throws AdminServiceException
    // *
    // * @author yaolin <yaolin@bestwiz.cn>
    // */
    // public JhfMailAddress obtainJhfMailAddress(String customerId, int
    // priority)
    // throws AdminServiceException {
    //
    // JhfMailAddress mailAddress = null;
    //
    // try {
    // DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
    //
    // JhfMailAddressId id = new JhfMailAddressId();
    // id.setCustomerId(customerId);// 用户id
    // id.setMailAddressPriority(new BigDecimal(priority));// 邮件优先级
    // id.setMailAddressType(new BigDecimal(
    // MailAddressTypeEnum.ADDR_PC_MAIL_ENUM.getValue()));//
    // 邮件类型,pc,mobile,本期都是pc
    //
    // mailAddress = (JhfMailAddress) m_mailDao.get(JhfMailAddress.class,
    // id);
    //
    // DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
    // } catch (AdminServiceException e) {
    // DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
    // m_log.error("[AccountService.obtainJhfMailAddress()] error!", e);
    // throw e;
    // } catch (Exception e) {
    // DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
    // m_log.error("[AccountService.obtainJhfMailAddress()] error!", e);
    // throw new AdminServiceException(
    // AdminServiceException.DATA_ACCESS_ERROR, e);
    // }
    //
    // return mailAddress;
    // }
    //
    // /**
    // * <p>
    // * 用户情报取得操作
    // *
    // (1).根据顾客ID，检索JHF_CUSTOMER及JHF_CUSTOMER_STATUS表，取得用户情报信息，并以CustomerInfo对象返回。
    // * </p>
    // *
    // * @param String
    // * (customerId)
    // * @return CustomerInfo (getCustomer)
    // * @throws AdminServiceException
    // *
    // * @author mengfj <mengfj@bestwiz.cn>
    // */
    // public CustomerInfo obtainCustomer(String customerId)
    // throws AdminServiceException {
    // JhfCustomer jhfCustomer = null;// 数据库操作bean
    // CustomerInfo customerInfo = new CustomerInfo();// 类传递实体bean
    // try {
    // DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
    // jhfCustomer = m_customerDao.obtainCustomer(customerId);
    // DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
    // } catch (Exception e) {
    // DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
    // m_log.error("[AccountService.obtainCustomer()] error!", e);
    // throw new AdminServiceException(
    // AdminServiceException.DATA_ACCESS_ERROR, e);
    // }
    // if (jhfCustomer != null) {
    // customerInfo.setCustomerId(jhfCustomer.getCustomerId());
    //
    // customerInfo.setCustFirstName(jhfCustomer.getCustFirstName());
    // customerInfo.setCustFirstNameKana(jhfCustomer
    // .getCustFirstNameKana());
    // customerInfo.setCustLastName(jhfCustomer.getCustLastName());
    //
    // customerInfo.setCustLastNameKana(jhfCustomer.getCustLastNameKana());
    // customerInfo.setAccountOpenDate(jhfCustomer.getAccountOpenDate());
    // }
    // return customerInfo;
    // }
    //
    // /**
    // * <p>
    // * 用户属性情报取得操作 (1).根据顾客ID，调用CustomerDao检索JHF_CUSTOMER_CONFIG表，取得用户属性情报信息，
    // * 并以JHFCustomerConfig对象的LIST返回；
    // * (2).循环遍历该LIST，将JHFCustomerConfig对象转为Properties对象。
    // * </p>
    // *
    // * @param String
    // * (customerId)
    // * @return Properties (getCustomerProperties)
    // * @throws AdminServiceException
    // * @throws Exception
    // *
    // * @author mengfj <mengfj@bestwiz.cn>
    // */
    // public Properties obtainCustomerProperties(String customerId)
    // throws AdminServiceException {
    // List<JhfCustomerConfig> lstJhfCusConfig = null;
    // JhfCustomerConfig tmpConfig = null;
    // try {
    // DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
    // lstJhfCusConfig = m_customerDao.obtainCustomerConfigs(customerId,
    // null);
    // DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
    // } catch (Exception e) {
    // DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
    // m_log
    // .error(
    // "[AccountService.obtainCustomerProperteis()] error!",
    // e);
    // throw new AdminServiceException(
    // AdminServiceException.DATA_ACCESS_ERROR, e);
    // }
    //
    // Properties properties = new Properties();
    // if (null != lstJhfCusConfig) {
    // Iterator<JhfCustomerConfig> iterator = lstJhfCusConfig.iterator();
    // while (iterator.hasNext()) {
    // tmpConfig = iterator.next();
    // properties.setProperty(tmpConfig.getId().getConfigCode(),
    // tmpConfig.getConfigValue());
    // }
    // }
    // return properties;
    // }
    //
    // /**
    // * <p>
    // * 用户属性情报设定操作 (1).根据键值与顾客ID，调用CustomerDao，检索JHF_CUSTOMER_CONFIG表，并以LIST返回；
    // *
    // (2).若该LIST为空，则将用户属性情报信息插入到JHF_CUSTOMER_CONFIG表；若不为空，则将用户属性情报信息更新到JHF_CUSTOMER_CONFIG表。
    // * </p>
    // *
    // * @param String
    // * (customerId)
    // * @param String
    // * (key)
    // * @param String
    // * (value)
    // * @param String
    // * (type) -- add by mengfj 2006/10/17
    // * @return
    // * @throws AdminServiceException
    // * @throws Exception
    // *
    // * @author mengfj <mengfj@bestwiz.cn>
    // *
    // */
    // public void storeCustomerProperty(String customerId, String key,
    // String value, String type) throws AdminServiceException {
    // List<JhfCustomerConfig> list = null;// 检验是否已存在config的list
    // JhfCustomerConfig storeConfig = new JhfCustomerConfig();// 呆保存config对象
    // JhfCustomerConfigId configId = new JhfCustomerConfigId();// configId对象
    // Date date = new Date();
    // configId.setCustomerId(customerId);
    // configId.setConfigType(new BigDecimal(type));
    // configId.setConfigCode(key);
    //
    // storeConfig.setId(configId);
    // storeConfig.setActiveFlag(BigDecimal.valueOf(BoolEnum.BOOL_YES_ENUM
    // .getValue()));// 未删除标志
    // storeConfig.setConfigValue(value);
    // storeConfig.setUpdateDate(date);
    // try {
    // DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
    // list = m_customerDao.obtainCustomerConfigs(customerId, key);// 查询是否存在
    // if (null == list || 0 == list.size()) {
    // storeConfig.setInputDate(date);
    // m_customerDao.storeCustomerConfig(storeConfig);// insert
    // } else {
    // for (int i = 0, size = list.size(); i < size; i++) {
    // if (String.valueOf(list.get(i).getId().getConfigType())
    // .equals(type)) {
    // storeConfig.setInputDate(list.get(i).getInputDate());
    // break;
    // }
    // }
    // m_customerDao.modifyCustomerConfig(storeConfig);// update
    // }
    // DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
    // } catch (Exception e) {
    // DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
    // m_log.error("[AccountService.sotreCustomerProperty()] error", e);
    // throw new AdminServiceException(
    // AdminServiceException.DATA_ACCESS_ERROR, e);
    // }
    // }

    /**
     * 列出JhfPostZipAction中的addressKanji1(都道府县)
     * 
     * @return List
     * @throws AdminServiceException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public List listAddressCity() throws Exception {

        List list = null;

        try {
            DbSessionFactory.beginReadOnlyTransaction(DbSessionFactory.INFO);

            list = DAOFactory.getCsDao().listAddressCity();

            DbSessionFactory.commitReadOnlyTransaction(DbSessionFactory.INFO);
   
        } catch (Exception e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.INFO);
            throw new Exception(
                    "listAddressCity other error", e);
        }

        return list;
    }

    /**
     * 根据loginId从数据库检索用户状态信息
     * 
     * @param loginId
     * @return List<JhfCustomerStatus>
     * @throws Exception
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
//    public List<JhfCustomerStatus> obtainCustomerStatusByLoginId(String loginId)
//            throws Exception {
//
//        List<JhfCustomerStatus> list = null;
//
//        try {
//            DbSessionFactory.beginReadOnlyTransaction(DbSessionFactory.MAIN);
//
//            list = m_customerDao.obtainCustomerStatusByLoginId(loginId);
//
//            DbSessionFactory.commitReadOnlyTransaction(DbSessionFactory.MAIN);
//        } catch (Exception e) {
//            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
//            throw e;
//        
//
//        return list;
//    }

    /**
     * 向顾客发送邮件
     * 
     * @param customer
     * @throws Exception
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void sendMail(CustomerInfo customer) throws Exception {

        String mailActionId = MailActionIdEnum.OPEN_ACCOUNT_ENUM.getName();
        Hashtable<String, String> map = new Hashtable<String, String>();

        String lastName = customer.getLastName();

        map.put("name", (lastName == null ? "" : lastName) + " "
                + customer.getFirstName());

        logger.debug("mail: " + map);

        BizMailService service = new BizMailService();

        try {
            service.sendMailDirect(customer.getCustomerId(), mailActionId, map);
        } catch (Exception e) {
            throw new Exception(
                    
                    "sendMail Database error", e);
        }
    }

    /**
     * 统计指定姓名的顾客数量
     * 
     * @param lastName
     * @param firstName
     * @param lastNameKana
     * @param firstNameKana
     * @return long
     * @throws Exception
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public long countCustomerByName(String lastName, String firstName,
            String lastNameKana, String firstNameKana)
            throws Exception {

        long count = 0;

        try {
            count = ServiceFactory.getAccountService().countCustomerByName(
                    lastName, firstName, lastNameKana, firstNameKana);
        } catch (Exception e) {
            throw new Exception(
                    
                    "countCustomerByName Database error", e);
        }

        return count;
    }

    /**
     * 统计指定邮件地址的顾客数量
     * 
     * @param mailAddress
     * @param customerId
     *            排除指定的customerId
     * @return long
     * @throws Exception
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public long countCustomerByMailAddress(String mailAddress, String customerId)
            throws Exception {

        long count = 0;

        try {
            count = ServiceFactory.getAccountService()
                    .countCustomerByMailAddress(mailAddress, customerId);
        } catch (Exception e) {
            throw new Exception(
                    
                    "countCustomerByMailAddress Database error", e);
        }

        return count;
    }
    /**
     * 获取MAIN中的数据库Bean
     * 
     * @param clazz
     * @param id
     * @return Object 数据库Bean
     * @throws Exception
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public Object mainGet(Class clazz, Serializable id)
            throws Exception {

        Object obj = null;

        try {
            DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);

            obj = baseMainDao.get(clazz, id);

            DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
        } catch (Exception e) {
            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
            throw e;
        }

        return obj;
    }
    
    /**
     * 将顾客的信息同步的cti
     * 
     * @param customer
     * @throws Exception
     * 
     * @author liuxb <liuxb@bestwiz.cn>
     */
    private void updateCti(CustomerInfo customer) throws Exception {
        CtiService ctiService = new CtiService();

        ctiService.updateUserInfo(customer.getCustomerId());

        List<String> customerIds = new ArrayList<String>(1);
        customerIds.add(customer.getCustomerId());
        ctiService.SyncEmailoptout(customerIds);

    }
    
//    public CustomerBean getUserInfo(String customerId) throws Exception {
//    	
//		CustomerBean bean = new CustomerBean();
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("customerId", customerId);
//		
//		LoadCustomerSettingHandler customer = new LoadCustomerSettingHandler();
//		List ls = customer.executeAction(map);
//		
//		if (ls.size() > 1)
//			bean = (CustomerBean) ls.get(1);
//		
//		return bean;
//    }
    
//    public void updateAccountInfo(javax.servlet.http.HttpServletRequest request,
//            cn.bestwiz.jhf.web.trade.form.AccountCustomerForm customerInfo) throws Exception {
//
//        try {
//            DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
//
//            /** 4.修改保存用户详细信息到JHF_CUST_BANK_ACCOUNT表 */
//            CustomerInfo customer = new CustomerInfo();
//            customer.setAccountClassification(new BigDecimal(customerInfo.getAccountClassification()));
//            customer.setAccountNumber(customerInfo.getAccountNumber());
//            customer.setBranchCode(customerInfo.getBranchCode());
//            customer.setBranchName(customerInfo.getBranchName());
//            customer.setCustomerId(customerInfo.getCustomerId());
//            customer.setFinancialInstitutionCode(customerInfo.getFinancialInstitutionCode());
//            customer.setFinancialInstitutionName(customerInfo.getFinancialInstitutionName());
//            customer.setFinancialInstitutionSelect(customerInfo.getFinancialInstitutionSelect());
//
//            String logMemo1 = "出金口座変更";
//
//            customer.setLogMemo1(logMemo1);
//
//            AccountService accountService = new AccountService();
//            CustomerBean bean = accountService.getUserInfo(customerInfo.getCustomerId());
//
//            updateCustBankAccount(customer);
//          
//            JhfCustomerInfoChangeLog logBean = storeJhfCustomerInfoChangeLog(customer, bean);
//
//          logger.warn("ACCOUNT_CHANGE CustomerId=" + customerInfo.getCustomerId() + " Old info=" + logBean.getOriData()
//        		  + " New info=" + logBean.getChangedData());
//            /** 10.保存JhfCustomerInfoChangeLog信息 */
//            saveJhfCustomerInfoChangeLog(logBean);
//
//            DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
//        } catch (Exception e) {
//            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
//            throw e;
//        } catch (Exception e) {
//            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
//            throw new Exception(Exception.DATABASE_ERROR,
//                    "executeUpdateAccount Database error", e);
//        }
//
//    }
//
//    private void updateCustBankAccount(CustomerInfo customer) throws DaoException, Exception {
//
//        JhfCustBankAccountId id = new JhfCustBankAccountId();
//        id.setCustomerId(customer.getCustomerId());
//        id.setCurrencyCode("JPY");
//        id.setWithdrawalFlag(new BigDecimal(CustBankAccountWithdrawalFlagEnum.BOTH_ENUM.getValue()));
//
//        JhfCustBankAccount custBankAccount = (JhfCustBankAccount) m_customerDao.get(JhfCustBankAccount.class, id); // 数据库操作bean
//
//        if (custBankAccount == null) {
//            throw new Exception(Exception.NOT_EXIST_ERROR,
//                    "[AccountUpdateService.updateCustBankAccount()] customer isn't exist!");
//        }
//        if (customer.getFinancialInstitutionCode() == null) {
//            return;
//        }
//
//        customer.setVirtualAccountFlag(custBankAccount.getVirtualAccountFlag());
//        customer.setAccountNameName(custBankAccount.getAccountNameName());
//        customer.setAccountNameNameKana(custBankAccount.getAccountNameNameKana());
//        customer.setUpdateStaffId(custBankAccount.getUpdateStaffId());
//        
//        JhfBankBranchId jhfBankBranchId = new JhfBankBranchId();
//        OgnlUtil.copy(customer, jhfBankBranchId, null);
//
//        JhfBankBranch jhfBankBranch = (JhfBankBranch) m_customerDao.get(JhfBankBranch.class, jhfBankBranchId);
//
//        if (jhfBankBranch == null) {
//            throw new Exception(Exception.PARAM_ERROR,
//                    "updateCustBankAccount error: jhfBankBranch is null! financialInstitutionCode="
//                            + customer.getFinancialInstitutionCode() + ", branchCode=" + customer.getBranchCode());
//        }
//
//        customer.setFinancialInstitutionName(jhfBankBranch.getFinancialInstitutionName());
//        customer.setFinInstitutionNameKana(jhfBankBranch.getFinInstitutionNameKana());
//        customer.setBranchName(jhfBankBranch.getBranchName());
//        customer.setBranchNameKana(jhfBankBranch.getBranchNameKana());
//
//        OgnlUtil.copy(customer, custBankAccount, null);
//
//        m_customerDao.update(custBankAccount);
//
//    }    

    /**
     * 添加JhfCustomerInfoChangeLog信息。 1.创建要保存的JhfCustomerInfoChangeLog bean；
     * 2.根据customer和oldCustomer来比较出金口座、和メールアドレス(PC),来set oriData和changedDate属性； 3.根据customer set其它所需属性； 4.save bean。
     * 
     * @param customer
     *            [new]
     * @param oldCustomer
     *            [old]
     * @throws Exception
     * 
     * @author guoqiang <guoqiang@bestwiz.cn>
     */
//    public JhfCustomerInfoChangeLog storeJhfCustomerInfoChangeLog(CustomerInfo customer, CustomerBean oldCustomer)
//            throws AccountException {
//
//        JhfCustomerInfoChangeLog logBean = new JhfCustomerInfoChangeLog();
//        CoreService coreService = new CoreService();
//        // 页面的【顧客コード】
//        String customerId = customer.getCustomerId();
//        // 页面的【更新理由】
//        String reason = customer.getLogMemo1();
//        // 页面的【備考1】
//        String memo1 = customer.getLogMemo2();
//        // 页面的【備考2】
//        String memo2 = !GenericValidator.isBlankOrNull(customer.getLogMemo3()) ? customer.getLogMemo3() : null;
//        // 页面的【変更前データ】
//        String oriData ;
//        // 页面的【変更後データ】
//        String changedDate ;
//        // CS顧客ID
//        String csId ;  
//        // CS顧客ID
//        String updateStaffId ;
//        // CS顧客ID
//        String inputStaffId ;        
//        try {
//
//        csId = coreService.getSystemStaffId();  
//
//        updateStaffId = coreService.getSystemStaffId();
//
//        inputStaffId = coreService.getSystemStaffId();
//        } catch (Exception e){
//
//            csId = "system";  
//
//            updateStaffId = "system";  
//
//            inputStaffId = "system";       	
//        }
//
//        oriData=oldCustomer.getBankName()+" "+oldCustomer.getBranchName()+" "+oldCustomer.getAccountClassification()+" "+oldCustomer.getAccountNumber();
//        changedDate=customer.getFinancialInstitutionName()+" "+customer.getBranchName()+" "+AccountClassificationEnumFlash.getEnum(customer.getAccountClassification().intValue()).getName()+" "+customer.getAccountNumber();
//
//        // 设置JHF_CUSTOMER_INFO_CHANGE_LOG对象的customerId为页面的【顧客コード】
//        logBean.setCustomerId(customerId);
//        // 设置JHF_CUSTOMER_INFO_CHANGE_LOG对象的reason值为页面的【更新理由】
//        logBean.setReason(reason);
//        // 设置JHF_CUSTOMER_INFO_CHANGE_LOG对象的memo1值为页面的【備考1】
//        logBean.setMemo1(memo1);
//        // 设置JHF_CUSTOMER_INFO_CHANGE_LOG对象的memo2值为页面的【備考2】
//        logBean.setMemo2(memo2);
//        // 设置JHF_CUSTOMER_INFO_CHANGE_LOG对象的oriData值为上面组织好的【変更前データ】
//        logBean.setOriData(oriData);
//        // 设置JHF_CUSTOMER_INFO_CHANGE_LOG对象的changedData值为上面组织好的【変更後データ】
//        logBean.setChangedData(changedDate);
//        // 设置JHF_CUSTOMER_INFO_CHANGE_LOG对象的csId值为当前的【CS顧客ID】
//        logBean.setCsId(csId);
//        // 设置JHF_CUSTOMER_INFO_CHANGE_LOG对象的type值为1
//        logBean.setType(BigDecimal.ONE);
//        // 设置JHF_CUSTOMER_INFO_CHANGE_LOG对象的updateStaffId值为当前的【CS顧客ID】
//        logBean.setUpdateStaffId(updateStaffId);
//        // 设置JHF_CUSTOMER_INFO_CHANGE_LOG对象的inputStaffId值为当前的【CS顧客ID】
//        logBean.setInputStaffId(inputStaffId);
//
//        return logBean;
//    }

    /**
     * 添加JhfCustomerInfoChangeLog信息。 （根据logBean参数进行save操作）
     * 
     * @param customer
     * @throws Exception
     * 
     * @author guoqiang <guoqiang@bestwiz.cn>
     */
    public void saveJhfCustomerInfoChangeLog(JhfCustomerInfoChangeLog logBean) throws AccountException {

        try {
            m_customerDao.save(logBean);
        } catch (DataBaseConnectionException e) {
            throw new AccountException(AccountException.DATA_ACCESS_ERROR,
                    "saveJhfCustomerInfoChangeLog Database connection error", e);
        } catch (DaoException e) {
            throw new AccountException(AccountException.DATA_ACCESS_ERROR,
                    "saveJhfCustomerInfoChangeLog Database error", e);
        } catch (Exception e) {
            throw new AccountException(AccountException.DATA_ACCESS_ERROR, "saveJhfCustomerInfoChangeLog other error",
                    e);
        }
    }
    
    /**
     * 生成loginId
     * 
     * @param customer
     * @throws DaoException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private synchronized void generateLoginId(CustomerInfo customer) 
    throws DaoException {

        if (!StringUtils.isBlank(customer.getLoginId())) {
            return;
        }

        StringBuffer loginId = new StringBuffer();

        if (CorporationTypeEnum.TYPE_CORPORATE_ENUM.getValue() == customer.getCorporationType().intValue()) {
            loginId.append("C");
        } else {
            loginId.append("I");
        }

        String emailPc = customer.getEmailPc();

        int len = emailPc.length();
        int n = 0;

        for (int i = 0; i < len && n < 2; i++) {

            char c = emailPc.charAt(i);

            if (ValidatorUtil.isHalfWidthDigitorAlpha(c)) {
                loginId.append(c);
                n++;
            }
        }

        if (n < 2) {

            SecureRandom random = new SecureRandom();

            for (; n <= 2; n++) {
                int x = random.nextInt(PasswordCharsBean.length() - 1);
                loginId.append(PasswordCharsBean.getAt(x));
            }
        }

        String max = m_customerDao.getCustomerStatusMaxLoginId(loginId + "");

        BigDecimal num;

        try {
            num = new BigDecimal(max);
        } catch (Exception e) {
            num = BigDecimal.ZERO;
        }

        num = num.add(BigDecimal.ONE);

        DecimalFormat formatter = new DecimalFormat("00000");

        loginId.append(formatter.format(num));

        customer.setLoginId(loginId + "");

        System.out.println("generateLoginId loginId: " + loginId);
    }

    
    
    private synchronized void assignVirtualAccountNo(CustomerInfo customer) throws Exception {

        if (!StringUtils.isBlank(customer.getVirtualAccountNo())) {
            return;
        }

        JhfVirtualAccountNo jhfVirtualAccountNo = m_customerDao.obtainMinUnUsedVirtualAccountNo();

        if (jhfVirtualAccountNo == null) {
            throw new Exception(
                    "assignVirtualAccountNo no virtualAccountNo error");
        }

        jhfVirtualAccountNo.setCustomerId(customer.getCustomerId());
        jhfVirtualAccountNo
                .setStatus(new BigDecimal(VirtualAccountStatusEnum.VIRTUALACCOUNTSTATUS_USED_ENUM.getValue()));
        jhfVirtualAccountNo.setUpdateStaffId(customer.getUpdateStaffId());

        m_customerDao.update(jhfVirtualAccountNo);

        String virtualAccountNo = jhfVirtualAccountNo.getVirtualAccountNo();

        customer.setVirtualAccountNo(virtualAccountNo);
        customer.setBankName(jhfVirtualAccountNo.getBankName());
        customer.setBankBranchName(jhfVirtualAccountNo.getBankBranchName());

        System.out.println("assignVirtualAccountNo virtualAccountNo: " + virtualAccountNo);
    }
}
