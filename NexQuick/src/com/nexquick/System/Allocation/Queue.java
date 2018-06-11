package com.nexquick.System.Allocation;

import java.util.LinkedList;

public interface Queue {

	void clear();

	void put(Object o);

	Object pop();

	int size();

}