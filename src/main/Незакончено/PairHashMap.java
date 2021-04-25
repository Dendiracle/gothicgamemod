package mrfinger.gothicgamemod.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class PairHashMap<K, V1, V2> extends AbstractPairMap<K, V1, V2> implements Serializable {
	
    /**
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * The bin count threshold for using a tree rather than list for a
     * bin.  Bins are converted to trees when adding an element to a
     * bin with at least this many nodes. The value must be greater
     * than 2 and should be at least 8 to mesh with assumptions in
     * tree removal about conversion back to plain bins upon
     * shrinkage.
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * The bin count threshold for untreeifying a (split) bin during a
     * resize operation. Should be less than TREEIFY_THRESHOLD, and at
     * most 6 to mesh with shrinkage detection under removal.
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * The smallest table capacity for which bins may be treeified.
     * (Otherwise the table is resized if too many nodes in a bin.)
     * Should be at least 4 * TREEIFY_THRESHOLD to avoid conflicts
     * between resizing and treeification thresholds.
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

	@Override
	public Set<PairEntry<K, V1, V2>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	static class PairNode<K, V1, V2> implements PairMap.PairEntry<K, V1, V2> {
		
		final int hash;
        final K key;
        V1 value;
        V2 value2;
        PairNode<K, V1, V2> next;

        PairNode(int hash, K key, V1 value1, V2 value2, PairNode<K, V1, V2> next) {
            this.hash = hash;
            this.key = key;
            this.value = value1;
            this.value2 = value2;
            this.next = next;
        }
        

        public final K getKey()        { return key; }
        public final V1 getValue1()      { return value; }
        public final V2 getValue2()      { return value2; }
        public final String toString() { return key + "=" + value; }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V1 setValue1(V1 newValue) {
            V1 oldValue = value;
            value = newValue;
            return oldValue;
        }
        
        public final V2 setValue2(V2 newValue) {
            V2 oldValue = value2;
            value2 = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof PairMap.PairEntry) {
            	PairMap.PairEntry<?,?,?> e = (PairMap.PairEntry<?,?,?>)o;
                if (Objects.equals(key, e.getKey()) &&
                    Objects.equals(value, e.getValue1()) &&
                	Objects.equals(value2, e.getValue2()))
                    return true;
            }
            return false;
        }


		@Override
		public Entry<V1, V2> getPair() {
			return (Entry<V1, V2>) new HashMap();
		}        
	}
	
	static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * Returns x's Class if it is of the form "class C implements
     * Comparable<C>", else null.
     */
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c; Type[] ts, as; Type t; ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                        ((p = (ParameterizedType)t).getRawType() ==
                         Comparable.class) &&
                        (as = p.getActualTypeArguments()) != null &&
                        as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }
    
    
    @SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 :
                ((Comparable)k).compareTo(x));
    }
    
    
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    } 
    
    transient PairNode<K,V1, V2>[] table;

    /**
     * Holds cached entrySet(). Note that AbstractMap fields are used
     * for keySet() and values().
     */
    transient Set<PairMap.PairEntry<K,V1, V2>> entrySet;

    /**
     * The number of key-value mappings contained in this map.
     */
    transient int size;

    /**
     * The number of times this HashMap has been structurally modified
     * Structural modifications are those that change the number of mappings in
     * the HashMap or otherwise modify its internal structure (e.g.,
     * rehash).  This field is used to make iterators on Collection-views of
     * the HashMap fail-fast.  (See ConcurrentModificationException).
     */
    transient int modCount;

    /**
     * The next size value at which to resize (capacity * load factor).
     *
     * @serial
     */
    // (The javadoc description is true upon serialization.
    // Additionally, if the table array has not been allocated, this
    // field holds the initial array capacity, or zero signifying
    // DEFAULT_INITIAL_CAPACITY.)
    int threshold;

    /**
     * The load factor for the hash table.
     *
     * @serial
     */
    final float loadFactor;
    
    
    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and load factor.
     *
     * @param  initialCapacity the initial capacity
     * @param  loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public PairHashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                                               initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public PairHashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public PairHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }
    
    
    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    
   /* public V1 get1(Object key) {
        PairNode<K, V1, V2> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }
    
    public V2 get2(Object key) {
        PairNode<K, V1, V2> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value2;
    }

    /**
     * Implements Map.get and related methods
     *
     * @param hash hash for key
     * @param key the key
     * @return the node, or null if none
     */
   /* final PairNode<K, V1, V2> getNode(int hash, Object key) {
        PairNode<K, V1, V2>[] tab; PairNode<K, V1, V2> first, e; int n; K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
            (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                if (first instanceof PairTreeNode)
                    return ((PairTreeNode<K, V1, V2>)first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }
    
    
    public boolean containsKey(Object key) {
        return getNode(hash(key), key) != null;
    }
    
    public Map.Entry<V1, V2> put(K key, V1 value1, V2 value2) {
        return putVal(hash(key), key, value1, value2, false, true);
    }
    
    
    final Map.Entry<V1, V2> putVal(int hash, K key, V1 value1, V2 value2, boolean onlyIfAbsent, boolean evict) {
	 PairNode<K, V1, V2>[] tab; PairNode<K, V1, V2> p; int n, i;
	 if ((tab = table) == null || (n = tab.length) == 0)
	     n = (tab = resize()).length;
	 if ((p = tab[i = (n - 1) & hash]) == null)
	     tab[i] = newNode(hash, key, value1, value2, null);
	 else {
		 PairNode<K, V1, V2> e; K k;
	     if (p.hash == hash &&
	         ((k = p.key) == key || (key != null && key.equals(k))))
	         e = p;
	     else if (p instanceof PairTreeNode)
	         e = ((PairTreeNode<K,V1, V2>)p).putTreeVal(this, tab, hash, key, value1, value2);
	     else {
	         for (int binCount = 0; ; ++binCount) {
	             if ((e = p.next) == null) {
	                 p.next = newNode(hash, key, value1, value2, null);
	                 if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
	                     treeifyBin(tab, hash);
	                 break;
	             }
	             if (e.hash == hash &&
	                 ((k = e.key) == key || (key != null && key.equals(k))))
	                 break;
	             p = e;
	         }
	     }
	     if (e != null) { // existing mapping for key
	         V1 oldValue1 = e.value;
	         V2 oldValue2 = e.value2;
	         if (!onlyIfAbsent || (oldValue1 == null && oldValue2 == null)) {
	             e.value = value1;
	             e.value2 = value2;
	         }
	         afterNodeAccess(e);
	         return e.getPair();
	     }
	 }
	 ++modCount;
	 if (++size > threshold)
	     resize();
	 afterNodeInsertion(evict);
	 return null;
	}
    
    /**
     * Initializes or doubles table size.  If null, allocates in
     * accord with initial capacity target held in field threshold.
     * Otherwise, because we are using power-of-two expansion, the
     * elements from each bin must either stay at same index, or move
     * with a power of two offset in the new table.
     *
     * @return the table
     */
    /*final PairNode<K, V1, V2>[] resize() {
        PairNode<K, V1, V2>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int oldThr = threshold;
        int newCap, newThr = 0;
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }
            else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY)
                newThr = oldThr << 1; // double threshold
        }
        else if (oldThr > 0) // initial capacity was placed in threshold
            newCap = oldThr;
        else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;
        @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    if (e.next == null)
                        newTab[e.hash & (newCap - 1)] = e;
                    else if (e instanceof TreeNode)
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    
    
    
	    

	final class EntrySet extends AbstractSet<PairMap.PairEntry<K, V1, V2>> {
		
        public final int size()                 { return size; }
        public final void clear()               { PairHashMap.this.clear(); }
        public final Iterator<PairMap.PairEntry<K, V1, V2>> iterator() {
            return new EntryIterator();
        }
        public final boolean contains(Object o) {
            if (!(o instanceof PairMap.PairEntry))
                return false;
            PairMap.PairEntry<?,?,?> e = (PairMap.PairEntry<?,?,?>) o;
            Object key = e.getKey();
            PairNode<K, V1, V2> candidate = getNode(hash(key), key);
            return candidate != null && candidate.equals(e);
        }
        public final boolean remove(Object o) {
            if (o instanceof PairMap.PairEntry) {
                PairMap.PairEntry<?,?,?> e = (PairMap.PairEntry<?,?,?>) o;
                Object key = e.getKey();
                Object value = e.getValue1();
                Object value2 = e.getValue2();
                return removeNode(hash(key), key, value, value2, true, true) != null;
            }
            return false;
        }
        public final Spliterator<PairMap.PairEntry<K,V1, V2>> spliterator() {
            return new EntrySpliterator<>(PairHashMap.this, 0, -1, 0, 0);
        }
        public final void forEach(Consumer<? super PairMap.PairEntry<K, V1, V2>> action) {
            PairNode<K ,V1, V2>[] tab;
            if (action == null)
                throw new NullPointerException();
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (PairNode<K, V1, V2> e = tab[i]; e != null; e = e.next)
                        action.accept(e);
                }
                if (modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
    }*/
}
