package interaction;


import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Calendar;

import javax.swing.*;

import general.*;


@SuppressWarnings("unchecked")
/**
 * Customer's command conveyed by GUI is accepted, then a corresponding computation is triggered.
 * Boundary class in the system. 
 * @author wendywang
 * @version
 */
public class BuildFrame extends JFrame implements ActionListener, ItemListener{

	/**
	 * A flag to indicate "no assignment" for driver and train
	 */
	static Journey j0 = new Journey(3,1,"blank");//as a flag
	/**
	 * All drivers in the system
	 */
	static DriverList driverlist = new DriverList();
	/**
	 * All trains in the system
	 */
	static TrainList trainlist = new TrainList();
	/**
	 * All routes in the system
	 */	
	static RouteList routelist = new RouteList();
	/**
	 * All stations in the system
	 */
	static StationList stationlist = new StationList();
	/**
	 * A system timer
	 */
	static TimeConvertor timecvt = new TimeConvertor();
	/**
	 * GUI component to view train/driver assignment
	 */
	JPanel selectArea = new JPanel();
	/**
	 * GUI component to view train/driver assignment
	 */
	JRadioButton driverButton, trainButton;
	/**
	 * GUI component to view train/driver assignment
	 */
	JButton quitButton;
	/**
	 * GUI component to view train/driver assignment
	 */
	ButtonGroup btg = new ButtonGroup();
	/**
	 * GUI component to view train/driver assignment
	 */
	JTextArea taskArea = new JTextArea(20,20);
	/**
	 * GUI component to view train/driver assignment
	 */
	JScrollPane jsp = new JScrollPane(taskArea);
	/**
	 * GUI component for manipulating journey operation
	 */
	JPanel selectRoute = new JPanel();
	/**
	 * GUI component for manipulating journey operation
	 */
	JComboBox allRoutes = new JComboBox();
	/**
	 * GUI component for manipulating journey operation
	 */
	JTextArea routeInfo = new JTextArea(20,20);
	/**
	 * GUI component for manipulating journey operation
	 */
	JScrollPane jspRoute = new JScrollPane(routeInfo);
	/**
	 * GUI component for manipulating journey operation
	 */
	JButton view, add, confirmJney, rmvJney;
	/**
	 * GUI component for manipulating journey operation
	 */
	JPanel jneyRequest = new JPanel(); //add new journey
	/**
	 * GUI component for manipulating journey operation
	 */
	JTextField inputName, inputStart, inputDr, inputTr;
	/**
	 * Flag to indicate if "add" button has been clicked
	 */
	boolean addFlag = false;
	/**
	 * GUI component to stop or restart train
	 */
	JPanel stopStart = new JPanel();
	/**
	 * GUI component to stop or restart train
	 */
	JPanel trainArea = new JPanel();
	/**
	 * GUI component to stop or restart train
	 */
	JButton stop, start, setTime;
	/**
	 * GUI component to show train's identifier
	 */
	JRadioButton allTrains[];
	/**
	 * GUI component to indicate location and state of train
	 */
	JLabel allTrainsInfo[];
	/**
	 * GUI component for driver work
	 */
	JPanel driverAct = new JPanel();
	/**
	 * GUI component for driver work
	 */
	JPanel driverView = new JPanel();
	/**
	 * GUI component for driver to stop or restart a train
	 */
	JButton drStop;
	/**
	 * GUI component for driver to see the train he currently serves
	 */
	JLabel drTime, drTrainInfo;
	/**
	 * GUI component for driver to see his current assignment
	 */
	JTextArea drAssignment;
	/**
	 * The train that a driver currently serves
	 */
	Train train;
	/**
	 * The journey that a driver currently serves
	 */
	Journey drJney;
	//gui comps for user check next
	/**
	 * GUI component for user to check next train that will come to a specified station
	 */
	JPanel usrSelect = new JPanel();
	/**
	 * GUI component for user to check next train that will come to a specified station
	 */
	JPanel usrView = new JPanel();
	/**
	 * GUI component for user to check next train that will come to a specified station
	 */
	JComboBox  allStations = new JComboBox();
	/**
	 * GUI component for user to check next train that will come to a specified station
	 */
	JTextArea stationInfo = new JTextArea(20,30);
	/**
	 * GUI component for user to check next train that will come to a specified station
	 */
	JScrollPane jspStation = new JScrollPane(stationInfo);
	/**
	 * GUI component for user to check next train that will come to a specified station
	 */
	JButton usrStation;
	//gui comps for user search journey
	/**
	 * GUI component for user to search available journeys when a starting station and destination are specified
	 */
	JPanel usrLocations = new JPanel();
	/**
	 * GUI component for user to search available journeys when a starting station and destination are specified
	 */
	JButton search;
	/**
	 * GUI component for user to search available journeys when a starting station and destination are specified
	 */
	JComboBox from, to;
	/**
	 * GUI component for user to search available journeys when a starting station and destination are specified
	 */
	JTextArea journeyInfo = new JTextArea(20,20);
	/**
	 * GUI component for user to search available journeys when a starting station and destination are specified
	 */
	JScrollPane jspJourney = new JScrollPane(journeyInfo);
	//gui comps for add route
	/**
	 * GUI component for manager to add a new route to system
	 */
	JPanel routeControl = new JPanel();
	/**
	 * GUI component for manager to add a new route to system
	 */
	JPanel routeTime = new JPanel();
	/**
	 * GUI component for manager to add a new route to system
	 */
	JPanel setStations = new JPanel();
	/**
	 * GUI component for manager to add a new route to system
	 */
	JPanel setDurations = new JPanel();
	/**
	 * GUI component for manager to add a new route to system
	 */
	JButton confirmStation, confirmDuration;
	/**
	 * GUI component for manager to add a new route to system
	 */
	JCheckBox stations[];//all stations
	/**
	 * GUI component for manager to add a new route to system
	 */
	int selectedStations[];//selected stations,with running back
	/**
	 * GUI component for manager to add a new route to system
	 */
	JTextField durations[];//set duration
	/**
	 * as a flag
	 */
	boolean isManager = false;
//******************************************************************
	/**
	 * Constructor
	 */
	public BuildFrame(){
		addWindowListener(new WL());
	}
	//inner class
	class WL extends WindowAdapter{ 
		public void windowClosing(WindowEvent we){
			getContentPane().removeAll();
			selectRoute.removeAll();
			stopStart.removeAll();
			trainArea.removeAll();
			driverAct.removeAll();
			driverView.removeAll();
			usrSelect.removeAll();
			usrView.removeAll();
			usrLocations.removeAll();
			routeTime.removeAll();
			setDurations.removeAll();
			revalidate();
			setVisible(false);
			System.out.println("close window");
		}
	}
	/**
	 * Set up GUI components for loading system
	 */
	public void setGUI(){
		guiPreset_drtr();
		//guiPreset_journey();
		guiPreset_newJourney();
		//guiPreset_stopStart();
		//guiPreset_nextTrain();
		guiPreset_addRoute();
	}
//******************************************************************
	/**
	 * Read station information from file and write to system
	 */
	public void setupStation() {
		String stationInfo;
		int count = -1;		
		try {
			BufferedReader stationReader = new BufferedReader(new FileReader("record/station.txt")); 
			stationInfo = stationReader.readLine();
			while(stationInfo != null){				
				count ++;
				stationlist.addStation(count, new Station(stationInfo, j0));
				stationInfo = stationReader.readLine();
			}
			stationReader.close();
			System.out.println("Station settings done");
		}catch (IOException e) {
				System.out.println("Reading station settings error");
				e.printStackTrace();
		}
	}
	/**
	 * Read route information from file and write to system
	 */
	public void setupRoute(){
		int size = -1;
		int station = -1;
		int[] stationIndex;
		int[] duration;
		String identifier;
		int count = -1;	//marking the index of route in list; only for initializing phase
		String routeInfo;
		String[] divided = new String[4];
		String[] stationBuffer;
		try {
			BufferedReader routeReader = new BufferedReader(new FileReader("record/route.txt"));
			routeInfo = routeReader.readLine();
			while(routeInfo != null){
				count ++;
				divided = routeInfo.split("#");
				size = Integer.parseInt(divided[0]);	//number of stations for this route
				stationBuffer = new String[size];
				stationIndex= new int[size];	//array recording station index
				stationBuffer = divided[1].split("&");
				for(int i=0; i<size; i++)	
					stationIndex[i] = Integer.parseInt(stationBuffer[i]);
				duration = new int[size];	//array recording durations
				stationBuffer = divided[2].split("&");
				for(int i=0; i<size; i++)
					duration[i] = Integer.parseInt(stationBuffer[i]);
				identifier = divided[3];
				//create routes and save
				routelist.add(new Route(size));
				((Route)routelist.get(count)).setIdentifier(identifier);
				for(int i=0; i<size; i++){	//add stations to routes
					station = stationIndex[i];
					((Route)routelist.get(count)).setRoute(i, stationlist.getStation(station));
					((Route)routelist.get(count)).setDuration(i, duration[i]);
				}		
				for(int i=0; i<(size+1)/2; i++){	//add route info to station
					station = stationIndex[i];
					stationlist.getStation(station).addRoute((Route)routelist.get(count));
				}
				//check if there are any other routes
				routeInfo = routeReader.readLine();
			}
			routeReader.close();
			System.out.println("Route settings done");
		} catch (IOException e) {
			System.out.println("Failed to read route settings.");
			e.printStackTrace();
		}
	}
	/**
	 * Read driver and train information from file and write to system
	 */
	public void setupDrTr(){	
		try {
			BufferedReader drtrReader = new BufferedReader(new FileReader("record/driverTrain.txt"));
			//driver info
			String[]  drBuffer = drtrReader.readLine().split("#");
			int drNum = Integer.parseInt(drBuffer[0]);
			String[] drName = drBuffer[1].split("&");
			for(int i=0; i<drNum; i++)
				driverlist.add(new Driver(drName[i], j0));
			//train info
			String[] trBuffer = drtrReader.readLine().split("#");
			int trNum = Integer.parseInt(trBuffer[0]);
			String[] trName = trBuffer[1].split("&");
			for(int i=0; i<trNum; i++)
				trainlist.add(new Train(trName[i], j0));
			//close
			drtrReader.close();
			System.out.println("driver/train settings done");
		} catch (IOException e) {
			System.out.println("Failed to read drtr settings");
			e.printStackTrace();
		}
	}
	
//******************************************************************
	/**
	 * Read journey information from file and write to system
	 */
	public void setupJourney(){
		int routeIndex = -1;
		int drIndex = -1;
		int trIndex = -1;
		int startTime = -1;
		String identifier;
		Journey j;
		String jneyBuffer;	
		String[] divided;
		try {
			BufferedReader jneyReader = new BufferedReader(new FileReader("record/journey.txt"));
			jneyBuffer = jneyReader.readLine();
			while(jneyBuffer != null){
				divided = jneyBuffer.split("#");
				routeIndex = Integer.parseInt(divided[0]);
				drIndex = Integer.parseInt(divided[1]);
				trIndex = Integer.parseInt(divided[2]);
				startTime = Integer.parseInt(divided[3]);
				identifier = divided[4];
				j = ((Route)routelist.get(routeIndex)).addJourney(startTime, drIndex, trIndex, identifier, driverlist, trainlist);
				((Driver)driverlist.get(drIndex)).addAssignment(j, startTime, j0);
				((Train)trainlist.get(trIndex)).addAssignment(j, startTime, j0);
				jneyBuffer = jneyReader.readLine();
			}
			jneyReader.close();
			System.out.println("journey settings done");
		} catch (IOException e) {
			System.out.println("failed to read journey settingss");
			e.printStackTrace();
		}
		
	}
	/**
	 * System initialization. Load file information.
	 */
	public void setupSystem(){
		//set up routes
		setupStation();
		setupRoute();
		setupDrTr();
		setupJourney();
	}
	/**
	 * Interface for manager to remove a journey, with checking 
	 */
	public void removeJourney(){
		boolean valid = false;
		Route selectedRoute = (Route)routelist.get(allRoutes.getSelectedIndex());
		String identifier = JOptionPane.showInputDialog(null, "You choose "+selectedRoute.toString()+"\n"
		+"Please input the identifier of journey to delete:\n\n", "Route#_Journey#");
		valid = selectedRoute.removeJourney(identifier, j0);
		String message;
		if(valid){
			message = "Journey has been removed. "
			+"\nAssignment on driver and train has been removed";
		}else
			message = "No such journey";
		JOptionPane.showMessageDialog(null, message, "Message",JOptionPane.INFORMATION_MESSAGE, null);				
	}
	/**
	 * Add journey to route, write new journey and assignment to data repository
	 */
	public void addJourney(){
		Route selectedRoute = (Route)routelist.get(allRoutes.getSelectedIndex());
		String identifier = inputName.getText();
		String start = inputStart.getText();
		int dr = driverlist.checkIndex(inputDr.getText());
		int tr = trainlist.checkIndex(inputTr.getText());
		String[] time = start.split(":");
		int startTime = (Integer.parseInt(time[0])-8)*60 + (Integer.parseInt(time[1]));
		//check if there has already been a journey at that time
		boolean newJourney = true;
		for(int i=0; i<selectedRoute.getJourneyNum(); i++){
			if(selectedRoute.getJourney(i).getStartTime() == startTime){
				newJourney = false;
				break;
			}
		}
		String message;
		if(newJourney == false){
			message = "There has already been a journey at the time you set";
			message += "\nPlease change start time";
			JOptionPane.showMessageDialog(null, message, "message",JOptionPane.INFORMATION_MESSAGE, null);
		}else{
			Journey j = new Journey(selectedRoute.getStationNum(), startTime, dr, tr, selectedRoute, selectedRoute.getIdentifier()+"_"+identifier, driverlist, trainlist);
			//check if train/driver are free to take new assignment
			boolean drFree = ((Driver)driverlist.get(dr)).addAssignment(j, startTime, j0);
			boolean trFree = ((Train)trainlist.get(dr)).addAssignment(j, startTime, j0);	
			if(drFree && trFree){//modify route, train, driver and station info
				Station s;
				for(int i=0; i<selectedRoute.getStationNum(); i++){
					 s = (Station)selectedRoute.getList().get(i);
					 s.addJourney(j);
				}
				selectedRoute.addJourney(j);
				message = "A new journey has been set";
				JOptionPane.showMessageDialog(null, message, "message",JOptionPane.INFORMATION_MESSAGE, null);
			}else{
				message = "Driver/train has already got assignment at that time\n";
				message += "Remove current assignemnt or view other avaliable space";
				JOptionPane.showMessageDialog(null, message, "message",JOptionPane.INFORMATION_MESSAGE, null);
			}
		}
	}
	/**
	 * Get manager's request and assignment for a new route
	 */
	public void guiPreset_newJourney(){
		inputName = new JTextField("Journey1");
		inputStart = new JTextField("08:00");
		inputDr = new JTextField("DR_A");
		inputTr = new JTextField("TR_A");
		JButton viewDr = new JButton("View");
		JButton viewTr = new JButton("View");
		viewDr.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFrame tempWindow = new JFrame();
				JTextArea drInfo = new JTextArea();
				JScrollPane jspDrInfo = new JScrollPane(drInfo);
				drInfo.setText(viewDriver());
				tempWindow.add(jspDrInfo);
				tempWindow.setTitle("Driver Assignment");
				int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2;
				int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 100) / 2;
				tempWindow.setLocation(w, h);
				tempWindow.setSize(300,300);
				tempWindow.setVisible(true);
			}			
		});
		viewTr.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFrame tempWindow = new JFrame();
				JTextArea drInfo = new JTextArea();
				JScrollPane jspDrInfo = new JScrollPane(drInfo);
				drInfo.setText(viewTrain());
				tempWindow.add(jspDrInfo);
				tempWindow.setTitle("Train Assignment");
				int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2;
				int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 100) / 2;
				tempWindow.setLocation(w, h);
				tempWindow.setSize(300,300);
				tempWindow.setVisible(true);
			}			
		});	
		JPanel jneyRequest1 = new JPanel();
		jneyRequest1.setLayout(new GridLayout(2,2));
		jneyRequest1.add(new JLabel("Journey Name: "));
		jneyRequest1.add(inputName);
		jneyRequest1.add(new JLabel("Start time: "));
		jneyRequest1.add(inputStart);
		JPanel jneyRequest2 = new JPanel();
		jneyRequest2.setLayout(new GridLayout(2,3));
		jneyRequest2.add(new JLabel("Driver ID: "));
		jneyRequest2.add(inputDr);
		jneyRequest2.add(viewDr);
		jneyRequest2.add(new JLabel("Train ID: "));
		jneyRequest2.add(inputTr);
		jneyRequest2.add(viewTr);
		jneyRequest.setLayout(new GridLayout(3,1));
		jneyRequest.add(jneyRequest1);
		jneyRequest.add(jneyRequest2);
		//add(jneyRequest,BorderLayout.CENTER);
		//jneyRequest.setVisible(false);
	}
	/**
	 *  Journey operaitons. Manager may choose a route to check assignment or add journey
	 */
	public void guiPreset_journey(){
		allRoutes.removeAllItems();
		for(int i=0; i<routelist.size(); i++)
			allRoutes.addItem(((Route)routelist.get(i)).getIdentifier());
		allRoutes.addItemListener(this);
		view = new JButton("View");
		add = new JButton("Add Journey");
		confirmJney = new JButton("Confirm");
		rmvJney = new JButton("Remove");
		view.addActionListener(this);
		add.addActionListener(this);
		confirmJney.addActionListener(this);
		rmvJney.addActionListener(this);
		JLabel title = new JLabel("Select Route:");
		selectRoute.add(title);
		selectRoute.add(allRoutes);
		selectRoute.add(view);
		selectRoute.add(add);
		//selectRoute.add(confirmRoute);
		selectRoute.add(rmvJney);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2;
		setLocation(w, h);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(550,400);
		this.setVisible(false);
	}
	/**
	 * GUI for a user to view all journeys in system.
	 */
	public void userJourney(){
		isManager = false;
		guiPreset_journey();
		selectRoute.remove(add);
		selectRoute.remove(confirmJney);
		selectRoute.remove(rmvJney);
		add(selectRoute, BorderLayout.NORTH);
		add(jspRoute, BorderLayout.CENTER);
		setVisible(true);
	}
	//select a route to view all journeys, add new journey to route
	/**
	 * Interface for connecting manager and inside data repository. 
	 * Manager can select a route to view all journeys, add new journey to route
	 */
	public void journeyOperation(){
		isManager = true;
		guiPreset_journey();
		add(selectRoute, BorderLayout.NORTH);
		add(jspRoute, BorderLayout.CENTER);
		setVisible(true);
	}
	/**
	 * no operation to do with item events
	 */
	public void itemStateChanged(ItemEvent e){
	}
//******************************************************************
	/**
	 * Manager view all drivers' assignment
	 * @return assignment information
	 */
	public String viewDriver(){
		Driver drv_temp;
		int num = driverlist.size();
		StringBuffer driverMessage = new StringBuffer();
		for(int i=0; i<num; i++){
			drv_temp = (Driver) driverlist.getList().get(i);
			driverMessage.append(drv_temp.checkJourney());
		}
		//JOptionPane.showMessageDialog(null, driverMessage, "message",JOptionPane.INFORMATION_MESSAGE, null);
		return driverMessage.toString();
	}
	/**
	 * Driver view his own assignment
	 */
	public void viewDriver(int index){
		Driver drv_temp;
		String driverMessage = new String();
		drv_temp = (Driver) driverlist.getList().get(index);
		driverMessage += drv_temp.checkJourney();
		JOptionPane.showMessageDialog(null, driverMessage, "message",JOptionPane.INFORMATION_MESSAGE, null);
	}
	/**
	 * Manager view all trains' assignment
	 * @return assignment information
	 */
	public String viewTrain(){
		Train drv_temp;
		int num = trainlist.size();
		StringBuffer trainMessage = new StringBuffer();
		for(int i=0; i<num; i++){
			drv_temp = (Train) trainlist.get(i);
			trainMessage.append(drv_temp.checkJourney());
		}
		//JOptionPane.showMessageDialog(null, trainMessage, "message",JOptionPane.INFORMATION_MESSAGE, null);
		return trainMessage.toString();
	}
	
	/**
	 * GUI settings for viewing driver and train information.
	 */
	public void guiPreset_drtr(){
		//setTitle("View Driver/Train States");
		selectArea.setLayout(new FlowLayout());
		driverButton = new JRadioButton("Driver");
		driverButton.addActionListener(this);
		trainButton = new JRadioButton("Train");
		trainButton.addActionListener(this);
		quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
		btg.add(driverButton);
		btg.add(trainButton);
		selectArea.add(driverButton);
		selectArea.add(trainButton);
		selectArea.add(quitButton);
		//add(selectArea, BorderLayout.NORTH);
		//add(jsp, BorderLayout.SOUTH);
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - 400) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - 400) / 2;
		setLocation(w, h);
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500,400);
		this.setVisible(false);
	}

	/**
	 * Showing driver and train assignment onto GUI, defining trainList and driverList
	 */
	public void viewAssignment_drtr(){
		//guiPreset_drtr();
		add(selectArea, BorderLayout.NORTH);
		add(jsp, BorderLayout.SOUTH);
		this.setVisible(true);
	}
	/**
	 * GUI setting for a driver to check his current journey
	 * @param index driver's ID
	 */
	public void guiPreset_dr(int index){
		drStop = new JButton("Stop Train");
		drStop.addActionListener(this);
		//drStart = new JButton("Move on");
		driverAct.setLayout(new FlowLayout());
		driverAct.add(drStop);
		//driverAct.add(drStart);
		DriverTrain dr = (Driver)driverlist.get(index);
		int duration = (int)timecvt.getDuration();
		drJney = dr.getAssignment((int) (duration/120));
		driverView.setLayout(new GridLayout(0,1));
		if(drJney == j0){
			driverView.add(new JLabel("You have no assignment at the moment"));
			drStop.removeActionListener(this);
		}else{
			//System.out.println("Journey info "+j+"**");System.out.flush();
			drAssignment = new JTextArea("Current Assignment: \n\n\n"+drJney.viewJourney());
			driverView.add(drAssignment);
			train = (Train)trainlist.get(drJney.getTrainID());
			//System.out.println("Train info: "+tr.getID()+"**");System.out.flush();
			drTrainInfo = new JLabel();
			drTrainInfo.setText(train.getLocation(timecvt, j0, 0));
			drTime = new JLabel();
			drTime.setText(""+drJney.checkLate()+" minutes late");
			JPanel innerView = new JPanel(new GridLayout(3,1));
			innerView.add(new JLabel("TrainID: "+drJney.getTrainID()));
			innerView.add(drTrainInfo);
			innerView.add(drTime);
			driverView.add(innerView);
		}		
	}
	/**
	 * Driver's journey information that he wants to check is shown on GUI 
	 * @param id driver's ID
	 */
	public void driverCheck(int id){
		guiPreset_dr(id);
		add(driverAct, BorderLayout.NORTH);
		add(driverView, BorderLayout.CENTER);
		setVisible(true);
	}
//******************************************************************
	/**
	 * A user checks next arriving train for a station with a GUI
	 */
	public void nextTrain(){
		System.out.println("Stationlist Size: "+stationlist.stationNum());
		allStations.removeAllItems();
		for(int i=0; i<stationlist.stationNum(); i++){
			allStations.addItem(stationlist.getStation(i).toString());
			System.out.println("i: "+  stationlist.getStation(i).toString());
		}
		usrStation = new JButton("View Arriving Train");
		usrStation.addActionListener(this);
		usrSelect.add(new JLabel("Select Station:"));
		usrSelect.add(allStations);
		usrSelect.add(usrStation);	
		usrView.add(jspStation);
		setLayout(new BorderLayout());
		setSize(400,400);
		add(usrSelect, BorderLayout.NORTH);
		add(usrView, BorderLayout.CENTER);
		revalidate();
		setVisible(true);
	}
	
//******************************************************************	
	/**
	 * GUI settings for driver to stop or restart a train
	 */
	public void guiPreset_stopStart(){
		int trainNum = trainlist.size();
		trainArea.setLayout(new GridLayout(trainNum,2));
		allTrains = new JRadioButton[trainNum];
		ButtonGroup trainGroup = new ButtonGroup();
		allTrainsInfo = new JLabel[trainNum];
		for(int i=0; i<trainNum; i++){
			allTrains[i] = new JRadioButton("Train"+(i+1)+" :");
			allTrainsInfo[i] = new JLabel(((Train)trainlist.get(i)).getLocation(timecvt, j0, 0));
			trainGroup.add(allTrains[i]);
			trainArea.add(allTrains[i]);
			trainArea.add(allTrainsInfo[i]);
		}
		stop = new JButton("Stop");
		start = new JButton("Start");
		setTime = new JButton("SetTime");
		stop.addActionListener(this);
		start.addActionListener(this);
		setTime.addActionListener(this);
		stopStart.add(stop);
		stopStart.add(start);
		stopStart.add(setTime);
	}
	/**
	 *  GUI settings for driver to stop or restart a train
	 */
	public void stopTrian_gui(){
		guiPreset_stopStart();
		add(stopStart,BorderLayout.NORTH);
		add(trainArea,BorderLayout.CENTER);
		setVisible(true);
	}
//******************************************************************
	/**
	 * A GUI where user search for proper journey, with aimed stations specified
	 */
	public void searchJourney(){
		from = new JComboBox();
		to = new JComboBox();
		for(int i=0; i<stationlist.stationNum(); i++){
			from.addItem(stationlist.getStation(i).toString());
			to.addItem(stationlist.getStation(i).toString());
		}
		usrLocations.add(new JLabel("Start from: "));
		usrLocations.add(from);
		usrLocations.add(new JLabel("to: "));
		usrLocations.add(to);
		search = new JButton("Search Journey");
		search.addActionListener(this);
		usrLocations.add(search);
		add(usrLocations, BorderLayout.NORTH);
		add(jspJourney, BorderLayout.CENTER); 
		setSize(500,400);
		setVisible(true);
	}
//******************************************************************	
	/**
	 * Manager reset system time
	 */
	public void changeTime(){
		String time = JOptionPane.showInputDialog(null, "Current system time:\n"+timecvt.printTime()+"\nPlease insert a new time:\n", "08:00");
		boolean timeFlag = timecvt.setTime(time);
		if(timeFlag == true)
			JOptionPane.showMessageDialog(null, "Time Recorded", "Message",JOptionPane.INFORMATION_MESSAGE, null);
		else{
			JOptionPane.showMessageDialog(null, "Invalid time. Please try again.", "Message",JOptionPane.INFORMATION_MESSAGE, null);
			changeTime();
		}
		//timecvt.guiSetTime();
	}
//******************************************************************
	/**
	 * GUI settings for manager to add new route to system
	 */
	public void guiPreset_addRoute(){
		routeControl.add(new JLabel("Select stations to set up a new route: "));
		confirmStation = new JButton("Confirm");
		confirmStation.addActionListener(this);
		routeControl.add(confirmStation);
		//durations = new JTextField[stationlist.stationNum()];
		int num = stationlist.stationNum();
		stations = new JCheckBox[num];
		setStations.setLayout(new GridLayout(0, 1));
		for(int i=0; i<num; i++){ 
			stations[i] = new JCheckBox(stationlist.getStation(i).toString());
			setStations.add(stations[i]);
		}						
	}
	/**
	 * GUI settings for manager to add new route to system
	 */
	public void addRoute(){
		add(routeControl, BorderLayout.NORTH);
		add(setStations, BorderLayout.CENTER);
		setVisible(true);
	}
	/**
	 * Interface for manager to remove a route, with checking 
	 */
	public boolean removeRoute(){
		boolean valid = false;
		return valid;		
	}
	/**
	 * Manager set the time required for a train to running between stations
	 */
	public void setDuration(int num){
		//set control panel
		routeTime.add(new JLabel("Time required from Central Station to each station"));
		confirmDuration = new JButton("Confirm");
		confirmDuration.addActionListener(this);
		routeTime.add(confirmDuration);
		int stationNum = 2*num-1;
		selectedStations = new int[num];
		int count1 = -1;
		//get index of stations selected
		for(int i=0; i<stationlist.stationNum(); i++){
			if(stations[i].isSelected()){
				count1++;
				selectedStations[count1] = i;
			}
		}
		int count2 = -1;
		durations = new JTextField[stationNum];
		JLabel routeStations[] = new JLabel[stationNum];
		setDurations.setLayout(new GridLayout(stationNum, 2));
		//from central to destination, set duration
		for(int i=0; i<num; i++){
			count2++;
			routeStations[count2] = new JLabel(stations[selectedStations[i]].getText());
			setDurations.add(routeStations[count2]);
			durations[count2] = new JTextField();
			setDurations.add(durations[count2]);
			System.out.println("num="+num+", count="+count2+" i="+i);
		}
		//from destination to central, set duration
		for(int i=num-2; i>=0; i--){
			count2++;
			routeStations[count2] = new JLabel(stations[selectedStations[i]].getText());
			setDurations.add(routeStations[count2]);
			durations[count2] = new JTextField();
			setDurations.add(durations[count2]);
			System.out.println("num=" + num + " count="+count2+" i="+i);
		}
		add(routeTime,BorderLayout.NORTH);
		add(setDurations, BorderLayout.CENTER);
		setVisible(true);		
	}
//******************************************************************
	/**
	 * Save information in system's data repository to file, including information on station, route, driver, train and journey.
	 */
	public void saveSettings(){
		//save stations
		try {
			BufferedWriter stationWriter = new BufferedWriter(new FileWriter("record/station.txt", false));	
			for(int i=0; i<stationlist.stationNum(); i++){
				stationWriter.write(stationlist.getStation(i).toString()+"\n");
			}
			stationWriter.close();
			System.out.println("Station settings saved");			
		}catch(IOException e){
			System.out.println("Failed to save station settings");
			e.printStackTrace();
		}
		//save routes
		try{
			BufferedWriter routeWriter = new BufferedWriter(new FileWriter("record/route.txt", false));
			String routeInfo;
			Route r;
			for(int i=0; i<routelist.size(); i++){
				r = (Route)routelist.get(i);
				routeInfo = "";
				//stationNum 
				routeInfo += r.getStationNum(); 
				routeInfo += "#";
				//station indexes
				int j;
				for(j=0; j<r.getStationNum()-1; j++){
					routeInfo += stationlist.getStationIndex(r.getStation(j).toString());
					routeInfo += "&";
				}
				routeInfo +=stationlist.getStationIndex(r.getStation(j).toString());
				routeInfo += "#";
				//arriveTimes
				for(j=0; j<r.getStationNum()-1; j++){
					routeInfo += r.getArriveTime(j);
					routeInfo += "&";
				}
				routeInfo += r.getArriveTime(j);
				routeInfo += "#";
				routeInfo += r.toString();
				//write file
				routeWriter.write(routeInfo + "\n");
			}
			routeWriter.close();
			System.out.println("Route settings saved");
		}catch (IOException e){
			System.out.println("Failed to save route settings");
			e.printStackTrace();
		}
		//save journeys
		try{
			BufferedWriter journeyWriter = new BufferedWriter(new FileWriter("record/journey.txt", false));
			String journeyInfo = "";
			Route r;
			Journey jney;
			for(int i=0; i<routelist.size(); i++){
				r = (Route)routelist.get(i);				
				for(int j=0; j<r.getJourneyNum(); j++ ){
					jney = r.getJourney(j);
					journeyInfo = "";
					journeyInfo += i;
					journeyInfo += "#";
					journeyInfo += jney.getDriverID();
					journeyInfo += "#";
					journeyInfo += jney.getTrainID();
					journeyInfo += "#";
					journeyInfo += jney.getStartTime();
					journeyInfo += "#";
					journeyInfo += (jney.toString()+"\n");
					journeyWriter.write(journeyInfo);
					//System.out.println("i="+i+", j="+j+"  "+journeyInfo);
				}
			}
			journeyWriter.close();
			System.out.println("Journey settings saved");
		}catch (IOException e){
			System.out.println("Failed to save journey settings");
			e.printStackTrace();
		}
		//save driver/train members
		try{
			BufferedWriter drtrWriter = new BufferedWriter(new FileWriter("record/driverTrain.txt"));
			String drtrInfo = "";
			int i;
			//driver
			drtrInfo += (driverlist.size()+"#");
			for(i=0; i<driverlist.size()-1; i++){
				drtrInfo += ((Driver)driverlist.get(i)).toString();
				drtrInfo += "&";
			}
			drtrInfo += ((Driver)driverlist.get(i)).toString();
			drtrWriter.write(drtrInfo+"\n");			
			//train
			drtrInfo = "";
			drtrInfo += trainlist.size();
			drtrInfo += "#";
			for(i=0; i<trainlist.size()-1; i++){
				drtrInfo += ((Train)trainlist.get(i)).toString();
				drtrInfo += "&";
			}
			drtrInfo += ((Train)trainlist.get(i)).toString();
			drtrWriter.write(drtrInfo+"\n");	
			drtrWriter.close();
			System.out.println("driver and train settings saved");			
		}catch (IOException e){
			System.out.println("Failed to save driver settings");
			e.printStackTrace();
		}
	}
//******************************************************************
	@Override
	/**
	 * Detect instruction from customer and then provide with a feedback using inside code. 
	 */
	public void actionPerformed(ActionEvent e) {
		//0. get source
		JButton clicked = new JButton();
		JRadioButton selected;
		if(e.getSource() instanceof JButton)
			clicked = (JButton)e.getSource();
		else if(e.getSource() instanceof JRadioButton)
			selected  = (JRadioButton)e.getSource();
		//1. function: view drivers/trains
		String choice = e.getActionCommand();//detect quit command
		String taskInfo = new String();
		if(driverButton.isSelected()){//show driver assignment
			taskInfo = viewDriver();
			taskArea.setText(taskInfo);
		}else if(trainButton.isSelected()){//show train assignment
			taskInfo = viewTrain();
			taskArea.setText(taskInfo);
		}
		//if(choice.equals("Quit")){
		if(clicked == quitButton){
			setVisible(false);
			remove(selectArea);
			remove(jsp);
		}
		//2. function: view journey, add journey
		if(clicked == view){
			remove(jneyRequest);
			//jneyRequest.setVisible(false);
			add(jspRoute,BorderLayout.CENTER);
			selectRoute.remove(confirmJney);
			if(isManager == true)
				selectRoute.add(rmvJney);
			repaint();
			revalidate();
			int routeIndex = allRoutes.getSelectedIndex();
			Route selectedRoute = (Route)routelist.get(routeIndex);
			String allJourney = selectedRoute.viewRoute();
			routeInfo.setText(allJourney);
		}else if(clicked == add){
		//if(choice.equals("Add Journey")){
			remove(jspRoute);
			selectRoute.remove(rmvJney);
			selectRoute.add(confirmJney);
			revalidate();
			repaint();
			add(jneyRequest,BorderLayout.CENTER);
			//jneyRequest.setVisible(true);
			revalidate();
			repaint();
			System.out.println("new process");
			addFlag = true;
		}else if (clicked == rmvJney){
			removeJourney();
			System.out.println("Delete a journey here");
			repaint();
		}else if(clicked == confirmJney){
			if(addFlag == true){
				remove(jneyRequest);
				//repaint();
				//revalidate();
				//jneyRequest.setVisible(false);
				add(jspRoute,BorderLayout.CENTER);
				selectRoute.remove(confirmJney);
				selectRoute.add(rmvJney);
				revalidate();
				repaint();
				addJourney();
				System.out.println("written");
				int routeIndex = allRoutes.getSelectedIndex();
				Route selectedRoute = (Route)routelist.get(routeIndex);
				String allJourney = selectedRoute.viewRoute();
				routeInfo.setText(allJourney);
				addFlag = false;
			}else
				routeInfo.setText("Please click ADD to set new journey.");
		}
		//4. function: stop train, start train, get location
		if(choice.equals("Stop")){
			for(int i=0; i<allTrains.length; i++){
				if(allTrains[i].isSelected()){
					String stopMessage = ((Train)trainlist.get(i)).stopTrain(timecvt, 0, j0);
					if(stopMessage.equals("The train is not working now and need not be stopped") == false){
						allTrainsInfo[i].setText(stopMessage);
						revalidate();
					}
				}
			}
		}else if(choice.equals("Start")){
			for(int i=0; i<allTrains.length; i++){
				if(allTrains[i].isSelected()){
					String startMessage = ((Train)trainlist.get(i)).startTrain(timecvt, j0);
					if(startMessage.equals("denied")== false){
						allTrainsInfo[i].setText(startMessage);
						revalidate();
					}
				}
			}
		}else if(choice.equals("SetTime")){
			changeTime();
			System.out.println("New Location: ");
			for(int i=0; i<allTrains.length; i++){
				allTrainsInfo[i].setText(((Train)trainlist.get(i)).getLocation(timecvt, j0, 0));
				revalidate();
			}
		}
		//5. function: for driver, stop train/start train, check assignment
		if(clicked == drStop){
			if(clicked.getText().equals("Stop Train")){		
				String stopMessage = train.stopTrain(timecvt, 0, j0);
				if(stopMessage.equals("The train is not working now and need not be stopped") == false){
					drTrainInfo.setText(stopMessage);
					clicked.setText("Move on");
					repaint();
					revalidate();
				}						
			}else if(clicked.getText().equals("Move on")){
				clicked.setText("Stop Train");
				String startMessage = train.startTrain(timecvt, j0);
				if(startMessage.equals("denied") == false){
					drAssignment.setText("Current Assignment: \n\n\n"+drJney.viewJourney());
					drTrainInfo.setText(startMessage);
					drTime.setText(""+drJney.checkLate()+" minutes late");
					revalidate();
				}
			}else
				System.out.println("System error for driver stop/start");
		}
		//6. function: for user, check next train for selected station
		if(clicked == usrStation){
			int stationIndex = allStations.getSelectedIndex();
			Station s = stationlist.getStation(stationIndex);
			stationInfo.setText(s.checkNext(timecvt, 0));
			repaint();
			revalidate();
		}
		//7. function: for user, search proper journey
		if(clicked == search){
			Station s1 = stationlist.getStation(from.getSelectedIndex());
			Station s2 = stationlist.getStation(to.getSelectedIndex());			
			journeyInfo.setText(routelist.searchRoute(s1, s2));
		}
		//8. function: add new routes-- set stations and durations
		if(clicked == confirmStation){
			remove(routeControl);
			remove(setStations);
			revalidate();
			int numAll = stationlist.stationNum();
			int numStation = -1;
			//Route r = new Route(numAll, central, "noName");
			//Station s;
			for(int i=0; i<numAll; i++){
				if(stations[i].isSelected()){
					//s = stationlist.getStation(i);
					numStation++;
					//r.setRoute(numStation,s);
				}
			}			
			setDuration(numStation+1);
		}else if(clicked == confirmDuration){
			int num = selectedStations.length;
			Route r = new Route(num*2-1);
			int count = -1;
			for(int i=0; i<num; i++){
				count ++;
				r.setRoute(count, stationlist.getStation(selectedStations[i]));
				System.out.println("count="+count+", i="+i+", station="+ stationlist.getStation(selectedStations[i]).toString());
				r.setDuration(count, Integer.parseInt(durations[count].getText()));
				System.out.println("duration="+Integer.parseInt(durations[count].getText()));
			}
			for(int i=num-2; i>=0; i--){
				count++;
				r.setRoute(count, stationlist.getStation(selectedStations[i]));
				System.out.println("count="+count+", i="+i+", station="+ stationlist.getStation(selectedStations[i]).toString());
				r.setDuration(count, Integer.parseInt(durations[count].getText()));
				System.out.println("duration="+Integer.parseInt(durations[count].getText()));
			}
			if(count == durations.length-1)
				System.out.println("nice match");
			else
				System.out.println("not match");
			String identifier;
			do{
				identifier = JOptionPane.showInputDialog( null, "Please set an identifier for the route:", "Route#"); 
			}while(identifier == null || identifier.length() == 0);
			r.setIdentifier(identifier);
			JOptionPane.showMessageDialog(null, "Route successfully created.\n"
					+"You can check the route in main menu", "message",JOptionPane.INFORMATION_MESSAGE, null);
			routelist.add(r);
			System.out.println(identifier);
			setVisible(false);
			remove(routeTime);
			remove(setDurations);
		}
	}//end of ActionPerformed	
}
