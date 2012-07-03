package com.github.nessava;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {

	private final LinkedHashMap<byte[], byte[]> cache;
	private final int capacity;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		cache = new LinkedHashMap<byte[], byte[]>(capacity, 1f, true) {
			private static final long serialVersionUID = 304364011875656880L;

			@Override
			protected boolean removeEldestEntry(Map.Entry<byte[], byte[]> eldest) {
				return this.size() > LRUCache.this.capacity;
			}
		};
	}

	public byte[] get(byte[] key) {
		return cache.get(key);
	}

	public void set(byte[] key, byte[] value) {
		cache.put(key, value);
	}

	public void remove(byte[] key) {
		cache.remove(key);
	}

}
