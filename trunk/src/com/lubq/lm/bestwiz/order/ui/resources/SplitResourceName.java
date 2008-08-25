package com.lubq.lm.bestwiz.order.ui.resources;

/**
 * ポジションの分解用（Split計算機能－通貨組合せ式）
 * 
 * @author wenyi <jhf@bestwiz.cn>
 * 
 * @copyright 2006, BestWiz(Dalian) Co.,Ltd
 * @version $Id: SplitResourceName.java,v 1.3 2007/04/17 02:02:09 liyan Exp $
 */
public class SplitResourceName {

    // AUD/USD,USD/JPY,1
    public static final String SPLIT_PAIR_AUD_JPY = "AUD/JPY";

    // NZD/USD,USD/JPY,1
    public static final String SPLIT_PAIR_NZD_JPY = "NZD/JPY";

    // GBP/USD,USD/JPY,1|GBP/JPY,EUR/GBP,1
    public static final String SPLIT_PAIR_GBP_JPY = "GBP/JPY";

    // EUR/USD,USD/JPY,1|EUR/JPY,EUR/GBP,0
    public static final String SPLIT_PAIR_EUR_JPY = "EUR/JPY";

    // USD/JPY,USD/CAD,0
    public static final String SPLIT_PAIR_CAD_JPY = "CAD/JPY";

    // USD/JPY,USD/CHF,0|EUR/JPY,EUR/CHF,0
    public static final String SPLIT_PAIR_CHF_JPY = "CHF/JPY";

    // EUR/JPY,CHF/JPY,0|EUR/USD,USD/CHF,1
    public static final String SPLIT_PAIR_EUR_CHF = "EUR/CHF";

    // EUR/JPY,GBP/JPY,0|EUR/USD,GBP/USD,0
    public static final String SPLIT_PAIR_EUR_GBP = "EUR/GBP";

    // AUD/JPY,USD/JPY,0
    public static final String SPLIT_PAIR_AUD_USD = "AUD/USD";

    // NZD/JPY,USD/JPY,0
    public static final String SPLIT_PAIR_NZD_USD = "NZD/USD";

    // GBP/JPY,USD/JPY,0|EUR/USD,EUR/GBP,0
    public static final String SPLIT_PAIR_GBP_USD = "GBP/USD";

    // EUR/JPY,USD/JPY,0|EUR/GBP,GBP/USD,1
    public static final String SPLIT_PAIR_EUR_USD = "EUR/USD";

    // AUD/JPY,AUD/USD,0|NZD/JPY,NZD/USD,0|GBP/JPY,GBP/USD,0|EUR/JPY,EUR/USD,0|USD/CAD,CAD/JPY,1|USD/CHF,CHF/JPY,1
    public static final String SPLIT_PAIR_USD_JPY = "USD/JPY";

    // USD/JPY,CAD/JPY,0
    public static final String SPLIT_PAIR_USD_CAD = "USD/CAD";

    // USD/JPY,CHF/JPY,0|EUR/CHF,EUR/USD,0
    public static final String SPLIT_PAIR_USD_CHF = "USD/CHF";

    public static final String[] SPLIT_PAIRS = { SPLIT_PAIR_AUD_JPY,
            SPLIT_PAIR_NZD_JPY, SPLIT_PAIR_GBP_JPY, SPLIT_PAIR_EUR_JPY,
            SPLIT_PAIR_CAD_JPY, SPLIT_PAIR_CHF_JPY, SPLIT_PAIR_EUR_CHF,
            SPLIT_PAIR_EUR_GBP, SPLIT_PAIR_AUD_USD, SPLIT_PAIR_NZD_USD,
            SPLIT_PAIR_GBP_USD, SPLIT_PAIR_EUR_USD, SPLIT_PAIR_USD_JPY,
            SPLIT_PAIR_USD_CAD, SPLIT_PAIR_USD_CHF };
}
