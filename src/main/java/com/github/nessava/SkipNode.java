package com.github.nessava;

public class SkipNode {

	private byte[] value;

	private Long offset;

	private OPT opt;

	public static SkipNode del() {
		return new SkipNode(null, OPT.REMOVE, null);
	}

	public static SkipNode add(byte[] value, long offset) {
		return new SkipNode(value, OPT.ADD, offset);
	}

	public static boolean isNil(SkipNode node) {
		return node == null || node.isDeleted();
	}

	private SkipNode(byte[] value, OPT opt, Long offset) {
		this.value = value;
		this.opt = opt;
		this.offset = offset;
	}

	public boolean isDeleted() {
		return this.opt == OPT.REMOVE;
	}

}

enum OPT {
	ADD(), REMOVE()
}
