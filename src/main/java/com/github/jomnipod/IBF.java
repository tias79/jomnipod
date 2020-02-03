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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.jomnipod.logrecord.LogRecord;

public class IBF {

	private IBFVersions versions;

	private PDMVersion pdmVersion;

	private boolean isDash;

	private List<LogRecord> logRecords;

	public IBF(InputStream inputStream) throws IOException {
		versions = new IBFVersions(new IBFRecord(inputStream));
		IBFRecord pdmVersionRecord = new IBFRecord(inputStream);
		pdmVersion = new PDMVersion(pdmVersionRecord.iterator());
		Version dashVersion = new Version(3,0,0);
		if (pdmVersion.compareTo(dashVersion) >= 0) {
			isDash = true;
		}
		IBFRecord manufacturingData = new IBFRecord(inputStream);

		BasalPrograms basalPrograms = new BasalPrograms(new IBFRecord(inputStream));

		EepromSettings eepromSettings = new EepromSettings(new IBFRecord(inputStream));

		Profile profile0 = new Profile(new IBFRecord(inputStream), basalPrograms);
		Profile profile1 = new Profile(new IBFRecord(inputStream), basalPrograms);
		Profile profile2 = new Profile(new IBFRecord(inputStream), basalPrograms);
		Profile profile3 = new Profile(new IBFRecord(inputStream), basalPrograms);
		Profile profile4 = new Profile(new IBFRecord(inputStream), basalPrograms);
		Profile profile5 = new Profile(new IBFRecord(inputStream), basalPrograms);
		Profile profile6 = new Profile(new IBFRecord(inputStream), basalPrograms);
		Profile profile7 = new Profile(new IBFRecord(inputStream), basalPrograms);
		Profile profile8 = new Profile(new IBFRecord(inputStream), basalPrograms);
		Profile profile9 = new Profile(new IBFRecord(inputStream), basalPrograms);
		Profile profile10 = new Profile(new IBFRecord(inputStream), basalPrograms);

		LogDescriptions logDescriptions = new LogDescriptions(new IBFRecord(inputStream));

		List<LogRecord> tmpLogRecords = new ArrayList<>();
		while (inputStream.available() > 0) {
			LogRecord newLogRecord = new LogRecord(new IBFRecord(inputStream));
			tmpLogRecords.add(newLogRecord);
		}

		logRecords = tmpLogRecords;
	}

	public IBFVersions ibfVersions() {
		return versions;
	}

	public PDMVersion pdmVersion(){
		return pdmVersion;
	}

	public List<LogRecord> logRecords() {
		return new ArrayList<>(logRecords);
	}

	public boolean isDash() {
		return isDash;
	}
}
