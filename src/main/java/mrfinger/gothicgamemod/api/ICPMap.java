package mrfinger.gothicgamemod.api;

import java.util.Iterator;

import mrfinger.gothicgamemod.api.ICMap.IPair;

public interface ICPMap<K, V1, V2> extends ICMap<K, V1> {
	
	
	public V2 getValue2(K key);

	@Override
	public ITriplet<K, V1, V2>[] getArr();
	
	public Iterator<V2> value2Iterator();
	
	
	public static interface ITriplet<V1, V2, V3> extends IPair<V1, V2> {		

		V3 getValue3();
	}
	
}
