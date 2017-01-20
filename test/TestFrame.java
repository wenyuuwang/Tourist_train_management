package test;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

import general.*;
/**
 * This test was designe for an quite old version of this project's code.
 * Code has been modified. The test cannot run now.
 */
public class TestFrame {
	BuildFrame frame = new BuildFrame();
	@Test
	@Ignore
	public void checkRole(){
		System.out.println("----1----");
		String role;		
		role = frame.setRole(1);
		assertEquals("manager", role);
	}
	@Test
	@Ignore
	public void checkFunction(){
		System.out.println("----2----");
		String activity;
		activity = frame.chooseFunction(1);
		assertEquals("SetJourney", activity);		
	}
	@Test
	@Ignore
	public void testSetup(){
		System.out.println("----ALWAYS----");
		frame.setupSystem();
	}	
	@Test
	@Ignore
	public void checkActivity(){
		System.out.println("----4----");
		frame.journey();
	}
	@Test
	@Ignore
	public void checkDriver(){
		System.out.println("----5----");
		int num = frame.viewDriver();
		assertEquals(num, 3);
	}
	@Test
	public void testTime(){
		TimeConvertor timecvt = new TimeConvertor();
		long duration = timecvt.getDuration();
		System.out.println(duration);
	}
}
