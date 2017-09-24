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

public interface LogRecordVisitor {

	public void visit(ActivateLogRecordDetails details);

	public void visit(EndMarkerLogRecordDetails details);

	public void visit(DeactivateLogRecordDetails details);

	public void visit(TimeChangeLogRecordDetails details);

	public void visit(BolusLogRecordDetails details);

	public void visit(BasalLogRecordDetails details);

	public void visit(SuspendLogRecordDetails details);

	public void visit(DateChangeLogRecordDetails details);

	public void visit(SuggestedCalcLogRecordDetails details);

	public void visit(RemoteHazardAlarmLogRecordDetails details);

	public void visit(AlarmLogRecordDetails details);

	public void visit(BloodGlucoseLogRecordDetails details);

	public void visit(CarbLogRecordDetails details);

	public void visit(TerminateBolusLogRecordDetails details);

	public void visit(TerminateBasalLogRecordDetails details);

	public void visit(ResumeLogRecordDetails details);

	public void visit(DownloadLogRecordDetails details);

	public void visit(OcclusionLogRecordDetails details);

	public void visit(UnknownLogRecordDetails details);

	public void visit(PumpAlarmDetails details);

	public void visit(DeletedLogRecordDetails details);

	public void visit(IgnoreLogRecordDetails details);

}
