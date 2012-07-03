package com.github.nessava;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SkipList {

	private final int count;
	private final AtomicInteger size;
	private final ConcurrentSkipListMap<byte[], SkipNode> skip;

	private final Data data;
	private final Log log;

	public SkipList(int count, String logfile, Data data) {
		this.count = count;
		this.size = new AtomicInteger(0);
		this.skip = new ConcurrentSkipListMap<byte[], SkipNode>();
		this.data = data;
		this.log = new Log(logfile);
	}

	public SkipList(String logfile, Data data) {
		this(10000, logfile, data);
	}

	public SkipNode lookup(byte[] key) {
		return skip.get(key);
	}

	public long offset(byte[] key) {
		long offset = Data.INVALID_OFFSET;
		SkipNode node = this.lookup(key);
		if (node != null) {
			if (node.isDeleted()) {
				return offset;
			} else {
				offset = node.offset;
			}
		}
		return offset;
	}

	public boolean isFull() {
		return size.intValue() > count;
	}

	public boolean insert(byte[] key, byte[] value) {
		long offset = data.writeWitchCatch(value);
		if (offset >= 0) {
			if (log.append(key, offset)) {
				skip.put(key, SkipNode.add(offset));
				size.incrementAndGet();
				return true;
			}
		}
		return false;
	}

}
