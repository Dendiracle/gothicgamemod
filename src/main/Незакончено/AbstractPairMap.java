package mrfinger.gothicgamemod.util;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public abstract class AbstractPairMap<K, V1, V2> implements PairMap<K, V1, V2> {
	
	
	protected AbstractPairMap() {
		
	}
	
	
	@Override
	public int size() {
		return entrySet().size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		Iterator<PairEntry<K, V1, V2>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
            	PairEntry<K, V1, V2> e = i.next();
                if (e.getKey()==null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
            	PairEntry<K, V1, V2> e = i.next();
                if (key.equals(e.getKey()))
                    return true;
            }
        }
        return false;
	}

	@Override
	public boolean containsValue(Object value) {
		Iterator<PairEntry<K, V1, V2>> i = entrySet().iterator();
        if (value==null) {
            while (i.hasNext()) {
                PairEntry<K, V1, V2> e = i.next();
                if (e.getValue1()==null || e.getValue2() == null)
                    return true;
            }
        } else {
            while (i.hasNext()) {
            	PairEntry<K, V1, V2> e = i.next();
                if (value.equals(e.getValue1()) || value.equals(e.getValue2()))
                    return true;
            }
        }
        return false;
	}

	@Override
	public V1 getV1(Object key) {
        Iterator<PairEntry<K, V1, V2>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
            	PairEntry<K, V1, V2> e = i.next();
                if (e.getKey()==null)
                    return e.getValue1();
            }
        } else {
            while (i.hasNext()) {
            	PairEntry<K, V1, V2> e = i.next();
                if (key.equals(e.getKey()))
                    return e.getValue1();
            }
        }
        return null;
	}

	@Override
	public V2 getV2(Object key) {
        Iterator<PairEntry<K, V1, V2>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
            	PairEntry<K, V1, V2> e = i.next();
                if (e.getKey()==null)
                    return e.getValue2();
            }
        } else {
            while (i.hasNext()) {
            	PairEntry<K, V1, V2> e = i.next();
                if (key.equals(e.getKey()))
                    return e.getValue2();
            }
        }
        return null;
	}

	@Override
	public Map.Entry<V1, V2> put(K key, V1 value1, V2 value2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Map.Entry<V1, V2> remove(Object key) {
        Iterator<PairEntry<K, V1, V2>> i = entrySet().iterator();
        PairEntry<K, V1, V2> correctEntry = null;
        if (key==null) {
            while (correctEntry==null && i.hasNext()) {
            	PairEntry<K, V1, V2> e = i.next();
                if (e.getKey()==null)
                    correctEntry = e;
            }
        } else {
            while (correctEntry==null && i.hasNext()) {
            	PairEntry<K, V1, V2> e = i.next();
                if (key.equals(e.getKey()))
                    correctEntry = e;
            }
        }

        Map.Entry<V1, V2> oldPair = null;
        if (correctEntry !=null) {
            oldPair = correctEntry.getPair();
            i.remove();
        }
        return oldPair;
	}

	@Override
	public void putAll(PairMap<? extends K, ? extends V1, ? extends V2> m) {
        for (PairEntry<? extends K, ? extends V1, ? extends V2> e : m.entrySet())
            put(e.getKey(), e.getValue1(), e.getValue2());        
	}

	@Override
	public void clear() {
        entrySet().clear();
	}
	
    // Views

    /**
     * Each of these fields are initialized to contain an instance of the
     * appropriate view the first time this view is requested.  The views are
     * stateless, so there's no reason to create more than one of each.
     */
    transient volatile Set<K>        	keySet;
    
    transient volatile Collection<V1> 	values;

    transient volatile Collection<V2> 	values2;

	@Override
	public Set<K> keySet() {
        if (keySet == null) {
            keySet = new AbstractSet<K>() {
                public Iterator<K> iterator() {
                    return new Iterator<K>() {
                        private Iterator<PairEntry<K, V1, V2>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public K next() {
                            return i.next().getKey();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractPairMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractPairMap.this.isEmpty();
                }

                public void clear() {
                    AbstractPairMap.this.clear();
                }

                public boolean contains(Object k) {
                    return AbstractPairMap.this.containsKey(k);
                }
            };
        }
        return keySet;
	}

	public Collection<V1> values1() {
    	if (values == null) {
        	values = new AbstractCollection<V1>() {
        		
            	public Iterator<V1> iterator() {
                    return new Iterator<V1>() {
                        private Iterator<PairEntry<K, V1, V2>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public V1 next() {
                            return i.next().getValue1();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractPairMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractPairMap.this.isEmpty();
                }

                public void clear() {
                    AbstractPairMap.this.clear();
                }

                public boolean contains(Object v) {
                    return AbstractPairMap.this.containsValue(v);
                }
            };
        }
        return values;
    }


	@Override
	public Collection<V2> values2() {
		if (values2 == null) {
        	values2 = new AbstractCollection<V2>() {
        		
            	public Iterator<V2> iterator() {
                    return new Iterator<V2>() {
                        private Iterator<PairEntry<K, V1, V2>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public V2 next() {
                            return i.next().getValue2();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractPairMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractPairMap.this.isEmpty();
                }

                public void clear() {
                    AbstractPairMap.this.clear();
                }

                public boolean contains(Object v) {
                    return AbstractPairMap.this.containsValue(v);
                }
            };
        }
        return values2;
	}

	@Override
	public abstract Set<PairEntry<K, V1, V2>> entrySet();
	
	
    public static class SimplePairEntry<K, V1, V2> implements PairEntry<K, V1, V2>, java.io.Serializable {
    	
	    private static final long serialVersionUID = -8499721149061103585L;
	
	    private final K key;
	    private V1 value1;
	    private V2 value2;
	
	    /**
	     * Creates an entry representing a mapping from the specified
	     * key to the specified value.
	     *
	     * @param key the key represented by this entry
	     * @param value the value represented by this entry
	     */
	    public SimplePairEntry(K key, V1 value, V2 value2) {
	        this.key   = key;
	        this.value1 = value;
	        this.value2 = value2;
	    }
	
	    /**
	     * Creates an entry representing the same mapping as the
	     * specified entry.
	     *
	     * @param entry the entry to copy
	     */
	    public SimplePairEntry(PairEntry<? extends K, ? extends V1, ? extends V2> entry) {
	        this.key   = entry.getKey();
	        this.value1 = entry.getValue1();
	        this.value2 = entry.getValue2();
	    }
	
	    /**
	     * Returns the key corresponding to this entry.
	     *
	     * @return the key corresponding to this entry
	     */
	    public K getKey() {
	        return key;
	    }
	
	    /**
	     * Returns the value corresponding to this entry.
	     *
	     * @return the value corresponding to this entry
	     */
	    public V1 getValue1() {
	        return value1;
	    }
	    
	    public V2 getValue2() {
	        return value2;
	    }
	
		@Override
		public Map.Entry<V1, V2> getPair() {
			return new AbstractMap.SimpleEntry(value1, value2);
		}
	    /**
	     * Replaces the value corresponding to this entry with the specified
	     * value.
	     *
	     * @param value new value to be stored in this entry
	     * @return the old value corresponding to the entry
	     */
	    public V1 setValue1(V1 value) {
	        V1 oldValue = this.value1;
	        this.value1 = value;
	        return oldValue;
	    }
	    
	    public V2 setValue2(V2 value) {
	        V2 oldValue = this.value2;
	        this.value2 = value;
	        return oldValue;
	    }

    }
    
	    public static class SimpleImmutablePairEntry<K, V1, V2> implements PairEntry<K, V1, V2>, java.io.Serializable
	{
	    private static final long serialVersionUID = 7138329143949025153L;
	
	    private final K key;
	    private final V1 value1;
	    private final V2 value2;
	
	    /**
	     * Creates an entry representing a mapping from the specified
	     * key to the specified value.
	     *
	     * @param key the key represented by this entry
	     * @param value the value represented by this entry
	     */
	    public SimpleImmutablePairEntry(K key, V1 value, V2 value2) {
	        this.key   = key;
	        this.value1 = value;
	        this.value2 = value2;
	    }
	
	    /**
	     * Creates an entry representing the same mapping as the
	     * specified entry.
	     *
	     * @param entry the entry to copy
	     */
	    public SimpleImmutablePairEntry(PairEntry<? extends K, ? extends V1, ? extends V2> entry) {
	        this.key   = entry.getKey();
	        this.value1 = entry.getValue1();
	        this.value2 = entry.getValue2();
	    }
	
	    /**
	     * Returns the key corresponding to this entry.
	     *
	     * @return the key corresponding to this entry
	     */
	    public K getKey() {
	        return key;
	    }
	
	    /**
	     * Returns the value corresponding to this entry.
	     *
	     * @return the value corresponding to this entry
	     */
	    public V1 getValue1() {
	        return value1;
	    }
	    
	    public V2 getValue2() {
	        return value2;
	    }
	
	    /**
	     * Replaces the value corresponding to this entry with the specified
	     * value (optional operation).  This implementation simply throws
	     * <tt>UnsupportedOperationException</tt>, as this class implements
	     * an <i>immutable</i> map entry.
	     *
	     * @param value new value to be stored in this entry
	     * @return (Does not return)
	     * @throws UnsupportedOperationException always
	     */
	    public V1 setValue1(V1 value) {
	        throw new UnsupportedOperationException();
	    }
	    
	    public V2 setValue2(V2 value) {
	        throw new UnsupportedOperationException();
	    }

		@Override
		public Map.Entry<V1, V2> getPair() {
			return new AbstractMap.SimpleImmutableEntry(value1, value2);
		}
	
	}	

	
}
