package com.github.nessava;

import java.io.File;

public class DB {
	private final LRUCache lru;
	private final Index index;

	public static DB open(String dir) {
		return new DB(dir);
	}

	private DB(String dir) {
		ensureDirExist(dir);
		this.lru = new LRUCache(10000);
		this.index = new Index(dir);
	}

	private boolean ensureDirExist(String dir) {
		File dbdir = new File(dir);
		return dbdir.mkdirs();
	}

	public byte[] get(byte[] key) {
		byte[] value = lru.get(key);
		if (value == null) {
			value = index.get(key);
			if (value != null) {
				lru.set(key, value);
			}
		}
		return value;
	}

	public boolean exist(byte[] key) {
		return false;
	}

	public boolean add(byte[] key, byte[] value) {
		lru.remove(key);
		return index.add(key, value);
	}

	public boolean remove(byte[] key) {
		lru.remove(key);
		return index.remove(key);
	}

	public void close() {

	}

	public String info() {
		return null;
	}

	public static void main(String[] args) {
		System.out.println("hello nessava!");
	}
}
