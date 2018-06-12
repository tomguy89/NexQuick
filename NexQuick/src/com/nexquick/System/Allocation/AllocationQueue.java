package com.nexquick.System.Allocation;

import java.util.LinkedList;
import java.util.Queue;

import com.nexquick.model.vo.CallInfo;

public class AllocationQueue {
	private Queue<CallInfo> calls = new LinkedList();
	
	private static AllocationQueue instance = new AllocationQueue();
	private AllocationQueue() {}
	
	public static AllocationQueue getInstance() {
		if(instance == null) {
			synchronized(AllocationQueue.class) {
				instance = new AllocationQueue();
			}
		}
		return instance;
	}
	
	
	public Queue<CallInfo> getLinkedList() {
		return calls;
	}
	
	public synchronized void clear() {
			calls.clear();
	}
	
	public void offer(CallInfo callInfo) {
		synchronized(AllocationQueue.class) {
			calls.offer(callInfo);
			this.notify();
		}
	}
	
	public CallInfo poll() {
		CallInfo callInfo = null;
		synchronized(AllocationQueue.class) {
			callInfo = calls.poll();
		}
		return callInfo;
	}
	
	public int size() {
		return calls.size();
	}
}
