package gui;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import interaction.BuildFrame;

import java.util.ArrayList;
/**
 * This class is the beginning of system functions. Main menu is established here.
 * @author Group36
 * @version 4.1
 */


public class simpleGUI extends JFrame implements ActionListener {
	/**
	 * The buttons provides role selection
	 */
	ArrayList<JButton> blocks;
	/**
	 * Instance of BuildFrame class. It connects UIs to inside algorithms.
	 */
	BuildFrame frame;
	/**
	 * Constructor
	 * Data in files are loaded to set up system. Customers choose a role here to launch a corresponding subsystem.
	 */
	public simpleGUI(){
		JOptionPane.showMessageDialog(null, "Choose a mode to lanch the system", "message",JOptionPane.INFORMATION_MESSAGE, null);
		frame = new BuildFrame();
		//predefine system issues 
		frame.setupSystem();
		frame.setGUI();
	}
	

	/**
	 * set up system's role selection GUI
	 */
	public void MultipleButtons(){
		setLayout(new GridLayout(3,1));
		blocks = new ArrayList<JButton>();	
		//create buttons
		blocks.add(new JButton("Manager"));
		blocks.add(new JButton("Driver"));
		blocks.add(new JButton("Tourist"));
		for(int i=0; i<3; i++){
			blocks.get(i).addActionListener(this);
			this.add(blocks.get(i));
		}
	
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2;
		setLocation(w, h);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,300);
		this.setVisible(true);
	}
	
	@Override
	/**
	 * The customer's role is recorded and a log-in interface is made.
	 */
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		int index = blocks.indexOf(clicked);
		System.out.println(index);
		switch(index){
			case 0://manager operation
				//Manager_LogIn login = new Manager_LogIn();
				//boolean valid = false;
				ManagerWork manager = new ManagerWork(frame);
				manager.login();
				//manager.managerButtons(frame);
				break;
			case 1://driver operation
				DriverWork driver = new DriverWork(frame);
				driver.login();
				//driver.driverButtons(, 1);//1 is the driver ID
				break;
			case 2://user operation
				UserWork user = new UserWork();
				user.userButtons(frame);
				break;
			default:
				JOptionPane.showMessageDialog(null, "System Error", "Warning",JOptionPane.INFORMATION_MESSAGE, null);
				System.exit(1);
		}	
	}
	
}

