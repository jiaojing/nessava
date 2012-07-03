package com.github.nessava;

import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

public class DataTest {

	static final Data data = new Data("/tmp/");

	@Test
	public void test() throws Exception {
		List<Long> offsets = Lists.newArrayList();
		for (int i = 0; i < 100; i++) {
			long offset = data.write(Ints.toByteArray(i));
			offsets.add(offset);
		}

		Collections.reverse(offsets);
		int j = 99;
		for (long offset : offsets) {
			int i = Ints.fromByteArray(data.get(offset));
			System.out.println(i);
			Assert.assertEquals(j, i);
			j--;
		}

	}

}
