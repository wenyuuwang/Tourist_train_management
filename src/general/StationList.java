package general;

import java.util.ArrayList;
/**
 * ArrayList in the class contains all stations in the system
 * @author Group36
 * @version 3.0
 *
 */
public class StationList {
	private ArrayList<Station> stationList;
	/**
	 * Constructor
	 */
	public StationList(){
		stationList = new ArrayList<Station>();
	}
	/**
	 * get station list 
	 * @return
	 */
	public ArrayList getList(){
		return stationList;
	}
	/**
	 * add Station to list
	 * @param s
	 */
	public void addStation(Station s){
		stationList.add(s);
	}
	/**
	 * add Station to list, with position specified
	 * @param index
	 * @param s
	 */
	public void addStation(int index, Station s){
		if(stationList.size() < index+1)
			stationList.ensureCapacity(index+3);
		stationList.add(index, s);			
	}
	/**
	 * get station from list, with respect to index
	 * @param index
	 * @return
	 */
	public Station getStation(int index){
		return stationList.get(index);
	}
	/**
	 * get station from list, with respect to name
	 * @param name
	 * @return
	 */
	public int getIndexbyName(String name){
		int aimedIndex = -2;
		for(int i=0; i<stationList.size()-1; i++){
			if(stationList.get(i).toString().equals(name)){
				//s = stationList.get(i);
				aimedIndex = i;
				break;
			}
		}
		return aimedIndex;
	}
	/**
	 * get the number of stations in list
	 * @return
	 */
	public int stationNum(){
		return stationList.size();
	}
	/**
	 * get the index of a Station, specified by name
	 * @param name
	 * @return
	 */
	public int getStationIndex(String name){
		int aimedIndex = -1;
		for(int i=0; i<stationList.size()-1; i++){
			if(stationList.get(i).toString().equals(name)){
				aimedIndex = i;
				break;
			}
		}
		return aimedIndex;
	}

}
