package com.github.nessava;

public class Index {

	// skiplist
	private final SkipList skiplist;
	// log
	private final Log log;
	// bloomfilter
	private final Bloomfilter bloom;
	// sst
	private final SST sst;

	private final Data data;

	public Index(String dir) {
		this.skiplist = new SkipList(100000);
		this.log = new Log();
		this.bloom = new Bloomfilter();
		this.sst = new SST();
		this.data = new Data();
	}

	public byte[] get(byte[] key) {
		byte[] value = null;
		if (!bloom.contain(key)) {
			return value;
		}
		// skiplist
		SkipNode node = skiplist.lookup(key);
		if (node != null && node.isDeleted()) {
			return value;
		}
		// merge skiplist lookup
		long offset = sst.get(key);
		if (offset != 0L) {
			value = data.get(offset);
		}

		return value;
	}

	public boolean add(byte[] key, byte[] value) {
		// log append here
		long offset = log.append(key, value);
		SkipList now = this.skiplist;
		if (skiplist.isFull()) {
			// merge here.
			now = new SkipList(100000);
		}
		// 插入到skiplist
		boolean succ = now.insert(key, SkipNode.value(offset));
		if (succ) {
			bloom.add(key);
		}
		return false;
	}

}
