package mrfinger.gothicgamemod.api;

import java.util.Iterator;

public interface ICMap<K, V> {
	
		
	public int getCapacity();
	
	public V getValue(K key);		

	IPair<K, V>[] getArr();	
	
	public Iterator<IPair<K, V>> iterator();
	
	public Iterator<K> keyIterator();
	
	public Iterator<V> value1Iterator();
	
	
	
	
	public static interface IPair<V1, V2> {
		
		V1 getValue1();
		
		V2 getValue2();
	}

	
	public static interface ICapabilityIterator {

		boolean hasNext();
		
	}


	
}
