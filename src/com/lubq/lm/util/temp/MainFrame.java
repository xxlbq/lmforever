package com.lubq.lm.util.temp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel;

public class MainFrame extends JFrame {

	private JMenuBar mainBar = new JMenuBar();
	private JMenu sysMenu = new JMenu("系统");
	private JMenuItem exitItem = new JMenuItem("退出");
	private JMenuItem loginItem = new JMenuItem("登陆");
	private JMenu aboutMenu = new JMenu("关于");
	private JMenuItem helpItem = new JMenuItem("帮助");
	
	private JPopupMenu popup = new JPopupMenu();
	private JMenuItem sendItem = new JMenuItem("发信息");
	private JMenuItem infoItem = new JMenuItem("看账户");
	private JMenuItem logoutItem = new JMenuItem("注销");

	private JTable table;
	private JScrollPane jspLogin;
	private JScrollPane jsplogTA;
	private JPanel displayPanel;
	private JPanel btnPanel;
	private JButton dispalyBtn;
	private JButton preBtn;
	private JButton nextBtn;

	public static DefaultTableModel defaultModel;
	public static JTextArea logTA;	
	
	public MainFrame() {
		super("皮肤测试");
		setMainBar();
		setPanel();
		initFrame(600, 450);
	}
	
	private void initFrame(int frameWidth, int frameHeight) {
		setTitle("皮肤测试");
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenDimension = tk.getScreenSize();
		int screenWidth = screenDimension.width;
		int screenHeight = screenDimension.height;
		setLocation((screenWidth - frameWidth) / 2,
				(screenHeight - frameHeight) / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(frameWidth, frameHeight);
		setVisible(true);
	}	

	private void setMainBar() {

		setJMenuBar(mainBar);
		mainBar.add(sysMenu);
		mainBar.add(aboutMenu);
		sysMenu.add(loginItem);
		sysMenu.addSeparator();
		sysMenu.add(exitItem);
		aboutMenu.add(helpItem);
		popup.add(sendItem);
		popup.add(infoItem);
		popup.add(logoutItem);
	}

	private void setPanel() {
		String[][] playerInfo = new String[0][0];
		String[] names = { "用户", "登陆时间", "当前状态" };
		defaultModel = new DefaultTableModel(playerInfo, names);
		logTA = new JTextArea();
		logTA.setLineWrap(true);
		jsplogTA = new JScrollPane(logTA);
		table = new JTable(defaultModel);
		jspLogin = new JScrollPane(table);
		displayPanel = new JPanel();
		btnPanel = new JPanel();

		displayPanel.setLayout(new BorderLayout());
		displayPanel.add(jsplogTA, BorderLayout.CENTER);
		displayPanel.add(btnPanel, BorderLayout.SOUTH);

		dispalyBtn = new JButton("显示10条");
		preBtn = new JButton("向前10条");
		nextBtn = new JButton("向后10条");
		btnPanel.add(dispalyBtn);
		btnPanel.add(preBtn);
		btnPanel.add(nextBtn);
		jspLogin.setPreferredSize(new Dimension(600, 69));
		add(jspLogin, BorderLayout.NORTH);
		add(displayPanel, BorderLayout.CENTER);
	}	
	
	
	
		
		public static void main(String[] args) {

		System.out.println("  ==  1  ==");
		JFrame.setDefaultLookAndFeelDecorated(true);
		System.out.println("  ==  1  ==");
		try {
			UIManager
					.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());
		} catch (Exception e) {
			System.out.println("Substance Raven Graphite failed to initialize");
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainFrame w = new MainFrame();
				w.setVisible(true);
			}
		});
	}
}