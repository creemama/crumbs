/**
 * Copyright 2009 Creemama
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.crumbs.regex;

import java.util.regex.Pattern;

/**
 * <a href=
 * "http://regexlib.com/UserPatterns.aspx?authorId=a31a0874-118f-4550-933e-a7c575d149ae"
 * ></a>
 * 
 * <pre>
 * ^(?:(?:(?:0?[13578]|1[02])(\/|-|\.)31)\1|(?:(?:0?[13-9]|1[0-2])(\/|-|\.)(?:29|30)\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$|^(?:0?2(\/|-|\.)29\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:(?:0?[1-9])|(?:1[0-2]))(\/|-|\.)(?:0?[1-9]|1\d|2[0-8])\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$
 * </pre>
 * 
 * @author Chris Topher
 * @version 0.0, May 6, 2012
 */
public class MonthDayYearRegEx extends RegExExample {
	public static void main(String[] args) {
		final MonthDayYearRegEx re = new MonthDayYearRegEx(new JRegExBuilderFactory());

		final RegExBuilder builder = re.create();
		final Pattern pattern = Pattern.compile(builder.toString());
		System.out.println(builder.asCode());
		System.out.println(pattern);
		System.out.println(pattern.matcher("01.1.02").matches());
		System.out.println(pattern.matcher("11-30-2001").matches());
		System.out.println(pattern.matcher("2/29/2000").matches());
		System.out.println(pattern.matcher("02/29/01").matches());
		System.out.println(pattern.matcher("13/01/2002").matches());
		System.out.println(pattern.matcher("11/00/02").matches());

		final MonthDayYearRegEx vim = new MonthDayYearRegEx(new VimRegExBuilderFactory());
		System.out.println(vim.create());
	}

	public MonthDayYearRegEx(RegExBuilderFactory f) {
		super(f);
	}

	public RegExBuilder create() {
		RegExBuilder divider = r().orGroup(r().re('/'), r().re('-'), r().re('.'));
		return r().orNoCaptureGroup(createMonthDayYear0(divider), createMonthDayYear1(divider),
				createMonthDayYear2(divider));
	}

	private RegExBuilder createMonthDayYear0(RegExBuilder divider) {
		// ^(?:(?:(?:0?[13578]|1[02])(\/|-|\.)31)\1|(?:(?:0?[13-9]|1[0-2])(\/|-|\.)(?:29|30)\2))(?:(?:1[6-9]|[2-9]\d)?\d{2})$

		RegExBuilder month0 = r().optional(r().re('0')).charClass("13578");
		RegExBuilder month1 = r().re('1').charClass("02");
		RegExBuilder day0 = r().re("31");
		RegExBuilder monthDay0 = r().noCaptureGroup(r().orNoCaptureGroup(month0, month1).re(divider).re(day0))
				.backReference(1);

		RegExBuilder month2 = r().optional(r().re('0')).charClass(c().c('1').range('3', '9'));
		RegExBuilder month3 = r().re('1').charClass(c().range('0', '2'));
		RegExBuilder day1 = r().orNoCaptureGroup(r().re("29"), r().re("30"));
		RegExBuilder monthDay1 = r().orNoCaptureGroup(month2, month3).re(divider).re(day1).backReference(2);

		RegExBuilder yearA = r().re('1').charClass(c().range('6', '9'));
		RegExBuilder yearB = r().charClass(c().range('2', '9')).digit();
		RegExBuilder year0 = r()
				.noCaptureGroup(r().optional(r().orNoCaptureGroup(yearA, yearB)).repeat(2, r().digit()));

		return r().matchLineStart().orNoCaptureGroup(monthDay0, r().noCaptureGroup(monthDay1)).re(year0).matchLineEnd();
	}

	private RegExBuilder createMonthDayYear1(RegExBuilder divider) {
		// ^(?:0?2(\/|-|\.)29\3(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$

		RegExBuilder monthDay = r().optional(r().re('0')).re('2').re(divider).re("29").backReference(3);
		RegExBuilder yearA = r().re('1').re(r().charClass(c().range('6', '9')));
		RegExBuilder yearB = r().charClass(c().range('2', '9')).digit();
		RegExBuilder yearAB = r().optional(r().orNoCaptureGroup(yearA, yearB));
		RegExBuilder yearC = r().orNoCaptureGroup( //
				r().re('0').charClass("48"), //
				r().charClass("2468").charClass("048"), //
				r().charClass("13579").charClass("26"));
		RegExBuilder year0 = yearAB.re(yearC);

		RegExBuilder year1 = r().orNoCaptureGroup( //
				r().re("16"), //
				r().charClass("2468").charClass("048"), //
				r().charClass("3579").charClass("26") //
				).re("00");

		return r().matchLineStart()
				.noCaptureGroup(monthDay, r().noCaptureGroup(r().orNoCaptureGroup(year0, r().noCaptureGroup(year1))))
				.matchLineEnd();
	}

	private RegExBuilder createMonthDayYear2(RegExBuilder divider) {
		// ^(?:(?:0?[1-9])|(?:1[0-2]))(\/|-|\.)(?:0?[1-9]|1\d|2[0-8])\4(?:(?:1[6-9]|[2-9]\d)?\d{2})$

		RegExBuilder monthAOrDay = r().optional(r().re('0')).charClass(c().range('1', '9'));
		RegExBuilder monthB = r().re('1').charClass(c().range('0', '2'));
		RegExBuilder month = r().orNoCaptureGroup(r().noCaptureGroup(monthAOrDay), r().noCaptureGroup(monthB));

		RegExBuilder day = r().orNoCaptureGroup(monthAOrDay, r().re('1').digit(),
				r().re('2').charClass(c().range('0', '8')));

		RegExBuilder yearA = r().re('1').re(r().charClass(c().range('6', '9')));
		RegExBuilder yearB = r().charClass(c().range('2', '9').digit());
		RegExBuilder yearAB = r().optional(r().orNoCaptureGroup(yearA, yearB));
		RegExBuilder year = yearAB.repeat(2, r().digit());

		return r().matchLineStart().re(month).re(divider).re(day).backReference(4).noCaptureGroup(year).matchLineEnd();
	}
}
