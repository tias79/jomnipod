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
package com.github.jomnipod.logrecord;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import com.github.jomnipod.BloodGlucoseUnits;
import com.github.jomnipod.IBFRecord.Iterator;

import lombok.ToString;

@ToString
public class BloodGlucoseLogRecordDetails implements LogRecordDetails {

	public enum Flag {
		MANUAL_FLAG(0x01), TEMPERATURE_FLAG(0x02), BELOW_TARGET_FLAG(0x04), ABOVE_TARGET_FLAG(
				0x08), RANGE_ERROR_LOW_FLAG(0x10), RANGE_ERROR_HIGH_FLAG(0x20), OTHER_ERROR_FLAG(0x40);

		private long id;

		private Flag(long id) {
			this.id = id;
		}

		public long getId() {
			return id;
		}

		public static EnumSet<Flag> valueOf(long flags) {
			EnumSet<Flag> result = EnumSet.noneOf(Flag.class);

			for (Flag flag : values()) {
				if ((flag.id & flags) == flags) {
					result.add(flag);
				}
			}

			return result;
		}
	}

	private BloodGlucoseUnits bgReading;
	private Set<Flag> flags;

	public BloodGlucoseLogRecordDetails(Iterator iterator) {
		long errorCode = iterator.nextUnsignedLEInteger();
		bgReading = new BloodGlucoseUnits(iterator.nextUnsignedLEShort());
		String userTag1 = iterator.nextString(24);
		String userTag2 = iterator.nextString(24);
		flags = Flag.valueOf(iterator.nextByte());
	}

	public BloodGlucoseUnits units() {
		return bgReading;
	}

	public Set<Flag> flags() {
		return new HashSet<>(flags);
	}
}
