package com.github.nessava;

import java.util.concurrent.ConcurrentSkipListMap;

public class SkipLog {

	private final ConcurrentSkipListMap<byte[], SkipNode> skip;

	public SkipLog() {
		this.skip = new ConcurrentSkipListMap<byte[], SkipNode>();
	}

	public boolean add(byte[] key, SkipNode value) {
		this.skip.put(key, value);
		return true;
	}
}
