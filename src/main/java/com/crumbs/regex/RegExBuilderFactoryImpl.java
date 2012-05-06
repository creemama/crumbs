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
 * @author Chris Topher
 * @version 0.0, 07/20/2009
 */
class RegExBuilderFactoryImpl implements RegExBuilderFactory {

	private RegExBuilderImpl regEx() {
		return new RegExBuilderImpl(new StringBuilder());
	}

	public RegExBuilder regEx(byte obj) {
		return regEx().regEx(obj);
	}

	public RegExBuilder regEx(short obj) {
		return regEx().regEx(obj);
	}

	public RegExBuilder regEx(int obj) {
		return regEx().regEx(obj);
	}

	public RegExBuilder regEx(long obj) {
		return regEx().regEx(obj);
	}

	public RegExBuilder regEx(float obj) {
		return regEx().regEx(obj);
	}

	public RegExBuilder regEx(double obj) {
		return regEx().regEx(obj);
	}

	public RegExBuilder regEx(boolean obj) {
		return regEx().regEx(obj);
	}

	public RegExBuilder regEx(char obj) {
		return regEx().regEx(obj);
	}

	public RegExBuilder quote(Object obj) {
		return regEx().quote(obj);
	}

	public RegExBuilder rangeClass(char min, char max) {
		return regEx().rangeClass(min, max);
	}

	public RegExBuilder charClass(char c0, char c1, char... classes) {
		return regEx().charClass(c0, c1, classes);
	}

	public RegExBuilder charClass(CharClassBuilder innard) {
		return regEx().charClass(innard);
	}

	public RegExBuilder optional(RegExBuilder expression,
			RegExBuilder... expressions) {
		return regEx().optional(expression, expressions);
	}

	public RegExBuilder anyChar() {
		return regEx().anyChar();
	}

	public RegExBuilder matchLineStart() {
		return regEx().matchLineStart();
	}

	public RegExBuilder matchLineEnd() {
		return regEx().matchLineEnd();
	}

	public RegExBuilder matchInputStart() {
		return regEx().matchInputStart();
	}

	public RegExBuilder matchInputEndStrict() {
		return regEx().matchInputEndStrict();
	}

	public RegExBuilder matchInputEnd() {
		return regEx().matchInputEnd();
	}

	public RegExBuilder wordBoundary() {
		return regEx().wordBoundary();
	}

	public RegExBuilder notWordBoundary() {
		return regEx().notWordBoundary();
	}

	public RegExBuilder previousMatchEnd() {
		return regEx().previousMatchEnd();
	}

	// ==========
	// Characters
	// ==========

	public RegExBuilder bell() {
		return regEx().bell();
	}

	public RegExBuilder tab() {
		return regEx().tab();
	}

	public RegExBuilder lineFeed() {
		return regEx().lineFeed();
	}

	public RegExBuilder verticalTab() {
		return regEx().verticalTab();
	}

	public RegExBuilder formFeed() {
		return regEx().formFeed();
	}

	public RegExBuilder carriageReturn() {
		return regEx().carriageReturn();
	}

	public RegExBuilder escape() {
		return regEx().escape();
	}

	public RegExBuilder control(char c) {
		return regEx().control(c);
	}

	public RegExBuilder ascii(int hex) {
		return regEx().ascii(hex);
	}

	public RegExBuilder unicode(int hex) {
		return regEx().unicode(hex);
	}

	// ===========================
	// Shorthand Character Classes
	// ===========================

	public RegExBuilder digit() {
		return regEx().digit();
	}

	public RegExBuilder notDigit() {
		return regEx().notDigit();
	}

	public RegExBuilder whitespace() {
		return regEx().whitespace();
	}

	public RegExBuilder notWhitespace() {
		return regEx().notWhitespace();
	}

	public RegExBuilder wordCharacter() {
		return regEx().wordCharacter();
	}

	public RegExBuilder notWordCharacter() {
		return regEx().notWordCharacter();
	}

	public RegExBuilder group(RegExBuilder expression,
			RegExBuilder... expressions) {
		return regEx().group(expression, expressions);
	}

	public RegExBuilder noCaptureGroup(RegExBuilder expression,
			RegExBuilder... expressions) {
		return regEx().noCaptureGroup(expression, expressions);
	}

	public RegExBuilder atomicGroup(RegExBuilder expression,
			RegExBuilder... expressions) {
		return regEx().atomicGroup(expression, expressions);
	}

	public RegExBuilder backReference(int group) {
		return regEx().backReference(group);
	}

	public RegExBuilder comment(String str) {
		return regEx().comment(str);
	}

	public RegExBuilder noCaptureGroup(RegExBuilder regEx, RegExMatchFlag flag,
			RegExMatchFlag... flagN) {
		return regEx().noCaptureGroup(regEx, flag, flagN);
	}

	public RegExBuilder positiveLookahead(RegExBuilder lookahead,
			RegExBuilder... other) {
		return regEx().positiveLookahead(lookahead, other);
	}

	public RegExBuilder negativeLookahead(RegExBuilder lookahead,
			RegExBuilder... other) {
		return regEx().negativeLookahead(lookahead, other);
	}

	public RegExBuilder positiveLookbehind(RegExBuilder lookahead,
			RegExBuilder... other) {
		return regEx().positiveLookbehind(lookahead, other);
	}

	public RegExBuilder negativeLookbehind(RegExBuilder lookahead,
			RegExBuilder... other) {
		return regEx().negativeLookbehind(lookahead, other);
	}

	public RegExBuilder unicodeGrapheme() {
		return regEx().unicodeGrapheme();
	}

	@Override
	public RegExBuilder notCharClass(char c0, char c1, char... cN) {
		return regEx().notCharClass(c0, c1, cN);
	}

	@Override
	public RegExBuilder notPOSIX(POSIXCharacterClass posixCharClass) {
		return regEx().notPOSIX(posixCharClass);
	}

	@Override
	public RegExBuilder notRangeClass(char min, char max) {
		return regEx().notRangeClass(min, max);
	}

	@Override
	public RegExBuilder notUnicode(UnicodeBlock block) {
		return regEx().notUnicode(block);
	}

	@Override
	public RegExBuilder notUnicode(UnicodeCharacterProperty category) {
		return regEx().notUnicode(category);
	}

	@Override
	public RegExBuilder octal(int n) {
		return regEx().octal(n);
	}

	@Override
	public RegExBuilder oneOrMore(RegExBuilder regEx, RegExBuilder... regExN) {
		return regEx().oneOrMore(regEx, regExN);
	}

	@Override
	public RegExBuilder oneOrMoreLazy(RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().oneOrMoreLazy(regEx, regExN);
	}

	@Override
	public RegExBuilder oneOrMorePossessive(RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().oneOrMorePossessive(regEx, regExN);
	}

	@Override
	public RegExBuilder optionalLazy(RegExBuilder regEx, RegExBuilder... regExN) {
		return regEx().optionalLazy(regEx, regExN);
	}

	@Override
	public RegExBuilder optionalPossessive(RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().optionalPossessive(regEx, regExN);
	}

	@Override
	public RegExBuilder orNoCaptureGroup(RegExBuilder regEx0,
			RegExBuilder regEx1, RegExBuilder... regExN) {
		return regEx().orNoCaptureGroup(regEx0, regEx1, regExN);
	}

	@Override
	public RegExBuilder posix(POSIXCharacterClass posixCharClass) {
		return regEx().posix(posixCharClass);
	}

	@Override
	public RegExBuilder regEx(Object obj, Object... objN) {
		return regEx().regEx(obj, objN);
	}

	@Override
	public RegExBuilder repeat(int nTimes, RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().repeat(nTimes, regEx, regExN);
	}

	@Override
	public RegExBuilder repeat(int atLeastNTimes, int atMostMTimes,
			RegExBuilder regEx, RegExBuilder... regExN) {
		return regEx().repeat(atLeastNTimes, atMostMTimes, regEx, regExN);
	}

	@Override
	public RegExBuilder repeatAtLeast(int nTimes, RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().repeatAtLeast(nTimes, regEx, regExN);
	}

	@Override
	public RegExBuilder repeatAtLeastPossessive(int nTimes, RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().repeatAtLeastPossessive(nTimes, regEx, regExN);
	}

	@Override
	public RegExBuilder repeatAtLeastLazy(int nTimes, RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().repeatAtLeastLazy(nTimes, regEx, regExN);
	}

	@Override
	public RegExBuilder repeatLazy(int nTimes, RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().repeatLazy(nTimes, regEx, regExN);
	}

	@Override
	public RegExBuilder repeatLazy(int atLeastNTimes, int atMostMTimes,
			RegExBuilder regEx, RegExBuilder... regExN) {
		return regEx().repeatLazy(atLeastNTimes, atMostMTimes, regEx, regExN);
	}

	@Override
	public RegExBuilder repeatPossessive(int nTimes, RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().repeatPossessive(nTimes, regEx, regExN);
	}

	@Override
	public RegExBuilder repeatPossessive(int atLeastNTimes, int atMostMTimes,
			RegExBuilder regEx, RegExBuilder... regExN) {
		return regEx().repeatPossessive(atLeastNTimes, atMostMTimes, regEx,
				regExN);
	}

	@Override
	public RegExBuilder unicode(UnicodeBlock block) {
		return regEx().unicode(block);
	}

	@Override
	public RegExBuilder unicode(UnicodeCharacterProperty category) {
		return regEx().unicode(category);
	}

	@Override
	public RegExBuilder zeroOrMore(RegExBuilder regEx, RegExBuilder... regExN) {
		return regEx().zeroOrMore(regEx, regExN);
	}

	@Override
	public RegExBuilder zeroOrMoreLazy(RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().zeroOrMoreLazy(regEx, regExN);
	}

	@Override
	public RegExBuilder zeroOrMorePossessive(RegExBuilder regEx,
			RegExBuilder... regExN) {
		return regEx().zeroOrMorePossessive(regEx, regExN);
	}

	@Override
	public RegExBuilder turnOnOffMatchFlags(RegExMatchFlag flag,
			RegExMatchFlag... flagN) {
		return regEx().turnOnOffMatchFlags(flag, flagN);
	}

	@Override
	public RegExBuilder regEx(RegExBuilder regEx, RegExBuilder... regExN) {
		return regEx().regEx(regEx, regExN);
	}

	@Override
	public CharClassBuilder charClass() {
		return new CharClassBuilderImpl(new StringBuilder());
	}

	@Override
	public RegExBuilder charClass(String str) {
		return regEx().charClass(str);
	}

	@Override
	public RegExBuilder intersection(CharClassBuilder charClass1,
			CharClassBuilder charClass2) {
		return regEx().intersection(charClass1, charClass2);
	}

	@Override
	public RegExBuilder notCharClass(String str) {
		return regEx().notCharClass(str);
	}

	@Override
	public RegExBuilder union(CharClassBuilder charClass0,
			CharClassBuilder charClass1) {
		return regEx().union(charClass0, charClass1);
	}

	@Override
	public RegExBuilder orGroup(RegExBuilder regEx0, RegExBuilder regEx1,
			RegExBuilder... regExN) {
		return regEx().orGroup(regEx0, regEx1, regExN);
	}
}
