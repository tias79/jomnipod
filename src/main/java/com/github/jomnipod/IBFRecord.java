/*
 * Copyright (c) 2017 Mattias Eklöf <mattias.eklof@gmail.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package com.github.jomnipod;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.github.jomnipod.datatypes.SignedBEInteger;
import com.github.jomnipod.datatypes.SignedLEInteger;
import com.github.jomnipod.datatypes.SignedLEShort;
import com.github.jomnipod.datatypes.UnsignedBEShort;
import com.github.jomnipod.datatypes.UnsignedLEInteger;
import com.github.jomnipod.datatypes.UnsignedLEShort;
import com.github.jomnipod.datatypes.ZeroTerminatedString;

public class IBFRecord {

	public class Iterator {

		private ByteBuffer buffer;

		public Iterator(ByteBuffer buffer) {
			this.buffer = buffer;
		}

		public void skipBytes(int nrOfBytes) {
			buffer.get(new byte[nrOfBytes]);
		}

		public String nextString(int maxLength) {
			return new ZeroTerminatedString(buffer, maxLength).value();
		}

		public Byte nextByte() {
			return buffer.get();
		}

		public Integer nextSignedBEInteger() {
			return new SignedBEInteger(buffer).value();
		}

		public Integer nextSignedLEInteger() {
			return new SignedLEInteger(buffer).value();
		}

		public Short nextSignedLEShort() {
			return new SignedLEShort(buffer).value();
		}

		public Integer nextUnsignedBEShort() {
			return new UnsignedBEShort(buffer).value();
		}

		public Long nextUnsignedLEInteger() {
			return new UnsignedLEInteger(buffer).value();
		}

		public Integer nextUnsignedLEShort() {
			return new UnsignedLEShort(buffer).value();
		}
	}

	private byte[] buffer;

	public IBFRecord(InputStream dataStream) throws IOException {
		int recordSize = new UnsignedBEShort(dataStream).value();

		buffer = new byte[recordSize - 2];
		dataStream.read(buffer);

		int checksum = 0;
		for (int i = 0; i < buffer.length; i++) {
			checksum += buffer[i] & 0xff;
		}

		int expectedChecksum = new UnsignedBEShort(dataStream).value();

		if (checksum != expectedChecksum) {
			throw new IBFIntegrityException("Checksum error", String.valueOf(expectedChecksum),
					String.valueOf(checksum));
		}
	}

	public Iterator iterator() {
		return new Iterator(ByteBuffer.wrap(buffer));
	}
}
