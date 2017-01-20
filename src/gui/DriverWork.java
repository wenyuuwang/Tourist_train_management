package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

import interaction.BuildFrame;

/**
 * Driver subsystem: driver's log-in interface and responsibilities at work
 * @author Group36
 * @version 4.1
 */
public class DriverWork extends JFrame implements ActionListener{
	/**
	 * The only BuildFrame object in a system, coming from role-selection stage. 
	 */
	static BuildFrame frame;
	/**
	 * Driver's identity number
	 */
	int myIndex;
	JButton assignment, state, quit;
	JFrame jf;
	JTextField jtf;
	JPasswordField jpf;
	JButton login, cancel;
	/**
	 * Flag implying if the driver inputs a correct password to log in.
	 */
	boolean valid = false;
	/**
	 * Constructor
	 * Launch Driver subsystem
	 * @param f the only version of BuildFrame instance in system.
	 */
	public DriverWork(BuildFrame f){
		frame = f;
	}
	/**
	 * A driver is asked to enter ID and password
	 */
	public void login(){
		jf = new JFrame("Driver Login");
		jf.setLayout(new GridLayout(5, 1));
		jf.add(new JPanel());
		JLabel jl1 = new JLabel("Driver ID:");
		jtf = new JTextField("Your Index", 12);
		JPanel jp1 = new JPanel();
		jp1.add(jl1);
		jp1.add(jtf);
		jf.add(jp1);
 
		JLabel jl2 = new JLabel("Password: ");
		jpf = new JPasswordField(12);
		JPanel jp2 = new JPanel();
		jp2.add(jl2);
		jp2.add(jpf);
		jf.add(jp2);
 
		JPanel jp3 = new JPanel();
		
		login = new JButton("Log In");
		login.addActionListener(this);
		cancel = new JButton("Cancel");
		cancel.addActionListener(this);
		
		jp3.add(login);
		jp3.add(cancel);
		jf.add(jp3);
 
		jf.setResizable(false);
		jf.setSize(300, 200);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 300) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 200) / 2;
		jf.setLocation(w, h);
		//jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	/**
	 * Check if the driver's ID and password match
	 * @return driver's ID
	 */
	public int verifyDriver(){
		int driverIndex = -1;
		if ("".equals(jtf.getText())
				|| "".equals(new String(jpf.getPassword()))
				|| jpf.getPassword() == null) {
			JOptionPane.showConfirmDialog(
					jf, // Èç¹ûÎªnull£¬´Ë¿ò¼ÜÏÔÊ¾ÔÚÖÐÑë£¬ÎªjfÔòÏÔÊ¾ÎªjfµÄÖÐÑë
					"ID or password can not be empty!\nPlease reenter", "Error",
					JOptionPane.DEFAULT_OPTION);
			jtf.setText(null);
			jpf.setText(null);
	        jtf.requestFocus();// ¹â±ê»ØÀ´
		}else if(!jtf.getText().matches("^[0-9_]+$")){
			JOptionPane.showConfirmDialog(
					jf, // Èç¹ûÎªnull£¬´Ë¿ò¼ÜÏÔÊ¾ÔÚÖÐÑë£¬ÎªjfÔòÏÔÊ¾ÎªjfµÄÖÐÑë
					"Driver ID must be number!\nPlease reenter", "Error",
					JOptionPane.DEFAULT_OPTION);
		}
				
		else {
			String s = jtf.getText() + "&&" + new String(jpf.getPassword());
			String name = jtf.getText() + "&&";
			File file = new File("record/DriverInfo.txt");
			try { // ¶ÁÎÄ¼þµÄ×¢²áÐÅÏ¢
				FileInputStream fis = new FileInputStream(file);
				String s1 = "";
				byte[] b = new byte[1024];
				while (true) {
					int i = fis.read(b);
					if (i == -1)
						break;
					s1 = s1 + new String(b, 0, i);
				}
				fis.close();
				int i = s1.indexOf(name);
				int j = s1.indexOf(s);
				if (i == -1) { 
					JOptionPane.showMessageDialog(jf, "There is no such driver ID\nPlease confirm and try agian", "ID Error",
							JOptionPane.ERROR_MESSAGE);
					jtf.setText(null);
					jpf.setText(null);
					jtf.requestFocus();
				} else {
					if (j == -1) {
						JOptionPane.showMessageDialog(jf, "Wrong password!\nPlease confirm your password:", "Passsord Error",
								JOptionPane.ERROR_MESSAGE);
						jpf.setText(null);
						jpf.requestFocus();
					} else {
						JOptionPane.showMessageDialog(jf, "Log in successfully\n" + "ID: " + jtf.getText(),
								"Success", JOptionPane.INFORMATION_MESSAGE);
						valid = true;	
						driverIndex = Integer.parseInt(jtf.getText());
						jf.dispose();
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return driverIndex;
	}
	/**
	 * GUI to show driver's responsibilities. 
	 * @param index Driver's ID
	 */
	public void driverButtons(int index){		
		myIndex = index;//to be checked
		setLayout(new GridLayout(3,1));
		assignment = new JButton("My Assignment");
		state = new JButton("My Current Journey");
		quit = new JButton("Log out"); 
		add(assignment);
		add(state);
		add(quit);
		assignment.addActionListener(this);
		state.addActionListener(this);
		quit.addActionListener(this);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2;
		setLocation(w, h);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,300);
		this.setVisible(true);		
	}
	/**
	 * Detect driver's instruction and then provide with a feedback using inside code. 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		if(clicked == cancel){
			jf.dispose();
		}else if(clicked == login){
			int driverIndex = verifyDriver();
			System.out.println(valid+" to display");
			System.out.println("driver: "+driverIndex);
			if(valid && (driverIndex != -1)){
				driverButtons(driverIndex);
				revalidate();
			}
		}else if(clicked == assignment){
			frame.viewDriver(myIndex);
		}else if(clicked == state){
			frame.driverCheck(myIndex);
		}else if(clicked == quit){
			setVisible(false);
			JOptionPane.showMessageDialog(null, "Log out successfully", "Message",JOptionPane.INFORMATION_MESSAGE, null);
		}else
			JOptionPane.showMessageDialog(null, "System error", "Warning",JOptionPane.INFORMATION_MESSAGE, null);
		
	}

}
