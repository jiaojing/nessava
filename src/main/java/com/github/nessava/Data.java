package com.github.nessava;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import com.google.common.io.Files;
import com.google.common.primitives.Ints;

/**
 * 一个写，多个读。提高并发性？
 * 
 * @author tony
 */
public class Data {

	private FileChannel channel;

	@SuppressWarnings("resource")
	public Data(String dir) {
		try {
			File dataFile = new File(dir + "/ness.db");
			Files.touch(dataFile);
			this.channel = new RandomAccessFile(dataFile, "rw").getChannel();
		} catch (Exception e) {
			// log here...
			System.exit(-1);
		}
	}

	/**
	 * maybe 可以做group commit？
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public long write(byte[] value) throws IOException {
		long offset = channel.position();
		ByteBuffer buffer = ByteBuffer.allocate(Ints.BYTES + value.length);
		buffer.putInt(value.length).put(value).flip();
		while (buffer.hasRemaining()) {
			channel.write(buffer);
		}
		channel.force(false);
		return offset;
	}

	public byte[] get(long offset) throws IOException {
		ByteBuffer header = ByteBuffer.allocate(Ints.BYTES);
		channel.read(header, offset);
		int length = Ints.fromByteArray(header.array());
		ByteBuffer buffer = ByteBuffer.allocate(length);
		channel.read(buffer, offset + Ints.BYTES);
		return buffer.array();
	}

	public byte[] getWithCatch(long offset) {
		try {
			return get(offset);
		} catch (IOException e) {
			// log here...
			return null;
		}
	}

}
