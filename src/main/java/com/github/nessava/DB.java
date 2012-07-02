package com.github.nessava;

public class DB {
	private final String dir;
	private final LLRU llru;
	private final Index index;

	public static DB open(String dir) {
		return new DB(dir);
	}

	private DB(String dir) {
		this.dir = dir;
		this.llru = new LLRU();
		this.index = new Index(dir);
	}

	public byte[] get(byte[] key) {
		byte[] value = llru.get(key);
		if (value == null) {
			value = index.get(key);
			if (value != null) {
				llru.set(key, value);
			}
		}
		return value;
	}

	public boolean exist(byte[] key) {
		return false;
	}

	public boolean add(byte[] key, byte[] value) {
		llru.remove(key,value);
		return index.add(key,value);
	}

	public boolean remove(byte[] key) {
		return false;
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
