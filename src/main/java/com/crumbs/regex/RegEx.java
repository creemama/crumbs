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

/**
 * @author Chris Tohper
 * @version 0.0, 07/20/2009
 */
public class RegEx {
	public static RegExBuilderFactory f = new RegExBuilderFactoryImpl();

	private RegEx() {
		// prevent instantiation
	}

	public static RegExBuilderFactory getInstance() {
		return f;
	}

	public static RegExBuilder regEx(RegExBuilder expression,
			RegExBuilder... expressions) {
		return f.regEx(expression, expressions);
	}

	public static RegExBuilder regEx(String str) {
		return f.regEx(str);
	}

	public static RegExBuilder regEx(int integer) {
		return f.regEx(integer);
	}

	// with the brackets
	public static RegExBuilder rangeClass(char min, char max) {
		return f.rangeClass(min, max);
	}

	public static RegExBuilder charClass(char c0, char c1, char... classes) {
		return f.charClass(c0, c1, classes);
	}

	public static RegExBuilder charClass(CharClassBuilder charClass) {
		return f.charClass(charClass);
	}

	// Quantifiers

	public static RegExBuilder optional(RegExBuilder expression,
			RegExBuilder... expressions) {
		return f.optional(expression, expressions);
	}

	public static RegExBuilder zeroOrMore(RegExBuilder expression) {
		return f.zeroOrMore(expression);
	}

	public static RegExBuilder oneOrMore(RegExBuilder expression) {
		return f.oneOrMore(expression);
	}

	public static RegExBuilder whitespace() {
		return f.whitespace();

	}

	public static RegExBuilder digits() {
		return f.digit();
	}

	public static RegExBuilder group(RegExBuilder expression,
			RegExBuilder... expressions) {
		return f.group(expression, expressions);
	}

	public static RegExBuilder noCaptureGroup(RegExBuilder expression,
			RegExBuilder... expressions) {
		return f.noCaptureGroup(expression, expressions);
	}

	public static CharClassBuilder charClass() {
		return f.charClass();
	}

	public static RegExBuilder orNoCaptureGroup(RegExBuilder regEx0,
			RegExBuilder regEx1, RegExBuilder... regExN) {
		return f.orNoCaptureGroup(regEx0, regEx1, regExN);
	}

	public static RegExBuilder orGroup(RegExBuilder regEx0,
			RegExBuilder regEx1, RegExBuilder... regExN) {
		return f.orGroup(regEx0, regEx1, regExN);
	}

	public static RegExBuilder repeatAtLeast(int nTimes, RegExBuilder regEx,
			RegExBuilder... regExN) {
		return f.repeatAtLeast(nTimes, regEx, regExN);
	}

	public static RegExBuilder repeat(int nTimes, RegExBuilder regEx,
			RegExBuilder... regExN) {
		return f.repeat(nTimes, regEx, regExN);
	}
}
