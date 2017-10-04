package com.github.jomnipod;

import java.time.LocalDate;

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
public class Date implements Comparable<Date> {

	private Integer year;

	private Integer month;

	private Integer day;

	public Date(Integer day, Integer month, Integer year) {
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.github.jomnipod.Date#toString()
	 */
	@Override
	public String toString() {
		return String.format("%04d", year) + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
	}

	@Override
	public int compareTo(Date other) {
		if (!year.equals(other.year)) {
			return year.compareTo(other.year);
		}

		if (!month.equals(other.month)) {
			return month.compareTo(other.month);
		}

		return day.compareTo(other.day);
	}

	public LocalDate toLocalDate() {
		if (year.equals(0) && month.equals(0) && day.equals(0)) {
			return LocalDate.of(0, 1, 1);
		}

		return LocalDate.of(year, month, day);
	}
}
