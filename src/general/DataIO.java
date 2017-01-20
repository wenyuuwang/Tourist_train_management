package general;

import java.util.ArrayList;

/**
 * The interface define behaviors that lists may act
 * @author Group36
 * @version 3.0
 */
public interface DataIO {
	public abstract Object get(String identifier);
	public abstract Object get(int index);
	public abstract int size();
	public abstract void add(Object o);
	public abstract void remove(String identifier);
	public abstract int checkIndex(String identifier);
	public abstract ArrayList getList();
}
