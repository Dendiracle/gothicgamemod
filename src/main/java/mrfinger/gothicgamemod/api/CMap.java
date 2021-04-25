package mrfinger.gothicgamemod.api;

import java.util.Iterator;

public class CMap<K, V> implements ICMap<K, V> {	
	
		
	public final IPair[] arr;
	
	public CMap(IPair<K, V>... pair) {		
		this.arr = pair;		
	}	
	
	@Override
	public int getCapacity() {
		return this.arr.length;
	}
	
	@Override
	public V getValue(K key) {
		int l = this.arr.length;
		for (int i = 0; i < l; ++i) {
			if (this.arr[i].getValue1() == key) return (V) this.arr[i].getValue2();
		}
		return null;
	}

	@Override
	public IPair<K, V>[] getArr() {
		return this.arr;
	}	
	
	@Override
	public Iterator<IPair<K, V>> iterator() {
        return new PairIterator();
	}
	
	@Override
	public Iterator<K> keyIterator() {
		return new KeyIterator();
	}
	
	@Override
	public Iterator<V> value1Iterator() {
		return new Value1Iterator();
	}
	
	
	public static class Pair<V1, V2> implements IPair<V1, V2> {
		
		
		public final V1 value1;
		
		public final V2 value2;
		
		public Pair(V1 value1, V2 value2) {
			this.value1 = value1;
			this.value2 = value2;
		}		

		@Override
		public V1 getValue1() {
			return this.value1;
		}

		@Override
		public V2 getValue2() {
			return this.value2;
		}
		
	}

	
	protected abstract class CapabilityIterator implements ICapabilityIterator {
				
		protected int c;        
        
        @Override
        public final boolean hasNext() {
            return c < arr.length;
        }

        final IPair<K, V> nextTuple() {
        	return arr[c++];
        }
        
    }
	
    final class PairIterator extends CapabilityIterator implements Iterator<IPair<K, V>> {    	
	
		@Override
		public IPair<K, V> next() {
			return nextTuple();
		}
    }
    
    final class KeyIterator extends CapabilityIterator implements Iterator<K> {

		@Override
		public K next() {
			return this.nextTuple().getValue1();
		}    	
    }
    
    final class Value1Iterator extends CapabilityIterator implements Iterator<V> {

		@Override
		public V next() {
			V value = this.nextTuple().getValue2();
			if (value != null) {
				return value;
			}
			else {
				return this.next();
			}
		}    	
    }
    
}
