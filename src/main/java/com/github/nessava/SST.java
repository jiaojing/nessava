package com.github.nessava;

import java.io.File;
import java.io.FilenameFilter;

public class SST {

	private final String dir;
	private final Meta meta;

	public SST(String dir) {
		this.dir = dir;
		this.meta = new Meta();
		_init();
	}

	private void _init() {
		File dbDir = new File(dir);
		if (!dbDir.isDirectory()) {
			System.exit(-1);
		}
		File[] sstFiles = dbDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".sst");
			}
		});
		for (File sst : sstFiles) {
			
		}
	}

	public long getOffset(byte[] key) {
		return 0;
	}

}
