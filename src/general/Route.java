package general;

import java.util.ArrayList;
/**
 * Route's operations and attributes
 * @author Group36
 * @version 4.1
 *
 */
public class Route {
	/**
	 * including all journeys in this route
	 */
	protected ArrayList<Journey> journeyList;
	/**
	 * including all stations in this route
	 */
	protected ArrayList<Station> stationList;
	/**
	 * size of stationList
	 */
	protected int stationNum;
	/**
	 * Time required to arrive each station
	 */
	protected int[] arriveTime;
	/**
	 * Unique identifier of the route
	 */
	protected String identifier;
	
	/**
	 * constructor, must set the number of stations in this journey
	 * @param stationNum
	 * @param central
	 * @param identifier
	 */
	public Route(int stationNum, Station central, String identifier){
		journeyList = new ArrayList<Journey>();
		stationList = new ArrayList<Station>();
		this.stationNum = stationNum;
		arriveTime = new int[stationNum];
		arriveTime[0] = 0;
		this.identifier = identifier;
	}
	/**
	 * constructor, must set the number of stations in this journey
	 * @param stationNum
	 */
	public Route(int stationNum){
		journeyList = new ArrayList<Journey>();
		stationList = new ArrayList<Station>();
		this.stationNum = stationNum;
		arriveTime = new int[stationNum];
	}
	/**
	 * Add station to Route
	 * @param s station to be added
	 */
	public void setRoute(Station s){
		stationList.add(s);
	}
	/**
	 * Add station to Route, specifying station's index in station list
	 * @param s station to be added
	 */
	public void setRoute(int index, Station s){
		if(stationList.size() < index+1)
			stationList.ensureCapacity(index+3);// a little larger than required
		stationList.add(index,s);
	}
	/**
	 * set the time from central station to station[i]
	 * @param index
	 * @param duration
	 */
	public void setDuration(int index, int duration){
		arriveTime[index] = duration;
	}
	/**
	 * get the arriveTimes when station index specified
	 * @param index
	 * @return
	 */
	public int getArriveTime(int index){
		return arriveTime[index];
	}
	/**
	 * get the time that this route will cover
	 * @return
	 */
	public int getRouteDuration(){
		return arriveTime[stationNum-1];
	}
	/**
	 * get the index of a station in this route
	 * @param s
	 * @return
	 */
	public int getStationIndex(Station s){
		return stationList.indexOf(s);
	}
	/**
	 * get journey by index
	 * @param index
	 * @return
	 */
	public Journey getJourney(int index){
		return (Journey)journeyList.get(index);
	}
	/**
	 * get "this.stationList"
	 * @return
	 */
	public ArrayList getList(){
		return this.stationList;
	}
	/**
	 * get station when index specified
	 * @param index
	 * @return
	 */
	public Station getStation(int index){
		return (Station)stationList.get(index);
	}
	/**
	 * Set identifier
	 * @param identifier
	 */
	public void setIdentifier(String identifier){
		this.identifier = identifier;
	}
	/**
	 * get identifier
	 * @return
	 */
	public String getIdentifier(){
		return this.identifier;
	}
	/**
	 * get identifier
	 * @return
	 */
	public String toString(){
		return this.identifier;
	}
	/**
	 * get the number of stations for this route
	 * @return
	 */
	public int getStationNum(){
		return this.stationNum;
	}
	/**
	 * get the number of journeys in this route
	 * @return
	 */
	public int getJourneyNum(){
		return journeyList.size();
	}
	/**
	 * view basic info of all stations in this journey
	 */
	public void viewStation(){
		System.out.printf("There are %d stations in this route\n",stationNum);//format all-right?
		for(int i=0; i<stationNum; i++){
			System.out.println(stationList.get(i));
		}
	}

	/*public Journey addJourney(int start, int drvID, int trainID, Route route, String identifier,DriverList drlist, TrainList trlist){
		Journey j = new Journey(this.stationNum, start, drvID, trainID, route, identifier, drlist, trlist);
		journeyList.add(j);	
		System.out.println("New journey added.");
		return j;
	}*/
	/** 
	 * set a new journey and save it
	 * @param start
	 * @param drvID
	 * @param trainID
	 * @param route
	 * @param identifier
	 * @param drlist
	 * @param trlist
	 * @return
	 */
	public Journey addJourney(int start, int drvID, int trainID, String identifier, DriverList drlist, TrainList trlist){
		Journey j = new Journey(this.stationNum, start, drvID, trainID, this, identifier, drlist, trlist);
		journeyList.add(j);	
		System.out.println("New journey added.");
		return j;
	}
	/**
	 * add existing journey to route
	 * @param j
	 * @return
	 */
	public Journey addJourney(Journey j){
		journeyList.add(j);
		return j;
	}
	/**
	 * cancel a journey
	 * @param identifier
	 * @param j0
	 * @return
	 */
	public boolean removeJourney(String identifier, Journey j0){
		boolean valid = false;
		Journey aimedJney = null;
		for(Journey j: journeyList){
			if(j.toString().equals(identifier)){
				aimedJney = j;
				break;
			}
		}
		if(aimedJney != null){
			journeyList.remove(aimedJney);
			aimedJney.removeAssignment(j0);
			valid = true;
		}
		return valid;
	}
	/**
	 * print details of the route, including each of the journey info(time, station, etc)
	 * may be modified to support detailed/simple view version later.
	 * @return
	 */
	public String viewRoute(){
		StringBuffer allJourney = new StringBuffer();
		int start; //the time offset from journey's beginning time to 8:00, in minutes 
		int hour; //for calculation
		int minute;//for calculation
		for(Journey j : journeyList){
			start = j.getStartTime();
			allJourney.append(j.viewJourney());
		}
		return allJourney.toString();
	}
}
