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

import java.util.EnumSet;

import com.github.jomnipod.logrecord.LogRecordDetails;

public abstract class HistoryLogRecord implements LogRecordDetails {

	public enum Type {
		END_MARKER(0x0000), DEACTIVATE(0x0001), TIME_CHANGE(0x0002), BOLUS(0x0004), BASAL_RATE(0x0008), SUSPEND(
				0x0010), DATE_CHANGE(0x0020), SUGGESTED_CALC(0x0040), REMOTE_HAZARD_ALARM(0x0080), ALARM(
						0x0400), BLOOD_GLUCOSE(0x0800), CARB(0x1000), TERMINATE_BOLUS(0x2000), TERMINATE_BASAL(
								0x4000), ACTIVATE(0x8000), RESUME(0x10000), DOWNLOAD(0x20000), OCCLUSION(0x40000);

		private long id;

		private Type(long id) {
			this.id = id;
		}

		public static Type valueOf(long id) {
			for (Type type : values()) {
				if (type.id == id) {
					return type;
				}
			}

			throw new IllegalStateException("Unknown history log record type: " + id);
		}
	}

	public enum Flag {
		CARRY_OVER(0x01), NEW_DAY(0x02), IN_PROGRESS(0x04), END_DAY(0x08), UNCOMFIRMED(0x10), REVERSE_CORR(
				0x0100), MAX_BOLUS(0x0200), ERROR(0x80000000);

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

	private EnumSet<Flag> flags;

	public HistoryLogRecord(EnumSet<Flag> flags) {
		this.flags = flags;
	}

	public EnumSet<Flag> flags() {
		return EnumSet.copyOf(flags);
	}

}
