package mrfinger.gothicgamemod.api;

import java.util.Iterator;

import mrfinger.gothicgamemod.api.ICMap.IPair;
import mrfinger.gothicgamemod.api.CMap.CapabilityIterator;
import mrfinger.gothicgamemod.api.CMap.Pair;
import mrfinger.gothicgamemod.api.ICPMap.ITriplet;

public class CPMap<K, V1, V2> extends CMap<K, V1> implements ICPMap<K, V1, V2> {
	
		
	public CPMap(ITriplet... triplet) {
		super(triplet);
	}	
	
	
	@Override
	public V2 getValue2(K key) {
		
		int l = this.arr.length;
		ITriplet<K, V1, V2>[] entry = (ITriplet<K, V1, V2>[]) this.arr;
		for (int i = 0; i < l; ++i) {
			if (entry[i].getValue1() == key) return entry[i].getValue3();
		}
		
		return null;
	}
	
	@Override
	public ITriplet<K, V1, V2>[] getArr() {
		return (ITriplet<K, V1, V2>[]) this.arr;
	}	
	
	@Override
	public Iterator<V2> value2Iterator() {
		return new Value2Iterator();
	}
	

	public static class Triplet<V1, V2, V3> extends Pair<V1, V2> implements ITriplet<V1, V2, V3> {
		
		public final V3 value3;

		public Triplet(V1 value1, V2 value2, V3 value3) {
			super(value1, value2);
			this.value3 = value3;
		}
		
		@Override
		public V3 getValue3() {
			return this.value3;
		}		
	}
    
    final class Value2Iterator extends CapabilityIterator implements Iterator<V2> {

		@Override
		public V2 next() {
			V2 value = ((ITriplet<K,V1,V2>) this.nextTuple()).getValue3();
			if (value != null) {
				return value;
			}
			else {
				return this.next();
			}
		}    	
    }
	
}
