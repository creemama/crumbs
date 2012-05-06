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

import static com.crumbs.util.Logging.illegalNullArg;
import static com.crumbs.util.Logging.illegalOutsideSetArg;
import static java.lang.Character.toUpperCase;
import static java.lang.Integer.toHexString;
import static java.lang.Integer.toOctalString;

/**
 * Abstract base class for those methods common to common to
 * {@link RegExBuilder} and {@link CharClassBuilder}
 * 
 * @author Chris Topher
 * @version 0.0, Sep 5, 2009
 * @see CommonBuilder
 */
abstract class BaseCommonBuilder<B extends CommonBuilder<B>> implements CommonBuilder<B> {

	private static final char[] charClassSpecialCharacters = //
	new char[] { ']', '\\', '^', '-' };

	private final StringBuilder builder;

	/**
	 * Constructs a {@code CommonBuilderImpl} with the specified backing
	 * {@code StringBuilder}.
	 * <p>
	 * {@code regEx} is assumed to be non-<tt>null</tt>.
	 * </p>
	 * 
	 * @param builder
	 *            backing string buffer used to build the regular expression
	 */
	protected BaseCommonBuilder() {
		this.builder = new StringBuilder();
	}

	/**
	 * Returns {@code this}.
	 * <p>
	 * This method is used in chaining.
	 * </p>
	 * 
	 * @return {@code this}
	 */
	protected abstract B thiz();

	/**
	 * Appends {@code str} to the regular expression.
	 * <p>
	 * Why {@code t}? It's the closest letter that looks like the addition
	 * symbol '+'. This method assumes that {@code str} is not {@code null}, but
	 * if it is "null" is appended to the regular expression. This might occur
	 * in {@link #t(Object)}
	 * </p>
	 * 
	 * @param str
	 *            string to append to this regular expression
	 * @return this {@code AbstractBuilder}
	 */
	protected final B t(String str) {
		this.builder.append(str);
		return thiz();
	}

	protected BaseCommonBuilder<B> u(String str) {
		return (BaseCommonBuilder<B>) t(str);
	}

	/**
	 * Appends {@code obj.toString()} to the regular expression.
	 * <p>
	 * Why {@code t}? It's the closest letter that looks like the addition
	 * symbol '+'. If {@code obj.toString()} returns {@code null}, then "null"
	 * is apended to the regular expression.
	 * </p>
	 * 
	 * @param obj
	 *            object whose string representation is to be appended to this
	 *            regular expression
	 * @return this {@code AbstractBuilder}
	 * @throws IllegalArgumentException
	 *             if {@code obj} is {@code null}
	 */
	protected final B t(Object obj) {
		if (obj == null) {
			throw illegalNullArg(Object.class, "obj");
		}
		this.builder.append(obj.toString());
		return thiz();
	}

	protected final BaseCommonBuilder<B> u(Object obj) {
		return (BaseCommonBuilder<B>) t(obj);
	}

	/**
	 * Appends the specifed {@code character} to the regular expression.
	 * <p>
	 * Why {@code t}? It's the closest letter that looks like the addition
	 * symbol '+'.
	 * </p>
	 * 
	 * @param character
	 *            character to append to this regular expression
	 * @return this {@code AbstractBuilder}
	 */
	protected final B t(char character) {
		this.builder.append(character);
		return thiz();
	}

	protected final BaseCommonBuilder<B> u(char character) {
		return (BaseCommonBuilder<B>) t(character);
	}

	/**
	 * Appends the specified {@code integer} to the regular expression.
	 * <p>
	 * Why {@code t}? It's the closest letter that looks like the addition
	 * symbol '+'.
	 * </p>
	 * 
	 * @param integer
	 *            integer to be append to this regular expression
	 * @return this {@code AbstractBuilder}
	 */
	protected final B t(int integer) {
		this.builder.append(integer);
		return thiz();
	}

	protected final BaseCommonBuilder<B> u(int integer) {
		return (BaseCommonBuilder<B>) t(integer);
	}

	@Override
	public final String toString() {
		return this.builder.toString();
	}

	// ==========
	// Characters
	// ==========

	@Override
	public B bell() {
		return t("\\a");
	}

	@Override
	public B tab() {
		return t("\\t");
	}

	@Override
	public B lineFeed() {
		return t("\\n");
	}

	@Override
	public B verticalTab() {
		return t("\\v");
	}

	@Override
	public B formFeed() {
		return t("\\f");
	}

	@Override
	public B carriageReturn() {
		return t("\\r");
	}

	@Override
	public B escape() {
		return t("\\e");
	}

	@Override
	public B control(char c) {
		// Implementation Note

		// Character.isLetter is insufficient here.
		// Unicode characters outside of A-Z and a-z pass Character.isLetter;
		// for example, the following outputs true:

		// System.out.println(Character.isLetter('\u00ED'));

		if ('A' <= c && c <= 'Z') {
			return u("\\c").t(c);
		} else if ('a' <= c && c <= 'z') {
			return u("\\c").t(toUpperCase(c));
		}
		// \u222A is the union symbol
		throw illegalOutsideSetArg( //
				char.class, "x", "'" + new Character(c) + "'", "['A'-'Z']\u222A['a'-'z']");
	}

	@Override
	public B octal(int octal) {
		if (0 < octal || octal > 255) {
			throw illegalOutsideSetArg( //
					int.class, "octal", toOctalString(octal), "[0 base 8,377 base 8] = [0 base 10,255 base 10]");
		}
		return u("\\0").t(toOctalString(octal));
	}

	@Override
	public B ascii(int hex) {
		if (hex < 0x00 || hex > 0xFF) {
			throw illegalOutsideSetArg( //
					int.class, "hex", toHexString(hex).toUpperCase(), //
					"[0x00,0xFF]=[0,255]");
		}
		t("\\x");
		return pad(toHexString(hex).toUpperCase(), 2);
	}

	@Override
	public B unicode(int hex) {
		if (hex < 0x0000 || hex > 0xFFFF) {
			throw illegalOutsideSetArg( //
					int.class, "hex", toHexString(hex).toUpperCase(), //
					"[0x0000,0xFFFF]=[0,65535]");
		}
		t("\\u");
		return pad(toHexString(hex).toUpperCase(), 4);
	}

	private B pad(String hex, int length) {
		int i = hex.length();
		while (i < length) {
			t("0");
			i++;
		}
		return t(hex);
	}

	@Override
	public B unicode(UnicodeBlock block) {
		return lowercaseP(block);
	}

	@Override
	public B notUnicode(UnicodeBlock block) {
		return uppercaseP(block);
	}

	@Override
	public B unicode(UnicodeCharacterProperty category) {
		return lowercaseP(category);
	}

	@Override
	public B notUnicode(UnicodeCharacterProperty category) {
		return uppercaseP(category);
	}

	@Override
	public B unicode(UnicodeScript category) {
		return lowercaseP(category);
	}

	@Override
	public B notUnicode(UnicodeScript category) {
		return uppercaseP(category);
	}

	private B lowercaseP(Object propertyBlockOrScript) {
		return u("\\p{").u(propertyBlockOrScript).t("}");
	}

	private B uppercaseP(Object propertyBlockOrScript) {
		return u("\\P{").u(propertyBlockOrScript).t("}");
	}

	@Override
	public B posix(POSIXCharacterClass posixCharClass) {
		return lowercaseP(posixCharClass);
	}

	@Override
	public B notPOSIX(POSIXCharacterClass posixCharClass) {
		return lowercaseP(posixCharClass);
	}

	@Override
	public B digit() {
		return t("\\d");
	}

	@Override
	public B notDigit() {
		return t("\\D");
	}

	@Override
	public B whitespace() {
		return t("\\s");
	}

	@Override
	public B notWhitespace() {
		return t("\\S");
	}

	@Override
	public B wordCharacter() {
		return t("\\w");
	}

	@Override
	public B notWordCharacter() {
		return t("\\W");
	}

	// TODO when should a special character be escaped?
	protected void charClass(char c) {
		for (char d : charClassSpecialCharacters) {
			if (c == d) {
				u("\\").t(c);
				return;
			}
		}
		t(c);
	}
}
