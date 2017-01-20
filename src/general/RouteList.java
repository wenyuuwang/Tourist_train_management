package general;

import java.util.ArrayList;;
/**
 * ArrayList in the class contains all routes in the system
 * @author Group36
 * @version 3.0
 *
 */
public class RouteList implements DataIO{
	private ArrayList<Route> routeList;
	/**
	 * Constructor
	 */
	public RouteList(){
		routeList = new ArrayList<Route>();
	}

	/**
	 * select route
	 * @param s1 Station that the route passes through
	 * @param s2 Station that the route passes through
	 * @return
	 */
	public String searchRoute(Station s1, Station s2){
		StringBuffer result = new StringBuffer();
		ArrayList<Route> takeRoute = new ArrayList<Route>();
		for(Route r : routeList){
			int count = 0;
			for(int i=0; i<(r.stationNum+1)/2; i++){
				if(r.stationList.get(i) == s1)
					count ++;
				if(r.stationList.get(i) == s2)
					count ++;
				if(count == 2){
					takeRoute.add(r);		
					break;
				}
			}
		}
		//print selected route
		System.out.println("You may take the following trains: ");
		System.out.println();
		result.append("You may take the following trains:\n\n\n");
		for(Route r: takeRoute){
			r.viewRoute();
			result.append(r.viewRoute()+"\n");
		}
		return result.toString();
	}

	/**
	 * get route by identifier
	 */
	public Object get(String identifier) {
		Route aimedRoute = null;
		for(Route r: routeList){
			if(r.toString().equals(identifier))
				aimedRoute = r;
		}
		return aimedRoute;
	}
	/**
	 * get route by index
	 */
	public Object get(int index) {
		return routeList.get(index);
	}
	/**
	 * get the number of routes currently existing in system
	 */
	public int size() {
		return routeList.size();
	}
	/**
	 * add a new route
	 */
	public void add(Object o) {
		Route r = (Route)o;
		routeList.add(r);
		System.out.println("New route has bee recorded.");
	}
	/**
	 * delete a route
	 */
	public void remove(String identifier) {
		Route r = (Route)get(identifier);
		routeList.remove(r);
		System.out.println("Route has been canceled. Please re-assign driver/car");
	}
	/**
	 * get the index of route by it identifier
	 */
	public int checkIndex(String identifier) {
		int index = -2;
		index = routeList.indexOf((Route)get(identifier));
		return index;
	}
	/**
	 * get route list
	 */
	public ArrayList getList() {
		return routeList;
	}
}
