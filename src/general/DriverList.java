package general;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * ArrayList in the class contains all drivers' information
 * @author Group36
 * @version 4.1
 *
 */
public class DriverList implements DataIO{
	/**
	 * An ArrayList that containing all drivers' information
	 */
	static ArrayList<Driver> driverlist;
	/**
	 * The number of drivers in the system
	 */
	private int size;
	
	public DriverList(){
		driverlist = new ArrayList<Driver>();
	}
	/**
	 * get driver's index in list according to identifier
	 */
	public Object get(String identifier) {
		int aimedIndex = -1;
		for(Driver drv: driverlist){
			if(drv.toString().equals(identifier))
				aimedIndex = driverlist.indexOf(drv);
		}
		return aimedIndex;		
	}
	/**
	 * get driver by index
	 */
	public Object get(int index) {
		return driverlist.get(index);
	}

	/**
	 * get the number of drivers in list
	 */
	public int size() {
		return driverlist.size();
	}

	/**
	 * add driver to list
	 */
	public void add(Object o) {
		Driver drv = (Driver)o;
		driverlist.add(drv);
		System.out.println("Driver "+ drv.toString()+" has been added.");
		System.out.println("Index in list is "+driverlist.indexOf(drv));	
	}
	@Override
	public void remove(String identifier) {
		// TODO Auto-generated method stub		
	}
	/**
	 * get driver's index in list according to identifier
	 */
	public int checkIndex(String identifier){
		int aimedIndex = -1;
		for(Driver drv: driverlist){
			if(drv.toString().equals(identifier))
				aimedIndex = driverlist.indexOf(drv);
		}
		return aimedIndex;					
	}
	/**
	 * get ArrayList where drivers lie
	 */
	public ArrayList getList(){
		return this.driverlist;
	}
	
}
