package com.github.nessava;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

public class Log {

	private FileChannel file;

	public Log(String logfile) {
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
}
