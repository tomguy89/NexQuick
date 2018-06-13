package com.nexquick.System.Allocation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.nexquick.model.vo.CallInfo;

public class AllocationQueue {
	private Queue<Map<String, Object>> calls = new LinkedList();
	
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
	
	
	public Queue<Map<String, Object>> getLinkedList() {
		return calls;
	}
	
	public synchronized void clear() {
			calls.clear();
	}
	
	public synchronized void offer(CallInfo callInfo, int repeat) {
		Map<String, Object> map = new HashMap<>();
		map.put("repeat", repeat);
		map.put("callInfo", callInfo);
		calls.offer(map);
		this.notify();
	}
	
	public synchronized Map<String, Object> poll() {
		Map<String, Object> map = null;
			try {
				if(calls.size()==0) {
					System.out.println("대기중");
					this.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		map = calls.poll();
		return map;
	}
	
	public int size() {
		return calls.size();
	}
}
