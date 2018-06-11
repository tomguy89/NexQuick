package com.nexquick.System.Allocation;

import java.util.LinkedList;

public class AllocationQueue implements Queue {
	private static final Object monitor = new Object();
	private LinkedList calls = new LinkedList();
	
	private static Queue instance = new AllocationQueue();
	private AllocationQueue() {}
	
	public static Queue getInstance() {
		if(instance == null) {
			synchronized(AllocationQueue.class) {
				instance = new AllocationQueue();
			}
		}
		return instance;
	}
	
	
	public LinkedList getLinkedList() {
		return calls;
	}
	
	@Override
	public void clear() {
		synchronized(this.monitor) {
			calls.clear();
		}
	}
	
	@Override
	public void put(Object o) {
		synchronized(monitor) {
			calls.addLast(o);
			monitor.notify();
		}
	}
	
	@Override
	public Object pop() {
		Object o = null;
		synchronized(monitor) {
			try {
				if (calls.isEmpty()) {
					monitor.wait();
				}
				o = calls.removeFirst();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return o;
	}
	
	@Override
	public int size() {
		return calls.size();
	}
}
