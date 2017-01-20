package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import interaction.BuildFrame;
/**
 * Driver subsystem: System's useful functions to users.
 * @author Group36
 * @version 3.0
 */
public class UserWork extends JFrame implements ActionListener {
	JButton search, station, browse, preference, quit;
	/**
	 * A copy of system's BuildFrame object
	 */
	static BuildFrame frame;
	/**
	 * GUI to show system functions for users
	 * @param f
	 */
	public void userButtons(BuildFrame f){
		frame = f;
		setLayout(new GridLayout(4,1));
		search = new JButton("Search Journey");
		station = new JButton("View Station");
		browse = new JButton("All Journeys");
		//preference = new JButton("Set Preference");//not implemented
		quit = new JButton("Quit"); 
		add(search);
		add(station);
		add(browse);
		//add(preference);
		add(quit);
		search.addActionListener(this);
		station.addActionListener(this);
		browse.addActionListener(this);
		//preference.addActionListener(this);
		quit.addActionListener(this);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2;
		setLocation(w, h);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400,300);
		this.setVisible(true);
	}

	@Override
	/**
	 * Detect user's instruction and then provide with a feedback using inside code. 
	 */
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton)e.getSource();
		if(clicked == search){
			frame.searchJourney();
		}else if(clicked == station){
			frame.nextTrain();
		}else if(clicked == browse){
			frame.userJourney();
		}else if(clicked == quit){
			setVisible(false);
			JOptionPane.showMessageDialog(null, "Log out successfully", "Message",JOptionPane.INFORMATION_MESSAGE, null);
		}else
			JOptionPane.showMessageDialog(null, "System Error", "Warning",JOptionPane.INFORMATION_MESSAGE, null);

	}

}
