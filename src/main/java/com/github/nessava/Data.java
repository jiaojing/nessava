package com.github.nessava;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.google.common.primitives.Ints;

public class Data {

	private FileChannel file;

	public Data(String dir) {
		try {
			this.file = new RandomAccessFile(dir + "ness.db", "rw")
					.getChannel();
		} catch (FileNotFoundException e) {
			// log here...
			System.exit(-1);
		}
	}

	public long write(byte[] value) throws IOException {
		long offset = file.position();
		ByteBuffer buffer = ByteBuffer.allocate(Ints.BYTES + value.length);
		buffer.putInt(value.length).put(value).flip();
		while (buffer.hasRemaining()) {
			file.write(buffer);
		}
		file.force(false);
		return offset;
	}

	public byte[] get(long offset) {
		// TODO Auto-generated method stub
		return null;
	}

}
