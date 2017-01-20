package general;

/**
 * Driver Class
 * @author Group36
 * @version 3.0
 *
 */
public class Driver extends DriverTrain{

	public Driver(String identifier, Journey j0) {
		super(identifier, j0);		
	}
	/**
	 * Show current assignment of a driver. Format has been set. 
	 */
	public String checkJourney(){
		String resultMessage;
		resultMessage = "Assignment for Driver [ "+ this.identifier+" ] \n";
		resultMessage += super.checkJourney();
		return resultMessage;
	}

}
