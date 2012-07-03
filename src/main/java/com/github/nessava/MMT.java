package com.github.nessava;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.primitives.Ints;

public class MMT {
	private final String dir;
	private final Data data;

	private int lognum;
	private SkipList now;
	private SkipList merging = null;

	public MMT(String dir, Data data) {
		this.dir = dir;
		this.data = data;
		logRecover();
		this.now = new SkipList(nextlog(), data);
	}

	public long lookup(byte[] key) {
		long offset = Data.INVALID_OFFSET;
		// now
		SkipNode node = now.lookup(key);
		if (node != null) {
			if (node.isDeleted()) {
				return offset;
			} else {
				offset = node.offset;
			}
		}
		// merging list
		if (Data.invalid(offset)) {
			if (merging != null) {
				offset = merging.offset(key);
			}
		}
		return offset;
	}

	public boolean add(byte[] key, byte[] value) {
		if (now.isFull()) {
			merging = now;
			now = new SkipList(nextlog(), data);
			// merge here...
		}
		return now.insert(key, null);
	}

	private String nextlog() {
		lognum++;
		return dir + "/" + lognum + ".log";
	}

	private void logRecover() {
		File dbDir = new File(this.dir);
		if (!dbDir.isDirectory()) {
			System.exit(-1);
		}
		// 1.extract log files
		File[] logfiles = findLogFiles(dbDir);
		// 2. sort it.
		sortByNum(logfiles);
		// 3.
		for (File log : logfiles) {
			
		}

	}

	@VisibleForTesting
	static File[] sortByNum(File[] logfiles) {
		Arrays.sort(logfiles, new Comparator<File>() {
			public int compare(File o1, File o2) {
				int o1num = num(o1.getName());
				int o2num = num(o2.getName());
				return Ints.compare(o1num, o2num);
			}

			private int num(String filename) {
				int num = -1;
				Matcher m = p.matcher(filename);
				if (m.matches()) {
					num = Integer.valueOf(m.group(1));
				}
				return num;
			}
		});
		return logfiles;
	}

	@VisibleForTesting
	static File[] findLogFiles(File dbDir) {
		File[] logfiles = dbDir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				Matcher m = p.matcher(name);
				return m.matches();
			}
		});
		return logfiles;
	}

	private static final Pattern p = Pattern.compile("(\\d+).log");
}
