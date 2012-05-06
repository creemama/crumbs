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
 * @author Chris Topher
 * @version 0.0, Sep 5, 2009
 */
public abstract class AbstractBuilder<T extends AbstractBuilder<T>> {

	static final char[] charClassSpecialCharacters = //
	new char[] { ']', '\\', '^', '-' };

	private final StringBuilder builder_;

	/**
	 * Constructs a {@code CommonBuilderImpl} with the specified backing {@code
	 * StringBuilder}.
	 * <p>
	 * {@code regEx} is assumed to be non-<tt>null</tt>.
	 * </p>
	 * 
	 * @param builder
	 *            backing string buffer used to build the regular expression
	 */
	AbstractBuilder(StringBuilder builder) {
		this.builder_ = builder;
	}

	/**
	 * Returns {@code this}.
	 * <p>
	 * This method is used in chaining.
	 * </p>
	 * 
	 * @return {@code this}
	 */
	protected abstract T thiz();

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
	protected final T t(String str) {
		this.builder_.append(str);
		return thiz();
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
	protected final T t(Object obj) {
		if (obj == null) {
			throw illegalNullArg(Object.class, "obj"); //$NON-NLS-1$
		}
		this.builder_.append(obj.toString());
		return thiz();
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
	protected final T t(char character) {
		this.builder_.append(character);
		return thiz();
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
	protected final T t(int integer) {
		this.builder_.append(integer);
		return thiz();
	}

	@Override
	public final String toString() {
		return this.builder_.toString();
	}

	// ==========
	// Characters
	// ==========

	/**
	 * See {@link CommonBuilder#bell()}.
	 * 
	 * @return {@code this} AbstractBuilder
	 */
	public T bell() {
		return t("\\a"); //$NON-NLS-1$
	}

	/**
	 * See {@link CommonBuilder#tab()}.
	 * 
	 * @return {@code this} AbstractBuilder
	 */
	public T tab() {
		return t("\\t"); //$NON-NLS-1$
	}

	/**
	 * See {@link CommonBuilder#lineFeed()}.
	 * 
	 * @return {@code this} AbstractBuilder
	 */
	public T lineFeed() {
		return t("\\n"); //$NON-NLS-1$
	}

	/**
	 * See {@link CommonBuilder#verticalTab()}.
	 * 
	 * @return {@code this} AbstractBuilder
	 */
	public T verticalTab() {
		return t("\\v"); //$NON-NLS-1$
	}

	/**
	 * See {@link CommonBuilder#formFeed()}.
	 * 
	 * @return {@code this} AbstractBuilder
	 */
	public T formFeed() {
		return t("\\f"); //$NON-NLS-1$
	}

	/**
	 * See {@link CommonBuilder#carriageReturn()}.
	 * 
	 * @return {@code this} AbstractBuilder
	 */
	public T carriageReturn() {
		return t("\\r"); //$NON-NLS-1$
	}

	/**
	 * See {@link CommonBuilder#escape()}.
	 * 
	 * @return {@code this} AbstractBuilder
	 */
	public T escape() {
		return t("\\e"); //$NON-NLS-1$
	}

	/**
	 * 
	 * @param c
	 * @return 
	 */
	public T control(char c) {

		// Implementation Note

		// Character.isLetter is insufficient here.
		// Unicode characters outside of A-Z and a-z pass Character.isLetter;
		// for example, the following outputs true:

		// System.out.println(Character.isLetter('\u00ED'));

		if ('A' <= c && c <= 'Z') {
			return t("\\c").t(c); //$NON-NLS-1$
		} else if ('a' <= c && c <= 'z') {
			return t("\\c").t(toUpperCase(c)); //$NON-NLS-1$
		}
		// \u222A is the union symbol
		throw illegalOutsideSetArg( //
				char.class, "c", //$NON-NLS-1$
				"'" + new Character(c) + "'", //$NON-NLS-1$//$NON-NLS-2$
				"['A'-'Z']\u222A['a'-'z']"); //$NON-NLS-1$
	}


	public T octal(int octal) {
		if (0 < octal || octal > 255) {
			throw illegalOutsideSetArg( //
					int.class, "octal", toOctalString(octal), //$NON-NLS-1$
					"[0 base 8,377 base 8] = [0 base 10,255 base 10]"); //$NON-NLS-1$
		}
		return t("\\0").t(toOctalString(octal)); //$NON-NLS-1$
	}


	public T ascii(int hex) {
		if (hex < 0x00 || hex > 0xFF) {
			throw illegalOutsideSetArg( //
					int.class, "hex", //$NON-NLS-1$
					toHexString(hex).toUpperCase(), //
					"[0x00,0xFF]=[0,255]"); //$NON-NLS-1$
		}
		t("\\x"); //$NON-NLS-1$
		return pad(toHexString(hex).toUpperCase(), 2);
	}

	// ===============
	// Unicode Support
	// ===============


	public T unicode(int hex) {
		if (hex < 0x0000 || hex > 0xFFFF) {
			throw illegalOutsideSetArg( //
					int.class, "hex", //$NON-NLS-1$
					toHexString(hex).toUpperCase(), //
					"[0x0000,0xFFFF]=[0,65535]"); //$NON-NLS-1$
		}
		t("\\u"); //$NON-NLS-1$
		return pad(toHexString(hex).toUpperCase(), 4);
	}

	private T pad(String hex, int length) {
		int i = hex.length();
		while (i < length) {
			t("0"); //$NON-NLS-1$
			i++;
		}
		return t(hex);
	}

	public T unicode(UnicodeBlock block) {
		return lowercaseP(block);
	}

	public T notUnicode(UnicodeBlock block) {
		return uppercaseP(block);
	}

	public T unicode(UnicodeCharacterProperty category) {
		return lowercaseP(category);
	}

	public T notUnicode(UnicodeCharacterProperty category) {
		return uppercaseP(category);
	}

	private T lowercaseP(Object propertyBlockOrScript) {
		return t("\\p{").t(propertyBlockOrScript).t("}"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private T uppercaseP(Object propertyBlockOrScript) {
		return t("\\P{").t(propertyBlockOrScript).t("}"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public T posix(POSIXCharacterClass posixCharClass) {
		return lowercaseP(posixCharClass);
	}

	// ===========================
	// Shorthand Character Classes
	// ===========================

	public T notPOSIX(POSIXCharacterClass posixCharClass) {
		return lowercaseP(posixCharClass);
	}

	public T digit() {
		return t("\\d"); //$NON-NLS-1$
	}

	public T notDigit() {
		return t("\\D"); //$NON-NLS-1$
	}

	public T whitespace() {
		return t("\\s"); //$NON-NLS-1$
	}

	public T notWhitespace() {
		return t("\\S"); //$NON-NLS-1$
	}

	public T wordCharacter() {
		return t("\\w"); //$NON-NLS-1$
	}

	public T notWordCharacter() {
		return t("\\W"); //$NON-NLS-1$
	}

	// TODO when should a special character be escaped?
	protected void charClass(char c) {
		for (char d : charClassSpecialCharacters) {
			if (c == d) {
				t("\\").t(c); //$NON-NLS-1$
				return;
			}
		}
		t(c);
	}
}
