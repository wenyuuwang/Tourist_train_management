package general;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Common attributes and operations for a driver and a train
 * @author Group36
 * @version 4.1
 *
 */
public abstract class DriverTrain {
	protected Journey[] assignment = new Journey[6];
	//DriverList drList;
	//TrainList trainList;
	/**
	 * The unique identifier of a train 
	 */
	String identifier = "blank";
	//int id;
	//modify==true when a train is forced stopped and set false when re-started. 
	//late==true once a train is stopped
	/*boolean move, modify=false, late=false;
	int currentStation = -1;
	int stopTime = 0;*/
	
	JPanel selectArea = new JPanel();
	JRadioButton driverButton, trainButton;
	ButtonGroup btg = new ButtonGroup();
	JTextArea taskArea = new JTextArea(5,20);
	JScrollPane jsp = new JScrollPane(taskArea);
	
	//初始化方式1
	/*public DriverTrain(String name, int id, Journey j0){
		this.identifier = name;
		//this.id = id;
		for(int i=0; i<6; i++){ //8-20时，每两小时为一段，记录安排的journey if any			
			assignment[i] = j0;
		}
	}*/
	//初始化方式2
	/*public DriverTrain(int id, Journey j0){
		//this.id = id;
		for(int i=0; i<6; i++){ //8-20时，每两小时为一段，记录安排的journey if any			
			assignment[i] = j0;
		}
	}*/
	
	/**
	 * final adapted initialization
	 * @param identifier The unique dentifier of driver/train
	 * @param j0 used as flag
	 */
	public DriverTrain(String identifier, Journey j0){
		this.identifier = identifier;
		for(int i=0; i<6; i++){ 		
			assignment[i] = j0;
		}
	}
	
	/**
	 * assign journey to driver/train, while verifying availability
	 * @param j The assignment
	 * @param start Time to start the journey
	 * @param j0 used as flag
	 * @return if the assignment is valid
	 */
	public boolean addAssignment(Journey j, int start, Journey j0){
		int routeDuration = j.getRoute().getRouteDuration();
		int end = start + routeDuration;
		int t = start;
		boolean free = true;
		int count = 0;
		//if the selected duration has been occupied
		do{
			if(this.assignment[t/120] == j0){
				t += 120;
				count ++;
			}
			else
				free = false;
			System.out.println("check: t = "+t+", count = "+count);
		}while(t < end && free);
		if(free){
			t = start;
			do{
				this.assignment[t/120] = j;
				t += 120 ;
				count --;
				System.out.println("write: t = "+t+", count = "+count);
			}while(count > 0);
			System.out.println(identifier+": assinment added");
		}else{
			System.out.println(identifier+": already has an assignment at that time");
			System.out.println("Remove current assignemnt or view other avaliable space");
		}	
		return free;
	}
	
	//remove assignment
	/*public void removeAssignment(int t, Journey j0){
		if(this.assignment[t/120] != j0){
			this.assignment[t/120] = j0;
			System.out.println("assinment has been removed from "+ (t/60+8)+ " to "+(t/60+10));
		}else
			System.out.println("No assignment at that time");	
	}*/
	
	/**
	 * remove a journey from driver/train's assignment list
	 * @param removedJney assignment to remove
	 * @param j0 used as flag
	 */
	public void removeAssignemnt(Journey removedJney, Journey j0){
		Journey j;
		for(int i=0; i<6; i++){
			j = getAssignment(i);
			if(j.toString().equals(removedJney.toString()));
				assignment[i] = j0;			
		}
	}
	
	/**
	 * check assignment for one period of time
	 * @param index time
	 * @return assignment at the specified time
	 */
	public Journey getAssignment(int index){
		return assignment[index];
	}
	/**
	 * Override toString to return object's identifier
	 */
	public String toString(){
		return this.identifier;
	}
	//stop train
	/*public String stopTrain(TimeConvertor timecvt, int assumedMin, Journey j0){
		String stopInfo;
		int duration = (int)timecvt.getDuration();
		Journey j = this.assignment[duration/120];
		if(j == j0){
			duration = 0;
			stopInfo = "The train is not working now and need not be stopped";
			JOptionPane.showMessageDialog(null, stopInfo, "message",JOptionPane.INFORMATION_MESSAGE, null);
		}else{	//stop the train, record current time, record currentStation, set flags
			this.move = false;
			modify = true;
			late = true;
			stopTime = duration;
			int covered = (int)duration - j.start ;
			Route r = j.getRoute();
			//find the two stations between which the train is currently located
			for(int i=0; i<r.stationNum-1; i++){ 
				if(covered >=r.arriveTime[i] && covered<=r.arriveTime[i+1]){
					currentStation = i;
					break;
				}
			}
			stopInfo = "Train has been stopped between "+j.route.stationList.get(currentStation)+" and "
					+j.route.stationList.get(currentStation+1)+". \nStop time is being recorded.";
			//System.out.println(stopInfo);
			JOptionPane.showMessageDialog(null, stopInfo, "message",JOptionPane.INFORMATION_MESSAGE, null);
			stopInfo = "Stopped between "+j.route.stationList.get(currentStation)+" and "+j.route.stationList.get(currentStation+1);
		}
		return stopInfo;
	}*/
	//re-start train and renew time-table
	/*public void startTrain(TimeConvertor timecvt, int assumedMin, Journey j0, int stoptime){
		int duration = (int)timecvt.getDuration();
		Journey j = this.assignment[duration/120];
		if(j == j0){
			duration = 0;
			System.out.println("The train is not working now and could not be started.");
		}else{
			this.move = true;			
			//after re-start, renew the time at which this train will arrive at following stations
			for(int i=currentStation+1; i<j.stationNum; i++)
				j.offset[i] += (duration - stoptime);			
		}
	}*/
	/*public String startTrain(TimeConvertor timecvt, Journey j0){
		String startInfo = "denied";
		int duration = (int)timecvt.getDuration();
		Journey j = this.assignment[duration/120];
		if(modify == true){
			this.move = true;
			modify = false;
			for(int i=currentStation+1; i<j.stationNum; i++)
				j.offset[i] += (duration - stopTime);	
			JOptionPane.showMessageDialog(null, "Re-start now\nHeading from Station"+currentStation+" to Station"+(currentStation+1)+"\n"
			+(duration-stopTime)+" Minutes late", "Message",JOptionPane.INFORMATION_MESSAGE, null);
			startInfo = "Heading from "+j.route.stationList.get(currentStation)+" to "+j.route.stationList.get(currentStation+1);
		}else{
			if(j == j0){
				JOptionPane.showMessageDialog(null, "This train has no assignment at present and cannot be started.\n"
						+ "You may like to add a new journey", "message",JOptionPane.INFORMATION_MESSAGE, null);
			}else{
				JOptionPane.showMessageDialog(null, "The train is moving and does not need starting", "message",JOptionPane.INFORMATION_MESSAGE, null);
			}				
		}
		return startInfo;
	}*/
	//打印司机/车辆时间表
	/*public String checkJourney(boolean isDriver){
		StringBuffer resultMessage = new StringBuffer();
		String type = "train";
		if(isDriver)
			type = "driver";
		resultMessage.append("Assignment for "+ type + " "+ this.identifier+"\n");
		for(int i=0; i<6; i++){
			String status = assignment[i].toString();
			//System.out.println("" + (8+i) +":00 - "+(10+i)+":00 " + status);
			resultMessage.append("" + (8+2*i) +":00 - "+(10+2*i)+":00 " + status+"\n") ;
			//System.out.println(assignment.toString());
		}
		resultMessage.append("\n");
		return resultMessage.toString();
	}*/
	/**
	 * Return the information of a journey, including timetable, driver and train
	 * @return
	 */
	public String checkJourney(){
		StringBuffer resultMessage = new StringBuffer();
		for(int i=0; i<6; i++){
			String status = assignment[i].toString();
			//System.out.println("" + (8+i) +":00 - "+(10+i)+":00 " + status);
			resultMessage.append("" + (8+2*i) +":00 - "+(10+2*i)+":00 " + status+"\n") ;
			//System.out.println(assignment.toString());
		}
		resultMessage.append("\n");
		return resultMessage.toString();
	}
	//check next station 
	/*public String getLocation(TimeConvertor timecvt, Journey j0, int assumedMin){
		String locationInfo = "Get Location Error_A";
		int stationIndex = -1;
		long duration = timecvt.getDuration();
		Journey j = this.assignment[(int) (duration/120)];//train's assignment at the moment
		System.out.println(this.identifier + "_"+j.identifier+" start=" + j.start);System.out.flush();
		//1: when the train has no assignment at the moment
		if(j == j0){
			if(stationIndex == -1){
				System.out.println("Not on charge at the moment.");
				locationInfo = "Not on charge at the moment.";
			}else{
				System.out.println("Get location Error");
				locationInfo = "Get location Error";
			}
		}else{
			//if the train is on a journey, find the two stations between which the train is currently located
			int covered = (int)duration - j.start ;
			System.out.println("#"+covered+this.identifier+"Start = "+j.start);System.out.flush();
			Route r = j.getRoute();
			for(int i=0; i<r.stationNum-2; i++){ 
				if(covered >=r.arriveTime[i] && covered<=r.arriveTime[i+1]){
					stationIndex = i;
					break;
				}
			}
			if(late == false){
				//2: when a train is running on time
				 if(stationIndex == -1){	//	has not started or has finished assigned journey in this 120 min
					 System.out.println("Not on charge at the moment.");
						locationInfo = "Not on charge at the moment.";
				 }else{
					 System.out.println("Heading from " + r.stationList.get(stationIndex) 
						+ " to "+r.stationList.get(stationIndex+1));
						locationInfo = "Heading from " + r.stationList.get(stationIndex) 
						+ " to "+r.stationList.get(stationIndex+1);
				 }			
			}else if(modify == true){
				//3: when a train is forced stopped.
				System.out.println("Stopped between " + r.stationList.get(currentStation) 
				+ " and "+r.stationList.get(currentStation+1));
				locationInfo = "Stoppedd between " + r.stationList.get(currentStation) 
				+ " and "+r.stationList.get(currentStation+1);
			}else{
				//4: when a train is re-started
				System.out.println("Heading from " + r.stationList.get(stationIndex) 
				+ " to "+r.stationList.get(stationIndex+1));
				locationInfo = "Heading from " + r.stationList.get(stationIndex) 
				+ " to "+r.stationList.get(stationIndex+1);
			}		
		}
		return locationInfo;
	}*/
}
