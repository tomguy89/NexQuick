package com.nexquick.system.allocation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.nexquick.model.vo.CallInfo;

public class AllocationQueue {
	private Queue<Map<String, Object>> calls = new LinkedList();
	//스레드가 동시가 아닌 순차적으로 큐에 접근해야 하기 때문에 모든 메서드에 Synchronized를 적용했다.
	
	//큐에 싱글톤 적용 
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
	
	
	//고객의 콜 신청이 완료되면 큐에 해당 콜을 넣는다.
	public synchronized void offer(CallInfo callInfo, int repeat) {
		Map<String, Object> map = new HashMap<>();
		map.put("repeat", repeat);
		map.put("callInfo", callInfo);
		calls.offer(map);
		this.notify();
	}
	
	//배차를 담당하는 스레드가 Queue에서 하나씩 빼간다.
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
