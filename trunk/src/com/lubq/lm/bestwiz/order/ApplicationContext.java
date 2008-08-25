package com.lubq.lm.bestwiz.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lubq.lm.bestwiz.order.ui.login.DealerView;




/**
 * システムに使うオブジェクトのコンテキストを保存するためのクラス
 * 
 * @author wenyi <jhf@bestwiz.cn>
 * 
 * @copyright 2006, BestWiz(Dalian) Co.,Ltd
 * @version $Id: ApplicationContext.java,v 1.10 2007/04/24 09:15:32 wenyi Exp $
 */
public class ApplicationContext {
    public static final String LOGON_BEAN_NAME = "logon_bean_name";

    private static final String CURRENCY_PAIR_MAP = "CurrencyPairMap";

    private static final String COUNTER_PARTY_MAP = "CounterPartyMap";

    private static final String SPLIT_PARTY_MAP = "SplitPairMap";

    private static final String ALL_DEALER_MAP = "AllDealerMap";

    private static final String OTHER_ALL_DEALER_MAP = "OtherAllDealerMap";

    private static final String CustRateBaseColumn = "CustRateBaseColumn";

    private static final String PLAY_AUDIO = "playAudio";

    private static ApplicationContext m_instance = new ApplicationContext();

    private Map m_attribs = new HashMap();
    /**
     * ApplicationContextを取得する
     * @return
     */
    public static ApplicationContext getContext() {
        return m_instance;
    }

    /**
     * オブジェクト保存
     * 
     * @param sName
     * @param obj
     */
    public void setAttrib(String sName, Object obj) {
        m_attribs.put(sName, obj);
    }

    /**
     * オブジェクト取得
     * 
     * @param sName
     * @return
     */
    public Object getAttrib(String sName) {
        return m_attribs.get(sName);
    }

    /**
     * オブジェクト削除
     * 
     * @param sName
     * @return
     */
    public Object removeAttrib(String sName) {
        return m_attribs.remove(sName);
    }

    /**
     * オーディオファイルの格納
     * 
     * @param map
     */
    public void setPlayAudio(Map map) {
        setAttrib(PLAY_AUDIO, map);
    }

    /**
     * オーディオファイルの洗い出し
     * 
     * @return
     */
    public Map getPlayAudio() {
        return (Map) getAttrib(PLAY_AUDIO);
    }

    /**
     * ユーザ情報の格納
     * 
     * @param bean
     */
    public void setLoginBean(DealerView bean) {
        setAttrib(LOGON_BEAN_NAME, bean);
    }

    /**
     * ユーザ情報の取得
     * 
     * @return
     */
    public DealerView getLoginBean() {
        return (DealerView) getAttrib(LOGON_BEAN_NAME);
    }

    /**
     * 対顧CHART生成及びレート表示の基準カラム、,#-1 bid  #1 ask  #0 midの格納
     * 
     * @param bean
     */
    public void setCustRateBaseColumn(String bean) {
        setAttrib(CustRateBaseColumn, bean);
    }

    /**
     * 対顧CHART生成及びレート表示の基準カラム、#-1 bid  #1 ask  #0 midの取得
     * 
     * @return
     */
    public String getCustRateBaseColumn() {
        return (String) getAttrib(CustRateBaseColumn);
    }

    /**
     * システムで使用する通貨ペア情報の格納
     * 
     * @param map
     */
    public void setCurrencyPairMap(Map map) {
        setAttrib(CURRENCY_PAIR_MAP, map);
    }

    /**
     * システムで使用する通貨ペア情報の取得
     * 
     * @return
     */
    public Map getCurrencyPairMap() {
        return (Map) getAttrib(CURRENCY_PAIR_MAP);
    }

    /**
     * システムで使用するCPの設定
     * 
     * @param map
     */
    public void setCounterPartyMap(Map map) {
        setAttrib(COUNTER_PARTY_MAP, map);
    }

    /**
     * システムで使用するCPの取得
     * 
     * @return
     */
    public Map getCounterPartyMap() {
        return (Map) getAttrib(COUNTER_PARTY_MAP);
    }

    /**
     * Split計算機能－通貨組合せの設定
     * 
     * @param map
     */
    public void setSplitPairMap(Map map) {
        setAttrib(SPLIT_PARTY_MAP, map);
    }

    /**
     * Split計算機能－通貨組合せの取得
     * 
     * @return
     */
    public Map getSplitPairMap() {
        return (Map) getAttrib(SPLIT_PARTY_MAP);
    }

    /**
     * システムでの全てのdealer設定
     * 
     * @param map
     */
    public void setAllDealerMap(Map map) {
        setAttrib(ALL_DEALER_MAP, map);
    }

    /**
     * システムでの全てのdealer(削除されたdealer含め)設定
     * 
     * @param map
     */
    public void setOtherAllDealerMap(Map map) {
        setAttrib(OTHER_ALL_DEALER_MAP, map);
    }

    /**
     * システムでの全てのdealerの取得
     * 
     * @return
     */
    public Map getAllDealerMap() {
        return (Map) getAttrib(ALL_DEALER_MAP);
    }

    /**
     * システムでの、全てのdealer(削除されたdealer含め)の取得
     * 
     * @return
     */
    public Map getOtherAllDealerMap() {
        return (Map) getAttrib(OTHER_ALL_DEALER_MAP);
    }

    /**
     * ユーザが登録したかどうかを判断
     * 
     * @return
     */
    public boolean isLogin() {
//        return getLoginBean() != null;
        return true;
    }
//
//    private List<ICpRateAcceptor> m_cpRateAcceptors = Collections
//            .synchronizedList(new ArrayList<ICpRateAcceptor>());
//
//    /**
//     * CpRateAcceptorsキューに追加
//     * 
//     * @param acceptor
//     */
//    public void addCpRateAcceptors(ICpRateAcceptor acceptor) {
//        m_cpRateAcceptors.add(acceptor);
//    }
//
//    /**
//     * CpRateAcceptorsキューから外す
//     * 
//     * @param acceptor
//     */
//    public void removeCpRateAcceptors(ICpRateAcceptor acceptor) {
//        m_cpRateAcceptors.remove(acceptor);
//    }
//
//    /**
//     * CpRateAcceptorsを取得する
//     * 
//     * @return
//     */
//    public List getCpRateAcceptors() {
//        return m_cpRateAcceptors;
//    }
//
//    private List<IAdminMessageAcceptor> m_adminMessageAcceptors = Collections
//            .synchronizedList(new ArrayList<IAdminMessageAcceptor>());
//
//    /**
//     * adminMessageAcceptorsキューに追加
//     * 
//     * @param acceptor
//     */
//    public void addAdminMessageAcceptors(IAdminMessageAcceptor acceptor) {
//        m_adminMessageAcceptors.add(acceptor);
//    }
//
//    /**
//     * adminMessageAcceptorsキューから外す
//     * 
//     * @param acceptor
//     */
//    public void removeAdminMessageAcceptors(IAdminMessageAcceptor acceptor) {
//        m_adminMessageAcceptors.remove(acceptor);
//    }
//
//    /**
//     * adminMessageAcceptorsを取得する
//     * 
//     * @return
//     */
//    public List getAdminMessageAcceptors() {
//        return m_adminMessageAcceptors;
//    }
//    
//    
//    
//    private List<ICoreRateAcceptor> m_coreRateAcceptors = Collections
//            .synchronizedList(new ArrayList<ICoreRateAcceptor>());
//
//    /**
//     * CoreRateAcceptorsキューに追加
//     * 
//     * @param acceptor
//     */
//    public void addCoreRateAcceptors(ICoreRateAcceptor acceptor) {
//        m_coreRateAcceptors.add(acceptor);
//    }
//
//    /**
//     * CoreRateAcceptorsキューから外す
//     * 
//     * @param acceptor
//     */
//    public void removeCoreRateAcceptors(ICoreRateAcceptor acceptor) {
//        m_coreRateAcceptors.remove(acceptor);
//    }
//
//    /**
//     * CoreRateAcceptorsを取得する
//     * 
//     * @return
//     */
//    public List getCoreRateAcceptors() {
//        return m_coreRateAcceptors;
//    }
//
//    private List<IDealingAcceptor> m_dealingAcceptors = Collections
//            .synchronizedList(new ArrayList<IDealingAcceptor>());
//
//    /**
//     * DealingAcceptorsキューに追加
//     * 
//     * @param acceptor
//     */
//    public void addDealingAcceptors(IDealingAcceptor acceptor) {
//        m_dealingAcceptors.add(acceptor);
//    }
//
//    /**
//     * DealingAcceptorsキューから外す
//     * 
//     * @param acceptor
//     */
//    public void removeDealingAcceptors(IDealingAcceptor acceptor) {
//        m_dealingAcceptors.remove(acceptor);
//    }
//
//    /**
//     * DealingAcceptorsを取得する
//     * 
//     * @return
//     */
//    public List getDealingAcceptors() {
//        return m_dealingAcceptors;
//    }
//
//    private List<ICpStatusAcceptor> m_cpStatusAcceptors = Collections
//            .synchronizedList(new ArrayList<ICpStatusAcceptor>());
//
//    /**
//     * CpStatusAcceptorsキューに追加
//     * 
//     * @param acceptor
//     */
//    public void addCpStatusAcceptors(ICpStatusAcceptor acceptor) {
//        m_cpStatusAcceptors.add(acceptor);
//    }
//
//    /**
//     * CpStatusAcceptorsキューから外す
//     * 
//     * @param acceptor
//     */
//    public void removeCpStatusAcceptors(ICpStatusAcceptor acceptor) {
//        m_cpStatusAcceptors.remove(acceptor);
//    }
//
//    /**
//     * CpStatusAcceptorsを取得する
//     * 
//     * @return
//     */
//    public List getCpStatusAcceptors() {
//        return m_cpStatusAcceptors;
//    }
//
//    private List<IDealerLogAcceptor> m_dealerLogAcceptors = Collections
//            .synchronizedList(new ArrayList<IDealerLogAcceptor>());
//
//    /**
//     * DealerLogAcceptorsキューに追加
//     * 
//     * @param acceptor
//     */
//    public void addDealerLogAcceptors(IDealerLogAcceptor acceptor) {
//        m_dealerLogAcceptors.add(acceptor);
//    }
//
//    /**
//     * DealerLogAcceptorsキューから外す
//     * 
//     * @param acceptor
//     */
//    public void removeDealerLogAcceptors(IDealerLogAcceptor acceptor) {
//        m_dealerLogAcceptors.remove(acceptor);
//    }
//
//    /**
//     * DealerLogAcceptorsを取得する
//     * 
//     * @return
//     */
//    public List getDealerLogAcceptors() {
//        return m_dealerLogAcceptors;
//    }

}
