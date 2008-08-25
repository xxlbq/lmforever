package com.lubq.lm.bestwiz.order.ui.login;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import cn.bestwiz.jhf.core.dao.bean.main.JhfDealer;

/**
 * ログインバリデーション
 * 
 * @author wenyi <jhf@bestwiz.cn>
 * 
 * @copyright 2006, BestWiz(Dalian) Co.,Ltd
 * @version $Id: LoginCheck.java,v 1.12 2007/04/14 07:56:39 liyan Exp $
 */
public class LoginCheck {

    private DealerView dealerView = new DealerView();

    private static final int lock_limit = 3;

    /**
     * ユーザ入力したloginIdでユーザ情報を取得する
     * 
     * @param loginId
     * @return
     */
    private JhfDealer findDealer(String loginId) {
        JhfDealer dealer = null;
//        try {
//            dealer = ServiceFactory.getLoginService().getJhfDealerByLoginId(
//                    loginId);
//        } catch (ServiceException e) {
//            dealer = null;
//            e.printStackTrace();
//            MailSender.sendMail(e);
//            DealerActivator.logException(e);
//        }

        return dealer;
    }

    /**
     * ユーザにロックされるかどうかを検索する
     * 
     * @param dealer
     * @return
     */
    private boolean checkDealerLock(JhfDealer dealer) {
//        if (dealer.getLoginConstraint().intValue() == LoginConstraintEnum.BOOL_NO_ENUM
//                .getValue()) {
//            return true;
//        } else {
//            return false;
//        }
    	
    	return false;
    }

    /**
     * ユーザ入力したパスワードは正確するかどうかをチェックする
     * 
     * @param dealer
     * @param password
     * @return
     */
    private boolean checkDealerPassword(JhfDealer dealer, String password) {
//        if (DigestController.get(password, DigestController.PTN_SHA).equals(
//                dealer.getLoginPassword())) {
//            return true;
//        } else {
//            return false;
//        }
    	
    	return true;
    }

    /**
     * 最後ログイン時間を更新する
     * 
     * @param dealer
     */
    private void updateLoginTime(JhfDealer dealer) {
//        dealer.setLastLoginTime(DateHelper.getTodaysTimestamp());
//        dealer.setPasswordFailCount(new BigDecimal(0));
//        try {
//            ServiceFactory.getLoginService().updateJhfDealer(dealer);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            MailSender.sendMail(e);
//            DealerActivator.logException(e);
//        }
    }

    /**
     * ログイン失敗回数を更新する
     * 
     * @param dealer
     */
//    private void updateLoginFailCount(JhfDealer dealer) {
//        try {
//            int failConut = dealer.getPasswordFailCount().intValue() + 1;
//            dealer.setPasswordFailCount(new BigDecimal(failConut));
//            setDealerErrorCode(1);
//            if (failConut == lock_limit) {
//                dealer.setLoginConstraint(new BigDecimal(
//                        LoginConstraintEnum.BOOL_NO_ENUM.getValue()));
//                setDealerErrorCode(3);
//            }
//            ServiceFactory.getLoginService().updateJhfDealer(dealer);
//        } catch (ServiceException e) {
//            e.printStackTrace();
////            MailSender.sendMail(e);
////            DealerActivator.logException(e);
//        }
//    }

    /**
     * dealerログインパスワードを更新する
     * 
     * @param dealerCode
     * @param newPassword
     * @return
     */
//    public boolean updateLoginPassword(String dealerCode, String newPassword) {
//        try {
//            ServiceFactory.getLoginService().updateJhfDealerPassword(
//                    dealerCode, newPassword);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            MailSender.sendMail(e);
//            DealerActivator.logException(e);
//            return false;
//        }
//
//        return true;
//    }

    /**
     * 1.ユーザ名或はパスワードが不正 2.カレントアカウントにロックされた
     * 3.ユーザ或はパスワードミスが三回以上になった場合、アカウントがロックされる
     * 
     * @param errorCode
     */
    private void setDealerErrorCode(int errorCode) {
        this.dealerView.setErrorCode(errorCode);
    }

    /**
     * ユーザログインの認証
     * 
     * @param loginId
     * @param password
     * @return
     */
    public DealerView checkLogin(String loginId, String password) {
    	
        JhfDealer dealer = findDealer(loginId);
//        if (dealer == null) {
//            setDealerErrorCode(1);
//            return dealerView;
//        } else {
//            if (!checkDealerLock(dealer)) {
//                if (checkDealerPassword(dealer, password)) {
//                    updateLoginTime(dealer);
                    setDealerView(dealer);
//                    return dealerView;
//                } else {
//                    updateLoginFailCount(dealer);
//
//                    return dealerView;
//                }
//            } else {
//                setDealerErrorCode(2);
//                return dealerView;
//            }
//        }
                    return dealerView;
    }

    /**
     * ユーザパスワード変更の認証
     * 
     * @param loginId
     * @param password
     * @return
     */
    public DealerView checkPasswordChange(String loginId, String password) {
    	
    	return dealerView;
//        JhfDealer dealer = findDealer(loginId);
//        if (dealer == null) {
//            setDealerErrorCode(1);
//            return dealerView;
//        } else {
//            if (!checkDealerLock(dealer)) {
//                if (checkDealerPassword(dealer, password)) {
//                    setDealerView(dealer);
//                    return dealerView;
//                } else {
//                    setDealerErrorCode(1);
//                    return dealerView;
//                }
//            } else {
//                setDealerErrorCode(2);
//                return dealerView;
//            }
//        }
    }

    /**
     * DealerViewを組み合わせる
     * 
     * @param dealer
     */
    private void setDealerView(JhfDealer dealer) {
//        dealerView.setDealerCode(dealer.getDealerCode());
//        dealerView.setDealerRole(dealer.getDealerRole().intValue());
//        dealerView.setLoginConstraint(dealer.getLoginConstraint().intValue());
//        dealerView.setLoginId(dealer.getDealerName());
//        dealerView.setLoginPassword(dealer.getLoginPassword());
    }

    /**
     * ログインのホスト情報を取得する
     * 
     * @return
     */
    public static String getHost() {
        StringBuffer sb = new StringBuffer();
        InetAddress ia;
        try {
            ia = InetAddress.getLocalHost();
            sb.append("Send mail time = ");
//            sb.append(DatetimeUtil.dateToString(new Date(),
//                    "yyyy-MM-dd HH:mm:ss.sss"));
            sb.append(" ");
            sb.append("Host Address = ");
            sb.append(ia.getHostAddress());
            sb.append(" ");
            sb.append("Host Name = ");
            sb.append(ia.getHostName());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
