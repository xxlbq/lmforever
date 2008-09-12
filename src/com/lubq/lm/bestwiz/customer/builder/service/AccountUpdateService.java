package com.lubq.lm.bestwiz.customer.builder.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.validator.GenericValidator;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;

import cn.bestwiz.jhf.admin.common.StrutsResources;
import cn.bestwiz.jhf.admin.common.enums.AccountClassificationEnum;
import cn.bestwiz.jhf.admin.common.enums.AccountOpenStatusChangeEnum;
import cn.bestwiz.jhf.admin.common.enums.CorporationTypeEnum;
import cn.bestwiz.jhf.admin.common.enums.StopMailFlagEnum;
import cn.bestwiz.jhf.admin.common.exception.AdminServiceException;
import cn.bestwiz.jhf.admin.common.util.DocumentFileUtil;
import cn.bestwiz.jhf.admin.common.util.FileUtil;
import cn.bestwiz.jhf.admin.common.util.ValidatorUtil;
import cn.bestwiz.jhf.admin.customer.bean.AccountOpenStatusChangeBean;
import cn.bestwiz.jhf.admin.customer.bean.CustomerAccountCashBean;
import cn.bestwiz.jhf.admin.customer.bean.CustomerAccountLogBean;
import cn.bestwiz.jhf.admin.customer.bean.CustomerDocumentBean;
import cn.bestwiz.jhf.admin.customer.bean.PasswordCharsBean;
import cn.bestwiz.jhf.admin.dao.CustomerAccountStatusDetailDao;
import cn.bestwiz.jhf.core.bo.bean.AccountInfo;
import cn.bestwiz.jhf.core.bo.bean.CustomerInfo;
import cn.bestwiz.jhf.core.bo.enums.AccountActiveStatusEnum;
import cn.bestwiz.jhf.core.bo.enums.AccountOpenStatusEnum;
import cn.bestwiz.jhf.core.bo.enums.AccountStatusEnum;
import cn.bestwiz.jhf.core.bo.enums.BoolEnum;
import cn.bestwiz.jhf.core.bo.enums.ConstraintTypeEnum;
import cn.bestwiz.jhf.core.bo.enums.CustBankAccountWithdrawalFlagEnum;
import cn.bestwiz.jhf.core.bo.enums.CustomerChangeLogTypeEnum;
import cn.bestwiz.jhf.core.bo.enums.MailActionIdEnum;
import cn.bestwiz.jhf.core.bo.enums.MailAddressTypeEnum;
import cn.bestwiz.jhf.core.bo.enums.MailNeedMapFlagEnum;
import cn.bestwiz.jhf.core.bo.enums.MailPriorityEnum;
import cn.bestwiz.jhf.core.bo.enums.VirtualAccountStatusEnum;
import cn.bestwiz.jhf.core.bo.exceptions.DaoException;
import cn.bestwiz.jhf.core.bo.exceptions.ServiceException;
import cn.bestwiz.jhf.core.control.AdminControl;
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
import cn.bestwiz.jhf.core.dao.bean.main.JhfCurrency;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustBankAccount;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustBankAccountId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustomer;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustomerDocument;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustomerInfoChangeLog;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustomerLock;
import cn.bestwiz.jhf.core.dao.bean.main.JhfCustomerStatus;
import cn.bestwiz.jhf.core.dao.bean.main.JhfGroup;
import cn.bestwiz.jhf.core.dao.bean.main.JhfGroupProductBand;
import cn.bestwiz.jhf.core.dao.bean.main.JhfLeverageGroup;
import cn.bestwiz.jhf.core.dao.bean.main.JhfLeverageGroupId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfMailAction;
import cn.bestwiz.jhf.core.dao.bean.main.JhfMailActionMap;
import cn.bestwiz.jhf.core.dao.bean.main.JhfMailActionMapId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfMailAddress;
import cn.bestwiz.jhf.core.dao.bean.main.JhfMailAddressId;
import cn.bestwiz.jhf.core.dao.bean.main.JhfOrderNoGenerator;
import cn.bestwiz.jhf.core.dao.bean.main.JhfPersonal;
import cn.bestwiz.jhf.core.dao.bean.main.JhfVirtualAccountNo;
import cn.bestwiz.jhf.core.dao.exception.DataBaseConnectionException;
import cn.bestwiz.jhf.core.dao.util.DbSessionFactory;
import cn.bestwiz.jhf.core.formula.FormulaFactory;
import cn.bestwiz.jhf.core.formula.FormulaService;
import cn.bestwiz.jhf.core.idgenerate.IdGenerateFacade;
import cn.bestwiz.jhf.core.mail.BizMailService;
import cn.bestwiz.jhf.core.service.AccountService;
import cn.bestwiz.jhf.core.service.CtiService;
import cn.bestwiz.jhf.core.service.ServiceFactory;
import cn.bestwiz.jhf.core.service.exception.AccountException;
import cn.bestwiz.jhf.core.service.exception.CoreException;
import cn.bestwiz.jhf.core.util.DateHelper;
import cn.bestwiz.jhf.core.util.LogUtil;
import cn.bestwiz.jhf.core.util.OgnlUtil;

/**
 * 顧客情報変更 Service
 * 
 * @author JHF Team <jhf@bestwiz.cn>
 * 
 * 
 * @copyright 2006-2007, BestWiz(Dalian) Co.,Ltd
 */
public final class AccountUpdateService {

    private static final Log LOGGER = LogUtil.getLog(AccountUpdateService.class);

    private static AccountUpdateService instance = new AccountUpdateService();

    private final CustomerDao m_customerDao = DAOFactory.getCustomerDao(); // 用户操作dao类

    private final MailDao m_mailDao = DAOFactory.getMailDao(); // 邮件操作dao类

    private final CashflowDao m_cashflowDao = DAOFactory.getCashflowDao(); // 用户金额操作dao类

    private final ProductDao m_productDao = DAOFactory.getProductDao(); // 产品信息操作dao类

    private final AccountService accountService = ServiceFactory.getAccountService();

    private static final String CASHFLOW_CURRENCY = "JPY";

    public static final String SEPARATOR_CSLOG_MEMO = CustomerAccountLogBean.SEPARATOR_MEMO;

    public static final String SPACES = " ";

    /**
     * 构造方法
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private AccountUpdateService() {

        super();
    }

    /**
     * 返回实例
     * 
     * @return AccountUpdateService
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public static AccountUpdateService getInstance() {

        return instance;
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
    private JhfMailAddress buildJhfMailAddress(CustomerInfo customer, int priority,
            MailAddressTypeEnum mailAddressType, String mail, JhfMailAddress mailAddress) {

        JhfMailAddressId id = mailAddress.getId();

        if (id == null) {
            id = new JhfMailAddressId();
            id.setCustomerId(customer.getCustomerId()); // 待开户用户id
            id.setMailAddressPriority(new BigDecimal(priority)); // 邮件优先级
            id.setMailAddressType(new BigDecimal(mailAddressType.getValue())); // 邮件类型,pc,mobile
            mailAddress.setId(id);

            mailAddress.setInputStaffId(customer.getUpdateStaffId()); // 执行操作人员
        }

        mailAddress.setUpdateStaffId(customer.getUpdateStaffId()); // 执行操作人员

        if (mail != null) {
            mailAddress.setMailAddress(mail); // 邮件地址
        } else {
            mailAddress.setMailAddress("");
        }

        return mailAddress;
    }

    /**
     * 保存用户详细信息到JHF_CUSTOMER_STATUS表
     * 
     * @param customer
     * @throws DaoException
     * @throws AdminServiceException
     * @throws UnsupportedEncodingException
     * @throws CoreException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private synchronized void storeCustomerStatus(CustomerInfo customer) throws DaoException, AdminServiceException,
            UnsupportedEncodingException, CoreException {

        customer.setNew(true);

        String groupId = customer.getGroupId();

        if (GenericValidator.isBlankOrNull(groupId)) {
            // 获取系统的默认group为后续操作准备数据
            groupId = getDefaultGroupId();
            customer.setGroupId(groupId);
        }

        String password = generatePassword();

        String originalPassword = AccountService.encodeOriginalPassword(password);

        // 取得ACCOUNT_OPEN的JHF_LOCK表的锁
        accountService.lockAccountOpen();

        generateLoginId(customer);

        assignVirtualAccountNo(customer);

        String frontDate = DateHelper.formatDate(customer.getDateTime());
        customer.setAccountActiveStatusDate(frontDate);
        customer.setPasswordUpdateDate(frontDate);
        customer.setAccountOpenDate(ServiceFactory.getCoreService().getFrontDate());

        customer.setCustomerOrderNo(BigDecimal.ZERO + "");

        JhfCustomerStatus status = new JhfCustomerStatus(); // 数据库操作bean
        OgnlUtil.copy(customer, status, null);

        status.setLoginPassword(AccountService.encodePassword(password));
        status.setOldLoginPassword(status.getLoginPassword());
        status.setOriginalPassword(originalPassword);

        status.setInputStaffId(customer.getUpdateStaffId());
        // all #26 add
        status.setAlertRatioFlag(BigDecimal.ZERO);
        status.setLosscutRatioFlag(BigDecimal.ZERO);
        status.setAlertRatio(BigDecimal.ZERO);
        status.setLosscutRatio(BigDecimal.ZERO);
        status.setAlertMailCount(BigDecimal.ONE);
        status.setLosscutMailCount(BigDecimal.ZERO);
        // all #26 add
        JhfCustomer jhfCustomer = obtJhfCustomer(customer, LockMode.UPGRADE);

        // 设置JHF_CUSTOMER表的 ACCOUNT_OPEN_DATE 開設日付
        jhfCustomer.setAccountOpenDate(customer.getAccountOpenDate());

        m_customerDao.update(jhfCustomer);

        m_customerDao.storeCustomerStatus(status);

    }

    /**
     * 保存用户邮件模板对应信息到JHF_MAIL_ACCTION_MAP 对每一个JHF_MAIL_ADDRESS和JHF_MAIL_ACTION建立一条关联数据。也就是说默认上客户可以收到所有的 对顾客的MAIL.
     * 
     * @param customer
     * @throws DaoException
     * @throws AdminServiceException
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     * 
     */
    private void storeMailActionMap(CustomerInfo customer) throws DaoException, AdminServiceException {

        List<JhfMailAction> list = null;

        // 获取所有的mailActionId进行mail的映射操作
        list = m_mailDao.getAllMailAction();
        for (JhfMailAction mailAction : list) {

            if (MailNeedMapFlagEnum.NEEDMAP_YES_ENUM.getValue() == mailAction.getNeedMapFlag().intValue()) {

                m_mailDao.buildMailActionMap(createMailActionMap(customer, MailPriorityEnum.PRIMARY.getValue(),
                        MailAddressTypeEnum.getEnum(mailAction.getId().getMailAddressType().intValue()), mailAction
                                .getId().getMailActionId()));
            }
        }
    }

    /**
     * 根据邮件主次和类型不同,生成对应的数据库操作需要的mailActionMap bean
     * 
     * @param customer
     * @param priority
     * @param mailAddressType
     * @param mailActionId
     * @return JhfMailActionMap
     * 
     * @throws DaoException
     * @throws AdminServiceException
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private JhfMailActionMap createMailActionMap(CustomerInfo customer, int priority,
            MailAddressTypeEnum mailAddressType, String mailActionId) throws DaoException, AdminServiceException {

        JhfMailActionMapId mapId = new JhfMailActionMapId();
        mapId.setCustomerId(customer.getCustomerId()); // 待开户用户id
        mapId.setMailAddressType(new BigDecimal(mailAddressType.getValue())); // 邮件类型
        mapId.setMailActionId(mailActionId); // 对应的mailActionId
        mapId.setMailAddressPriority(new BigDecimal(priority)); // 邮件优先级

        JhfMailActionMap mailActionMap = (JhfMailActionMap) m_mailDao.get(JhfMailActionMap.class, mapId);

        if (mailActionMap != null) {
            throw new AdminServiceException(AdminServiceException.DUPLICATE_RECORD_ERROR,
                    "[AccountUpdateService.createMailActionMap()] customer already exist!");
        }

        mailActionMap = new JhfMailActionMap(); // 数据库操作bean

        mailActionMap.setId(mapId);

        mailActionMap.setInputStaffId(customer.getUpdateStaffId()); // 操作人员
        mailActionMap.setUpdateStaffId(customer.getUpdateStaffId()); // 操作人员

        return mailActionMap;
    }

    /**
     * 保存用户与所对应的product信息到JHF_LEVERAGE_GROUP
     * 
     * @param customer
     * @param groupId
     * @throws DaoException
     * @throws AdminServiceException
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void storeLeverageGroup(CustomerInfo customer) throws DaoException, AdminServiceException {

        List<JhfGroupProductBand> list = null; // 用户对应组别的所有product信息

        // 检索用户所在组的所有product信息
        String groupId = customer.getGroupId();
        list = m_productDao.getProductByGroup(groupId);

        for (JhfGroupProductBand groupProductBand : list) {

            JhfLeverageGroupId leverageGroupId = new JhfLeverageGroupId();
            leverageGroupId.setCustomerId(customer.getCustomerId());
            leverageGroupId.setProductId(groupProductBand.getId().getProductId());

            JhfLeverageGroup group = (JhfLeverageGroup) m_customerDao.get(JhfLeverageGroup.class, leverageGroupId);

            if (group != null) {
                throw new AdminServiceException(AdminServiceException.DUPLICATE_RECORD_ERROR,
                        "[AccountUpdateService.storeLeverageGroup()] customer already exist!");
            }

            group = new JhfLeverageGroup();

            group.setId(leverageGroupId);

            group.setCurrencyPair(groupProductBand.getCurrencyPair());

            group.setInputStaffId(customer.getUpdateStaffId());
            group.setUpdateStaffId(customer.getUpdateStaffId());

            m_customerDao.storeLeverageGroup(group);
        }
    }

    /**
     * 生成用户的帐户余额信息.新开户默认都是0
     * 
     * @param customer
     * @throws DaoException
     * @throws AdminServiceException
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void storeCashBalance(CustomerInfo customer) throws DaoException, AdminServiceException {

        JhfCashBalanceId id = new JhfCashBalanceId(); // balance id
        id.setCustomerId(customer.getCustomerId()); // 用户id
        id.setCurrencyCode(CASHFLOW_CURRENCY); // JHF_CURRENCY.ACCOUNT_STATUS为1的货币才放在JHF_CASHBALANCE中，本期只有JPY的ACCOUNT_STATUS为1。

        JhfCashBalance balance = (JhfCashBalance) m_cashflowDao.get(JhfCashBalance.class, id);

        if (balance != null) {
            throw new AdminServiceException(AdminServiceException.DUPLICATE_RECORD_ERROR,
                    "[AccountUpdateService.storeCashBalance()] customer already exist!");
        }

        balance = new JhfCashBalance(); // 数据库操作的balance bean

        balance.setId(id);

        /** 新开户用户默认的金额全部为零 */
        balance.setPrePreBalance(BigDecimal.ZERO);
        balance.setPreviousBalance(BigDecimal.ZERO);
        balance.setCashBalance(BigDecimal.ZERO);

        balance.setInputStaffId(customer.getUpdateStaffId()); // 操作员(新增)
        balance.setUpdateStaffId(customer.getUpdateStaffId()); // 操作员(更新)

        // 保存calanceBalance信息到db
        m_cashflowDao.createCashBalance(balance);
    }

    /**
     * 保存用户详细信息到JHF_CUSTOMER_LOCK表
     * 
     * @param customer
     * @throws AdminServiceException
     * @throws DaoException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void storeJhfCustomerLock(CustomerInfo customer) throws AdminServiceException, DaoException {

        JhfCustomerLock lock = (JhfCustomerLock) m_customerDao.get(JhfCustomerLock.class, customer.getCustomerId());

        if (lock != null) {
            throw new AdminServiceException(AdminServiceException.DUPLICATE_RECORD_ERROR,
                    "[AccountUpdateService.openAccount()] customer already exist!");
        }

        lock = new JhfCustomerLock(); // 数据库操作bean
        OgnlUtil.copy(customer, lock, null);

        // 保存用户状态信息到JHF_CUSTOMER_STATUS表
        m_customerDao.getSession().save(lock);
    }

    /**
     * 保存用户详细信息到JhfOrderNoGenerator表
     * 
     * @param customer
     * @throws AdminServiceException
     * @throws DaoException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void storeJhfOrderNoGenerator(CustomerInfo customer) throws AdminServiceException, DaoException {

        JhfOrderNoGenerator jhfOrderNoGenerator = (JhfOrderNoGenerator) m_customerDao.get(JhfOrderNoGenerator.class,
                customer.getCustomerId());

        if (jhfOrderNoGenerator != null) {
            throw new AdminServiceException(AdminServiceException.DUPLICATE_RECORD_ERROR,
                    "[AccountUpdateService.storeJhfOrderNoGenerator()] jhfOrderNoGenerator already exist!");
        }

        jhfOrderNoGenerator = new JhfOrderNoGenerator(); // 数据库操作bean
        OgnlUtil.copy(customer, jhfOrderNoGenerator, null);

        m_customerDao.save(jhfOrderNoGenerator);
    }

    /**
     * 执行用户信息更新(带同步锁)
     * 
     * @param customer
     * @param request
     * @throws AdminServiceException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private synchronized void synchronizedUpdateAccount(CustomerInfo customer,
            List<CustomerDocumentBean> toChangeDocBeanList, HttpServletRequest request) throws AdminServiceException {

        executeUpdateAccount(customer, toChangeDocBeanList, request);
    }

    /**
     * 更新用户信息
     * 
     * @param customer
     * @throws AdminServiceException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public void updateAccount(CustomerInfo customer, List<CustomerDocumentBean> toChangeDocBeanList,
            HttpServletRequest request) throws AdminServiceException {

        LOGGER.info("AccountUpdateService.updateAccount start : \r\n" + customer);

        int accountOpenStatus = customer.getAccountOpenStatus().intValue();
        String loginId = customer.getLoginId();

        if (StringUtils.isBlank(loginId)
                && accountOpenStatus == AccountOpenStatusEnum.ACCOUNT_CONFIRM_ACCEPT_ENUM.getValue()) {
            synchronizedUpdateAccount(customer, toChangeDocBeanList, request);
        } else {
            executeUpdateAccount(customer, toChangeDocBeanList, request);
        }

        updateCti(customer);

        if (customer.isNew()) {
            sendMail(customer);
        }

        customer.organizeShowCustomerInfo();

        LOGGER.info("AccountUpdateService.updateAccount end : \r\n" + customer);
    }

    /**
     * 执行用户信息更新
     * 
     * @param customer
     * @throws AdminServiceException
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void executeUpdateAccount(CustomerInfo customer, List<CustomerDocumentBean> toChangeDocBeanList,
            HttpServletRequest request) throws AdminServiceException {

        boolean isNew = false;

        try {

            DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);
            DbSessionFactory.beginTransaction(DbSessionFactory.INFO);

            CustomerInfo oldCustomer = ServiceFactory.getAccountService().obtainCustomerInfo(customer.getCustomerId());
            if (oldCustomer == null) {
                throw new AdminServiceException(AdminServiceException.NOT_EXIST_ERROR,
                        "[AccountUpdateService.executeUpdateAccount()] oldCustomer isn't exist!");
            }

            long count = new AccountService().countCustomerByMailAddress(customer.getEmailPc(), customer
                    .getCustomerId());

            if (count > 0) {
                throw new AdminServiceException(AdminServiceException.DUPLICATE_RECORD_ERROR,
                        "executeUpdateAccount duplicate emailPc error");
            }

            organizeCustomerInfo(customer);

            /** 1.修改保存用户基本信息到JHF_CUSTOMER表 */
            updateCustomer(customer);

            /** 2.修改保存用户详细信息到JHF_CUSTOMER_STATUS表。 */
            updateCustomerStatus(customer);

            isNew = customer.isNew();

            if (isNew) {
                storeJhfCustomerLock(customer);
            }

            /** 3.修改保存用户邮件信息 */
            updateCustomerMailAddress(customer);

            /** 4.修改保存用户详细信息到JHF_CUST_BANK_ACCOUNT表 */
            updateCustBankAccount(customer);

            if (isNew) {

                /** 5.保存邮件模板绑定信息 */
                storeMailActionMap(customer);

                /** 6.保存levagegroup信息 */
                storeLeverageGroup(customer);

                /** 7.保存cash_balance信息 */
                storeCashBalance(customer);

                /** 8.保存JhfOrderNoGenerator信息 */
                storeJhfOrderNoGenerator(customer);
            }

            /** 9.更新顧客書類信息到JHF_CUSTOMER_DOCUMENT表 */
            updateCustomerDocument(request, customer, toChangeDocBeanList);

            /** 10.组织并保存JhfCustomerInfoChangeLog bean */
            saveJhfCustomerInfoChangeLog(customer, oldCustomer);

            DbSessionFactory.commitTransaction(DbSessionFactory.INFO);
            DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
        } catch (AdminServiceException e) {
            DbSessionFactory.rollbackTransaction(DbSessionFactory.INFO);
            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
            LOGGER.error("[AccountUpdateService.executeUpdateAccount()] error!", e);
            throw e;
        } catch (Exception e) {
            DbSessionFactory.rollbackTransaction(DbSessionFactory.INFO);
            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
            LOGGER.error("[AccountUpdateService.executeUpdateAccount()] error!", e);
            throw new AdminServiceException(AdminServiceException.DATABASE_ERROR,
                    "executeUpdateAccount Database error", e);
        }

        try {
            AdminControl.clearDaoCache(JhfLeverageGroup.class);
        } catch (Exception e) {
            throw new AdminServiceException(AdminServiceException.CLEARDAOCACHE_ERROR,
                    "executeUpdateAccount clearDaoCache error", e);
        }

        if (!StringUtils.isBlank(customer.getLoginId())) {
            try {
                LockHelper.updateFormula(customer.getCustomerId());
            } catch (DaoException de) {
                throw new AdminServiceException(AdminServiceException.DATABASE_ERROR, "executeUpdateAccount error: ",
                        de);
            } catch (Exception ee) {
                throw new AdminServiceException(AdminServiceException.DATABASE_ERROR, "executeUpdateAccount error: ",
                        ee);
            }
        }
    }

    /**
     * 更新用户基本信息到JHF_CUSTOMER表
     * 
     * @param customer
     * @throws AdminServiceException
     * @throws CoreException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void updateCustomer(CustomerInfo customer) throws AdminServiceException, CoreException {

        try {
            JhfCustomer jhfCustomer = obtJhfCustomer(customer, LockMode.UPGRADE);

            if (jhfCustomer == null) {
                throw new AdminServiceException(AdminServiceException.NOT_EXIST_ERROR,
                        "[AccountUpdateService.updateCustomer()] customer isn't exist!");
            }

            BigDecimal orgAccountOpenStatus = jhfCustomer.getAccountOpenStatus();
            BigDecimal destAccountOpenStatus = customer.getAccountOpenStatus();

            if (!AccountOpenStatusChangeBean.isAllow(orgAccountOpenStatus, destAccountOpenStatus)) {
                throw new AdminServiceException(AdminServiceException.NOT_ALLOW,
                        "[AccountUpdateService.updateCustomer()] accountOpenStatus change not allow!");
            }

            if (!orgAccountOpenStatus.equals(destAccountOpenStatus)) {
                customer.setAccountStatusChangeDatetime(customer.getDateTime());
                customer.setAccountStatusChangeDate(DateHelper.formatDate(customer.getDateTime()));

                if (AccountOpenStatusChangeEnum.ACCOUNT_APPLY_WEB_ENUM.getValue() == destAccountOpenStatus.intValue()) {
                    customer.setDocumentAcceptDate(ServiceFactory.getCoreService().getFrontDate());
                } else if (AccountOpenStatusEnum.ACCOUNT_BREAK_ENUM.getValue() == destAccountOpenStatus.intValue()) {
                    customer.setAccountCancelDate(DateHelper.formatDate(customer.getDateTime()));
                }
            }

            if (customer.getLastName() == null) {
                customer.setLastName(jhfCustomer.getLastName());
            }

            if (customer.getLastNameKana() == null) {
                customer.setLastNameKana(jhfCustomer.getLastNameKana());
            }

            OgnlUtil.copy(customer, jhfCustomer, null);

            m_customerDao.update(jhfCustomer);

        } catch (DaoException e) {
            LOGGER.error("[AccountUpdateService.updateCustomer()] error!", e);
            throw new AdminServiceException(AdminServiceException.DATABASE_ERROR, "updateCustomer Database error", e);
        }
    }

    /**
     * 修改JhfCustomerStatus
     * 
     * @param customer
     * @throws DaoException
     * @throws UnsupportedEncodingException
     * @throws AdminServiceException
     * @throws CoreException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void updateCustomerStatus(CustomerInfo customer) throws DaoException, UnsupportedEncodingException,
            AdminServiceException, CoreException {

        int accountOpenStatus = customer.getAccountOpenStatus().intValue();
        int accountActiveStatus;

        if (accountOpenStatus == AccountOpenStatusEnum.ACCOUNT_OPEN_ENUM.getValue()
                || accountOpenStatus == AccountOpenStatusEnum.HAVE_EXECUTION_ENUM.getValue()) {
            accountActiveStatus = AccountActiveStatusEnum.NORMAL_ENUM.getValue();
            customer.setAccountActiveStatus(new BigDecimal(accountActiveStatus));
        } else {
            accountActiveStatus = AccountActiveStatusEnum.INEFFICACY_ENUM.getValue();
            customer.setAccountActiveStatus(new BigDecimal(accountActiveStatus));
            customer.setAccountStatus(null);
        }

        String futureGroupId = customer.getFutureGroupId();
        JhfGroup jhfGroup = null;

        if (futureGroupId != null) {
            jhfGroup = (JhfGroup) m_customerDao.get(JhfGroup.class, futureGroupId, LockMode.UPGRADE);

            if (jhfGroup == null) {
                throw new AdminServiceException(AdminServiceException.NOT_EXIST_ERROR,
                        "updateCustomerStatus futureGroup not exist error");
            }
        }

        JhfCustomerStatus status = null;

        status = m_customerDao.obtainCustomerStatus(customer.getCustomerId());

        if (status == null) {
            if (accountOpenStatus != AccountOpenStatusEnum.BEFORE_APPLY_ENUM.getValue()
                    && accountOpenStatus != AccountOpenStatusEnum.ACCOUNT_DOCUMENT_WAIT_ENUM.getValue()
                    && accountOpenStatus != AccountOpenStatusEnum.ACCOUNT_APPLY_WEB_ENUM.getValue()
                    && accountOpenStatus != AccountOpenStatusEnum.DATA_IMPERFECTION_ENUM.getValue()
                    && accountOpenStatus != AccountOpenStatusEnum.IMPERFECTION_CONFIRMING_ENUM.getValue()
                    && accountOpenStatus != AccountOpenStatusEnum.REJECT_ENUM.getValue()
                    && accountOpenStatus != AccountOpenStatusEnum.ACCOUNT_CONFIRM_ACCEPT_ENUM.getValue()
                    && accountOpenStatus != AccountOpenStatusEnum.AUTO_CANCEL_ENUM.getValue()
                    && accountOpenStatus != AccountOpenStatusEnum.CANCEL_APPLY_ENUM.getValue()) {
                throw new AdminServiceException(AdminServiceException.NOT_EXIST_ERROR,
                        "[AccountUpdateService.updateCustomerStatus()] customer isn't exist!");
            }

            if (accountOpenStatus == AccountOpenStatusEnum.ACCOUNT_CONFIRM_ACCEPT_ENUM.getValue()) {
                storeCustomerStatus(customer);
            }

            return;
        }

        if (futureGroupId != null) {

            // 当前用户使用的货币对不在新的组中，不允许换组
            boolean haveCurrencyPair = AccountUpdateService.getInstance().checkCurrencyPairForChangeGroup(
                    status.getGroupId(), futureGroupId);

            if (!haveCurrencyPair) {
                throw new AdminServiceException(AdminServiceException.NOT_ALLOW,
                        "updateCustomerStatus currencyPair not in futureGroup error");
            }

            this.changeGroup(status, jhfGroup);
        }

        if (accountOpenStatus == AccountOpenStatusChangeEnum.CANCEL_APPLY_ENUM.getValue()) {
            throw new AdminServiceException(AdminServiceException.NOT_ALLOW,
                    "[AccountUpdateService.updateCustomerStatus()] not allow cancel!");
        }

        if (AccountOpenStatusEnum.ACCOUNT_BREAK_ENUM.getValue() == customer.getAccountOpenStatus().intValue()) {

            // chexk amount_no_settled > 0
            ActionMessages errors = terminationCheckNoSettled(customer);
            if (errors != null && !errors.isEmpty()) {
                throw new AdminServiceException(AdminServiceException.NOT_ALLOW,
                        "[AccountUpdateService.updateCustomerStatus()] accountOpenStatus change not allow!");
            }

            BigDecimal disable = new BigDecimal(BoolEnum.BOOL_YES_ENUM.getValue());
            BigDecimal disableAll = new BigDecimal(ConstraintTypeEnum.TYPE_DISABLE_ALL_ENUM.getValue());

            customer.setWithdrawalConstraint(disable);
            customer.setLoginConstraint(disable);
            customer.setOpenBuyConstraint(disableAll);
            customer.setOpenSellConstraint(disableAll);
            customer.setCloseBuyConstraint(disableAll);
            customer.setCloseSellConstraint(disableAll);
            customer.setStraddleOptionFlag(disable);
        }

        if (status.getAccountStatus() == null
                && AccountActiveStatusEnum.INEFFICACY_ENUM.getValue() != accountActiveStatus) {
            customer.setAccountStatus(new BigDecimal(AccountStatusEnum.ACCOUNT_NORMAL.getValue()));
        }

        customer.setGroupId(status.getGroupId());

        String frontDate = DateHelper.formatDate(customer.getDateTime());

        if (!customer.getAccountActiveStatus().equals(status.getAccountActiveStatus())) {
            customer.setAccountActiveStatusDate(frontDate);
        }

        OgnlUtil.copy(customer, status, null);

        String newPassword = customer.getNewPassword();

        if (!StringUtils.isBlank(newPassword)) {
            status.setOldLoginPassword(status.getLoginPassword());

            status.setLoginPassword(AccountService.encodePassword(newPassword));

            status.setPasswordUpdateDate(frontDate);
            customer.setPasswordUpdateDate(frontDate);
        }
        m_customerDao.updateCustomerStatus(status);
    }

    /**
     * 更新用户邮件信息
     * 
     * @param customer
     * @throws AdminServiceException
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void updateCustomerMailAddress(CustomerInfo customer) throws AdminServiceException {

        boolean delete = false;

        BigDecimal pcStopMailFlag = customer.getPcStopMailFlag();
        BigDecimal pcReserveStopMailFlag = customer.getPcReserveStopMailFlag();
        BigDecimal mobileStopMailFlag = customer.getMobileStopMailFlag();

        try {
            // 更新主邮件地址
            String emailPc = customer.getEmailPc();

            if (emailPc != null) {
                JhfMailAddress jhfMailAddress = updateJhfMailAddress(customer, MailPriorityEnum.PRIMARY.getValue(),
                        MailAddressTypeEnum.ADDR_PC_MAIL_ENUM, emailPc);

                if (!pcStopMailFlag.equals(jhfMailAddress.getStopMailFlag())) {
                    jhfMailAddress.setStopMailFlag(pcStopMailFlag);
                    if (pcStopMailFlag.equals(new BigDecimal(StopMailFlagEnum.YES_ENUM.getValue()))) {
                        jhfMailAddress.setCurrentErrorCount(BigDecimal.ZERO);
                    }
                }
                if (delete) {
                    m_mailDao.delete(jhfMailAddress);
                } else {
                    m_mailDao.updateMailAddress(jhfMailAddress);
                }
            }

            // 更新从邮件地址
            String emailPcSecondary = customer.getEmailPcSecondary();
            JhfMailAddress jhfMailAddressAdd = updateJhfMailAddress(customer, MailPriorityEnum.SECONDARY.getValue(),
                    MailAddressTypeEnum.ADDR_PC_MAIL_ENUM, emailPcSecondary);

            if (jhfMailAddressAdd != null) {
                if (delete) {

                    if (jhfMailAddressAdd != null) {
                        m_mailDao.delete(jhfMailAddressAdd);
                    }
                } else {
                    if (!pcReserveStopMailFlag.equals(jhfMailAddressAdd)) {
                        jhfMailAddressAdd.setStopMailFlag(pcReserveStopMailFlag);
                        if (pcReserveStopMailFlag.equals(new BigDecimal(StopMailFlagEnum.YES_ENUM.getValue()))) {
                            jhfMailAddressAdd.setCurrentErrorCount(BigDecimal.ZERO);
                        }
                        m_mailDao.updateMailAddress(jhfMailAddressAdd);
                    }
                }
            }

            // 更新mobile邮件地址
            String emailMobile = customer.getEmailMobile();

            JhfMailAddress jhfMailAddressMobile = updateJhfMailAddress(customer, MailPriorityEnum.PRIMARY.getValue(),
                    MailAddressTypeEnum.ADDR_MOBILE_MAIL_ENUM, emailMobile);

            if (!mobileStopMailFlag.equals(jhfMailAddressMobile.getStopMailFlag())) {
                jhfMailAddressMobile.setStopMailFlag(mobileStopMailFlag);
                if (mobileStopMailFlag.equals(new BigDecimal(StopMailFlagEnum.YES_ENUM.getValue()))) {
                    jhfMailAddressMobile.setCurrentErrorCount(BigDecimal.ZERO);
                }
            }

            if (delete) {
                m_mailDao.delete(jhfMailAddressMobile);
            } else {
                m_mailDao.updateMailAddress(jhfMailAddressMobile);
            }

        } catch (DaoException e) {
            LOGGER.error("[AccountUpdateService.updateCustomerMailAddress()]error!", e);
            throw new AdminServiceException(AdminServiceException.DATABASE_ERROR,
                    "updateCustomerMailAddress Database error", e);
        }
    }

    /**
     * 根据邮件的主次和类型不同分别生成对应的JhfMailAddress实例
     * 
     * @param customer
     * @param priority
     * @param mail
     * @return JhfMailAddress
     * @throws AdminServiceException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private JhfMailAddress updateJhfMailAddress(CustomerInfo customer, int priority,
            MailAddressTypeEnum mailAddressType, String mail) throws AdminServiceException {

        JhfMailAddress mailAddress = obtainJhfMailAddress(customer.getCustomerId(), priority, mailAddressType);

        if (mailAddress == null) {
            throw new AdminServiceException(AdminServiceException.NOT_EXIST_ERROR,
                    "[AccountUpdateService.updateJhfMailAddress()] customer isn't exist!");
        }

        return buildJhfMailAddress(customer, priority, mailAddressType, mail, mailAddress);
    }

    /**
     * 根据邮件的主次和类型不同获取对应的JhfMailAddress实例
     * 
     * @param customerId
     * @param priority
     * @param mailAddressType
     * @return JhfMailAddress
     * @throws AdminServiceException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public JhfMailAddress obtainJhfMailAddress(String customerId, int priority, MailAddressTypeEnum mailAddressType)
            throws AdminServiceException {

        JhfMailAddress mailAddress = null;

        try {
            DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);

            JhfMailAddressId id = new JhfMailAddressId();
            id.setCustomerId(customerId); // 用户id
            id.setMailAddressPriority(new BigDecimal(priority)); // 邮件优先级
            id.setMailAddressType(new BigDecimal(mailAddressType.getValue())); // 邮件类型,pc,mobile

            mailAddress = (JhfMailAddress) m_mailDao.get(JhfMailAddress.class, id);

            DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
        } catch (AdminServiceException e) {
            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
            LOGGER.error("[AccountUpdateService.obtainJhfMailAddress()] error!", e);
            throw e;
        } catch (Exception e) {
            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
            LOGGER.error("[AccountUpdateService.obtainJhfMailAddress()] error!", e);
            throw new AdminServiceException(AdminServiceException.DATABASE_ERROR,
                    "obtainJhfMailAddress Database error", e);
        }

        return mailAddress;
    }

    /**
     * 更新用户详细信息到JHF_CUST_BANK_ACCOUNT表
     * 
     * @param customer
     * @throws DaoException
     * @throws AdminServiceException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void updateCustBankAccount(CustomerInfo customer) throws DaoException, AdminServiceException {

        JhfCustBankAccountId id = new JhfCustBankAccountId();
        id.setCustomerId(customer.getCustomerId());
        id.setCurrencyCode(CASHFLOW_CURRENCY);
        id.setWithdrawalFlag(new BigDecimal(CustBankAccountWithdrawalFlagEnum.BOTH_ENUM.getValue()));

        JhfCustBankAccount custBankAccount = (JhfCustBankAccount) m_customerDao.get(JhfCustBankAccount.class, id); // 数据库操作bean

        if (custBankAccount == null) {
            throw new AdminServiceException(AdminServiceException.NOT_EXIST_ERROR,
                    "[AccountUpdateService.updateCustBankAccount()] customer isn't exist!");
        }
        if (customer.getFinancialInstitutionCode() == null) {
            LOGGER.info("this is oldData for CustBankAccount return");
            return;
        }

        JhfBankBranchId jhfBankBranchId = new JhfBankBranchId();
        OgnlUtil.copy(customer, jhfBankBranchId, null);

        JhfBankBranch jhfBankBranch = (JhfBankBranch) m_customerDao.get(JhfBankBranch.class, jhfBankBranchId);

        if (jhfBankBranch == null) {
            throw new AdminServiceException(AdminServiceException.PARAM_ERROR,
                    "updateCustBankAccount error: jhfBankBranch is null! financialInstitutionCode="
                            + customer.getFinancialInstitutionCode() + ", branchCode=" + customer.getBranchCode());
        }

        customer.setFinancialInstitutionName(jhfBankBranch.getFinancialInstitutionName());
        customer.setFinInstitutionNameKana(jhfBankBranch.getFinInstitutionNameKana());
        customer.setBranchName(jhfBankBranch.getBranchName());
        customer.setBranchNameKana(jhfBankBranch.getBranchNameKana());

        OgnlUtil.copy(customer, custBankAccount, null);

        m_customerDao.update(custBankAccount);
    }

    /**
     * 更新顧客書類信息到JHF_CUSTOMER_DOCUMENT表
     * 
     * @param customer
     *            CustomerInfo Bean
     * @param toChangeDocBeanList
     *            存有要修改信息的CustomerDocumentBean列表
     * @throws AdminServiceException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void updateCustomerDocument(HttpServletRequest request, CustomerInfo customer,
            List<CustomerDocumentBean> toChangeDocBeanList) throws Exception {

        if (toChangeDocBeanList == null || toChangeDocBeanList.isEmpty()) {
            return;
        }

        String customerId = customer.getCustomerId();

        JhfCustomerDocument[] docArr = null;

        try {
            docArr = accountService.obtainJhfCustomerDocumentArr(customerId);

            for (CustomerDocumentBean docBean : toChangeDocBeanList) {
                if (docBean == null || !docBean.isChanged()) {
                    throw new Exception(Exception.PARAM_ERROR,
                            "[AccountUpdateService.updateCustomerDocument()] toChangeDocBeanList error!");
                }

                int no;

                try {
                    no = Integer.parseInt(docBean.getCustomerDocumentNo());
                } catch (NumberFormatException e) {
                    throw new Exception(Exception.PARAM_ERROR,
                            "[AccountUpdateService.updateCustomerDocument()] toChangeDocBeanList error no: "
                                    + docBean.getCustomerDocumentNo());
                }

                if (no < 1 || no > AccountService.CUSTOMER_DOCUMENT_NO_MAX) {
                    throw new Exception(Exception.PARAM_ERROR,
                            "[AccountUpdateService.updateCustomerDocument()] toChangeDocBeanList error no: " + no);
                }

                boolean toDelete = docBean.getDocumentName() == null || docBean.getDocumentFilename() == null
                        || docBean.getDocumentContent() == null;

                JhfCustomerDocument jhfCustomerDocument = docArr[no - 1];

                boolean newDoc = jhfCustomerDocument == null;

                String oldPathname = null;

                if (newDoc) {

                    if (toDelete) {
                        continue;
                    }

                    jhfCustomerDocument = new JhfCustomerDocument();

                    jhfCustomerDocument.setCustomerId(customerId);

                    String documentId = null;

                    if (CorporationTypeEnum.LEGAL_ENUM.getValue() == customer.getCorporationType().intValue()) {
                        documentId = "C" + IdGenerateFacade.getDocumentIdCudC();
                    } else {
                        documentId = "I" + IdGenerateFacade.getDocumentIdCudI();
                    }

                    jhfCustomerDocument.setDocumentId(documentId);

                } else {
                    oldPathname = DocumentFileUtil.getServerDocumentPathname(request, jhfCustomerDocument
                            .getDocumentId(), jhfCustomerDocument.getDocumentFilename());
                }

                if (toDelete) {
                    m_customerDao.delete(jhfCustomerDocument);
                } else {

                    docBean.setDocumentId(jhfCustomerDocument.getDocumentId());

                    OgnlUtil.copy(docBean, jhfCustomerDocument, null);

                    jhfCustomerDocument.setDocumentAcceptDate(ServiceFactory.getCoreService().getFrontDate());
                    jhfCustomerDocument.setDocumentAcceptDatetime(customer.getDateTime());

                    jhfCustomerDocument.setDocumentCheckDate(null);
                    jhfCustomerDocument.setDocumentCheckDatetime(null);

                    jhfCustomerDocument.setUpdateStaffId(customer.getUpdateStaffId());

                    if (jhfCustomerDocument.getInputStaffId() == null) {
                        jhfCustomerDocument.setInputStaffId(customer.getUpdateStaffId());
                    }

                    if (newDoc) {
                        m_customerDao.save(jhfCustomerDocument);
                    } else {
                        m_customerDao.update(jhfCustomerDocument);
                    }
                }

                // 保存文件
                if (oldPathname != null) {
                    FileUtil.deleteFile(oldPathname);
                }

                if (!toDelete) {
                    String newPathname = DocumentFileUtil.getServerDocumentPathname(request, jhfCustomerDocument
                            .getDocumentId(), jhfCustomerDocument.getDocumentFilename());

                    FileUtil.saveFile(newPathname, docBean.getDocumentContent());
                }

            }
        } catch (ServiceException e) {
            throw new Exception(Exception.DATABASE_ERROR,
                    "updateCustomerDocument Database error", e);
        } catch (HibernateException e) {
            throw new Exception(Exception.DATABASE_ERROR,
                    "updateCustomerDocument Database error", e);
        } catch (DaoException e) {
            throw new Exception(Exception.DATABASE_ERROR,
                    "updateCustomerDocument Database error", e);
        } catch (Exception e) {
            throw new Exception(Exception.IO_ERROR, "updateCustomerDocument io error", e);
        }

    }

    /**
     * 获取默认的用户分组(系统目前全部为defaultGroup
     * 
     * @return
     * @throws Exception
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private String getDefaultGroupId() throws Exception {

        String groupId = null; // group,默认全部是defaultGroup从appProperty中取

        try {
            groupId = accountService.getDefaultGroupId();
        } catch (AccountException e) {
            throw new Exception(Exception.DATABASE_ERROR, "getDefaultGroupId Database error", e);
        }
        return groupId;
    }

    /**
     * 获取系统操作员Id
     * 
     * @return String
     * @throws Exception
     * 
     * @author mengfj <mengfj@bestwiz.cn>
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private String getSystemStaffId() throws Exception {

        LOGGER.info("AccountUpdateService.getSystemStaffId() start.");

        String staffId = null;

        try {
            staffId = accountService.getSystemStaffId();
        } catch (AccountException e) {
            throw new Exception(Exception.DATABASE_ERROR, "getSystemStaffId Database error", e);
        } catch (Exception e) {
            throw new Exception(Exception.OTHER_ERROR, "getSystemStaffId other error", e);
        }

        LOGGER.info("AccountUpdateService.getSystemStaffId() end.");

        return staffId;
    }

    /**
     * 检查是否允许解约
     * 
     * @param bean
     *            CustomerInfo
     * @return ActionErrors
     * @throws ServiceException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public ActionErrors terminationCheckNoSettled(CustomerInfo bean) throws Exception {

        ActionErrors errors = new ActionErrors();
        CustomerAccountStatusDetailDao dao = new CustomerAccountStatusDetailDao();

        try {
            DbSessionFactory.beginTransaction(DbSessionFactory.MAIN);

            if (!dao.terminationCheckSellted(bean.getCustomerId())) {
                errors.add("termination", new ActionMessage("errors.terminationWithSettled"));
            }

            if (!dao.terminationCheckCash(bean.getCustomerId())) {
                errors.add("termination", new ActionMessage("errors.terminationWithCash"));
            }

            DbSessionFactory.commitTransaction(DbSessionFactory.MAIN);
        } catch (DataBaseConnectionException e) {
            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.DATABASE_CONNECT_ERROR,
                    "ternimationCheckNoSettled Database connection error", e);
        } catch (DaoException e) {
            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.DATABASE_ERROR,
                    "ternimationCheckNoSettled Database error", e);
        } catch (Exception e) {
            DbSessionFactory.rollbackTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.OTHER_ERROR, "ternimationCheckNoSettled other error",
                    e);
        }

        return errors;
    }

    /**
     * 向顾客发送邮件
     * 
     * @param customer
     * @throws Exception
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void sendMail(CustomerInfo customer) throws Exception {

        String mailActionId = MailActionIdEnum.APPLICATION_OK_ENUM.getName();
        Hashtable<String, String> map = new Hashtable<String, String>();

        String lastName = customer.getLastName();

        map.put("name", (lastName == null ? "" : lastName) + " " + customer.getFirstName());

        String prefix = "mail." + mailActionId + ".";

        map.put("bank", customer.getBankName());
        map.put("branch", customer.getBankBranchName());
        map.put("accountNumber", customer.getVirtualAccountNo());
        map.put("webAddress", StrutsResources.getMessage(prefix + "webAddress"));

        LOGGER.debug("mail: " + map);

        BizMailService service = new BizMailService();

        try {
            service.sendMailDirect(customer.getCustomerId(), mailActionId, map);
        } catch (Exception e) {
            throw new Exception(Exception.DATABASE_ERROR, "sendMail Database error", e);
        }
    }

    /**
     * 组织CustomerInfo中的一些属性值
     * 
     * @param customer
     *            CustomerInfo
     * @throws Exception
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private void organizeCustomerInfo(CustomerInfo customer) throws Exception {

        // customer,customer_status用到的staffId(社员Id)
        String updateStaffId = customer.getUpdateStaffId();

        // 系统操作员Id
        if (GenericValidator.isBlankOrNull(updateStaffId)) {
            updateStaffId = getSystemStaffId();
            customer.setUpdateStaffId(updateStaffId);
        }

        customer.setDateTime(DateHelper.getTodaysTimestamp());
    }

    /**
     * 生成loginId
     * 
     * @param customer
     * @throws DaoException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private synchronized void generateLoginId(CustomerInfo customer) throws DaoException {

        if (!StringUtils.isBlank(customer.getLoginId())) {
            return;
        }

        StringBuffer loginId = new StringBuffer();

        if (CorporationTypeEnum.LEGAL_ENUM.getValue() == customer.getCorporationType().intValue()) {
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

        LOGGER.info("generateLoginId loginId: " + loginId);
    }

    /**
     * 分配VirtualAccountNo
     * 
     * @param customer
     * @throws DaoException
     * @throws Exception
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private synchronized void assignVirtualAccountNo(CustomerInfo customer) throws DaoException, Exception {

        if (!StringUtils.isBlank(customer.getVirtualAccountNo())) {
            return;
        }

        JhfVirtualAccountNo jhfVirtualAccountNo = m_customerDao.obtainMinUnUsedVirtualAccountNo();

        if (jhfVirtualAccountNo == null) {
            throw new Exception(Exception.NO_VIRTUALACCOUNTNO_ERROR,
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

        LOGGER.info("assignVirtualAccountNo virtualAccountNo: " + virtualAccountNo);
    }

    /**
     * 生成password
     * 
     * @return String
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public static String generatePassword() {

        StringBuffer password = new StringBuffer(8);

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 8; i++) {
            int n = random.nextInt(PasswordCharsBean.length() - 1);
            password.append(PasswordCharsBean.getAt(n));
        }

        LOGGER.debug("generatePassword password: " + password);

        return password + "";
    }

    /**
     * 取得完整的CustomerAccountCashBean
     * 
     * @param customerId
     * @return CustomerAccountCashBean
     * @throws Exception
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public CustomerAccountCashBean obtainCustomerAccountCashBean(CustomerInfo customerInfo)
            throws Exception {

        CustomerAccountCashBean bean = null; // 类传递实体bean

        String customerId = customerInfo.getCustomerId();

        if (customerInfo.getLoginId() == null) {
            return new CustomerAccountCashBean();
        }

        try {
            DbSessionFactory.beginReadOnlyTransaction(DbSessionFactory.MAIN);

            FormulaService formulaService = FormulaFactory.DEFAULT.getFormulaService();
            AccountInfo accountInfo = formulaService.obtainAccountInfo(customerId, true);

            bean = new CustomerAccountCashBean();

            bean.setRemainMarginBalance(convertToBigDecimal(accountInfo.getCrMarginForMarginRate())); // 預託証拠金残高
            bean.setAvailableAmount(convertToBigDecimal(accountInfo.getAvailableAmount())); // 有効証拠金
            bean.setPositionMargin(convertToBigDecimal(accountInfo.getPositionMarginTotal())); // ポジション証拠金
            bean.setContractPl(convertToBigDecimal(accountInfo.getSpotTotal())); // 建玉評価損益
            bean.setContractAvailableAmount(convertToBigDecimal(accountInfo.getContractAvailableAmount())); // 建玉可能額
            bean.setOrderMargin(convertToBigDecimal(accountInfo.getOrderMarginTotal())); // 注文証拠金
            bean.setWithdrawalAvailableAmount(convertToBigDecimal(accountInfo.getWithdrawAvailableAmountInT())); // 出金可能額
            bean.setPureMarginBalance(convertToBigDecimal(accountInfo.getPureMarginBalance())); // 純資産額
            bean.setMarginRatio(convertToBigDecimal(accountInfo.getMarginRate())); // 証拠金維持率

            bean.organizeMarginAccount();

            DbSessionFactory.commitReadOnlyTransaction(DbSessionFactory.MAIN);
        } catch (DataBaseConnectionException e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.DATABASE_CONNECT_ERROR,
                    "obtainCustomerAccountCashBean Database connection error", e);
        } catch (DaoException e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.DATABASE_ERROR,
                    "obtainCustomerAccountCashBean Database error", e);
        } catch (Exception e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.OTHER_ERROR,
                    "obtainCustomerAccountCashBean other error", e);
        }

        return bean;
    }

    /**
     * 把字符串数值转换成BigDecimal
     * 
     * @param str
     *            字符串数值
     * @return BigDecimal str转换成的BigDecimal对象，如果str为空，则返回null
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private BigDecimal convertToBigDecimal(String str) {

        if (StringUtils.isBlank(str)) {
            return null;
        }

        return new BigDecimal(str);
    }

    /**
     * 根据传入的ID获取相应的交易货币 (JhfCurrency)
     * 
     * @param id
     *            货币Code
     * @throws ServiceException
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public JhfCurrency getCurrency(String id) throws ServiceException {

        LOGGER.debug("getCurrency(String Id)");
        JhfCurrency currency = null;

        try {
            DbSessionFactory.beginReadOnlyTransaction(DbSessionFactory.MAIN);
            ConfigDao configDao = DAOFactory.getConfigDao();

            currency = configDao.getCurrency(id);

            DbSessionFactory.commitReadOnlyTransaction(DbSessionFactory.MAIN);
        } catch (Exception e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw e;
        } catch (DataBaseConnectionException e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.DATABASE_CONNECT_ERROR,
                    "getCurrency Database connection error", e);
        } catch (DaoException e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.DATABASE_ERROR, "getCurrency Database error", e);
        } catch (Exception e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.OTHER_ERROR, "getCurrency other error", e);
        }

        return currency;
    }

    /**
     * 获取JhfCustomer对象
     * 
     * @param customer
     *            CustomerInfo对象
     * @param lockMode
     *            锁定模式
     * @return JhfCustomer
     * @throws DaoException
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    private JhfCustomer obtJhfCustomer(CustomerInfo customer, LockMode lockMode) throws DaoException {

        Class clazz;
        if (CorporationTypeEnum.LEGAL_ENUM.getValue() == customer.getCorporationType().intValue()) {
            clazz = JhfArtificial.class;
        } else {
            clazz = JhfPersonal.class;
        }

        JhfCustomer jhfCustomer = (JhfCustomer) m_customerDao.get(clazz, customer.getCustomerId(), lockMode);

        return jhfCustomer;
    }

    /**
     * 用户换组时alert率losscut 率变更
     * 
     * @param status
     * @param group
     * @return
     * 
     * @author luyb <luyb@bestwiz.cn>
     */
    private JhfCustomerStatus changeGroup(JhfCustomerStatus status, JhfGroup group) throws Exception {

        if (status.getLosscutRatio() == null || group.getLosscutRatio() == null || group.getAlertRatio() == null
                || status.getAlertRatio() == null) {
            throw new Exception(Exception.PARAM_ERROR,
                    "[AccountUpdateService.changeGroup()] PARAM is NULL!");
        }
        BigDecimal cLossCut = status.getLosscutRatio();
        BigDecimal gLossCut = group.getLosscutRatio();
        BigDecimal gAlert = group.getAlertRatio();
        BigDecimal cAlert = status.getAlertRatio();
        if (cLossCut.compareTo(gLossCut) >= 0 && cLossCut.compareTo(gAlert) <= 0) {
        } else {
            status.setLosscutRatio(gLossCut);
        }
        if (cAlert.compareTo(gAlert) >= 0) {
        } else {
            status.setAlertRatio(gAlert);
        }
        return status;
    }

    /**
     * 判断顾客当前使用的货币对是否都在新的组中
     * 
     * @param customerId
     *            顾客customerId
     * @param futureGroupId
     *            要换成的组
     * @return boolean 如果顾客当前使用的货币对都在新的组中，则返回true，否认则返回false。
     * @throws Exception
     * 
     * @author yaolin <yaolin@bestwiz.cn>
     */
    public boolean checkCurrencyPairForChangeGroup(String customerId, String futureGroupId)
            throws Exception {

        boolean allow = false;

        try {
            DbSessionFactory.beginReadOnlyTransaction(DbSessionFactory.MAIN);

            allow = m_customerDao.checkCurrencyPairForChangeGroup(customerId, futureGroupId);

            DbSessionFactory.commitReadOnlyTransaction(DbSessionFactory.MAIN);
        } catch (Exception e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw e;
        } catch (DataBaseConnectionException e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.DATABASE_CONNECT_ERROR, "Database connection error",
                    e);
        } catch (DaoException e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.DATABASE_ERROR, "Database error", e);
        } catch (Exception e) {
            DbSessionFactory.rollbackReadOnlyTransaction(DbSessionFactory.MAIN);
            throw new Exception(Exception.OTHER_ERROR, "other error", e);
        }

        return allow;
    }

    /**
     * 将顾客的信息同步的cti
     * 
     * @param customer
     * @author liuxb <liuxb@bestwiz.cn>
     */
    private void updateCti(CustomerInfo customer) {

        CtiService ctiService = new CtiService();

        ctiService.updateUserInfo(customer.getCustomerId());

        List<String> customerIds = new ArrayList<String>(1);
        customerIds.add(customer.getCustomerId());
        ctiService.SyncEmailoptout(customerIds);
    }

    /**
     * 添加JhfCustomerInfoChangeLog信息。 1.创建要保存的JhfCustomerInfoChangeLog bean； 2.根据customer和oldCustomer来比较出金口座,来set
     * oriData和changedDate属性； 3.根据customer set其它所需属性； 4.save bean。
     * 
     * @param customer
     *            [new]
     * @param oldCustomer
     *            [old]
     * @author guoqiang <guoqiang@bestwiz.cn>
     */
    public void saveJhfCustomerInfoChangeLog(CustomerInfo customer, CustomerInfo oldCustomer)
            throws Exception {

        JhfCustomerInfoChangeLog logBean = new JhfCustomerInfoChangeLog();

        String customerId = customer.getCustomerId();
        String reason = customer.getLogMemo1();
        String memo1 = customer.getLogMemo2();
        String memo2 = !GenericValidator.isBlankOrNull(customer.getLogMemo3()) ? customer.getLogMemo3() : null;
        StringBuffer oriData = new StringBuffer(2000);
        StringBuffer changedDate = new StringBuffer(2000);
        String csId = customer.getUpdateStaffId();

        // 金融機関名
        String oldFinancialInstitutionName = oldCustomer.getFinancialInstitutionName();
        String newFinancialInstitutionName = customer.getFinancialInstitutionName();

        // 支店名
        String oldBranchName = oldCustomer.getBranchName();
        String newBranchName = customer.getBranchName();

        // 口座種別
        String oldAaccountClassification = AccountClassificationEnum.getEnum(
                oldCustomer.getAccountClassification().intValue()).getName();
        String newAaccountClassification = AccountClassificationEnum.getEnum(
                customer.getAccountClassification().intValue()).getName();

        // 口座番号
        String oldAccountNumber = oldCustomer.getAccountNumber();
        String newAccountNumber = customer.getAccountNumber();

        // 口座名義名
        String oldAccountNameName = oldCustomer.getAccountNameName();
        String newAccountNameName = customer.getAccountNameName();

        if (!oldFinancialInstitutionName.equals(newFinancialInstitutionName) || !oldBranchName.equals(newBranchName)
                || !oldAaccountClassification.equals(newAaccountClassification)
                || !oldAccountNumber.equals(newAccountNumber) || !oldAccountNameName.equals(newAccountNameName)) {
            oriData.append(oldFinancialInstitutionName + SPACES);
            changedDate.append(newFinancialInstitutionName + SPACES);
            oriData.append(oldBranchName + SPACES);
            changedDate.append(newBranchName + SPACES);
            oriData.append(oldAaccountClassification + SPACES);
            changedDate.append(newAaccountClassification + SPACES);
            oriData.append(oldAccountNumber + SPACES);
            changedDate.append(newAccountNumber + SPACES);
            oriData.append(oldAccountNameName + SPACES);
            changedDate.append(newAccountNameName + SPACES);
        }

        // メールアドレス(PC)
        String oldEmailPc = oldCustomer.getEmailPc();
        String newEmailPc = customer.getEmailPc();
        if (!oldEmailPc.equals(newEmailPc)) {
            oriData.append(oldEmailPc + SPACES);
            changedDate.append(newEmailPc + SPACES);
        }

        // 追加メールアドレス
        String oldEmailPcSecondary = oldCustomer.getEmailPcSecondary();
        String newEmailPcSecondary = customer.getEmailPcSecondary();
        if (!GenericValidator.isBlankOrNull(oldEmailPcSecondary)
                && !GenericValidator.isBlankOrNull(newEmailPcSecondary)) {
            if (!oldEmailPcSecondary.equals(newEmailPcSecondary)) {
                oriData.append(oldEmailPcSecondary + SPACES);
                changedDate.append(newEmailPcSecondary + SPACES);
            }
        } else if ((GenericValidator.isBlankOrNull(oldEmailPcSecondary) && !GenericValidator
                .isBlankOrNull(newEmailPcSecondary))
                || (!GenericValidator.isBlankOrNull(oldEmailPcSecondary) && GenericValidator
                        .isBlankOrNull(newEmailPcSecondary))) {
            oriData.append(GenericValidator.isBlankOrNull(oldEmailPcSecondary) ? SPACES : oldEmailPcSecondary + SPACES);
            changedDate.append(GenericValidator.isBlankOrNull(newEmailPcSecondary) ? SPACES : newEmailPcSecondary
                    + SPACES);
        }

        // 携帯メールアドレス
        String oldEmailMobile = oldCustomer.getEmailMobile();
        String newEmailMobile = customer.getEmailMobile();
        if (!GenericValidator.isBlankOrNull(oldEmailMobile) && !GenericValidator.isBlankOrNull(newEmailMobile)) {
            if (!oldEmailMobile.equals(newEmailMobile)) {
                oriData.append(oldEmailMobile + SPACES);
                changedDate.append(newEmailMobile + SPACES);
            }
        } else if ((GenericValidator.isBlankOrNull(oldEmailMobile) && !GenericValidator.isBlankOrNull(newEmailMobile))
                || (!GenericValidator.isBlankOrNull(oldEmailMobile) && GenericValidator.isBlankOrNull(newEmailMobile))) {
            oriData.append(GenericValidator.isBlankOrNull(oldEmailMobile) ? SPACES : oldEmailMobile + SPACES);
            changedDate.append(GenericValidator.isBlankOrNull(newEmailMobile) ? SPACES : newEmailMobile + SPACES);
        }

        logBean.setCustomerId(customerId);

        logBean.setReason(reason);

        logBean.setMemo1(memo1);

        logBean.setMemo2(memo2);

        String strOriData = String.valueOf(oriData).trim();
        String strChangedDate = String.valueOf(changedDate).trim();

        if (!GenericValidator.isBlankOrNull(strOriData))
            logBean.setOriData(strOriData);
        if (!GenericValidator.isBlankOrNull(strChangedDate))
            logBean.setChangedData(strChangedDate);

        logBean.setCsId(csId);

        logBean.setType(new BigDecimal(CustomerChangeLogTypeEnum.CUSTOMER_CHANGE_LOG_TYPE_ENUM1.getValue()));

        logBean.setUpdateStaffId(csId);

        logBean.setInputStaffId(csId);

        try {
            m_customerDao.save(logBean);
        } catch (DaoException e) {
            LOGGER.error("[AccountUpdateService.setJhfCustomerInfoChangeLog()]error!", e);
            throw new Exception(Exception.DATABASE_ERROR,
                    "setJhfCustomerInfoChangeLog Database error", e);
        }
    }
}
