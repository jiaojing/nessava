package com.github.nessava;

public class SkipNode {

	public static SkipNode value(long offset) {
		return new SkipNode(offset);
	}

	private SkipNode(long offset) {
	}

	public boolean isDeleted() {
		// TODO Auto-generated method stub
		return false;
	}

}
