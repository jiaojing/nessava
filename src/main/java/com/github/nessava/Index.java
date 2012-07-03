package com.github.nessava;

public class Index {
	// bloomfilter
	private final Bloom bloom;
	// memtable
	private final MMT mmt;
	// sst
	private final SST sst;
	// value stored in..
	private final Data data;

	public Index(String dir) {
		this.bloom = new Bloom();
		this.data = new Data(dir);
		this.mmt = new MMT(dir, this.data);
		this.sst = new SST(dir);
	}

	public byte[] get(byte[] key) {
		byte[] value = null;
		long offset = Data.INVALID_OFFSET;
		// bloom filter
		if (!bloom.contain(key)) {
			return value;
		}
		//mmt lookup
		offset = mmt.lookup(key);
		// sst lookup
		if (Data.invalid(offset)) {
			offset = sst.getOffset(key);
		}
		// get value
		if (!Data.invalid(offset)) {
			value = data.getWithCatch(offset);
		}
		return value;
	}

	public boolean add(byte[] key, byte[] value) {
		boolean succ = mmt.add(key, value);
		if (succ) {
			bloom.add(key);
		}
		return succ;
	}

	public boolean remove(byte[] key) {
		return false;
	}
}
