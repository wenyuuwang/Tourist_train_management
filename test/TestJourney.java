package test;

import general.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test is for final version project
 * @author wendywang
 *
 */
public class TestJourney extends TestCase {

 public TestJourney(String name) {
  super(name);
 }
 
 static DriverList driverlist = new DriverList();
 static TrainList trainlist = new TrainList();
 static RouteList routelist = new RouteList();
 static StationList stationlist = new StationList();
 	
	Route r1 = new Route(4);
	Route r2 = new Route(3);
	Journey j0 = new Journey(0, 0, "blank");
	Station s1 = new Station("Staiton1", j0);
	Station s2 = new Station("Station2", j0);
	Station s3 = new Station("Station3", j0);
	Station s4 = new Station("Station4", j0);
	Station central = new Station("central", j0);

 
	public void setupRoute(){
		System.out.println("----1----");
		//set up station
		stationlist.addStation(0, new Station("Central", j0));
	 	stationlist.addStation(1, new Station("Station1", j0));
		stationlist.addStation(2, new Station("Station2", j0));
		stationlist.addStation(3, new Station("Station3", j0));
		stationlist.addStation(4, new Station("Station4", j0));
		//setup driverlist and trainlist
		String[] drName = new String[3];
		drName[0] = "DR_A"; drName[1] = "DR_B"; drName[2] = "DR_C";
		String[] trName = new String[3];
		trName[0] = "TR_A"; trName[1] = "TR_B"; trName[2] = "TR_C";
		for(int i=0; i<3; i++){
			driverlist.add(new Driver(drName[i], j0));
			trainlist.add(new Train(trName[i], j0));
		}
		r1.setRoute(0,central);
		r1.setRoute(1, s1);
		r1.setRoute(2, s2);
		r1.setRoute(3, s3);
		r1.setDuration(0, 0);
		r1.setDuration(1, 10);
		r1.setDuration(2, 20);
		r1.setDuration(3, 30);
		r2.setRoute(0,central);
		r2.setRoute(1, s1);
		r2.setRoute(2, s4);
		r2.setDuration(0, 0);
		r2.setDuration(1, 30);
		r2.setDuration(2, 50);
		r1.viewStation();
		int t1 = r1.getRouteDuration();
		assertEquals(t1, 30);
		r2.viewStation();
		int t2 = r2.getRouteDuration();
		assertEquals(t2, 50);
	}
	
	public void setJourney(){
		System.out.println("----2----");
		setupRoute();
		Journey j1 = r1.addJourney(120, 0, 0, "Route1_Journey1",driverlist, trainlist);
		//System.out.println(j1);
		//System.out.println(r1.getRouteDuration());
		System.out.println(j1.viewJourney());
	}
	
	public void searchJourney(){
		routelist.add(r1);
		routelist.add(r1);
		setJourney();
		System.out.println(routelist.searchRoute(s1, s2));
	}


 public static Test suite() {
  TestSuite suite=new TestSuite();
  suite.addTest(new TestJourney("setupRoute"));
  suite.addTest(new TestJourney("setJourney"));
  suite.addTest(new TestJourney("searchJourney"));
  return  suite;
 }
}