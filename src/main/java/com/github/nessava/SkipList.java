package com.github.nessava;

import java.util.concurrent.atomic.AtomicInteger;

public class SkipList {

	private final AtomicInteger size;

	public SkipList(int i) {
		this.size = new AtomicInteger(i);
	}

	public SkipNode lookup(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isFull() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean insert(byte[] key, SkipNode skipNode) {
		return false;
	}

}
