package com.lubq.lm.bestwiz.order.ui.cons;

import java.util.ResourceBundle;



/**
 * define all リソースクラス
 * 
 * @author wenyi <jhf@bestwiz.cn>
 * 
 * @copyright 2006, BestWiz(Dalian) Co.,Ltd
 * @version $Id: GlobalResources.java,v 1.5 2007/04/09 02:46:26 liyan Exp $
 */
public class GlobalResources {
	
	private final static String prefix="com.lubq.lm.bestwiz.order.ui.resources";
	
    public static final ResourceBundle GlobalResource = ResourceBundle
            .getBundle(prefix+".GlobalResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle LoginResource = ResourceBundle
            .getBundle(prefix+".LoginResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle MenuResource = ResourceBundle.getBundle(
    		prefix+".MenuResource",
            DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle SplitResource = ResourceBundle
            .getBundle(prefix+".SplitResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle AdministrationResource = ResourceBundle
            .getBundle(
            		prefix+".AdministrationResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle DealingResource = ResourceBundle
            .getBundle(prefix+".DealingResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle InformationResource = ResourceBundle
            .getBundle(
            		prefix+".InformationResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle LadderResource = ResourceBundle
            .getBundle(prefix+".LadderResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle OrderResource = ResourceBundle
            .getBundle(prefix+".OrderResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle PositionResource = ResourceBundle
            .getBundle(prefix+".PositionResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle PriceResource = ResourceBundle
            .getBundle(prefix+".PriceResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle RateEditorResource = ResourceBundle
            .getBundle(
            		prefix+".RateEditorResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle BookingResource = ResourceBundle
            .getBundle(prefix+".BookingResource",
                    DealerSystemConstants.GlobalLocale);

    public static final ResourceBundle StatusResource = ResourceBundle
            .getBundle(prefix+".StatusResource",
                    DealerSystemConstants.GlobalLocale);
}
