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

import com.github.jomnipod.IBFRecord.Iterator;

public class EepromSettings {

	private Double bolusIncrementPercent;
	private Double bolusMaxPercent;
	private Double basalMaxPercent;
	private Long bolusIncr;
	private Long bolusMax;
	private Long basalMax;
	private Long lowVol;
	private Byte autoOff;
	private Byte language;
	private Byte expireAlert;
	private Byte bgReminder;
	private Byte confAlert;
	private Byte reminderAlert;
	private Long remoteId;
	private Byte tempBasalType;
	private Byte extBolusType;
	private Byte bolusReminder;
	private Byte bolusCalcs;
	private Byte bolusCalcsReverse;
	private Byte bgDisplay;
	private Byte bgSound;
	private Integer bgMin;
	private Integer bgGoalLow;
	private Integer bgGoalUp;
	private Byte insulinDuration;
	private Byte alarmRepairCount;
	private Long pdmConfig;

	protected EepromSettings(IBFRecord record) throws IOException {

		Iterator iterator = record.iterator();

		iterator.skipBytes(13);
		bolusIncr = iterator.nextUnsignedLEInteger();
		bolusMax = iterator.nextUnsignedLEInteger();
		basalMax = iterator.nextUnsignedLEInteger();
		lowVol = iterator.nextUnsignedLEInteger();
		autoOff = iterator.nextByte();
		language = iterator.nextByte();
		iterator.skipBytes(4);
		expireAlert = iterator.nextByte();
		iterator.skipBytes(5);
		bgReminder = iterator.nextByte();
		iterator.skipBytes(1);
		confAlert = iterator.nextByte();
		reminderAlert = iterator.nextByte();
		iterator.skipBytes(8);
		remoteId = iterator.nextUnsignedLEInteger();
		iterator.skipBytes(19);
		tempBasalType = iterator.nextByte();

		extBolusType = iterator.nextByte();
		bolusReminder = iterator.nextByte();
		bolusCalcs = iterator.nextByte();
		bolusCalcsReverse = iterator.nextByte();
		bgDisplay = iterator.nextByte();
		bgSound = iterator.nextByte();
		bgMin = iterator.nextUnsignedLEShort();
		bgGoalLow = iterator.nextUnsignedLEShort();
		bgGoalUp = iterator.nextUnsignedLEShort();
		insulinDuration = iterator.nextByte();
		iterator.skipBytes(19);
		alarmRepairCount = iterator.nextByte();
		pdmConfig = iterator.nextUnsignedLEInteger();
		iterator.skipBytes(14);

		// basalMaxPercent = bolusIncr / 100;
		// bolusMaxPercent = bolusMax / 100;
		// basalMaxPercent = basalMax / 100;
	}
}
