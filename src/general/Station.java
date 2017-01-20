package general;

import java.util.ArrayList;
/**
 * Station's operations and attributes
 * @author Group36
 * @version 3.0
 *
 */

public class Station {
	/**
	 * including all journey that come across this station
	 */
	private ArrayList<Journey> stationJourney;  
	/**
	 * including all route that come across this station
	 */
	private ArrayList<Route> stationRoute;
	/**
	 * unique identifier of the station
	 */
	private String name;
	/**
	 * Flag
	 */
	Journey j0;
	
	/**
	 * constructor
	 * @param name
	 * @param j0
	 */
	public Station(String name, Journey j0){
		this.name = name;
		stationJourney = new ArrayList<Journey>();	
		stationRoute = new ArrayList<Route>();
	}
	/**
	 * when a new journey passes the station
	 * @param j
	 */
	public void addJourney(Journey j){
		stationJourney.add(j);
	}
	/**
	 * when a new route passes the station
	 * @param r
	 */
	public void addRoute(Route r){
		stationRoute.add(r);
	}
	/**
	 * when a journey that previously passes the station is now removed
	 * @param j
	 */
	public void removeJourney(Journey j){
		stationJourney.remove(j);
	}
	/**
	 * when a route that previously passes the station is now removed
	 * @param r
	 */
	public void removeRoute(Route r){
		stationRoute.remove(r);
	}
	/**
	 * return identifier of the station
	 */
	public String toString(){
		return this.name;
	}
	/**
	 * find the index of this station in a route
	 * @param r 
	 * @return
	 */
	public int getIndexinRoute(Route r){
		int index = 0;
		for(int i=0; i<r.stationNum; i++){
			if(r.stationList.get(i).toString() == this.name){
				index = i;
				break;
			}
		}
		return index;
	}
	/**
	 * find the time for a train to arrive this station
	 * @param j Journey that we are now examining
	 * @param timecvt system's time
	 * @param assumedDuration abolished in this version
	 * @return
	 */
	public long timetoArrive(Journey j, TimeConvertor timecvt, long assumedDuration){
		int durationA, durationB;
		int stationIndex = this.getIndexinRoute(j.route);
		int currentDuration = (int)timecvt.getDuration();		
		//leave from central;
		int arrTime = j.route.arriveTime[stationIndex];
		//int station_index = j.route.getStationIndex(this);
		durationA = j.start + arrTime+j.offset[stationIndex];		
		if(durationA < currentDuration)
			durationA = Integer.MAX_VALUE;
		else 
			durationA = durationA - currentDuration;
		//go back to central
		stationIndex = j.route.getStationNum() - stationIndex -1;
		arrTime = j.route.arriveTime[stationIndex];
		durationB = j.start + arrTime+j.offset[stationIndex]; 
		if(durationB < currentDuration)
			durationB = Integer.MAX_VALUE;
		else 
			durationB = durationB - currentDuration;
		//System.out.println("A="+durationA+"B="+durationB);
		return Integer.min(durationA, durationB);		
	}
	/**
	 * find the journey (within a route) that is most close to current time
	 * @param r
	 * @param timecvt
	 * @param assumedDuration
	 * @return
	 */
	public Journey checkRecent(Route r, TimeConvertor timecvt, long assumedDuration){
		Journey j = j0;
		int duration;
		//set 1st route as reference
		if(r.getJourneyNum() > 0){
			j = r.journeyList.get(0);
			duration = (int) timetoArrive(j, timecvt, assumedDuration);
			//find the most recent one
			if(r.getJourneyNum() > 1){
				Journey j_temp;
				int duration_temp;
				for (int i=1; i<r.journeyList.size(); i++){			
					j_temp = r.journeyList.get(i);
					duration_temp = (int)timetoArrive(j_temp, timecvt, assumedDuration);
					if( duration_temp < duration){
						j = j_temp;	
						duration = duration_temp;
					}
				}
			}
			System.out.println("duraiton="+duration+", route="+r.getIdentifier());
		}else{
			System.out.println("no journey for "+r.getIdentifier());
		}
		return j;
	}
	/**
	 * find the next coming train, by comparing each route
	 * @param timecvt
	 * @param assumedDuration
	 * @return
	 */
	public String checkNext(TimeConvertor timecvt, long assumedDuration){
		StringBuffer nextInfo = new StringBuffer();
		Journey j = j0;
		long duration = -11;
		if(stationRoute.size() > 0){
			j = checkRecent(this.stationRoute.get(0), timecvt, assumedDuration);
			duration = timetoArrive(j, timecvt, assumedDuration);
			if(stationRoute.size() > 1){
				Journey j_temp;
				long duration_temp;
				for(int i=1; i<this.stationRoute.size(); i++){
					j_temp = checkRecent(this.stationRoute.get(i), timecvt, assumedDuration);
					duration_temp = timetoArrive(j_temp, timecvt, assumedDuration);
					if( duration_temp < duration){
						j = j_temp;	
						duration = duration_temp;
					}
				}
			}
			
		}
		if(j == j0){
			nextInfo.append("The station currently has no routes.");
		}else if(duration == Integer.MAX_VALUE){
			nextInfo.append("No trains are coming to this station today");
		}else{
			nextInfo.append(timecvt.getCalendar().getTime());
			nextInfo.append("\n\nTime to arrive: " + duration + "min\n\n") ;
			//System.out.println(nextInfo);
			j.viewJourney();
			nextInfo.append(j.viewJourney());
		}
		
		return nextInfo.toString();
	}
}
