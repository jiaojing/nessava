package com.github.nessava;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SkipList {

	private final AtomicInteger size;
	private final ConcurrentSkipListMap<byte[], SkipNode> skip;

	public SkipList(int i) {
		this.size = new AtomicInteger(i);
		this.skip = new ConcurrentSkipListMap<byte[], SkipNode>();
	}

	public SkipNode lookup(byte[] key) {
		return skip.get(key);
	}

	public boolean isFull() {
		return false;
	}

	public boolean insert(byte[] key, SkipNode skipNode) {
		skip.put(key, skipNode);
		return true;
	}

}
