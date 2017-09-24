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

import java.util.HashMap;
import java.util.Map;

import com.github.jomnipod.IBFRecord.Iterator;

public class BasalPrograms {

	private Map<Integer, String> basalPrograms = new HashMap<>();

	private Integer enabledIndex;

	public BasalPrograms(IBFRecord record) {
		Iterator iterator = record.iterator();

		Integer numberOfPrograms = iterator.nextUnsignedBEShort();
		enabledIndex = iterator.nextUnsignedBEShort();
		Integer maxNameSize = iterator.nextUnsignedBEShort();

		for (int i = 0; i < numberOfPrograms; i++) {
			Integer index = iterator.nextUnsignedBEShort();
			String name = iterator.nextString(maxNameSize);
			basalPrograms.put(index, name);
		}
	}

	public String name(Integer index) {
		return basalPrograms.get(index);
	}

	public Integer enabledIndex() {
		return enabledIndex;
	}
}
