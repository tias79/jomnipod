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

import com.github.jomnipod.HistoryLogRecord;
import com.github.jomnipod.IBFRecord.Iterator;
import com.github.jomnipod.InsulinUnits;

import lombok.ToString;

@ToString
public class BolusLogRecordDetails extends HistoryLogRecord {

	private InsulinUnits units;

	public BolusLogRecordDetails(EnumSet<Flag> flags, Iterator iterator) {
		super(flags);

		units = new InsulinUnits(iterator.nextUnsignedLEInteger());
		int extendedDurationMinutes = iterator.nextUnsignedLEShort();
		int calculationRecordOffset = iterator.nextUnsignedLEShort();
		int immediateDurationSeconds = iterator.nextUnsignedLEShort();

		boolean extendedBolusWithNoExtendedBolusDelivered = false;
		boolean nonExtendedBolus = false;
		if (extendedDurationMinutes == 65535) {
			extendedBolusWithNoExtendedBolusDelivered = true;
		} else {
			nonExtendedBolus = true;
		}
	}

	public InsulinUnits units() {
		return units;
	}
}
