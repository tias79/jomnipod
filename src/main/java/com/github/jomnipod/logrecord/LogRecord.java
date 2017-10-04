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

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.function.Consumer;

import com.github.jomnipod.DateTime;
import com.github.jomnipod.HistoryLogRecord;
import com.github.jomnipod.IBFDateTime;
import com.github.jomnipod.IBFRecord;
import com.github.jomnipod.IBFRecord.Iterator;

public class LogRecord {

	public enum Type {

		HISTORY(0x03), PUMP_ALARM(0x05), DELETED(0x80000000), IGNORE(0x100);

		private long id;

		private Type(long id) {
			this.id = id;
		}

		public long getValue() {
			return id;
		}

		public static Type valueOf(int id) {
			Type[] values = values();
			for (Type type : values) {
				if (type.id == id) {
					return type;
				}
			}

			throw new IllegalStateException("Unknown log record type: " + id);
		}

	}

	public enum Error {
		NO_ERR(0), GET_EEPROM_ERR(3), CRC_ERR(4), LOG_INDEX_ERR(6), REC_SIZE_ERR(8);

		private int id;

		private Error(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public static Error valueOf(long id) {
			for (Error error : values()) {
				if (error.id == id) {
					return error;
				}
			}

			throw new IllegalStateException("Unknown log record error: " + id);
		}
	}

	private DateTime timestamp;

	private Long secondsSincePowerup;

	private Error error;

	private Consumer<LogRecordVisitor> consumer;

	public LogRecord(IBFRecord record) {
		Iterator iterator = record.iterator();

		int logTypeId = iterator.nextByte();
		com.github.jomnipod.logrecord.LogRecord.Type type = LogRecord.Type.valueOf(logTypeId);

		int logIndex = iterator.nextSignedBEInteger();
		int recordSize = iterator.nextUnsignedBEShort();

		int errorCode = iterator.nextUnsignedBEShort();
		error = Error.valueOf(errorCode);

		timestamp = new IBFDateTime(iterator);

		iterator.skipBytes(1);

		secondsSincePowerup = iterator.nextUnsignedLEInteger();

		if (type == LogRecord.Type.HISTORY) {
			parseHistoryLogRecord(iterator);
		} else if (type == LogRecord.Type.PUMP_ALARM) {
			consumer = v -> v.visit(new PumpAlarmDetails(iterator));
		} else if (type == LogRecord.Type.DELETED) {
			consumer = v -> v.visit(new DeletedLogRecordDetails());
		} else if (type == LogRecord.Type.IGNORE) {
			consumer = v -> v.visit(new IgnoreLogRecordDetails());
		} else {
			throw new IllegalStateException("Unknown Log record type encountered: " + logTypeId);
		}
	}

	public LocalDateTime timestamp() {
		return timestamp.toLocalDateTime();
	}

	public long secondsSincePowerup() {
		return secondsSincePowerup;
	}

	public Error error() {
		return error;
	}

	private void parseHistoryLogRecord(Iterator iterator) {
		HistoryLogRecord.Type type = HistoryLogRecord.Type.valueOf(iterator.nextSignedLEInteger());
		EnumSet<HistoryLogRecord.Flag> flags = HistoryLogRecord.Flag.valueOf(iterator.nextUnsignedLEShort());

		iterator.skipBytes(2);

		switch (type) {
		case END_MARKER:
			consumer = v -> v.visit(new EndMarkerLogRecordDetails(flags));
			return;
		case DEACTIVATE:
			consumer = v -> v.visit(new DeactivateLogRecordDetails(flags));
			return;
		case TIME_CHANGE:
			consumer = v -> v.visit(new TimeChangeLogRecordDetails(flags, iterator));
			return;
		case BOLUS:
			consumer = v -> v.visit(new BolusLogRecordDetails(flags, iterator));
			return;
		case BASAL_RATE:
			consumer = v -> v.visit(new BasalLogRecordDetails(flags, iterator));
			return;
		case SUSPEND:
			consumer = v -> v.visit(new SuspendLogRecordDetails(flags));
			return;
		case DATE_CHANGE:
			consumer = v -> v.visit(new DateChangeLogRecordDetails(flags, iterator));
			return;
		case SUGGESTED_CALC:
			consumer = v -> v.visit(new SuggestedCalcLogRecordDetails(flags, iterator));
			return;
		case REMOTE_HAZARD_ALARM:
			consumer = v -> v.visit(new RemoteHazardAlarmLogRecordDetails(flags, iterator));
			return;
		case ALARM:
			consumer = v -> v.visit(new AlarmLogRecordDetails(flags, iterator));
			return;
		case BLOOD_GLUCOSE:
			consumer = v -> v.visit(new BloodGlucoseLogRecordDetails(flags, iterator));
			return;
		case CARB:
			consumer = v -> v.visit(new CarbLogRecordDetails(flags, iterator));
			return;
		case TERMINATE_BOLUS:
			consumer = v -> v.visit(new TerminateBolusLogRecordDetails(flags, iterator));
			return;
		case TERMINATE_BASAL:
			consumer = v -> v.visit(new TerminateBasalLogRecordDetails(flags, iterator));
			return;
		case ACTIVATE:
			consumer = v -> v.visit(new ActivateLogRecordDetails(flags, iterator));
			return;
		case RESUME:
			consumer = v -> v.visit(new ResumeLogRecordDetails(flags));
			return;
		case DOWNLOAD:
			consumer = v -> v.visit(new DownloadLogRecordDetails(flags));
			return;
		case OCCLUSION:
			consumer = v -> v.visit(new OcclusionLogRecordDetails(flags));
			return;
		default:
			consumer = v -> v.visit(new UnknownLogRecordDetails(flags));
			return;
		}
	}

	public void accept(LogRecordVisitor visitor) {
		consumer.accept(visitor);
	}
}
