package ws.mechanism.binding;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class WsMap<K, V> implements ObserveredAutoAction ,Serializable, Cloneable, Map<K,V>{
	private Map<K,V> trueMap = new HashMap<K,V>();
	private ContainerBinding bridge = null;


	private int processJudge() {
		if(this.bridge == null)
			return 1;//只执行自身修改操作
		if(this.bridge.getFront() != this)
			return 2;//执行自身修改操作，调用同步操作
		if(this.bridge.getMode() != ContainerBinding.BINDINGMODE_READONLY)
			return 2;//不允许写入不执行任何操作
		return 0;//只读模式，不做任何修改
	}

	public int size() {
		// TODO Auto-generated method stub
		return this.trueMap.size();
	}


	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.trueMap.isEmpty();
	}


	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return this.trueMap.containsKey(key);
	}


	public boolean containsValue(Object value) {
		return this.trueMap.containsValue(value);
	}

	
	public V get(Object key) {
		return this.trueMap.get(key);
	}

	public V put(K key, V value) {
		V rv =null;
		int mark = this.processJudge();
		if(mark > 0)
			this.trueMap.put(key, value);
		if(mark > 1)
			this.bridge.actionRecall(this, "public V put(K key, V value)");
		return rv;
	}

	public V remove(Object key) {
		V rv =null;
		int mark = this.processJudge();
		if(mark > 0)
			rv=this.trueMap.remove(key);
		if(mark > 1)
			this.bridge.actionRecall(this, "public V remove(Object key)");
		return rv;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		int mark = this.processJudge();
		if(mark > 0)
			this.trueMap.putAll(m);
		if(mark > 1)
			this.bridge.actionRecall(this, "public void putAll(Map<? extends K, ? extends V> m) ");
	}


	public void clear() {
		int mark = this.processJudge();
		if(mark > 0)
			this.trueMap.clear();
		if(mark > 1)
			this.bridge.actionRecall(this, "public void clear()");
		
	}

	public Set<K> keySet() {
		Set<K> rv = null;
		int mark = this.processJudge();
		if(mark > 0)
			rv = this.trueMap.keySet();
		if(mark > 1)
			this.bridge.actionRecall(this, "public Set<K> keySet()");
		return rv;
	}


	public Collection<V> values() {
		Collection<V> rv = null;
		int mark = this.processJudge();
		if(mark > 0)
			rv = this.trueMap.values();
		if(mark > 1)
			this.bridge.actionRecall(this, "public Collection<V> values()");
		return rv;
	}

	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> rv = null;
		int mark = this.processJudge();
		if(mark > 0)
			rv = this.trueMap.entrySet();
		if(mark > 1)
			this.bridge.actionRecall(this, "public Set<Entry<K, V>> entrySet()");
		return rv;
	}

	@Override
	public void _bindingFrom(ContainerBinding c) {
		// TODO Auto-generated method stub
		this.bridge = c;
	}

	@Override
	public void _sync_to(ObserveredAutoAction front) {
		// TODO Auto-generated method stub
		((WsMap<K,V>)front).trueMap = this.trueMap;
	}

	@Override
	public abstract void actionPerferm(String typeMsg);

}
