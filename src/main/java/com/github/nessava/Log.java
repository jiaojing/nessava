package com.github.nessava;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public class Log implements Iterable<SkipEntry> {

	private FileChannel file;

	public Log(String logfile) {
		this(new File(logfile));
	}

	public Log(File logfile) {
		try {
			this.file = new RandomAccessFile(logfile, "rw").getChannel();
		} catch (FileNotFoundException e) {
			// log here...
			System.exit(-1);
		}
	}

	public boolean append(byte[] key, long offset) {
		try {
			ByteBuffer buffer = ByteBuffer.allocate(Ints.BYTES + key.length
					+ Longs.BYTES);
			buffer.putInt(key.length).put(key).putLong(offset).flip();
			while (buffer.hasRemaining()) {
				file.write(buffer);
			}
			file.force(false);
		} catch (Exception e) {
			// log here...
			return false;
		}
		return true;
	}

	// file lock here...
	public Iterator<SkipEntry> iterator() {
		return new Iterator<SkipEntry>() {
			final ByteBuffer header = ByteBuffer.allocate(Ints.BYTES);
			long end = 0;
			{
				try {
					end = file.size();
				} catch (IOException e) {
					// log here...
					System.exit(-1);
				}
			}
			long now = 0L;

			private int vlen(ByteBuffer buf) {
				buf.flip();
				int len = Ints.fromByteArray(buf.array());
				buf.clear();
				return len;
			}

			public boolean hasNext() {
				return end > now;
			}

			public SkipEntry next() {
				SkipEntry entry = null;
				try {
					// read header
					Log.this.file.read(header);
					int len = vlen(header);
					final ByteBuffer vbuf = ByteBuffer.allocate(len
							+ Longs.BYTES);
					Log.this.file.read(vbuf);
					vbuf.flip();
					byte[] key = new byte[len];
					vbuf.get(key);
					long offset = vbuf.getLong();
					SkipNode node = null;
					if (Data.invalid(offset)) {
						node = SkipNode.del();
					} else {
						node = SkipNode.add(offset);
					}
					entry = new SkipEntry(key, node);
					
					now = Log.this.file.position();
				} catch (IOException e) {
					// log here...
					System.exit(-1);
				}
				return entry;
			}

			public void remove() {

			}
		};
	}
}
