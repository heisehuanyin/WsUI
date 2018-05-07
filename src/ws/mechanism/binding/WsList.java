package ws.mechanism.binding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public abstract class WsList<E> implements ObserveredAutoSync,Serializable, Cloneable, Iterable<E>, Collection<E>, List<E>, RandomAccess{
	private List<E> trueList = new ArrayList<E>();
	private WsContainerBinding bridge = null;
	
	private int processJudge() {
		if(this.bridge == null)
			return 1;//只执行自身修改操作
		if(this.bridge.getFront() != this)
			return 2;//执行自身修改操作，调用同步操作
		if(this.bridge.getMode() != WsContainerBinding.BINDINGMODE_READONLY)
			return 2;//不允许写入不执行任何操作
		return 0;//只读模式，不做任何修改
	}

	@Override
	public abstract void actionPerferm(String typeMsg);
	
	public boolean addAll(int index, Collection<? extends E> c) {
		boolean rv = false;
		int mark = this.processJudge();
		if(mark > 0) {
			rv = this.trueList.addAll(index, c);
		}
		if(mark > 1) {
			this.bridge.actionRecall(this, "public boolean addAll(int index, Collection<? extends E> c)");
		}
		
		return rv;
	}

	public E get(int index) {
		return this.trueList.get(index);
	}

	public E set(int index, E element) {
		E rv = null;
		int mark = this.processJudge();
		if(mark > 0) {
			rv = this.trueList.set(index, element);
		}
		if(mark > 1) {
			this.bridge.actionRecall(this, "public E set(int index, E element)");
		}
		
		return rv;
	}


	public void add(int index, E element) {
		int mark = this.processJudge();
		if(mark > 0) {
			this.trueList.add(index, element);
		}
		if(mark > 1) {
			this.bridge.actionRecall(this, "public void add(int index, E element)");
		}
	}

	
	public E remove(int index) {
		E rv = null;
		int mark = this.processJudge();
		if(mark>0)
			rv = this.trueList.remove(index);
		if(mark>1) {
			this.bridge.actionRecall(this, "public E remove(int index)");
		}
		return rv;
	}

	public int indexOf(Object o) {
		return this.trueList.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return this.trueList.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return this.trueList.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return this.trueList.listIterator(index);
	}


	public List<E> subList(int fromIndex, int toIndex) {
		return this.trueList.subList(fromIndex, toIndex);
	}

	public int size() {
		return this.trueList.size();
	}

	public boolean isEmpty() {
		return this.trueList.isEmpty();
	}

	public boolean contains(Object o) {
		return this.trueList.contains(o);
	}

	public Object[] toArray() {
		// TODO Auto-generated method stub
		return this.trueList.toArray();
	}


	public <T> T[] toArray(T[] a) {
		return this.trueList.toArray(a);
	}

	public boolean add(E e) {
		boolean tv = false;
		int mark = this.processJudge();
		if(mark > 0)
			tv = this.trueList.add(e);
		if(mark > 1)
			this.bridge.actionRecall(this, "public boolean add(E e)");
		return tv;
	}

	public boolean remove(Object o) {
		boolean tv = false;
		int mark = this.processJudge();
		if(mark > 0)
			tv = this.trueList.remove(o);
		if(mark > 1)
			this.bridge.actionRecall(this, "public boolean remove(Object o)");
		return tv;
	}

	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return this.trueList.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean tv = false;
		int mark = this.processJudge();
		if(mark > 0)
			tv = this.trueList.addAll(c);
		if(mark > 1)
			this.bridge.actionRecall(this,"public boolean addAll(Collection<? extends E> c)");
		return tv;
	}

	public boolean removeAll(Collection<?> c) {
		boolean rv = false;
		int mark = this.processJudge();
		if(mark > 0)
			rv = this.trueList.removeAll(c);
		if(mark > 1)
			this.bridge.actionRecall(this, "public boolean removeAll(Collection<?> c)");
		return rv;
	}

	public boolean retainAll(Collection<?> c) {
		boolean rv = false;
		int mark = this.processJudge();
		if(mark > 0)
			rv = this.trueList.retainAll(c);
		if(mark > 1)
			this.bridge.actionRecall(this, "public boolean retainAll(Collection<?> c)");
		return rv;
	}

	public void clear() {
		int mark = this.processJudge();
		if(mark > 0)
			this.trueList.clear();
		if(mark > 1)
			this.bridge.actionRecall(this, "clear()");
	}

	public Iterator<E> iterator() {
		return this.trueList.iterator();
	}

	@Override
	public void _bindingFrom(WsContainerBinding c) {
		// TODO Auto-generated method stub
		this.bridge = c;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void _sync_to(ObserveredAutoSync front) {
		// TODO Auto-generated method stub
		((WsList<E>)front).trueList = this.trueList;
	}

}
