package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import interaction.BuildFrame;

import java.util.ArrayList;
/**
 * Manager subsystem: manager's log-in/register interface and responsibilities at work
 * @author Group 36
 * @version 4.1
 */
public class ManagerWork extends JFrame implements ActionListener{
/**
 * Showing manager's responsibilities	
 */
ArrayList<JButton> blocks;
/**
 * A copy of system's BuildFrame instance
 */
static BuildFrame frame;
boolean valid = false;
JFrame jf;
JTextField jtf;
JPasswordField jpf;
JButton login, register, cancel;
	
	/**
	 * Constructor
	 * @param frame
	 */
	public ManagerWork(BuildFrame frame){
			this.frame= frame;
		}
	/**
	 * Manager's log-in interface. A manager can also choose to register an ID here.
	 */
	public void login(){		
		jf = new JFrame("Manager Login");
		jf.setLayout(new GridLayout(5, 1));
		jf.add(new JPanel());
		JLabel jl1 = new JLabel("ID:");
		jtf = new JTextField(12);
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
		register = new JButton("Register");
		register.addActionListener(this);
		login = new JButton("Log In");
		login.addActionListener(this);
		cancel = new JButton("Cancle");
		cancel.addActionListener(this);
		jp3.add(register);
		jp3.add(login);
		jp3.add(cancel);
		jf.add(jp3);
 
		jf.setResizable(false);
		jf.setVisible(true);		
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 300) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 200) / 2;
		jf.setLocation(w, h);
		jf.setSize(300, 200);
		
	}

	/**
	 * Check if the manager's ID and password match
	 * @return match or not
	 */
	public boolean verifyManager(){
		
			if ("".equals(jtf.getText())
					|| "".equals(new String(jpf.getPassword()))
					|| jpf.getPassword() == null) {
				JOptionPane.showConfirmDialog(
						jf, 
						"ID or password can not be empty!\nPlease reenter:", "Error",
						JOptionPane.DEFAULT_OPTION);
				jtf.setText(null);
				jpf.setText(null);
				jtf.requestFocus();
			} else {
				String s = jtf.getText() + "&&" + new String(jpf.getPassword());
				String name = jtf.getText() + "&&";
				File file = new File("record/ManagerInfo.txt");
				try { 
					BufferedReader managerReader = new BufferedReader(new FileReader("record/ManagerInfo.txt"));
					String recordline;
					String[] pieces;
					String passwd = new String(jpf.getPassword());
					int i= -1, j = -1;
					recordline = managerReader.readLine();
					while(recordline != null && (i == -1 || j == -1)){
						pieces = recordline.split("&&");
						System.out.println(pieces[1]);
						System.out.println(passwd);
						if(pieces[0].equals(jtf.getText()))
							i = 1;
						if(pieces[1].equals(passwd))
							j = 1;
						recordline = managerReader.readLine();
					}
					
					managerReader.close();
					System.out.println(jtf.getText()+" i= "+i);
					System.out.println(passwd +" j= "+j);
					if (i == -1) { 
						JOptionPane.showMessageDialog(
								jf, 
								"There is no such ID!\nPlease confirm your ID or register!", "ID Error",
								JOptionPane.ERROR_MESSAGE);
						jtf.setText(null);
						jpf.setText(null);
						jtf.requestFocus();
					} else {
						if (j == -1) {
							JOptionPane.showMessageDialog(
									jf, 
									"Wrong password!\nPlease confirm your password:", "Password Error",
									JOptionPane.ERROR_MESSAGE);
							jpf.setText(null);
							jpf.requestFocus();
						} else {
							JOptionPane.showMessageDialog(
									jf, 
									"Log in successfully\n" + "ID: " + jtf.getText()
											+ "\n","Success", JOptionPane.INFORMATION_MESSAGE);
							valid = true;
							jf.dispose();
							revalidate();
							System.out.println(valid);
						}
					}
				} catch (Exception e1) {
					System.out.println("Veryfying manager error");
					e1.printStackTrace();
				}
			}
		return valid;
	}
	
	/**
	 * Launch the working interface after a manager log-in
	 */
	public void managerButtons(){
		System.out.println("setup manager interface");
		blocks = new ArrayList<JButton>();	
		//create buttons
		blocks.add(new JButton("Route"));
		blocks.add(new JButton("Journey"));
		blocks.add(new JButton("Assignment"));
		blocks.add(new JButton("Train Screen"));
		blocks.add(new JButton("Station Screen"));
		blocks.add(new JButton("System Time"));
		blocks.add(new JButton("Save & Quit"));
		for(int i=0; i<blocks.size(); i++){
			blocks.get(i).addActionListener(this);
			this.add(blocks.get(i));
		}
		setLayout(new GridLayout(blocks.size(),1));
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 300) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 300) / 2;
		setLocation(w, h);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300,300);
		this.setVisible(true);
	}

	@Override
	/**
	 * Detect a manager's instructions at work. Provide feedback using inside codes.
	 */
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		if(clicked == cancel){
			jf.dispose();
		}else if(clicked == register){
			jf.dispose();
			new Manager_Register(frame);
		}else if(clicked == login){
			jf.dispose();
			valid = verifyManager();
			System.out.println(valid+" to display");
			 if(valid){
					managerButtons();
					revalidate();
				}
		}else{
			int index = blocks.indexOf(clicked);
			switch(index){
				case 0://setRoutes
					frame.addRoute();
					break;
				case 1://view and add journey
					frame.journeyOperation();				
					break;
				case 2://view train&driver assignment
					frame.viewAssignment_drtr();
					break;
				case 3://get location, stop, start
					frame.stopTrian_gui();
					break;
				case 4://view next train
					frame.nextTrain();
					break;
				case 5://reset system time
					frame.changeTime();
					break;
				case 6://save and quit
					dispose();
					frame.saveSettings();
					break;
				default:
					System.out.println("Error");
					System.exit(1);
			}
		}
	}

}
