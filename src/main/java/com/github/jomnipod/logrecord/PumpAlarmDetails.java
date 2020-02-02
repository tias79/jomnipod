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

import com.github.jomnipod.DateTime;
import com.github.jomnipod.IBFDateTime;
import com.github.jomnipod.IBFRecord.Iterator;
import com.github.jomnipod.IBFVersion;
import com.github.jomnipod.Version;

import lombok.ToString;

@ToString
public class PumpAlarmDetails implements LogRecordDetails {

	enum AlarmType {
		PDM_ERROR0(0, "PDM error", null), PDM_ERROR1(1, "PDM error", null), PDM_ERROR2(2, "PDM error",
				null), PDM_ERROR3(3, "PDM error", null), PDM_ERROR4(4, "PDM error", null), PDM_ERROR5(5, "PDM error",
						null), PDM_ERROR6(6, "PDM error", null), PDM_ERROR7(7, "PDM error", null), PDM_ERROR8(8,
								"PDM error", null), PDM_ERROR9(9, "PDM error", null), SYSTEM_ERROR10(10, "system error",
										false), UNKNOWN11(11, "Unknown alarm type", null), SYSTEM_ERROR12(12,
												"system error",
												null), HAZ_REMOTE(13, "clock reset alarm", false), HAZ_PUMP_VOL(14,
														"empty reservoir", true), HAZ_PUMP_AUTO_OFF(15, "auto-off",
																true), HAZ_PUMP_EXPIRED(16, "pod expired",
																		true), HAZ_PUMP_OCCL(17, "pump site occluded",
																				true), HAZ_PUMP_ACTIVATE(18,
																						"pod is a lump of coal",
																						false), UNKNOWN19(19,
																								"Unknown alarm type",
																								null), UNKNOWN20(20,
																										"Unknown alarm type",
																										null), ADV_KEY(
																												21,
																												"PDM stuck key detected",
																												false), UNKNOWN22(
																														22,
																														"Unknown alarm type",
																														null), ADV_PUMP_VOL(
																																23,
																																"low reservoir",
																																false), ADV_PUMP_AUTO_OFF(
																																		24,
																																		"15 minutes to auto-off warning",
																																		false), ADV_PUMP_SUSPEND(
																																				25,
																																				"suspend done",
																																				false), ADV_PUMP_EXP1(
																																						26,
																																						"pod expiration advisory",
																																						false), ADV_PUMP_EXP2(
																																								27,
																																								"pod expiration alert",
																																								false), SYSTEM_ERROR28(
																																										28,
																																										"system error",
																																										null), EXP_WARNING(
																																												37,
																																												"pod expiration advisory",
																																												false), HAZ_PDM_AUTO_OFF(
																																														39,
																																														"auto-off",
																																														true);
		private long id;
		private String explanation;
		private Boolean stopsDelivery;

		private AlarmType(long id, String explanation, Boolean stopsDelivery) {
			this.id = id;
			this.explanation = explanation;
			this.stopsDelivery = stopsDelivery;
		}

		public long getId() {
			return id;
		}

		public String getExplanation() {
			return explanation;
		}

		public Boolean isStopsDelivery() {
			return stopsDelivery;
		}

		public static AlarmType valueOf(long id) {
			for (AlarmType type : values()) {
				if (type.id == id) {
					return type;
				}
			}

			throw new IllegalStateException("Unknown alarm type: " + id);
		}
	}

	private DateTime timestamp;

	private AlarmType alarm;

	public PumpAlarmDetails(Iterator iterator) {
		timestamp = new IBFDateTime(iterator);

		iterator.skipBytes(1);
		alarm = AlarmType.valueOf(iterator.nextUnsignedLEShort());
		iterator.skipBytes(1);
		int alarmErrorCode = iterator.nextByte();
		long lotNumber = iterator.nextUnsignedLEInteger();
		long seqNumber = iterator.nextUnsignedLEInteger();

		Version processorVersion = new Version(iterator.nextByte().intValue(),
				iterator.nextByte().intValue(),
				iterator.nextByte().intValue());

		Version interlockVersion = new Version(iterator.nextByte().intValue(),
				iterator.nextByte().intValue(),
				iterator.nextByte().intValue());
	}

	public LocalDateTime timestamp() {
		return timestamp.toLocalDateTime();
	}

	public AlarmType alarmType() {
		return alarm;
	}
}
