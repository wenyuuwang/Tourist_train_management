package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.io.IOException;
import javax.swing.*;

import interaction.BuildFrame;
/**
 * Manager register an ID
 * @author Group36
 * @version 4.1
 */
public class Manager_Register implements ActionListener {

	JFrame jf;
	JTextField jtf;
	JPasswordField jpf, jpf2;
	BuildFrame frame;
	/**
	 * Manager register an ID.
	 * @param f A copy of system's BuildFrame instance.
	 */
	public Manager_Register(BuildFrame f) {
		this.frame = f;
		jf = new JFrame("Register");
		jf.setLayout(new GridLayout(6, 1));
		jf.add(new JPanel());
		JLabel jl1 = new JLabel("ID:");
		jtf = new JTextField(12);
		JPanel jp1 = new JPanel();
		jp1.add(jl1);
		jp1.add(jtf);
		jf.add(jp1);
 
		JLabel jl2 = new JLabel("Password:");
		jpf = new JPasswordField(12);
		JPanel jp2 = new JPanel();
		jp2.add(jl2);
		jp2.add(jpf);
		jf.add(jp2);
 
		JLabel jl3 = new JLabel("Confirm password:");
		jpf2 = new JPasswordField(12);
		JPanel jp3 = new JPanel();
		jp3.add(jl3);
		jp3.add(jpf2);
		jf.add(jp3);
 
		JPanel jp4 = new JPanel();
		JButton jb1 = new JButton("Register");
		jb1.addActionListener(this);
		JButton jb2 = new JButton("Cancle");
		jb2.addActionListener(this);
		jp4.add(jb1);
		jp4.add(jb2);
		jf.add(jp4);
 
		jf.setResizable(false);		
		jf.setSize(300, 240);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 300) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 240) / 2;
		jf.setLocation(w, h);
		//jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	/**
	 * Detect a manager's instructions during Register phase. Give feedbacks.
	 */
	public void actionPerformed(ActionEvent e) {
		String comm = e.getActionCommand();
		if (comm.equals("Register")) {
			// jf.dispose();
			if ("".equals(jtf.getText())
					|| "".equals(new String(jpf.getPassword()))
					|| jpf.getPassword() == null
					|| "".equals(new String(jpf2.getPassword()))
					|| jpf2.getPassword() == null) {
				final JFrame jf = new JFrame("Error");
				JLabel jl = new JLabel("ID or password can not be empty!");
				JPanel jp1 = new JPanel();
				JPanel jp2 = new JPanel();
				jp1.add(jl);
				jf.add(jp1);
				JButton jb = new JButton("OK");
				jb.addActionListener(new ActionListener() {
 
					
					public void actionPerformed(ActionEvent e) {
						jf.dispose();
					}
 
				});
				jp2.add(jb);
				jf.add(jp2);
				jf.setLayout(new GridLayout(2, 1));
				jf.setResizable(false);
				jf.setVisible(true);
				jf.pack();
				jf.setLocation(400, 300);
			} else {
				String s = jtf.getText() + "&&" + new String(jpf.getPassword())
						+ "\r\n";
				String name = jtf.getText() + "&&";
				File file = new File("record/ManagerInfo.txt");
				try {
					file.createNewFile(); 
				} catch (IOException e2) {
					e2.printStackTrace();
				}
				try { 
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
					if (i == -1) { 
						if (new String(jpf.getPassword()).equals(new String(
								jpf2.getPassword()))) {
							try {
								FileOutputStream fos = new FileOutputStream(
										file, true);
								fos.write(s.getBytes());
								fos.close();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
							int a = JOptionPane.showConfirmDialog(jf, "Register successfully!\n"
									+ "ID:" + jtf.getText() + "\nPassword: "
									+ new String(jpf.getPassword())
									+ "\nchoose ok to enter login interface", "Success",
									JOptionPane.OK_CANCEL_OPTION);
							if (a == 0) {
								jf.dispose();
								(new ManagerWork(frame)).login();
							}
						} else {
							JOptionPane.showConfirmDialog(
									jf,
									"Two passowrds are not the same\nPlease reset", "Password Failure",
									JOptionPane.CLOSED_OPTION);
							jpf.setText(null);
							jpf2.setText(null);
							jpf.requestFocus();
						}
					} else {
 
						JOptionPane.showConfirmDialog(
								jf, 
								"This ID has been registered\nPlease use a new ID to register again", "ID Failure",
								JOptionPane.CLOSED_OPTION);
						jtf.setText(null);
						jpf.setText(null);
						jpf2.setText(null);
						jtf.requestFocus();
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else if (comm.equals("Cancle")) {
			//System.exit(0);
			jf.dispose();
		}
 
	}
}

