package test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This test was designed for an old version of this project. 
 */
public class TestOrdered extends TestCase {

 public TestOrdered(String name) {
  super(name);
 }
 
 BuildFrame frame = new BuildFrame();
 
	public void checkRole(){
		System.out.println("----1----");
		String role;		
		role = frame.setRole(1);
		System.out.println(role);
	}

	public void checkFunction(){
		System.out.println("----2----");
		String activity;
		activity = frame.chooseFunction(1);
		System.out.println(activity);
	}

	public void checkSetup(){
		System.out.println("----setup----");
		frame.setupSystem();
	}	

	public void checkActivity(){
		System.out.println("----function1----");
		frame.journey();
	}

	public void checkDriver(){
		System.out.println("----function2----");
		int num = frame.viewDriver();
		System.out.println(num+" drivers info listed above");
	}
	
	public void checkTrain(){
		System.out.println("----function3----");
		int num = frame.viewTrain();
		System.out.println(num+" trains info listed above");
	}
	public void checkNextTrain(){
		System.out.println("----View next train----");
		frame.viewNextTrian();
	}
	public void checkRoute(){
		System.out.println("---View route timetable --- ");
		frame.viewRoute();
	}
	public void checkTrainLocation(){
		System.out.println("---View train location --- ");
		frame.viewLocation();
	}
	public void checkSopMethod(){
		System.out.println("---Timetable after a stop --- ");
		frame.stopTrain();
	}
	public void checkUserSearch(){
		System.out.println("---User check journey --- ");
		frame.selectJourney();
	}


 public static Test suite() {
  TestSuite suite=new TestSuite();
  suite.addTest(new TestOrdered("checkRole"));
  suite.addTest(new TestOrdered("checkFunction"));
  suite.addTest(new TestOrdered("checkSetup"));
  suite.addTest(new TestOrdered("checkActivity"));
  suite.addTest(new TestOrdered("checkDriver"));
  suite.addTest(new TestOrdered("checkTrain"));
  //suite.addTest(new TestOrdered("checkNextTrain"));
  suite.addTest(new TestOrdered("checkRoute"));
  suite.addTest(new TestOrdered("checkTrainLocation"));
  suite.addTest(new TestOrdered("checkSopMethod"));
  suite.addTest(new TestOrdered("checkUserSearch"));
  return  suite;
 }
}