package general;

import javax.swing.JOptionPane;
/**
 * Train's operations and attributes
 * @author Group36
 * @version 3.0
 *
 */
public class Train extends DriverTrain{
	/**
	 * if the train is moving or stopped
	 */
	private boolean move;
	/**
	 * if the train is forced stopping now
	 */
	private boolean modify=false;
	/**
	 * if the train's timetable has been modified
	 */
	private boolean late=false;
	private int currentStation = -1;
	/**
	 * calculating how long has the train been forced stopped
	 */
	private int stopTime = 0;
	/**
	 * journey that the train is serving
	 */
	private Journey currentJney;
	/**
	 * Constructor
	 * @param identifier
	 * @param j0
	 */
	public Train(String identifier, Journey j0) {
		super(identifier, j0);		
	}
	/**
	 * View all assignments of a train
	 */
	public String checkJourney(){
		String resultMessage;
		resultMessage = "Assignment for Train [ "+ this.identifier+" ] \n";
		resultMessage += super.checkJourney();
		return resultMessage;
	}
	/**
	 * Get location of a train
	 * @param timecvt passes in the current simulated time
	 * @param j0
	 * @param assumedMin abolished in this version
	 * @return
	 */
	public String getLocation(TimeConvertor timecvt, Journey j0, int assumedMin){
		String locationInfo = "Get Location Error_A";
		int stationIndex = -1;
		long duration = timecvt.getDuration();
		Journey j = this.assignment[(int) (duration/120)];//train's assignment at the moment
		System.out.println(this.identifier + "_"+j.identifier+" start=" + j.start);System.out.flush();
		int covered;
		Route r = null;
		if(j != j0){
			covered = (int)duration - j.start ;
			System.out.println("#"+covered+this.identifier+"Start = "+j.start);System.out.flush();
			r = j.getRoute();
			for(int i=0; i<r.stationNum-2; i++){ 
				if(covered >=r.arriveTime[i] && covered<=r.arriveTime[i+1]){
					stationIndex = i;
					break;
				}
			}
		}
		if(modify == true){	
					//1: when a train is forced stopped
					System.out.println("currently stopint at "+currentStation);
					System.out.println("Stopped between " + currentJney.getRoute().getStation(currentStation) 
					+ " and "+currentJney.getRoute().getStation(currentStation+1));
					locationInfo = "Stoppedd between " + currentJney.getRoute().getStation(currentStation) 
					+ " and "+currentJney.getRoute().getStation(currentStation+1);
		}else if(j == j0){//when a train is running on time
			if(stationIndex == -1){//3: when the train has no assignment at the moment
				System.out.println("Not on charge at the moment.");
				locationInfo = "Not on charge at the moment.";
			}else{
				System.out.println("Get location Error");
				locationInfo = "Get location Error";
			}
		}else{//4: if the train is on a journey, find the two stations between which the train is currently located
			 if(stationIndex == -1){	//	has not started or has finished assigned journey in this 120 min
				 System.out.println("Not on charge at the moment.");
					locationInfo = "Not on charge at the moment.";
			 }else{//when a train is running on time or restarted
				 System.out.println("Heading from " + r.getStation(stationIndex) 
					+ " to "+r.getStation(stationIndex+1));
					locationInfo = "Heading from " + r.getStation(stationIndex) 
					+ " to "+r.getStation(stationIndex+1);
			 }			
			
		}
		return locationInfo;
	}
	
	/**
	 * stop train and start to record stopping time
	 * @param timecvt
	 * @param assumedMin
	 * @param j0
	 * @return
	 */
	public String stopTrain(TimeConvertor timecvt, int assumedMin, Journey j0){
		String stopInfo;
		int duration = (int)timecvt.getDuration();
		Journey j = this.assignment[duration/120];
		if(j == j0 ){
			duration = 0;
			stopInfo = "The train is not working now and need not be stopped";
			JOptionPane.showMessageDialog(null, stopInfo, "message",JOptionPane.INFORMATION_MESSAGE, null);
		}else{	//stop the train, record current time, record currentStation, set flags
			this.move = false;
			stopTime = duration;
			int covered = (int)duration - j.start ;
			if(duration-j.start <0){
				stopInfo = "The train is not working now and need not be stopped";
				JOptionPane.showMessageDialog(null, "Reminder:\n"+stopInfo, "message",JOptionPane.INFORMATION_MESSAGE, null);
			}else{
				Route r = j.getRoute();
				//find the two stations between which the train is currently located
				for(int i=0; i<r.stationNum-1; i++){ 
					System.out.println("loop: time = "+ r.arriveTime[i]+" and "+r.arriveTime[i+1]);
					if(covered >=r.arriveTime[i] && covered<=r.arriveTime[i+1]){
						currentStation = i;
						break;
					}
				}
				System.out.println("i = "+ currentStation);
				currentJney = j;
				stopInfo = "Train has been stopped between "+j.route.stationList.get(currentStation)+" and "
						+j.route.stationList.get(currentStation+1)+". \nStop time is being recorded.";
				//System.out.println(stopInfo);
				JOptionPane.showMessageDialog(null, stopInfo, "message",JOptionPane.INFORMATION_MESSAGE, null);
				stopInfo = "Stopped between "+j.route.stationList.get(currentStation)+" and "+j.route.stationList.get(currentStation+1);
				modify = true;
				late = true;
			}
		}
		return stopInfo;
	}

	/**
	 * re-start train and renew time-table	
	 * @param timecvt
	 * @param j0
	 * @return
	 */
	public String startTrain(TimeConvertor timecvt, Journey j0){
		String startInfo = "denied";
		int duration = (int)timecvt.getDuration();
		Journey j = this.assignment[duration/120];
		if(modify == true){
			this.move = true;
			modify = false;
			for(int i=currentStation+1; i<j.stationNum(); i++)
				currentJney.offset[i] += (duration - stopTime);	
			JOptionPane.showMessageDialog(null, "Re-start now\nHeading from Station"+currentStation+" to Station"+(currentStation+1)+"\n"
			+(duration-stopTime)+" Minutes late", "Message",JOptionPane.INFORMATION_MESSAGE, null);
			startInfo = "Heading from "+currentJney.route.getStation(currentStation)+" to "+currentJney.route.getStation(currentStation+1);
		}else{
			if(j == j0){
				JOptionPane.showMessageDialog(null, "This train has no assignment at present and cannot be started.\n"
						+ "You may like to add a new journey", "message",JOptionPane.INFORMATION_MESSAGE, null);
			}else if(duration < j.getStartTime()){
				JOptionPane.showMessageDialog(null, "Reminder:\nThis train has no assignment at present and cannot be started.\n"
						+ "You may like to add a new journey", "message",JOptionPane.INFORMATION_MESSAGE, null);
			}else{
				JOptionPane.showMessageDialog(null, "The train is moving and does not need starting", "message",JOptionPane.INFORMATION_MESSAGE, null);
			}				
		}
		return startInfo;
	}
}

