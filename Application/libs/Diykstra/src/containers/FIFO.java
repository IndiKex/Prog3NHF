package containers;

import java.util.ArrayList;

public class FIFO<T> {
	ArrayList<T> array = new ArrayList<>();
	
	public void push(T t) {
		array.add(t);
	}
	
	public T pop() {
		return array.remove(0);
	}
	
	public int size() {
		return array.size();
	}
}
