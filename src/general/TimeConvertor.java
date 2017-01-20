package general;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
/**
 * System's timer. It is used for software simulation.
 * @author Group36
 * @version 3.0
 *
 */
public class TimeConvertor{
	//Date systemTime;
	/**
	 * A calendar for time simulation
	 */
	private Calendar calendar;
	/**
	 * real-world time when the system is launched.
	 */
	private long systemStart;
	/**
	 * real-world time currently.
	 */
	private long currentSysTime;
	/**
	 * simulated time when the system is launched.
	 */
	private long myStart; 
	/**
	 * Current simulated time
	 */
	private long myCurrent;
	private long duration, myDuration;
	private int hour, minute;
	private boolean modify;
	private boolean finish = false;
	
	/*JPanel inputArea = new JPanel();
	JPanel confirmArea = new JPanel();
	JTextField inputHour, inputMin;
	JButton confirm, cancel;
	JLabel showTime = new JLabel();*/
	
	/**
	 * constructor, defining an arbitrary time
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 */
	public TimeConvertor(int year, int month, int day, int hour, int minute){
		//systemTime = new Date(); //the actual system-time
		systemStart = System.currentTimeMillis();	
		calendar =  new GregorianCalendar();//represent time in our tour system		
		this.hour = hour;
		this.minute = minute;
		calendar.set(year, month-1, day, hour, minute);
		myStart = calendar.getTimeInMillis();
		//guiPreset();
	}
	/**
	 * Constructor, the time is automatically set to 2017-1-1, 8:00
	 */
	public TimeConvertor(){
		//systemTime = new Date(); //the actual system-time
		systemStart = System.currentTimeMillis();	
		calendar =  new GregorianCalendar();//represent time in our tour system		
		this.hour = 8;
		this.minute = 0;
		calendar.set(2017, 0, 1, hour, minute);
		System.out.println(calendar.getTime());
		myStart = calendar.getTimeInMillis();
		//guiPreset();
	}
	
	/**
	 * get a time duration with respect to myCalendar
	 * @return
	 */
	public long getDuration(){
		myCurrent = calendar.getTimeInMillis();
		myDuration = myCurrent - myStart;
		currentSysTime = System.currentTimeMillis();
		duration = currentSysTime - systemStart;
		if(modify == true)
			return myDuration/(1000*60);
		else
			return duration/(1000*60);
	}
	
	/**
	 * return myClalendar
	 * @return
	 */
	public Calendar getCalendar(){
		currentSysTime = System.currentTimeMillis();
		duration = currentSysTime - systemStart;
		//System.out.println(duration);
		int addHour = (int)(duration/(1000*60))/60;
		int addMinute = (int)(duration/(1000*60))%60;
		int addSecond = (int)(duration/1000)-addHour*3600-addMinute*60;
		//System.out.println("With actual system time added, h="+addHour+", m="+addMinute+", s="+addSecond);
		calendar.set(2017, 0, 1, calendar.get(Calendar.HOUR_OF_DAY)+addHour, 
				calendar.get(Calendar.MINUTE)+addMinute,addSecond);
		return this.calendar;
	}
	/**
	 * modify system time
	 * @param s new time, in a defined format
	 * @return
	 */
	public boolean setTime(String s){
		boolean validTime = false;
		if(s == null){
			validTime = true;
			JOptionPane.showMessageDialog(null, "You made no changes on system time", "Message",JOptionPane.INFORMATION_MESSAGE, null);
		}else{
			try{
				String parts[] = s.split(":");
				int hour = Integer.parseInt(parts[0]);
				int minute = Integer.parseInt(parts[1]);
				if(hour < 24 && hour > 0 && minute < 60 && minute >= 0){
					calendar.set(2017, 0, 1, hour, minute);
					modify = true;
					System.out.println("New time set");
					validTime = true;
				}
			}catch(Exception e){
				System.out.println("Invalid Format");
				e.printStackTrace();
			}
		}
		return validTime;
	}
	/**
	 * return current simulated time
	 * @return
	 */
	public String printTime(){
		String s = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		s = sdf.format(getCalendar().getTime());
		System.out.println(getCalendar().getTime());
		return s;
	}
}
	