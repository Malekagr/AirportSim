import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class LineQueue<T> implements Queue<T> {
	private Node first;
	private Node last;
	private int total = 0;
	public class Node{
		private T element;
		private Node next;
	}
	public LineQueue(){
		
	}
	
	 public void enqueue(T element)
	    {
	        Node current = last;
	        last = new Node();
	        last.element = element;

	        if (total++ == 0) first = last;
	        else current.next = last;

	    }
	
	
	public T dequeue(){
		if (total == 0) throw new java.util.NoSuchElementException();
        T element = first.element;
        first = first.next;
        if (--total == 0) last = null;
        return element;
	}
	public boolean addAll(Collection<? extends T> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean contains(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isEmpty() {
		
		return total==0;
	}
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean remove(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T[] toArray(T[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean add(T arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public T element() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean offer(T arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public T peek() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public T poll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public T remove() {
		// TODO Auto-generated method stub
		return null;
	}
}
