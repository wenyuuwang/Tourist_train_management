package general;

import java.util.ArrayList;
/**
 * ArrayList in the class contains all trains in the system
 * @author Group36
 * @version 4.0
 *
 */
public class TrainList implements DataIO{
	/**
	 * ArrayList recording all trains' information
	 */
	private static ArrayList<Train> trainlist;
	/**
	 * The number of trains in the system
	 */
	private int size;	
	/**
	 * Constructor
	 */
	public TrainList(){
		trainlist = new ArrayList<Train>();
	}
	
	/**
	 * get train by identifier
	 */
	public Object get(String identifier) {
		Train aimedTrain = null;
		for(Train drv: trainlist){
			if(drv.toString().equals(identifier))
				aimedTrain = drv;
		}
		return aimedTrain;
	}

	/**
	 * get train by index
	 */
	public Object get(int index) {
		return trainlist.get(index);
	}

	/**
	 * check the number of trains in the list
	 */
	public int size() {
		return trainlist.size();
	}

	/**
	 * add a new train to list
	 */
	public void add(Object o) {
		Train train = (Train)o;
		trainlist.add(train);
		System.out.println("Train "+train.toString()+" has been added.");
		System.out.println("Index in list is "+trainlist.indexOf(train));
	}

	@Override
	public void remove(String identifier) {
		// TODO Auto-generated method stub		
	}

	/**
	 * get train's index in list according to identifier
	 */
	public int checkIndex(String identifier) {
		int aimedIndex = -1;
		for(Train train: trainlist){
			if(train.toString().equals(identifier))
				aimedIndex = trainlist.indexOf(train);
		}
		return aimedIndex;	
	}

	@Override
	/**
	 * get system's train list object
	 */
	public ArrayList getList() {
		return trainlist;
	}
}
