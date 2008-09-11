package com.lubq.lm.bestwiz.order.ui.login;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.lubq.lm.bestwiz.order.ui.cons.GlobalConstants;
import com.lubq.lm.bestwiz.order.ui.cons.GlobalResources;
import com.lubq.lm.bestwiz.order.ui.cons.LoginResourceName;
import com.lubq.lm.bestwiz.order.ui.view.OrderBuilderView;
import com.lubq.lm.util.ResourceUtil;
import com.lubq.lm.util.SWTResourceManager;
import com.lubq.lm.util.WidgetUtil;








/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
/**
 * ログインウィンドウクラス
 * 
 * @author wenyi <jhf@bestwiz.cn>
 * 
 * @copyright 2006, BestWiz(Dalian) Co.,Ltd
 * @version $Id: LoginPanel.java,v 1.32 2007/04/17 01:59:13 liyan Exp $
 */
public class LoginPanel extends Composite {

    {
        SWTResourceManager.registerResourceUser(this);
    }

    private String m_username;

    private String m_password;

    private Button btnPass;

    private Label lblError;

    public static final int LOGIN_SUCCESSED = 0;

    public static final int LOGIN_ERRUSERORPWD = 1;

    private Label lblLoginId = null;

    private Label lblPassword = null;

    private Text txtLoginId = null;

    private Text txtPassword = null;

    private Button btnLogin = null;

    private Button btnCancel;

    private static Cursor cursorWait = SWTResourceManager
            .getCursor(SWT.CURSOR_WAIT);

    private static Cursor cursorArrow = SWTResourceManager
            .getCursor(SWT.CURSOR_ARROW);

    private ProgressBar progressBar;// 進捗バー

    private boolean checkOK = false; // 進捗バー状態をチェックする

    private DealerView dealerView;

    /**
     * コンストラクタ
     * 
     * @param parent
     * @param style
     */
    public LoginPanel(Composite parent, int style) {
        super(parent, style);
        initialize();
    }

    /**
     * 初期化画面
     * 
     */
    private void initialize() {

        // LoginId lable
        lblLoginId = new Label(this, SWT.NONE);
        lblLoginId.setAlignment(SWT.RIGHT);
        lblLoginId.setBounds(38, 46, 124, 21);
        lblLoginId.setText(GlobalResources.LoginResource
                .getString(LoginResourceName.LOGIN_LABEL_LOGINID));

        // Password lable
        lblPassword = new Label(this, SWT.NONE);
        lblPassword.setAlignment(SWT.RIGHT);
        lblPassword.setBounds(38, 75, 124, 18);
        lblPassword.setText(GlobalResources.LoginResource
                .getString(LoginResourceName.LOGIN_LABEL_PASSWORD));

        // LoginId text
        txtLoginId = new Text(this, SWT.BORDER);
        txtLoginId.setTextLimit(15);
        txtLoginId.setText("");
        txtLoginId.setBounds(new org.eclipse.swt.graphics.Rectangle(173, 46,
                111, 19));
        txtLoginId
                .addVerifyListener(new org.eclipse.swt.events.VerifyListener() {
                    public void verifyText(org.eclipse.swt.events.VerifyEvent e) {
                        WidgetUtil.verifyTextInput(e,
                                WidgetUtil.NUMBER_OR_CHAR_TYPE);
                    }
                });
        txtLoginId.addSelectionListener(new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
                if (txtLoginId.getText() != null
                        && txtLoginId.getText().length() > 0)
                    txtPassword.setFocus();
            }
        });

        // Password text
        txtPassword = new Text(this, SWT.BORDER);
        txtPassword.setEchoChar('*');
        txtPassword.setTextLimit(15);
        txtPassword.setText("");
        txtPassword.setBounds(new org.eclipse.swt.graphics.Rectangle(173, 75,
                111, 19));
        txtPassword.addSelectionListener(new SelectionAdapter() {
            public void widgetDefaultSelected(SelectionEvent e) {
                executeLogin();
            }
        });

        // Login button
        btnLogin = new Button(this, SWT.NONE);
        btnLogin.setBounds(16, 113, 118, 25);
        btnLogin.setBackground(ResourceUtil
                .getColorByName(GlobalConstants.COLOR_KEY_WHITE));
        btnLogin.setText(GlobalResources.LoginResource
                .getString(LoginResourceName.LOGIN_BUTTON_LOGIN));
        btnLogin
                .addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
                    public void widgetSelected(
                            org.eclipse.swt.events.SelectionEvent e) {
                        executeLogin();
                    }
                });

        // Cancel button
        btnCancel = new Button(this, SWT.NONE);
        btnCancel.setBounds(144, 114, 118, 25);
        btnCancel.setBackground(ResourceUtil
                .getColorByName(GlobalConstants.COLOR_KEY_WHITE));
        btnCancel.setText(GlobalResources.LoginResource
                .getString(LoginResourceName.LOGIN_BUTTON_CLEAR));
        btnCancel
                .addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
                    public void widgetSelected(
                            org.eclipse.swt.events.SelectionEvent e) {
                        ((Shell) getParent()).close();
                    }
                });

        // show error message lable
        {
            lblError = new Label(this, SWT.NONE | SWT.WRAP);
            lblError.setBounds(31, 146, 344, 30);
        }

        // password change button
        {
            btnPass = new Button(this, SWT.NONE);
            btnPass.setText(GlobalResources.LoginResource
                    .getString(LoginResourceName.LOGIN_BUTTON_PASSWORDCHANGE));
            btnPass.setBackground(ResourceUtil
                    .getColorByName(GlobalConstants.COLOR_KEY_WHITE));
            btnPass.setBounds(270, 115, 118, 25);
            btnPass.addSelectionListener(new SelectionAdapter() {
                public void widgetSelected(SelectionEvent evt) {
                    executePasswordChange();
                }
            });
        }
        {
            progressBar = new ProgressBar(this, SWT.NONE);
            progressBar.setBounds(0, 182, 407, 14);
            progressBar.setMaximum(200 - 1);
            progressBar.setVisible(false);
        }

        this.setSize(389, 207);
    }

    /**
     * 入力したUsernameを取得する
     * 
     * @return
     */
    private String getUsername() {
        if (txtLoginId.getText() != null) {
            m_username = txtLoginId.getText().trim();
        } else {
            m_username = "";
        }
        return m_username;
    }

    /**
     * 入力したPasswordを取得する
     * 
     * @return
     */
    private String getPassword() {
        if (txtPassword.getText() != null) {
            m_password = txtPassword.getText().trim();
        } else {
            m_password = "";
        }

        return m_password;
    }

    /**
     * ログイン処理を行う
     * 
     */
    public void executeLogin() {
        final String sUsername = getUsername();
        final String sPassword = getPassword();
        lblError.setText("");

        if (sUsername.equals("")) {
            lblError.setText(GlobalResources.LoginResource
                    .getString(LoginResourceName.LOGIN_USERNAME_REQUIRE));
            txtLoginId.setFocus();
            return;
        }
        if (sPassword.equals("")) {
            lblError.setText(GlobalResources.LoginResource
                    .getString(LoginResourceName.LOGIN_PASSWORD_REQUIRE));
            txtPassword.setFocus();
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                try {
                    getDisplay().asyncExec(new Runnable() {
                        public void run() {
                            setLoginBegin();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
//                    MailSender.sendMail(e);
                }

                checkOK = true;
                runProgressBar();
                final boolean checkSignIntoServer = signIntoServer();
                LoginCheck loginCheck = new LoginCheck();
                dealerView = loginCheck.checkLogin(sUsername, sPassword);
//                checkOK = false;

                try {
                    getDisplay().asyncExec(new Runnable() {
                        public void run() {
                            try {
                                if (!checkSignIntoServer) {
                                    lblError
                                            .setText(GlobalResources.GlobalResource
                                                    .getString(""
//                                                            + DealerServiceException.TIME_OUT_ERROR
                                                            ));
                                    txtLoginId.setFocus();
                                    return;
                                }
                                
                                // lubaoqiang add
//                                if(dealerView == null){
//                                	return;
//                                }
//                                
////                                if(dealerView){
////                                	
////                                }
//                                //
//                                
//                                
//                                if (dealerView.getErrorCode() == 1) {
//                                    lblError
//                                            .setText(GlobalResources.LoginResource
//                                                    .getString(LoginResourceName.LOGIN_PASSWORD_INVALID));
//                                    txtLoginId.setFocus();
//                                    return;
//                                } else if (dealerView.getErrorCode() == 2) {
//                                    lblError
//                                            .setText(GlobalResources.LoginResource
//                                                    .getString(LoginResourceName.LOGIN_USERNAME_LOCK));
//                                    txtLoginId.setFocus();
//                                    return;
//                                } else if (dealerView.getErrorCode() == 3) {
//                                    lblError
//                                            .setText(GlobalResources.LoginResource
//                                                    .getString(LoginResourceName.LOGIN_USERNAME_LOCK_LIMIT));
//                                    txtLoginId.setFocus();
//                                    return;
//                                }
                            } finally {
                                setLoginEnd();
                            }

//                            if (dealerView != null
//                                    && dealerView.getErrorCode() == 0) {
//                                ApplicationContext.getContext().setLoginBean(dealerView);
//                                try {
//                                    DealerLogUtil
//                                            .save(
//                                                    GlobalResourceName.LOG_DEALER_LOGIN_SUCCESS,
//                                                    new Object[] {
//                                                            dealerView
//                                                                    .getLoginId(),
//                                                            LoginCheck
//                                                                    .getHost() },
//                                                    DealerLogTypeEnum.LOG_ERROR_ENUM
//                                                            .getValue());
//                                } catch (ServiceException e) {
//                                    e.printStackTrace();
//                                    MailSender.sendMail(e);
//                                }
                            
                            ((Shell) getParent()).close();
                            System.out.println(" login window close() .");
                            
                            Shell mainShell = new Shell(Display.getDefault() ,SWT.CLOSE |SWT.MIN |SWT.MAX |SWT.TITLE);
//                        	Shell shell = new Shell(display ,SWT.CLOSE |SWT.MIN |SWT.MAX |SWT.TITLE);
                    		
                    		OrderBuilderView inst = new OrderBuilderView(mainShell, SWT.SYSTEM_MODAL);
                    		Point size = inst.getSize();
                    		mainShell.setLayout(new FillLayout());
                    		mainShell.layout();
                    		if(size.x == 0 && size.y == 0) {
//                    			inst.pack();
//                    			shell.pack();
                    		} else {
                    			Rectangle shellBounds = mainShell.computeTrim(0, 0, size.x, size.y);
                    			mainShell.setSize(shellBounds.width, shellBounds.height);
                    		}
                    		mainShell.open();
                    		
                    		
                    		
//                    		Display.getCurrent(display.asyncExec(new Runnable() {
//                    			public void run() {
//                    			//Inform the indicator that some amount of work has been done
//                    			indicator.worked(1);
//                    			}
//                    			}));
                    //
//                    		
                    		
//                    		while (!shell.isDisposed()) {
//                    			if (!display.readAndDispatch())
//                    				display.sleep();
//                    		}
                            
//                            ((Shell) getParent()).close();
//                            System.out.println(" login window close() .");
                            
//                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
//                    MailSender.sendMail(e);
                }
            }
        }).start();
    }

    /**
     * パスワード変更認証処理
     * 
     * @return
     */
    public void executePasswordChange() {
        final String sUsername = getUsername();
        final String sPassword = getPassword();
        lblError.setText("");

        if (sUsername.equals("")) {
            lblError.setText(GlobalResources.LoginResource
                    .getString(LoginResourceName.LOGIN_USERNAME_REQUIRE));
            txtLoginId.setFocus();
            return;
        }
        if (sPassword.equals("")) {
            lblError.setText(GlobalResources.LoginResource
                    .getString(LoginResourceName.LOGIN_PASSWORD_REQUIRE));
            txtPassword.setFocus();
            return;
        }

        new Thread(new Runnable() {
            public void run() {
                try {
                    getDisplay().asyncExec(new Runnable() {
                        public void run() {
                            setLoginBegin();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
//                    MailSender.sendMail(e);
                }

                checkOK = true;
                runProgressBar();
//                LoginCheck loginCheck = new LoginCheck();
//                dealerView = loginCheck.checkPasswordChange(sUsername,
//                        sPassword);
//                checkOK = false;

                try {
                    getDisplay().asyncExec(new Runnable() {
                        public void run() {
                            try {
                                if (dealerView.getErrorCode() == 1) {
                                    lblError
                                            .setText(GlobalResources.LoginResource
                                                    .getString(LoginResourceName.LOGIN_PASSWORD_INVALID));
                                    txtLoginId.setFocus();
                                } else if (dealerView.getErrorCode() == 2) {
                                    lblError
                                            .setText(GlobalResources.LoginResource
                                                    .getString(LoginResourceName.LOGIN_USERNAME_LOCK));
                                    txtLoginId.setFocus();
                                }
                                if (dealerView != null
                                        && dealerView.getErrorCode() == 0) {
//                                    DlgPasswordChange dlg = new DlgPasswordChange(
//                                            LoginPanel.this.getShell(),
//                                            SWT.NULL, dealerView);
//                                    dlg.open();
                                }
                            } finally {
                                setLoginEnd();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
//                    MailSender.sendMail(e);
                }
            }
        }).start();
    }

    /**
     * ログイン中画面状態を設定する
     * 
     */
    private void setLoginBegin() {

        txtLoginId.setEnabled(false);
        txtPassword.setEnabled(false);
        btnLogin.setEnabled(false);
        btnCancel.setEnabled(false);
        btnPass.setEnabled(false);

        setCursor(cursorWait);
    }

    /**
     * ログイン後画面状態を設定する
     * 
     */
    private void setLoginEnd() {

        txtLoginId.setEnabled(true);
        txtPassword.setEnabled(true);
        btnLogin.setEnabled(true);
        btnCancel.setEnabled(true);
        btnPass.setEnabled(true);
        txtPassword.setText("");
        txtLoginId.selectAll();
        txtLoginId.setFocus();

        setCursor(cursorArrow);
    }

    /**
     * 初期化画面
     * 
     * @return
     */
    private synchronized boolean signIntoServer() {
//        try {
//            InitConfigUtil.init();
//        } catch (ServiceException e) {
//            e.printStackTrace();
//            MailSender.sendMail(e);
//            DealerActivator.logException(e);
//            return false;
//        }

        return true;
    }

    /**
     * 進捗バーを表示する
     */
    private void runProgressBar() {
        final int[] count = new int[1];
        Thread runPB = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        for (int i = 0; i < 200; i++) {
                            if (checkOK == false) {
                                return;
                            }
                            try {
                                Thread.sleep(3);
                            } catch (InterruptedException e) {
                            }
                            count[0] = i;

                            try {
                                getDisplay().asyncExec(new Runnable() {
                                    public void run() {
                                        try {
                                            progressBar.setVisible(true);
                                            progressBar.setSelection(count[0]);
                                        } catch (Exception e) {
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                return;
                            }
                        }
                    }
                } finally {
                    try {
                        getDisplay().asyncExec(new Runnable() {
                            public void run() {
                                try {
                                    progressBar.setVisible(false);
                                } catch (Exception e) {
                                }
                            }
                        });
                    } catch (Exception e) {
                    }
                }
            }
        });
        runPB.start();
    }

}