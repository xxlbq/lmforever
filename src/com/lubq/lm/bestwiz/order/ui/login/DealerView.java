package com.lubq.lm.bestwiz.order.ui.login;

import java.io.Serializable;

/**
 * dealerユーザ情報
 * 
 * @author wenyi <jhf@bestwiz.cn>
 * 
 * @copyright 2006, BestWiz(Dalian) Co.,Ltd
 * @version $Id: DealerView.java,v 1.4 2007/04/14 07:56:38 liyan Exp $
 */
public class DealerView implements Serializable {

    private static final long serialVersionUID = -4691303956126988680L;

    // dealer code
    private String dealerCode;

    // ログイン制限（#0:不可 #1:可能）
    private int loginConstraint;

    // dealer name
    private String loginId;

    // password
    private String loginPassword;

    // #0:普通ユーザ #1:管理者
    private int dealerRole;

    // error Message
    private String errorMessage;

    // error Code
    private int errorCode = 0;

    /**
     * dealerCodeを取得する
     * 
     * @return
     */
    public String getDealerCode() {
        return dealerCode;
    }

    /**
     * dealerCodeを設定する
     * 
     * @param dealerCode
     */
    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    /**
     * dealerRoleを取得する
     * 
     * @return
     */
    public int getDealerRole() {
        return dealerRole;
    }

    /**
     * dealerRoleを設定する
     * 
     * @param dealerRole
     */
    public void setDealerRole(int dealerRole) {
        this.dealerRole = dealerRole;
    }

    /**
     * loginConstraintを取得する
     * 
     * @return
     */
    public int getLoginConstraint() {
        return loginConstraint;
    }

    /**
     * loginConstraintを設定する
     * 
     * @param loginConstraint
     */
    public void setLoginConstraint(int loginConstraint) {
        this.loginConstraint = loginConstraint;
    }

    /**
     * loginIdを取得する
     * 
     * @return
     */
    public String getLoginId() {
        return loginId;
    }

    /**
     * loginIdを設定する
     * 
     * @param loginId
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    /**
     * loginPasswordを取得する
     * 
     * @return
     */
    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * loginPasswordを設定する
     * 
     * @param loginPassword
     */
    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    /**
     * errorCodeを取得する
     * 
     * @return
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * errorCodeを設定する
     * 
     * @param errorCode
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * errorMessageを取得する
     * 
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * errorMessageを設定する
     * 
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
