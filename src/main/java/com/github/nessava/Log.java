package com.github.nessava;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public class Log {

	private FileChannel file;

	private final Data data;

	public Log(String dir, Data data) {
		try {
			this.file = new RandomAccessFile(dir, "rw").getChannel();
		} catch (FileNotFoundException e) {
			// log here...
			System.exit(-1);
		}
		this.data = data;
	}

	public long append(byte[] key, byte[] value) {
		long offset = 0L;
		try {
			offset = data.write(value);
			ByteBuffer buffer = ByteBuffer.allocate(Ints.BYTES + key.length
					+ Longs.BYTES);
			buffer.putInt(key.length).put(key).putLong(offset).flip();
			while (buffer.hasRemaining()) {
				file.write(buffer);
			}
			file.force(false);
		} catch (Exception e) {
			// log here...
		}
		return offset;
	}
}
