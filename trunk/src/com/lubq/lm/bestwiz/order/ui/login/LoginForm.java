package com.lubq.lm.bestwiz.order.ui.login;



import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.lubq.lm.bestwiz.order.ui.cons.GlobalConstants;
import com.lubq.lm.bestwiz.order.ui.cons.GlobalResources;
import com.lubq.lm.bestwiz.order.ui.cons.LoginResourceName;
import com.lubq.lm.util.ResourceUtil;
import com.lubq.lm.util.ShellUtil;



/**
 * 登録ウィンドウを創建するためのクラス
 * 
 * @author wenyi <jhf@bestwiz.cn>
 * 
 * @copyright 2006, BestWiz(Dalian) Co.,Ltd
 * @version $Id: LoginForm.java,v 1.6 2007/04/14 07:56:39 liyan Exp $
 */
public class LoginForm {

    private Shell sShell = null;

    private Composite composite = null;

    private Label label = null;

    private Label labelImage1 = null;

    private Label labelTitle = null;

    /**
     * コンストラクタ
     * 
     */
    public LoginForm() {
        createSShell();
        ShellUtil.centerShell(sShell);
        Display display = sShell.getDisplay();
        sShell.open();
        
        
        while (!sShell.isDisposed()) {
            if (!Display.getDefault().readAndDispatch())
                Display.getDefault().sleep();
        }
        
//        while (!sShell.isDisposed())
// 			if (!display.readAndDispatch())
// 				display.sleep();
// 		display.dispose();
    }

    /**
     * Compositeを創建する
     * 
     */
    private void createComposite() {
        composite = new LoginPanel(sShell, SWT.NONE);
        composite.setBounds(0, 0, 407, 196);
    }

    /**
     * Shellを創建する
     */
    private void createSShell() {
        sShell = new Shell(SWT.BORDER | SWT.CLOSE);

        sShell.setLayout(null);
        sShell.setImage(ResourceUtil.getDefaultImage());
        sShell.setSize(414, 230);
        sShell.setFont(ResourceUtil
                .getFontByName(GlobalConstants.FONT_KEY_MSPGOTHIC9));
        sShell.setText(GlobalResources.LoginResource
                .getString(LoginResourceName.LOGIN_TITLE));

        createComposite();
        label = new Label(sShell, SWT.NONE);
        label.setBounds(new org.eclipse.swt.graphics.Rectangle(105, 301, 433,
                16));
        labelImage1 = new Label(sShell, SWT.NONE);
        labelImage1.setBounds(new org.eclipse.swt.graphics.Rectangle(0, 0, 220,
                53));
        labelImage1.setText("");
        labelTitle = new Label(sShell, SWT.NONE);
        labelTitle.setBounds(new org.eclipse.swt.graphics.Rectangle(0, 0,
                sShell.getSize().x - 6, labelImage1.getSize().y));
        labelTitle.setText("");
    }

    
    
    public static void main(String[] args) {
    	LoginForm form = new LoginForm();
	}
}
