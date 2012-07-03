package com.github.nessava;

import java.io.File;

import org.junit.Test;

public class MMTTest {
	
	@Test
	public void test() {
		File[] logs = MMT.findLogFiles(new File("/tmp/nessdb/"));
		logs = MMT.sortByNum(logs);
		for(File log :logs){
			System.out.println(log.getName());
		}
	}

}
