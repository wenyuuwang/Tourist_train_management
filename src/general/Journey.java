package general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
/**
 * Journey's operations and attributes
 * @author Group36
 * @version 3.0
 *
 */
public class Journey{
	/**
	 * The difference between actual timetable and predefined timetable, when trains are late.
	 */
	protected int[] offset; 
	/**
	 * Index of the journey's train, in a trainlist
	 */
	protected int train_index;
	/**
	 * Index fo the journey's driver, in a driverlist
	 */
	protected int driver_index;
	/**
	 * The number of stations in the journey
	 */
	protected int stationNum;
	/**
	 * The starting time (in a day) of the journey
	 */
	protected int start;
	/**
	 * The route that this journey belongs to
	 */
	protected Route route;
	/**
	 * Journey's unique identifier
	 */
    String identifier;
    /**
     * System's driverlist
     */
	private DriverList driverlist;
	/**
	 * System's trainlist
	 */
	private TrainList trainlist;
	/**
	 * Constructor
	 * @param stationNum 
	 * @param start
	 * @param identifier
	 */
	public Journey(int stationNum,int start, String identifier){
		offset = new int[stationNum];
		for(int i=0; i<stationNum; i++)
			offset[i] = 0;
		this.stationNum = stationNum;
		this.start = start;
		this.identifier = identifier;
	}
	/**
	 * Constructor, with all journey information specified
	 * @param stationNum
	 * @param start
	 * @param driver
	 * @param train
	 * @param route
	 * @param identifier
	 * @param drlist
	 * @param trlist
	 */
	public Journey(int stationNum, int start, int driver, int train, Route route, String identifier, DriverList drlist, TrainList trlist){
		offset = new int[stationNum];
		for(int i=0; i<stationNum; i++)
			offset[i] = 0;
		this.stationNum = stationNum;
		this.start = start;
		this.driver_index = driver;
		this.train_index = train; 
		this.route = route;
		this.identifier = identifier;
		this.driverlist = drlist;
		this.trainlist = trlist;
	}
	/**
	 * Set train index for journey
	 * @param id
	 */
	public void setTrainID(int id){
		this.train_index = id;
	}
	/**
	 * Get train index of the journey
	 * @return
	 */
	public int getTrainID(){
		return this.train_index;
	}
	/**
	 * Set identifier for train
	 * @param new_ident
	 */
	public void setIdentifier(String new_ident){
		this.identifier = new_ident;
	}
	/**
	 * Set train index for journey
	 * @param id
	 */
	public void setDriverID(int id){
		this.driver_index = id;
	}
	/**
	 * Get driver index of the journey
	 * @return
	 */
	public int getDriverID(){
		return this.driver_index;
	}
	/**
	 * Specify the route that this journey belong to
	 * @param route
	 */
	public void setRoute(Route route){
		this.route = route;		
	}
	/**
	 * Get journey's route
	 * @return
	 */
	public Route  getRoute(){
		return this.route;
	}
	/**
	 * Check the time that the joureny is to start
	 * @return
	 */
	public int getStartTime(){
		return this.start;
	}
	/**
	 * show if the train will arrive late
	 * @return
	 */
	public int checkLate(){
		return offset[stationNum-1];
	}
	/**
	 * Get the number of stations for journey
	 * @return
	 */
	public int stationNum(){
		return stationNum;
	}
	/**
	 * Override to return the identifier of journey
	 */
	public String toString(){
		return this.identifier;
	}
	/**
	 * return detailed info of the journey
	 * @return
	 */
	public String viewJourney(){
		int start; //the time offset from journey's beginning time to 8:00, in minutes 
		int hour; //for calculation
		int minute;//for calculation
		StringBuffer journeyInfo = new StringBuffer();
		String temp = null;
		start = this.getStartTime();
		for(int i=0; i<stationNum; i++){
			journeyInfo.append(this.route.stationList.get(i)+"  ");
		}
		journeyInfo.append("\n");
		for(int i=0; i<stationNum; i++){
			hour = (start+this.route.arriveTime[i]+this.offset[i])/60;
			minute = (start+this.route.arriveTime[i]+this.offset[i])%60;
			//System.out.printf(" %02d:%02d    ", 8+hour, minute );
			temp = String.format(" %02d:%02d    ", 8+hour, minute );
			journeyInfo.append(temp);
		}
		journeyInfo.append("\n");
		journeyInfo.append("Name: " + this.identifier+"\n"+"Train:" + (Train)trainlist.get(train_index)+"\n"+
				"Driver:"+ (Driver)driverlist.get(driver_index)+"\n\n");
		return journeyInfo.toString();
	}
	/**
	 * remove assignment of driver and train when the journey is canceled.
	 * @param j0 used as flag
	 */
	public void removeAssignment(Journey j0){
		DriverTrain dr = (Driver)driverlist.get(getDriverID());
		dr.removeAssignemnt(this, j0);
		DriverTrain tr = (Train)trainlist.get(getTrainID());
		tr.removeAssignemnt(this, j0);
		System.out.println("Assignment has been canceled.");
	}
}
