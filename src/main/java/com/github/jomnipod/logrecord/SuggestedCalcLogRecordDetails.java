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

import com.github.jomnipod.BloodGlucoseUnits;
import com.github.jomnipod.HistoryLogRecord;
import com.github.jomnipod.IBFRecord.Iterator;
import com.github.jomnipod.InsulinUnits;

import lombok.ToString;

@ToString
public class SuggestedCalcLogRecordDetails extends HistoryLogRecord {

	private InsulinUnits correctionDelivered;
	private InsulinUnits carbBolusDelivered;
	private InsulinUnits correctionProgrammed;
	private InsulinUnits carbBolusProgrammed;
	private InsulinUnits correctionSuggested;
	private InsulinUnits carbBolusSuggested;
	private BloodGlucoseUnits currentBg;
	private BloodGlucoseUnits targetBg;

	public SuggestedCalcLogRecordDetails(EnumSet<Flag> flags, Iterator iterator) {
		super(flags);

		correctionDelivered = new InsulinUnits(iterator.nextUnsignedLEInteger());
		carbBolusDelivered = new InsulinUnits(iterator.nextUnsignedLEInteger());
		correctionProgrammed = new InsulinUnits(iterator.nextUnsignedLEInteger());
		carbBolusProgrammed = new InsulinUnits(iterator.nextUnsignedLEInteger());
		correctionSuggested = new InsulinUnits(iterator.nextSignedLEInteger());
		carbBolusSuggested = new InsulinUnits(iterator.nextUnsignedLEInteger());
		long correctionJob = iterator.nextUnsignedLEInteger();
		long mealJob = iterator.nextUnsignedLEInteger();
		long correctionFactorUsed = iterator.nextUnsignedLEShort();
		currentBg = new BloodGlucoseUnits(iterator.nextUnsignedLEShort());
		targetBg = new BloodGlucoseUnits(iterator.nextUnsignedLEShort());
		long bgCorrectionThreshold = iterator.nextUnsignedLEShort();
		long carbGrams = iterator.nextUnsignedLEShort();
		long icRatioUsed = iterator.nextUnsignedLEShort();
	}

	public InsulinUnits correctionDelivered() {
		return correctionDelivered;
	}

	public InsulinUnits carbBolusDelivered() {
		return carbBolusDelivered;
	}

	public InsulinUnits correctionProgrammed() {
		return correctionProgrammed;
	}

	public InsulinUnits correctionSuggested() {
		return correctionSuggested;
	}

	public InsulinUnits carbBolusSuggested() {
		return carbBolusSuggested;
	}

	public InsulinUnits carbBolusProgrammed() {
		return carbBolusProgrammed;
	}

	public BloodGlucoseUnits currentBg() {
		return currentBg;
	}

	public BloodGlucoseUnits targetBg() {
		return targetBg;
	}
}
