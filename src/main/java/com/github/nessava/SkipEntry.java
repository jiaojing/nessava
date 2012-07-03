package com.github.nessava;

import com.google.common.base.Objects;

public class SkipEntry {

	public final byte[] key;
	public final SkipNode node;

	public SkipEntry(byte[] key, SkipNode node) {
		this.key = key;
		this.node = node;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("key", this.key)
				.add("node", this.node).toString();
	}
}
