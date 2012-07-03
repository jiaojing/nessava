package com.github.nessava;

import com.google.common.base.Objects;

public class SkipNode {
	public final long offset;

	private OPT opt;

	public static SkipNode del() {
		return new SkipNode(OPT.REMOVE, 0L);
	}

	public static SkipNode add(long offset) {
		return new SkipNode(OPT.ADD, offset);
	}

	private SkipNode(OPT opt, long offset) {
		this.opt = opt;
		this.offset = offset;
	}

	public boolean isDeleted() {
		return this.opt == OPT.REMOVE;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("offset", this.offset)
				.add("opt", this.opt).toString();
	}

}

enum OPT {
	ADD(), REMOVE()
}
