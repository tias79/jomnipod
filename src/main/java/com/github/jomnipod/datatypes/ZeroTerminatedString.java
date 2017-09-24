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
package com.github.jomnipod.datatypes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class ZeroTerminatedString implements DataType<String> {

	private byte[] buffer;

	public ZeroTerminatedString(ByteBuffer byteBuffer, int maxLength) {
		buffer = new byte[maxLength];
		byteBuffer.get(buffer);
	}

	public ZeroTerminatedString(InputStream stream, int maxLength) throws IOException {
		buffer = new byte[maxLength];
		stream.read(buffer);
	}

	@Override
	public String value() {
		int length = buffer.length;
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i] == 0) {
				length = i;
				break;
			}
		}

		return new String(buffer, 0, length, Charset.forName("ISO-8859-1"));
	}
}
