package br.com.clebertm.procurados.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class IOUtils {

	private static final int IO_BUFFER_SIZE = 4 * 1024;

	/**
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void copyStream(InputStream in, OutputStream out)
			throws IOException {
		copyStream(in, out, IO_BUFFER_SIZE);
	}

	/**
	 * @param in
	 * @param out
	 * @param bufferSize
	 * @throws IOException
	 */
	public static void copyStream(InputStream in, OutputStream out,
			int bufferSize) throws IOException {
		byte[] b = new byte[IO_BUFFER_SIZE];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
		in.close();
		out.close();
	}

	/**
	 * @param c
	 */
	public static void closeQuietly(Closeable c) {
		try {
			c.close();
		} catch (IOException e) {}
	}

}
