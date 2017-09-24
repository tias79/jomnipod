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

import java.util.ArrayList;
import java.util.List;

import com.github.jomnipod.IBFRecord.Iterator;

public class Profile {

	private String name;

	private Integer errorCode;

	private Long operationTime;

	private List<Long> profile = new ArrayList<>();

	public Profile(IBFRecord record, BasalPrograms basalPrograms) {
		Iterator iterator = record.iterator();

		int index = iterator.nextByte();

		iterator.skipBytes(6);

		errorCode = iterator.nextUnsignedBEShort();

		operationTime = iterator.nextUnsignedLEInteger();

		ProfileType profileType = ProfileType.getProfileByIndex(index);
		name = "";
		if (profileType != null && profileType.isBasal()) {
			name = basalPrograms.name(index - 15);
		}

		for (int i = 0; i < 48; i++) {
			profile.add(iterator.nextUnsignedLEInteger());
		}
	}

	public String name() {
		return name;
	}
}
