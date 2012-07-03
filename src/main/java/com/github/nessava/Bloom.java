package com.github.nessava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class Bloom {
	private static final int EXPECTED_INSERTIONS = 1000000;

	private final BloomFilter<byte[]> bloom;

	public Bloom() {
		this.bloom = BloomFilter.create(Funnels.byteArrayFunnel(),
				EXPECTED_INSERTIONS);
	}

	public boolean contain(byte[] key) {
		return this.bloom.mightContain(key);
	}

	public void add(byte[] key) {
		this.bloom.put(key);
	}

}
