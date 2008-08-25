package com.lubq.lm.bestwiz.order.ui.cons;



/**
 * システムで使用されたグローバル定数(from GlobalResource)
 * 
 * @author wenyi <jhf@bestwiz.cn>
 * 
 * @copyright 2006, BestWiz(Dalian) Co.,Ltd
 * @version $Id: GlobalConstants.java,v 1.17 2007/04/14 07:56:26 liyan Exp $
 */
public class GlobalConstants {

    // resource path
    public static final String RESOURCE_PATH = GlobalResources.GlobalResource
            .getString(GlobalResourceName.GLOBAL_SYSTEM_RESOURCEPATH);

    // class path
    public static final String CLASS_PATH = GlobalResources.GlobalResource
            .getString(GlobalResourceName.GLOBAL_SYSTEM_CALSSPATH);

    // AUTO （autohedge时book）
    public static final String AUTOHEDGE_BOOK = GlobalResources.GlobalResource
            .getString(GlobalResourceName.GLOBAL_SYSTEM_AUTOHEDGEBOOK);

    // RS （对顾客约定的CP名）
    public static final String CUSTOMER_CP = GlobalResources.GlobalResource
            .getString(GlobalResourceName.GLOBAL_SYSTEM_CUSTOMERCP);

    // EMERGENCY
    public static final String EMERGENCY = GlobalResources.GlobalResource
            .getString(GlobalResourceName.GLOBAL_SYSTEM_EMERGE_STATUS);

    // MANUAL
    public static final String MANUAL = GlobalResources.GlobalResource
            .getString(GlobalResourceName.GLOBAL_SYSTEM_MANUAL_FEED);

    // SUSPEND
    public static final String SUSPEND = GlobalResources.GlobalResource
            .getString(GlobalResourceName.GLOBAL_SYSTEM_SUSPEND_STATUS);

    // CpSpotRateInfo
    public static final String JMS_KEY_CPRATE = "CpSpotRateInfo";

    // FxSpotRateInfo
    public static final String JMS_KEY_CORERATE = "FxSpotRateInfo";

    // TradeResultInfo
    public static final String JMS_KEY_DEALING = "TradeResultInfo";

    // CPStatusMessage
    public static final String JMS_KEY_CPSTATUS = "CPStatusMessage";

    // DealerLogMessage
    public static final String JMS_KEY_DEALERLOG = "DealerLogMessage";

    // FONT
    // MS PGothic 8
    public static final String FONT_KEY_MSPGOTHIC8 = "MSPGothic8";

    // MS PGothic 9
    public static final String FONT_KEY_MSPGOTHIC9 = "MSPGothic9";

    // MS PGothic 18
    public static final String FONT_KEY_MSPGOTHIC18 = "MSPGothic18";

    // MS PGothic 12
    public static final String FONT_KEY_MSPGOTHIC12 = "MSPGothic12";

    // COLOR
    // WHITE
    public static final String COLOR_KEY_WHITE = "white";

    // BLACK
    public static final String COLOR_KEY_BLACK = "black";

    // PINK
    public static final String COLOR_KEY_PINK = "pink";

    // RED
    public static final String COLOR_KEY_RED = "red";

    // CYAN
    public static final String COLOR_KEY_CYAN = "cyan";

    // GREEN
    public static final String COLOR_KEY_GREEN = "green";

    // YELLOW
    public static final String COLOR_KEY_YELLOW = "yellew";

    // ORANGE
    public static final String COLOR_KEY_ORANGE = "orange";

    // BABY BLUE
    public static final String COLOR_KEY_BABYBLUE = "babyBlue";

    // GRAY
    public static final String COLOR_KEY_GRAY = "gray";

    // BABY GREEN
    public static final String COLOR_KEY_BABYGREEN = "babyGreen";

    // HEAVY GREEN
    public static final String COLOR_KEY_HEAVYBLUE = "heavyblue";

    // WIDGET BACKGROUND
    public static final String COLOR_KEY_WIDGET_BACKGROUND = "widgetbackground";

    // WIDGET DARKSHADOW
    public static final String COLOR_KEY_WIDGET_DARKSHADOW = "widgetdarkshadow";

    // IMAGE
    // FXICON
    public static final String IMAGE_KEY_FXICON = "fxicon";

    // CHB_CHECKED
    public static final String IMAGE_KEY_CHB_CHECKED = "chb_checked";

    // CHB_UNCHECKED
    public static final String IMAGE_KEY_CHB_UNCHECKED = "chb_unchecked";

    // point
    public static final String IMAGE_KEY_POINT = "point";
}
