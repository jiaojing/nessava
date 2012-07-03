package com.github.nessava;

public class Index {
	// skiplist
	private final SkipList skiplist;
	// log
	private final Log log;
	// bloomfilter
	private final Bloom bloom;
	// sst
	private final SST sst;
	// value stored in..
	private final Data data;

	public Index(String dir) {
		this.skiplist = new SkipList(100000);
		this.data = new Data(dir);
		this.log = new Log(dir, data);
		this.bloom = new Bloom();
		this.sst = new SST();
	}

	public byte[] get(byte[] key) {
		byte[] value = null;
		if (!bloom.contain(key)) {
			return value;
		}
		// skiplist
		SkipNode node = skiplist.lookup(key);
		if (SkipNode.isNil(node)) {
			// merge skiplist lookup
			return value;
		}
		long offset = sst.get(key);
		if (offset != 0L) {
			value = data.getWithCatch(offset);
		}

		return value;
	}

	public boolean add(byte[] key, byte[] value) {
		// log append here
		long offset = log.append(key, value);
		SkipList now = this.skiplist;
		if (now.isFull()) {
			// merge here.
			now = new SkipList(100000);
			// log next
		}
		// 插入到skiplist
		boolean succ = now.insert(key, SkipNode.add(value, offset));
		if (succ) {
			bloom.add(key);
		}
		return false;
	}

}
