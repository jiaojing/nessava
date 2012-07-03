package com.github.nessava;

import org.junit.Test;

import com.google.common.primitives.Ints;

public class LogTest {

	static Log log = new Log("/tmp/nessdb/1.log");

	//@Test
	public void test_append() {
		for (int i = 0; i < 1000; i++) {
			log.append(Ints.toByteArray(i), i);
		}
	}

	@Test
	public void test_recovery() {
		for (SkipEntry entry : log) {
			System.out.println(Ints.fromByteArray(entry.key) + "--->"
					+ entry.node);
		}
	}
}
