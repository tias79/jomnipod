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

public enum ProfileType {

	CARB_RATIO(11, "carbRatio", "IC Ratio", false, "amount", "value"), INSULIN_SENSITIVITY(12, "insulinSensitivity",
			"Correction", false, "amount",
			"value"), BG_TARGET(13, "bgTarget", "Target BG", false, "low", "value"), BG_THRESHOLD(14, "bgThreshold",
					"BG Threshold", false, "amount", "value"), BASAL_PROFILE_0(15, "basalprofile0", "Basal Profile 0",
							true, "rate", "units"), BASAL_PROFILE_1(16, "basalprofile1", "Basal Profile 1", true,
									"rate", "units"), BASAL_PROFILE_2(17, "basalprofile2", "Basal Profile 2", true,
											"rate", "units"), BASAL_PROFILE_3(18, "basalprofile3", "Basal Profile 3",
													true, "rate", "units"), BASAL_PROFILE_4(19, "basalprofile4",
															"Basal Profile 4", true, "rate",
															"units"), BASAL_PROFILE_5(20, "basalprofile5",
																	"Basal Profile 5", true, "rate",
																	"units"), BASAL_PROFILE_6(21, "basalprofile6",
																			"Basal Profile 6", true, "rate", "units");

	private int index;

	private String name;

	private String mfrName;

	private boolean basal = false;

	private String keyName;

	private String valueName;

	private ProfileType(int value, String name, String mfrName, boolean basal, String keyName, String valueName) {
		this.index = value;
		this.name = name;
		this.mfrName = mfrName;
		this.basal = basal;
		this.keyName = keyName;
		this.valueName = valueName;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public String getMfrName() {
		return mfrName;
	}

	public boolean isBasal() {
		return basal;
	}

	public String getKeyName() {
		return keyName;
	}

	public String getValueName() {
		return valueName;
	}

	public static ProfileType getProfileByIndex(int index) {
		ProfileType[] values = values();
		for (ProfileType profile : values) {
			if (profile.index == index) {
				return profile;
			}
		}

		return null;
	}
}
