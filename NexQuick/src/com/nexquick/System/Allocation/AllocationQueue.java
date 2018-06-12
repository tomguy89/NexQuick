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
	
	public synchronized void offer(CallInfo callInfo) {
		calls.offer(callInfo);
		this.notify();
	}
	
	public synchronized CallInfo poll() {
		CallInfo callInfo = null;
			try {
				if(calls.size()==0) {
					System.out.println("대기중");
					this.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		callInfo = calls.poll();
		return callInfo;
	}
	
	public int size() {
		return calls.size();
	}
}
